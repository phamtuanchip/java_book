# Chương 32 — Optional và xử lý null an toàn

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu vấn đề `null` gây ra trong code Java, vì sao nó được gọi đùa là "lỗi tỷ đô".
- Dùng `Optional<T>` để biểu diễn tường minh "giá trị có thể không tồn tại".
- Dùng đúng các phương thức của `Optional`: `orElse`, `orElseGet`, `ifPresent`, `map`.
- Biết khi nào nên và không nên dùng `Optional`.

Code mẫu đầy đủ: [`code/ch32-optional/`](../../code/ch32-optional/).

## Vấn đề với `null`

Từ Chương 13, bạn đã biết `null` là giá trị "chưa trỏ tới object nào" của kiểu tham chiếu, và
`NullPointerException` là lỗi runtime cực kỳ phổ biến. Vấn đề cốt lõi: **không có gì trong chữ ký
phương thức cho biết giá trị trả về có thể là `null` hay không**:

```java
String email = diaChiEmail.get("chi"); // co the la null, nhung KHONG CO GI bao truoc dieu do
System.out.println(email.length());     // NullPointerException neu "chi" khong ton tai
```

Người gọi `diaChiEmail.get("chi")` **không có cách nào biết chắc** (chỉ dựa vào chữ ký phương thức)
liệu kết quả có thể `null` hay không — phải đọc tài liệu, hoặc đọc code cài đặt bên trong, hoặc đơn
giản là **quên kiểm tra** và gặp lỗi lúc chạy. `Optional<T>` (Java 8+) giải quyết đúng vấn đề này.

## `Optional<T>` — biểu diễn tường minh "có thể không có giá trị"

```java
Optional<String> optCoGiaTri = Optional.of("gia tri co san");     // BAT BUOC khac null, nem loi ngay neu truyen null
Optional<String> optRong = Optional.empty();                       // KHONG co gia tri
Optional<String> opt = Optional.ofNullable(diaChiEmail.get("chi")); // AN TOAN du gia tri co the null
```

- `Optional.of(giaTri)` — bọc một giá trị **chắc chắn không `null`**; nếu bạn truyền `null` vào,
  nó **chủ động ném `NullPointerException` ngay lập tức** (thà lỗi sớm, rõ ràng, còn hơn để lỗi lan
  xa mới phát hiện).
- `Optional.empty()` — biểu diễn tường minh "không có giá trị gì" (thay thế cho `null`).
- `Optional.ofNullable(giaTriCoTheNull)` — cách dùng phổ biến nhất: tự động chuyển giá trị `null`
  thành `Optional.empty()`, giá trị không `null` thành `Optional.of(...)`.

## Kiểm tra và lấy giá trị an toàn

```java
opt.isPresent();  // co gia tri khong?
opt.isEmpty();    // RONG khong? (nguoc lai voi isPresent, tu Java 11)

String ketQua = opt.orElse("gia-tri-mac-dinh"); // lay gia tri, hoac gia tri thay the neu rong
```

`orElse(giaTriMacDinh)` là cách an toàn để "mở" `Optional` ra lấy giá trị bên trong, luôn có kết
quả cụ thể (không bao giờ `null`), không cần `if/else` kiểm tra `isPresent()` thủ công.

### `orElseGet()` — chỉ tính giá trị thay thế khi thực sự cần

```java
String ketQua = opt.orElseGet(() -> taoGiaTriMacDinhTonKem());
```

Khác biệt với `orElse`: `orElseGet` nhận một `Supplier<T>` (Chương 30) — hàm này **chỉ được gọi khi
`Optional` thực sự rỗng**. Với `orElse(giaTriMacDinh)`, biểu thức `giaTriMacDinh` **luôn được tính
toán trước**, dù `Optional` có giá trị hay không (rồi bị bỏ đi nếu không cần dùng tới) — nếu việc
tính giá trị mặc định đó tốn kém (gọi API, truy vấn database...), `orElseGet` hiệu quả hơn hẳn vì
tránh được chi phí không cần thiết.

## Thực hiện hành động có điều kiện

```java
opt.ifPresent(giaTri -> System.out.println("Co gia tri: " + giaTri));

opt.ifPresentOrElse(
    giaTri -> System.out.println("Co gia tri: " + giaTri),
    () -> System.out.println("Khong co gia tri nao")
);
```

`ifPresent` (nhận một `Consumer<T>`, Chương 30) chỉ chạy hành động **nếu có giá trị**, thay thế cho:

```java
if (opt.isPresent()) {
    System.out.println("Co gia tri: " + opt.get());
}
```

`ifPresentOrElse` (Java 9+) thêm nhánh xử lý cho trường hợp rỗng, tương đương `if/else` đầy đủ.

## `map()` — biến đổi giá trị bên trong `Optional`

```java
Optional<String> optHoa = opt.map(String::toUpperCase);
```

Nếu `opt` **có** giá trị, `map` áp dụng hàm biến đổi và trả về `Optional` chứa kết quả mới; nếu
`opt` **rỗng**, `map` trả về `Optional.empty()` luôn, **không** gọi hàm biến đổi (tránh
`NullPointerException` nếu hàm biến đổi không xử lý được `null`). Đây chính là ý tưởng tương tự
`map` của Stream (Chương 31) — thực tế `Optional` có thể xem như "một Stream chứa tối đa một phần
tử".

## Thiết kế phương thức trả về `Optional<T>` thay vì `T` (có nguy cơ `null`)

```java
static Optional<String> timEmail(Map<String, String> danhBa, String ten) {
    return Optional.ofNullable(danhBa.get(ten));
}
```

Đây là cách dùng `Optional` **quan trọng và phổ biến nhất** trong thực tế: khi viết một phương
thức mà kết quả **có thể không tồn tại**, khai báo kiểu trả về là `Optional<T>` thay vì `T`. Chữ
ký phương thức tự nó đã "nói rõ": *"kết quả này có thể không có, bạn (người gọi) bắt buộc phải xử
lý trường hợp đó"* — người gọi **không thể** vô tình quên kiểm tra như với `null` (họ phải chủ
động gọi `orElse`/`get()`/`ifPresent()`... để lấy giá trị ra, quá trình đó tự nhiên buộc họ đối mặt
với khả năng rỗng).

## Khi nào KHÔNG nên dùng `Optional`

- **Không dùng `Optional` làm kiểu của field trong class** — `Optional` được thiết kế chủ yếu cho
  **kiểu trả về của phương thức**, không phải để lưu trữ trạng thái lâu dài; dùng làm field thường
  gây phức tạp không cần thiết (và không implements `Serializable` — sẽ gặp khi học Chương 36).
- **Không dùng `Optional` làm kiểu tham số phương thức** — nếu một tham số có thể "không có giá
  trị", cách thông thường hơn là dùng overload (Chương 10) cung cấp hai phiên bản phương thức, hoặc
  chấp nhận `null` với tài liệu rõ ràng, thay vì bắt người gọi phải bọc `Optional.of(...)` mỗi lần
  gọi.
- **Không gọi `.get()` trực tiếp mà không kiểm tra trước** — `Optional.get()` (không có trong ví dụ
  ở trên, cố tình tránh dùng) ném `NoSuchElementException` nếu `Optional` rỗng — dùng trực tiếp
  `get()` mà không qua `isPresent()`/`orElse()` gần như tái tạo lại đúng vấn đề `null` mà
  `Optional` sinh ra để giải quyết.

## Bài tập

1. Viết phương thức `Optional<Integer> timViTri(int[] mang, int giaTri)` trả về vị trí đầu tiên
   tìm thấy `giaTri` trong `mang` (dùng `Optional.empty()` nếu không tìm thấy), thay cho việc trả
   về `-1` như Chương 28.
2. Dùng `orElseThrow(...)` (tự tra cứu tài liệu Java, không có trong ví dụ trên) để ném một
   exception tự định nghĩa (Chương 21) khi `Optional` rỗng, thay vì dùng giá trị mặc định.
3. Viết một chuỗi `map()` liên tiếp trên `Optional<String>`: đầu tiên `trim()`, sau đó
   `toLowerCase()`, cuối cùng `orElse("khong-co")` — quan sát nếu `Optional` gốc rỗng, mọi bước
   `map` ở giữa đều tự động bị bỏ qua mà không cần kiểm tra thủ công ở từng bước.

## Lỗi thường gặp

- **Gọi `Optional.get()` mà không kiểm tra trước** — tái tạo lại đúng vấn đề `NullPointerException`
  dưới một cái tên khác (`NoSuchElementException`). Luôn dùng `orElse`/`orElseGet`/`ifPresent`.
- **Dùng `Optional.of(giaTri)` khi `giaTri` có thể là `null`** — ném `NullPointerException` ngay
  tại đó. Dùng `Optional.ofNullable(...)` nếu không chắc chắn giá trị luôn khác `null`.
- **So sánh hai `Optional` bằng `==`** — giống mọi kiểu tham chiếu khác (Chương 11, 13), `==` so
  sánh tham chiếu, không so sánh nội dung; dùng `.equals()` nếu cần so sánh nội dung hai `Optional`.
- **Lạm dụng `Optional` ở khắp mọi nơi, kể cả khi giá trị chắc chắn không bao giờ rỗng** — thêm độ
  phức tạp không cần thiết. Chỉ dùng `Optional` khi việc "không có giá trị" là một tình huống hợp
  lệ, có thật trong logic nghiệp vụ.
