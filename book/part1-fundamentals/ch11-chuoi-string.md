# Chương 11 — Chuỗi (String) và StringBuilder

## Mục tiêu học

Sau chương này, bạn sẽ:

- Dùng thành thạo các thao tác `String` cơ bản: độ dài, chuỗi con, tìm kiếm, thay thế, tách chuỗi.
- Hiểu `String` là **bất biến (immutable)** — vì sao, và hệ quả của điều đó.
- Biết chính xác vì sao **không được dùng `==` để so sánh nội dung `String`**, và String pool là gì.
- Dùng `StringBuilder` khi cần nối/sửa chuỗi nhiều lần, hiểu vì sao nó hiệu quả hơn `+`.

Code mẫu đầy đủ: [`code/ch11-chuoi-string/`](../../code/ch11-chuoi-string/).

## Các thao tác `String` cơ bản

```java
String ten = "Nguyen Van A";

ten.length()              // 12 - so ky tu
ten.toUpperCase()         // "NGUYEN VAN A"
ten.toLowerCase()         // "nguyen van a"
ten.charAt(0)             // 'N' - ky tu tai vi tri 0 (chi so bat dau tu 0, giong mang)
ten.substring(0, 6)       // "Nguyen" - tu vi tri 0 den TRUOC vi tri 6
ten.substring(7)          // "Van A" - tu vi tri 7 den het chuoi
ten.indexOf("Van")        // 7 - vi tri bat dau xuat hien, -1 neu khong tim thay
ten.contains("Nguyen")    // true
ten.replace("A", "B")     // "Nguyen Van B" - thay THE tat ca ky tu/chuoi con khop
"  xin chao  ".trim()     // "xin chao" - xoa khoang trang thua o dau/cuoi
ten.split(" ")            // ["Nguyen", "Van", "A"] - tach thanh mang String[]
```

`substring(start, end)` lấy từ chỉ số `start` đến **trước** chỉ số `end` (không bao gồm `end`) —
giống quy tắc `[start, end)` sẽ gặp lại nhiều lần trong lập trình. `substring(start)` (một tham số)
lấy từ `start` đến hết chuỗi.

## `String` là bất biến (immutable)

Đây là tính chất **quan trọng nhất** của `String` trong Java: **mọi thao tác "sửa" chuỗi thực chất
tạo ra một chuỗi mới**, chuỗi gốc không bao giờ thay đổi.

```java
String goc = "Java";
String daSua = goc.toUpperCase();
System.out.println(goc);    // "Java" - KHONG doi
System.out.println(daSua);  // "JAVA" - chuoi MOI, khac object voi goc
```

Nếu bạn gọi `goc.toUpperCase();` mà **không gán** kết quả cho biến nào, chuỗi kết quả bị "bỏ rơi",
`goc` vẫn y nguyên — đây là lỗi rất phổ biến với người mới (tưởng gọi phương thức là chuỗi tự đổi).

## So sánh `String`: `==` vs `.equals()`

Đây là lỗi kinh điển bậc nhất của người học Java. Nhắc lại từ Chương 6: `==` với kiểu **tham chiếu**
(như `String`) so sánh **hai biến có trỏ tới cùng một object trong bộ nhớ hay không**, chứ không so
sánh nội dung:

```java
String a = new String("hello");
String b = new String("hello");
System.out.println(a == b);         // false! - hai object KHAC NHAU trong bo nho
System.out.println(a.equals(b));    // true  - noi dung GIONG NHAU
```

**Luôn dùng `.equals()` để so sánh nội dung hai chuỗi**, không bao giờ dùng `==` (trừ trường hợp cố
ý kiểm tra hai biến có cùng trỏ một object hay không — hiếm gặp khi mới học).

### Vậy sao đôi khi `==` lại "hoạt động đúng" với String?

```java
String c = "hello";
String d = "hello";
System.out.println(c == d); // true !!
```

Java có một vùng nhớ đặc biệt gọi là **String pool**: khi bạn viết một chuỗi **literal** (viết
thẳng trong code bằng dấu nháy kép, như `"hello"`), Java tái sử dụng **cùng một object** cho mọi
literal có nội dung giống hệt nhau, để tiết kiệm bộ nhớ. Đó là lý do `c == d` cho `true` — cả hai
đều trỏ tới cùng một chuỗi trong pool. Nhưng `new String("hello")` **ép buộc tạo object mới**, nằm
ngoài pool, nên `==` giữa nó và bất kỳ chuỗi nào khác đều `false` dù nội dung giống hệt.

**Kết luận thực dụng**: đừng dựa vào String pool để dùng `==`. Hành vi String pool là chi tiết cài
đặt bên trong, không phải quy tắc bạn nên lập trình dựa vào — code của bạn có thể nhận `String` từ
nhiều nguồn khác nhau (nhập từ bàn phím, đọc từ file...) mà không phải lúc nào cũng đi qua String
pool. **Quy tắc duy nhất cần nhớ: so sánh nội dung `String` luôn dùng `.equals()`.**

## `StringBuilder` — khi cần nối/sửa chuỗi nhiều lần

Vì `String` bất biến, mỗi lần "nối chuỗi" bằng `+` thực chất **tạo ra một chuỗi hoàn toàn mới**,
sao chép lại toàn bộ nội dung cũ cộng thêm phần mới:

```java
String ketQua = "";
for (int i = 0; i < 50_000; i++) {
    ketQua = ketQua + i; // MOI LAN LAP tao mot chuoi moi, sao chep lai TOAN BO noi dung cu
}
```

Với vòng lặp ít lần, chuyện này không đáng lo. Nhưng lặp hàng chục nghìn lần trở lên, chi phí sao
chép lặp lại tăng rất nhanh (chạy thử `StringBuilderDemo.java` để thấy chênh lệch thời gian thực
tế). Giải pháp: dùng `StringBuilder` — một cấu trúc dữ liệu **có thể thay đổi (mutable)**, chuyên
để xây dựng chuỗi dần dần mà không tạo object mới ở mỗi bước:

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 50_000; i++) {
    sb.append(i); // sua NOI DUNG tai cho, khong tao chuoi moi
}
String ketQua = sb.toString(); // chuyen thanh String khi can dung nhu String binh thuong
```

Các phương thức `StringBuilder` hay dùng: `append(...)` (nối thêm vào cuối), `insert(vi_tri, ...)`
(chèn vào giữa), `replace(start, end, ...)` (thay thế đoạn), `reverse()` (đảo ngược), `toString()`
(chuyển thành `String` thông thường khi cần).

**Quy tắc thực dụng**: nối vài chuỗi cố định, viết bằng `+` cho dễ đọc (`"Xin chao, " + ten`) — hiệu
năng không đáng lo trong trường hợp này, trình biên dịch Java thậm chí tự tối ưu một số trường hợp
đơn giản. Chỉ chuyển sang `StringBuilder` khi nối chuỗi **trong vòng lặp** hoặc số lượng lớn.

## Bài tập

1. Viết chương trình nhận một câu (dùng `Scanner.nextLine()` — đọc cả dòng, kể cả có dấu cách),
   đếm số từ trong câu đó (gợi ý: dùng `split(" ")` rồi lấy độ dài mảng kết quả).
2. Viết chương trình kiểm tra một chuỗi có phải là "palindrome" (đọc xuôi và đọc ngược giống nhau,
   ví dụ "madam") hay không, dùng `StringBuilder.reverse()`.
3. Giải thích bằng lời: vì sao đoạn code sau **không** in ra "JAVA"?
   ```java
   String s = "java";
   s.toUpperCase();
   System.out.println(s);
   ```

## Lỗi thường gặp

- **Dùng `==` để so sánh nội dung `String`, thỉnh thoảng "vẫn đúng" (nhờ String pool) rồi ngỡ đó là
  cách làm đúng** — đây là lỗi tiềm ẩn nguy hiểm nhất chương: code có thể chạy đúng trong lúc test
  (vì dùng toàn literal) nhưng sai khi chạy thật (chuỗi đến từ `new String(...)`,
  `Scanner.nextLine()`, ghép chuỗi lúc chạy...). Luôn dùng `.equals()`.
- **Gọi phương thức "sửa" chuỗi mà không gán lại kết quả** — ví dụ `ten.trim();` một mình trên một
  dòng không làm gì cả, vì kết quả (chuỗi mới) bị bỏ đi ngay. Phải viết `ten = ten.trim();`.
- **`StringIndexOutOfBoundsException`** khi gọi `substring`/`charAt` với chỉ số vượt quá độ dài
  chuỗi — tương tự lỗi mảng ở Chương 9, luôn kiểm tra chỉ số hợp lệ trước khi truy cập.
- **Nối chuỗi bằng `+` trong vòng lặp lớn rồi thắc mắc sao chương trình chạy chậm** — chuyển sang
  `StringBuilder` khi số lần nối lớn (hàng nghìn lần trở lên).
- **`NullPointerException` khi gọi phương thức trên biến `String` chưa được gán giá trị (`null`)**
  — sẽ hiểu đầy đủ khái niệm `null` ở Chương 13; ghi nhớ trước: luôn đảm bảo biến `String` đã có
  giá trị thật trước khi gọi phương thức trên nó.
