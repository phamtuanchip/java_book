# Chương 16 — Kế thừa (Inheritance)

## Mục tiêu học

Sau chương này, bạn sẽ:

- Dùng `extends` để một class kế thừa field và phương thức từ class khác.
- Gọi constructor lớp cha bằng `super(...)`, hiểu vì sao gần như luôn bắt buộc.
- Override (ghi đè) phương thức của lớp cha, dùng `@Override` đúng cách.
- Gọi lại phiên bản của lớp cha từ lớp con bằng `super.tenPhuongThuc(...)`.
- Phân biệt **overriding** (Chương này) với **overloading** (đã học ở Chương 10) — hai khái niệm
  rất dễ nhầm vì tên gần giống nhau.

Code mẫu đầy đủ: [`code/ch16-ke-thua/`](../../code/ch16-ke-thua/).

## Vấn đề: trùng lặp code giữa các class liên quan

Giả sử bạn cần mô hình hoá nhiều loại nhân viên: nhân viên thường, nhân viên bán hàng (có thêm hoa
hồng), nhân viên quản lý (có thêm phụ cấp). Không có kế thừa, bạn phải viết lặp lại `ten`,
`luongCoBan`, `getTen()` trong **cả ba** class riêng biệt — trùng lặp, và nếu sau này cần sửa cách
lưu `ten`, phải sửa ở cả ba nơi.

## `extends` — kế thừa từ một class khác

```java
public class NhanVien {
    protected String ten;
    protected double luongCoBan;

    public NhanVien(String ten, double luongCoBan) {
        this.ten = ten;
        this.luongCoBan = luongCoBan;
    }

    public String getTen() {
        return ten;
    }

    public double tinhLuong() {
        return luongCoBan;
    }
}
```

```java
public class NhanVienBanHang extends NhanVien {
    private double doanhSo;

    public NhanVienBanHang(String ten, double luongCoBan, double doanhSo) {
        super(ten, luongCoBan);
        this.doanhSo = doanhSo;
    }
}
```

`NhanVienBanHang extends NhanVien` nghĩa là `NhanVienBanHang` **tự động có sẵn** mọi field và
phương thức `public`/`protected` của `NhanVien` (`ten`, `luongCoBan`, `getTen()`, `tinhLuong()`),
mà không cần viết lại — gọi `NhanVien` là **lớp cha (superclass/parent class)**, `NhanVienBanHang`
là **lớp con (subclass/child class)**.

## Vì sao field cần `protected` thay vì `private` ở đây?

Ở Chương 14, nguyên tắc là field nên `private`. Nhưng `private` **giấu hoàn toàn**, kể cả với lớp
con — lớp con sẽ **không truy cập trực tiếp được** field `private` của lớp cha. `protected` (nhắc
lại bảng ở Chương 14) mở thêm một mức: **cho phép lớp con** (kể cả ở package khác) truy cập trực
tiếp, trong khi vẫn giấu khỏi phần còn lại của chương trình. Đây là lý do `NhanVien.ten` và
`NhanVien.luongCoBan` khai báo `protected` — để `NhanVienQuanLy.tinhLuong()` dùng được `luongCoBan`
trực tiếp (xem phần override bên dưới).

## `super(...)` — gọi constructor lớp cha

```java
public NhanVienBanHang(String ten, double luongCoBan, double doanhSo) {
    super(ten, luongCoBan); // PHAI la dong dau tien trong constructor
    this.doanhSo = doanhSo;
}
```

`super(...)` gọi constructor của lớp cha, để lớp cha tự khởi tạo đúng phần dữ liệu của nó
(`ten`, `luongCoBan`). Quy tắc bắt buộc: nếu bạn gọi `super(...)`, nó **phải là dòng đầu tiên**
trong constructor lớp con. Nếu bạn **không** viết `super(...)` tường minh, Java **tự động chèn**
một lời gọi `super()` không tham số ở đầu constructor — nếu lớp cha **không có** constructor không
tham số (như `NhanVien` ở đây — nó chỉ có constructor 2 tham số), việc này gây **lỗi biên dịch**,
buộc bạn phải tự viết `super(...)` với đúng tham số phù hợp.

## Override (ghi đè) phương thức

```java
public class NhanVienBanHang extends NhanVien {
    private static final double TY_LE_HOA_HONG = 0.05;

    @Override
    public double tinhLuong() {
        return luongCoBan + doanhSo * TY_LE_HOA_HONG;
    }
}
```

**Override** là việc lớp con định nghĩa lại một phương thức đã có ở lớp cha, **cùng tên, cùng tham
số, cùng kiểu trả về** — thay thế hoàn toàn hành vi của lớp cha bằng hành vi mới khi phương thức đó
được gọi trên object của lớp con.

`@Override` là một **annotation** (chú thích đặc biệt, không ảnh hưởng logic chạy) — báo cho trình
biên dịch biết "tôi cố ý override phương thức này". Lợi ích: nếu bạn gõ sai tên phương thức, sai
kiểu tham số (vô tình tạo ra một phương thức overload mới thay vì override như ý muốn),
`@Override` khiến trình biên dịch **báo lỗi ngay** thay vì âm thầm để bạn tạo nhầm một phương thức
khác. **Luôn dùng `@Override`** khi có ý định ghi đè — đây là thói quen tốt gần như bắt buộc trong
code Java thực tế.

## `super.tenPhuongThuc(...)` — gọi lại phiên bản của lớp cha

```java
public class NhanVienQuanLy extends NhanVien {
    private double phuCapQuanLy;

    @Override
    public double tinhLuong() {
        return super.tinhLuong() + phuCapQuanLy;
    }
}
```

Đôi khi lớp con muốn **mở rộng** hành vi của lớp cha thay vì thay thế hoàn toàn — `super.tinhLuong()`
gọi đúng phiên bản `tinhLuong()` của `NhanVien`, rồi cộng thêm phần riêng của `NhanVienQuanLy`. Cách
này tránh việc phải chép lại logic `return luongCoBan;` một lần nữa trong `NhanVienQuanLy` — nếu
sau này cách tính lương cơ bản ở `NhanVien` thay đổi, `NhanVienQuanLy` tự động thừa hưởng thay đổi
đó mà không cần sửa gì.

## Overriding khác Overloading như thế nào?

Đây là cặp khái niệm **rất dễ nhầm** vì tên gần giống nhau trong tiếng Anh lẫn cách phát âm:

| | Overloading (Chương 10) | Overriding (chương này) |
|---|---|---|
| Xảy ra giữa | Nhiều phương thức **cùng một class** | Lớp con và lớp cha (**hai class khác nhau**) |
| Chữ ký (tên + tham số) | **Phải khác nhau** | **Phải giống hệt nhau** |
| Chọn phiên bản nào chạy | Lúc biên dịch, dựa trên đối số truyền vào | Lúc chạy, dựa trên **object thực sự** đang gọi (sẽ hiểu sâu ở Chương 17 — đa hình) |

## Bài tập

1. Tạo class `DongVat` với field `ten`, phương thức `keu()` in ra `"..."` (âm thanh chung chung).
   Tạo `Cho extends DongVat` và `Meo extends DongVat`, mỗi lớp override `keu()` in ra âm thanh riêng
   ("Gau gau!", "Meo meo!").
2. Thêm vào `NhanVien` (code mẫu) một phương thức `thuong(double soTien)` cộng thêm tiền thưởng một
   lần vào `luongCoBan`; xác nhận `NhanVienBanHang` và `NhanVienQuanLy` đều dùng được phương thức
   này mà không cần viết lại.
3. Thử xoá dòng `super(ten, luongCoBan);` trong `NhanVienBanHang`, biên dịch lại và đọc lỗi — giải
   thích vì sao lỗi xảy ra dựa trên phần "super(...)" ở trên.

## Lỗi thường gặp

- **Quên gọi `super(...)` khi lớp cha không có constructor không tham số** — lỗi biên dịch
  `constructor NhanVien in class NhanVien cannot be applied to given types` hoặc tương tự. Luôn gọi
  `super(...)` tường minh với đúng tham số khi lớp cha không có constructor rỗng.
- **Sai chữ ký khi định tâm override, vô tình tạo ra overload** — ví dụ viết
  `public double tinhLuong(int x)` (thêm tham số) thay vì `tinhLuong()` — đây không còn là override,
  mà là một phương thức hoàn toàn khác. Dùng `@Override` để trình biên dịch tự phát hiện lỗi này.
- **Field `private` ở lớp cha, lớp con tưởng dùng được trực tiếp** — `private` không kế thừa xuống
  được, kể cả lớp con. Cần `protected` (hoặc getter/setter `public`) nếu lớp con cần truy cập trực
  tiếp.
- **Java không hỗ trợ đa kế thừa class** (một class chỉ `extends` được **đúng một** class cha, khác
  với một số ngôn ngữ khác cho phép kế thừa nhiều class cùng lúc) — muốn "kế thừa" nhiều nguồn hành
  vi khác nhau, dùng `interface` (Chương 18) thay vì cố tìm cách kế thừa nhiều class.
