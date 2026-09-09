# Chương 17 — Đa hình (Polymorphism)

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu đa hình là gì và tại sao nó là một trong những lý do OOP mạnh mẽ.
- Phân biệt **upcasting** (tự động, an toàn) và **downcasting** (thủ công, có rủi ro).
- Dùng `instanceof` để kiểm tra kiểu thực sự của object trước khi downcasting.
- Hiểu **liên kết động (dynamic binding)** — cơ chế Java quyết định gọi phiên bản phương thức nào
  lúc **chạy**, dựa trên kiểu **thực sự** của object, không phải kiểu khai báo của biến.

Code mẫu đầy đủ: [`code/ch17-da-hinh/`](../../code/ch17-da-hinh/).

## Đa hình là gì?

Tiếp tục ví dụ `NhanVien`/`NhanVienBanHang`/`NhanVienQuanLy` từ Chương 16. **Đa hình** (polymorphism
— tiếng Hy Lạp nghĩa là "nhiều hình dạng") là khả năng xử lý nhiều loại object khác nhau **thông
qua cùng một kiểu tham chiếu chung** (ở đây: `NhanVien`), gọi **cùng một phương thức**
(`tinhLuong()`), nhưng mỗi object **tự chạy đúng logic riêng của loại thật sự của nó**:

```java
NhanVien[] danhSach = {
    new NhanVien("A", 10_000_000),
    new NhanVienBanHang("B", 8_000_000, 100_000_000),
    new NhanVienQuanLy("C", 15_000_000, 3_000_000)
};

for (NhanVien nv : danhSach) {
    System.out.println(nv.getTen() + ": " + nv.tinhLuong());
}
```

Vòng lặp trên **không cần biết** (và không cần quan tâm) mỗi phần tử thực sự là loại nhân viên nào
— nó chỉ gọi `tinhLuong()` một cách thống nhất, và mỗi object tự "biết" cách tính lương đúng theo
bản chất riêng của nó. Đây chính là giá trị thực tiễn lớn nhất của đa hình: viết code xử lý chung
**một lần**, hoạt động đúng cho **mọi** loại con hiện tại lẫn **mọi loại con sẽ được thêm sau này**
(thêm `NhanVienThucTap extends NhanVien` mới, vòng lặp trên vẫn chạy đúng mà không cần sửa gì).

## Upcasting — tự động, luôn an toàn

```java
NhanVien nv = new NhanVienBanHang("B", 8_000_000, 100_000_000);
```

Gán một object lớp con cho biến khai báo kiểu lớp cha gọi là **upcasting** ("đi lên" theo cây kế
thừa). Việc này **luôn hợp lệ và tự động** — logic: một `NhanVienBanHang` chắc chắn **là một**
`NhanVien` (nó kế thừa mọi thứ của `NhanVien`), nên gán được cho biến kiểu `NhanVien` không có gì
rủi ro.

**Hệ quả quan trọng**: biến `nv` (kiểu khai báo `NhanVien`) chỉ "nhìn thấy" được các phương thức
**định nghĩa trong `NhanVien`** — dù bản chất thật sự của object là `NhanVienBanHang`, bạn **không
gọi được trực tiếp** `nv.capNhatDoanhSo(...)` (phương thức chỉ có ở `NhanVienBanHang`) qua biến
`nv` — trình biên dịch báo lỗi, vì nó chỉ xét **kiểu khai báo của biến**, không xét bản chất thật
sự của object lúc chạy.

## Downcasting — thủ công, cần kiểm tra trước

Muốn gọi được phương thức riêng của lớp con qua một biến đang khai báo kiểu lớp cha, phải
**downcasting** ("đi xuống") — ép kiểu tường minh:

```java
if (nv instanceof NhanVienBanHang) {
    NhanVienBanHang nvbh = (NhanVienBanHang) nv;
    nvbh.capNhatDoanhSo(150_000_000);
}
```

`instanceof` kiểm tra xem object mà `nv` đang trỏ tới **có thực sự là** (hoặc là lớp con của)
`NhanVienBanHang` hay không, trả về `boolean`. **Luôn kiểm tra `instanceof` trước khi downcasting**
— nếu bạn ép kiểu sai (object thật sự không phải loại đó), chương trình ném
`ClassCastException` lúc **chạy**:

```java
NhanVien nv1 = new NhanVien("A", 10_000_000);
NhanVienBanHang sai = (NhanVienBanHang) nv1; // ClassCastException! nv1 KHONG phai NhanVienBanHang
```

### Pattern matching cho `instanceof` (Java 16+)

Cú pháp hiện đại gộp kiểm tra và ép kiểu thành một bước, tránh phải khai báo biến ép kiểu riêng:

```java
if (nv instanceof NhanVienBanHang nvbh) {
    // ben trong khoi if nay, 'nvbh' da san sang dung, KIEU NhanVienBanHang
    nvbh.capNhatDoanhSo(150_000_000);
}
```

Sách sẽ dùng lại cú pháp này nhiều hơn ở Chương 33 (Java hiện đại) — ở đây chỉ cần biết nó tồn tại
và làm đúng việc gì.

## Liên kết động (dynamic binding) — vì sao đa hình hoạt động được

Khi bạn gọi `nv.tinhLuong()`, Java **không** quyết định ngay lúc biên dịch phiên bản `tinhLuong()`
nào sẽ chạy dựa trên kiểu khai báo (`NhanVien`) của biến `nv`. Thay vào đó, **lúc chạy**, JVM nhìn
vào **object thực sự** mà `nv` đang trỏ tới, và gọi đúng phiên bản `tinhLuong()` đã được **override**
ở lớp thực sự của object đó (`NhanVienBanHang`, `NhanVienQuanLy`, hay giữ nguyên bản gốc của
`NhanVien` nếu không override). Cơ chế "quyết định lúc chạy, dựa trên object thật" này gọi là
**liên kết động (dynamic binding)** hay **virtual method invocation** — đây chính là cơ chế nền
tảng khiến vòng lặp `for (NhanVien nv : danhSach)` ở phần đầu chương hoạt động đúng cho từng loại
nhân viên khác nhau mà không cần bất kỳ `if/else` hay `instanceof` nào để phân biệt loại.

**So sánh với overloading (Chương 10)**: overloading được quyết định **lúc biên dịch** (dựa trên
kiểu tham số bạn viết trong code), còn override/đa hình được quyết định **lúc chạy** (dựa trên
object thực tế) — đây là khác biệt cốt lõi khiến đa hình linh hoạt và mạnh hơn nhiều so với chỉ
overloading.

## Bài tập

1. Tiếp nối bài tập Chương 16 (`DongVat`, `Cho`, `Meo`), tạo mảng `DongVat[]` chứa cả hai loại, viết
   vòng lặp gọi `keu()` trên từng phần tử — quan sát đa hình hoạt động.
2. Thêm class `NhanVienThucTap extends NhanVien` (không có gì đặc biệt, dùng nguyên `tinhLuong()`
   của lớp cha), thêm vào mảng `danhSachNhanVien` trong `Main.java`, chạy lại và xác nhận vòng lặp
   vẫn hoạt động đúng **mà không cần sửa bất kỳ dòng nào khác**.
3. Bỏ comment dòng `NhanVienBanHang epSai = (NhanVienBanHang) nv1;` trong `Main.java`, chạy và đọc
   kỹ thông báo `ClassCastException` — nó cho biết đang cố ép object thuộc lớp nào sang lớp nào.

## Lỗi thường gặp

- **`ClassCastException`** — downcasting sai loại thực sự của object. Luôn `instanceof` trước khi
  ép kiểu, hoặc dùng pattern matching `instanceof` để gộp làm một bước an toàn.
- **Tưởng gọi được phương thức riêng của lớp con qua biến khai báo kiểu lớp cha** — trình biên dịch
  chỉ xét kiểu khai báo của biến, không xét object thật sự lúc chạy. Cần downcasting trước.
- **Nhầm lẫn thời điểm quyết định: overloading là lúc biên dịch, override/đa hình là lúc chạy** —
  đọc lại phần "Liên kết động" nếu còn mơ hồ, đây là khái niệm nền tảng cho toàn bộ phần OOP nâng
  cao còn lại.
- **Lạm dụng `instanceof` + downcasting để giả lập đa hình bằng `if/else` dài dằng dặc** — nếu bạn
  thấy mình viết `if (x instanceof A) ... else if (x instanceof B) ...` lặp đi lặp lại ở nhiều nơi
  để xử lý khác nhau theo từng loại, đó thường là dấu hiệu nên **override phương thức** ở từng lớp
  con thay vì kiểm tra kiểu thủ công — để chính cơ chế đa hình tự làm việc đó cho bạn.
