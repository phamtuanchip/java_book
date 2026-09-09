# Chương 18 — Abstract class & Interface

## Mục tiêu học

Sau chương này, bạn sẽ:

- Viết được abstract class với phương thức `abstract` (bắt buộc override) trộn cùng phương thức
  thường (dùng chung, kế thừa nguyên).
- Viết được interface, hiểu `default` method và `static` method trong interface (Java 8+).
- Biết khi nào chọn abstract class, khi nào chọn interface.
- Hiểu vì sao một class `implements` được nhiều interface, trong khi chỉ `extends` được một class.

Code mẫu đầy đủ: [`code/ch18-abstract-interface/`](../../code/ch18-abstract-interface/).

## Abstract class — lớp cha "chưa hoàn chỉnh" có chủ đích

Ở Chương 16-17, `NhanVien` là một class **hoàn chỉnh**, `new NhanVien(...)` tạo object bình thường
được. Nhưng có những trường hợp lớp cha **chỉ nên tồn tại để kế thừa**, không nên tạo object trực
tiếp — ví dụ khái niệm "hình học" chung chung: không có object nào thực sự là "một hình học" trần
trụi, chỉ có hình tròn, hình chữ nhật... cụ thể. `abstract class` diễn đạt đúng ý định đó:

```java
public abstract class HinhHoc {
    protected String tenHinh;

    public HinhHoc(String tenHinh) {
        this.tenHinh = tenHinh;
    }

    public abstract double tinhDienTich();  // KHONG co than ham
    public abstract double tinhChuVi();      // KHONG co than ham

    public void inThongTin() {               // CO than ham day du
        System.out.println(tenHinh + ": dien tich = " + tinhDienTich());
    }
}
```

Hai điểm quan trọng:

- **`abstract class HinhHoc { ... }` không `new` trực tiếp được** — `new HinhHoc("test")` là lỗi
  biên dịch. Chỉ tạo object được từ **lớp con cụ thể** (`HinhTron`, `HinhChuNhat`) đã override đầy
  đủ mọi phương thức `abstract`.
- Abstract class **trộn lẫn được** phương thức `abstract` (không thân hàm, bắt buộc lớp con override
  — như `tinhDienTich()`) với phương thức thường có sẵn thân hàm (như `inThongTin()`, lớp con kế
  thừa nguyên, dùng chung không cần viết lại). Đây là điểm khác biệt quan trọng so với interface
  kiểu cũ (xem phần dưới).

Lớp con kế thừa abstract class **bắt buộc phải override tất cả** phương thức `abstract` của nó (trừ
khi lớp con cũng là abstract class), nếu không sẽ bị lỗi biên dịch — trình biên dịch **ép bạn không
được quên** implement bất kỳ hành vi bắt buộc nào, khác với chỉ dùng class thường và "hy vọng" lớp
con nhớ override.

## Interface — "hợp đồng" thuần tuý

```java
public interface CoTheVe {
    void ve(); // interface tu hieu day la abstract, khong can ghi tu khoa 'abstract'

    default void veDamNet() {
        System.out.println("=== VE DAM NET ===");
        ve();
        System.out.println("=== HET ===");
    }

    static void inHuongDan() {
        System.out.println("Goi ve() de ve hinh...");
    }
}
```

Interface định nghĩa **"cái gì phải làm được"** mà không quan tâm "làm bằng cách nào" — mọi phương
thức khai báo bình thường trong interface (`void ve();`) mặc định là `abstract`, **không có thân
hàm**, và class `implements` interface đó **bắt buộc** phải cung cấp phần thân hàm cụ thể.

### `default` method (Java 8+)

```java
default void veDamNet() {
    System.out.println("=== VE DAM NET ===");
    ve();
    System.out.println("=== HET ===");
}
```

Khác với phương thức abstract thông thường của interface, `default` method **có sẵn thân hàm ngay
trong interface**. Mọi class `implements` interface này **tự động kế thừa** hành vi mặc định đó,
**không bắt buộc** phải override (nhưng vẫn override được nếu muốn hành vi khác). Tính năng này
được thêm vào Java 8 chủ yếu để **mở rộng interface có sẵn** mà không phá vỡ code cũ đang implement
interface đó (nếu thêm một phương thức abstract mới vào interface đã tồn tại, mọi class cũ
implements nó sẽ lỗi biên dịch ngay; thêm `default` method thì không).

### `static` method trong interface

```java
static void inHuongDan() {
    System.out.println("...");
}
```

Gọi trực tiếp qua tên interface (`CoTheVe.inHuongDan();`), tương tự phương thức `static` của class
đã học ở Chương 10 — không gắn với object cụ thể nào, thường dùng cho các hàm tiện ích liên quan
tới interface đó.

## Đa kế thừa interface — điều `extends` không cho phép

```java
public class HinhTron extends HinhHoc implements CoTheVe, CoTheSoSanhDienTich {
    // ...
}
```

Nhắc lại từ Chương 16: một class chỉ `extends` được **đúng một** class cha. Nhưng một class
**`implements` được nhiều interface cùng lúc**, phân cách bằng dấu phẩy. Đây là cách Java cho phép
một object "vừa là cái này, vừa là cái kia" theo nhiều khía cạnh độc lập — `HinhTron` vừa **là một**
`HinhHoc` (qua kế thừa), vừa **có khả năng** `CoTheVe` (vẽ được), vừa **có khả năng**
`CoTheSoSanhDienTich` (so sánh diện tích được) — ba khía cạnh này độc lập với nhau, không có quan hệ
cha-con giữa chúng.

## Khi nào chọn abstract class, khi nào chọn interface?

| Tiêu chí | Abstract class | Interface |
|---|---|---|
| Số lượng kế thừa/implements cùng lúc | Chỉ 1 | Nhiều |
| Có field lưu trạng thái (như `tenHinh`) | Có | Không (interface không có field lưu trạng thái thực thụ, chỉ có hằng số `public static final` ngầm định) |
| Có phương thức đã có sẵn thân hàm, dùng chung | Có (phương thức thường) | Có (từ Java 8, qua `default`) |
| Ý nghĩa diễn đạt | "X **LÀ MỘT** loại Y" (quan hệ chặt, is-a) | "X **CÓ KHẢ NĂNG** làm được Z" (quan hệ lỏng hơn, capability) |

**Quy tắc thực dụng cho người mới**: nếu các lớp con chia sẻ **dữ liệu chung** và có quan hệ
"là một loại của nhau" rõ ràng (như mọi hình học đều có `tenHinh`), dùng abstract class. Nếu bạn
chỉ muốn đảm bảo nhiều class **khác nhau, không liên quan họ hàng** đều có một khả năng chung (vẽ
được, so sánh được, đọc/ghi được...), dùng interface.

## Bài tập

1. Tạo abstract class `PhuongTien` với field `bienSo`, phương thức abstract `dichChuyen()`; tạo
   `XeMay` và `OToTruong extends PhuongTien` (dùng tên khác `OTo` để tránh trùng nếu bạn từng đặt
   tên đó trước đó), mỗi lớp override `dichChuyen()` in ra cách di chuyển riêng.
2. Tạo interface `CoTheSac` với phương thức `int soChoNgoi()`, cho cả `XeMay` và `OToTruong` cùng
   `implements` interface này (cùng với `extends PhuongTien`), xác nhận một class vừa `extends` vừa
   `implements` được cùng lúc.
3. Thêm một `default` method vào `CoTheSac` in ra "Xe có X chỗ ngồi" dựa trên `soChoNgoi()`, xác
   nhận cả `XeMay` và `OToTruong` dùng chung được default method này mà không cần viết lại.

## Lỗi thường gặp

- **Cố `new` trực tiếp một abstract class** — lỗi biên dịch `HinhHoc is abstract; cannot be
  instantiated`. Chỉ tạo object được từ lớp con cụ thể đã override đầy đủ phương thức abstract.
- **Quên override một phương thức abstract khi kế thừa/implements** — lỗi biên dịch báo rõ tên
  phương thức còn thiếu, class con phải override đủ **tất cả** phương thức abstract của lớp cha/
  interface (hoặc chính nó cũng phải khai báo `abstract`).
- **Cố `extends` hai class cùng lúc** (`class X extends A, B`) — Java không hỗ trợ, đây là lỗi cú
  pháp. Nếu cần kết hợp nhiều nguồn hành vi, dùng `implements` nhiều interface thay vì cố kế thừa
  nhiều class.
- **Nhầm interface có thể lưu trạng thái như field bình thường** — mọi biến khai báo trong interface
  mặc định là `public static final` (hằng số dùng chung cho mọi class implements, không phải dữ
  liệu riêng từng object) — nếu cần dữ liệu riêng từng object, phải dùng (abstract) class, không
  phải interface.
