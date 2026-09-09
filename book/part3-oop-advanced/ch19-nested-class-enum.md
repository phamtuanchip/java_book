# Chương 19 — Lớp lồng, anonymous class, enum nâng cao

## Mục tiêu học

Sau chương này, bạn sẽ:

- Viết được **inner class** (lớp lồng không static) và **static nested class**, hiểu khác biệt.
- Dùng **anonymous class** để tạo object implements interface "ngay tại chỗ", không cần đặt tên
  class riêng.
- Viết enum có field, constructor, phương thức riêng — vượt xa enum "danh sách tên suông" bạn có
  thể đã gặp ở nơi khác.
- Viết enum với phương thức khác nhau cho từng hằng số (constant-specific class body).

Code mẫu đầy đủ: [`code/ch19-nested-enum/`](../../code/ch19-nested-enum/).

## Lớp lồng (nested class) là gì?

Java cho phép khai báo một class **bên trong** một class khác, khi class đó **chỉ có ý nghĩa gắn
liền** với class chứa nó — không cần tồn tại độc lập bên ngoài. Có hai loại chính:

### Static nested class — độc lập, chỉ "đóng gói" cho gọn

```java
public class DoiBongDa {
    public static class ThongKe {
        public int tongSoTran;
        public int tongBanThang;

        public ThongKe(int tongSoTran, int tongBanThang) {
            this.tongSoTran = tongSoTran;
            this.tongBanThang = tongBanThang;
        }
    }
}
```

`static class ThongKe` hoạt động gần như một class bình thường, độc lập hoàn toàn với bất kỳ object
`DoiBongDa` nào — chỉ khác là tên đầy đủ của nó là `DoiBongDa.ThongKe`, và bạn khai báo nó bên trong
`DoiBongDa` đơn thuần vì nó **có ý nghĩa liên quan chặt** tới `DoiBongDa` (thống kê của một đội
bóng), giúp code dễ tìm và tổ chức hơn. Tạo object: `new DoiBongDa.ThongKe(10, 18)`.

### Inner class (không static) — luôn gắn liền một object outer cụ thể

```java
public class DoiBongDa {
    private String tenDoi;

    public class CauThu {
        private String ten;

        public void gioiThieu() {
            System.out.println(ten + " - doi " + tenDoi); // truy cap TRUC TIEP field outer
        }
    }
}
```

Khác với static nested class, **inner class luôn gắn liền với một object cụ thể** của class ngoài
(gọi là "outer instance") — bên trong `CauThu`, bạn dùng được trực tiếp field `tenDoi` của
`DoiBongDa`, dù không truyền nó vào constructor `CauThu` — Java tự "nhớ" `CauThu` này thuộc về
object `DoiBongDa` nào. Muốn tạo inner class **từ bên ngoài** class chứa nó, cú pháp cần một object
outer đứng trước:

```java
DoiBongDa doi = new DoiBongDa("FC Java");
DoiBongDa.CauThu ct = doi.new CauThu("Le Van C", 9); // outerObject.new InnerClass(...)
```

**Khi nào dùng loại nào?** Nếu class lồng cần truy cập dữ liệu của outer instance và luôn "thuộc
về" một outer cụ thể (như `CauThu` luôn thuộc về một `DoiBongDa`), dùng inner class. Nếu class lồng
không cần gì từ outer instance, chỉ đơn thuần liên quan chủ đề, dùng static nested class — đây là
lựa chọn phổ biến hơn trong thực tế vì đơn giản hơn.

## Anonymous class — tạo object "ngay tại chỗ"

```java
HanhDong chao = new HanhDong() {
    @Override
    public void thucHien() {
        System.out.println("Xin chao tu anonymous class!");
    }
};
chao.thucHien();
```

**Anonymous class** (lớp vô danh) tạo ra **một object duy nhất** implements một interface (hoặc
kế thừa một class) **ngay tại nơi khai báo**, không cần đặt tên class riêng ở đâu khác. Cú pháp:
`new <Interface hoặc Class>() { <phần thân override> }`.

Dùng phổ biến nhất khi bạn cần một hành vi **chỉ dùng đúng một lần**, không đáng để tạo hẳn một
class riêng có tên:

```java
static void thucHienHanhDong(HanhDong hd) {
    hd.thucHien();
}

// goi truc tiep voi mot anonymous class lam doi so:
thucHienHanhDong(new HanhDong() {
    @Override
    public void thucHien() {
        System.out.println("Hanh dong duoc truyen truc tiep vao tham so!");
    }
});
```

**Ghi chú hướng tới sau này**: từ Chương 30, bạn sẽ học **lambda expression** — cú pháp Java hiện
đại hơn nhiều để thay thế phần lớn trường hợp dùng anonymous class implements interface chỉ có
**một phương thức** (như `HanhDong` ở đây). Chương này dạy anonymous class trước vì (1) nó vẫn xuất
hiện rất nhiều trong code Java hiện có, và (2) hiểu anonymous class giúp lambda ở Chương 30 trở nên
dễ hiểu hơn nhiều (lambda gần như chỉ là "anonymous class viết gọn hơn" cho đúng một trường hợp
riêng).

## Enum nâng cao: field, constructor, phương thức riêng

Bạn có thể đã thấy enum dạng đơn giản (`enum NgayTrongTuan { THU_HAI, THU_BA, ... }`). Enum trong
Java thực chất **mạnh hơn nhiều** — về bản chất, mỗi hằng số enum **là một object**, và bản thân
`enum` là một dạng class đặc biệt, có thể có field, constructor, phương thức như class bình thường:

```java
public enum HanhTinh {
    TRAI_DAT(5.976e24, 6.37814e6),
    SAO_HOA(6.421e23, 3.3972e6),
    SAO_MOC(1.9e27, 7.1492e7);

    private final double khoiLuong;
    private final double banKinh;

    HanhTinh(double khoiLuong, double banKinh) {
        this.khoiLuong = khoiLuong;
        this.banKinh = banKinh;
    }

    public double tinhTrongLuongBeMat() {
        return 6.673E-11 * khoiLuong / (banKinh * banKinh);
    }
}
```

Mỗi hằng số (`TRAI_DAT`, `SAO_HOA`...) tự gọi **constructor tương ứng đúng một lần** khi enum được
nạp. Constructor của enum **luôn ngầm định `private`** — không có cách nào tự `new HanhTinh(...)`
từ bên ngoài; danh sách hằng số khai báo sẵn chính là **toàn bộ** các object của enum này tồn tại
trong suốt chương trình.

Gọi phương thức: `HanhTinh.TRAI_DAT.tinhTrongLuongBeMat()`. Duyệt qua mọi hằng số bằng
`HanhTinh.values()` (trả về mảng chứa tất cả hằng số, theo đúng thứ tự khai báo).

### Enum với phương thức riêng cho từng hằng số (constant-specific class body)

```java
public enum PhuongThucThanhToan {
    TIEN_MAT {
        @Override
        public void xuLy(double soTien) {
            System.out.println("Nhan " + soTien + " tien mat.");
        }
    },
    THE_TIN_DUNG {
        @Override
        public void xuLy(double soTien) {
            double phi = soTien * 0.02;
            System.out.println("Tru the " + (soTien + phi));
        }
    };

    public abstract void xuLy(double soTien);
}
```

Khi mỗi hằng số cần **hành vi hoàn toàn khác nhau** cho cùng một phương thức, thay vì viết một
phương thức chung chứa `if/else`/`switch` dài dằng dặc kiểm tra "hằng số nào đang gọi", mỗi hằng số
tự cung cấp **thân hàm riêng** ngay sau tên nó. Đây là một cách dùng enum khá nâng cao nhưng rất phổ
biến trong code thực tế — bạn sẽ nhận ra nó là một dạng cụ thể của **đa hình** (Chương 17) áp dụng
cho enum: gọi `PhuongThucThanhToan.TIEN_MAT.xuLy(...)` và `THE_TIN_DUNG.xuLy(...)` giống hệt cách
gọi, nhưng mỗi hằng số tự chạy đúng logic riêng.

## Bài tập

1. Thêm static nested class `DoiBongDa.HuanLuyenVien` với field `ten`, `soNamKinhNghiem`.
2. Viết enum `MucDoUuTien` gồm `THAP`, `TRUNG_BINH`, `CAO`, mỗi hằng số có field `heSoCanhBao`
   (int), phương thức `hienThiCanhBao()` in ra thông báo khác nhau tuỳ mức độ.
3. Viết một anonymous class implements `Comparator`-giống-tự-định-nghĩa (tạo interface riêng
   `SoSanh { int soSanh(int a, int b); }`), truyền vào một phương thức nhận tham số kiểu đó để sắp
   xếp một mảng `int[]` theo thứ tự giảm dần (gợi ý: viết thuật toán sắp xếp đơn giản như
   bubble sort, gọi `soSanh` để quyết định thứ tự thay vì so sánh `<`/`>` trực tiếp).

## Lỗi thường gặp

- **Cố `new InnerClass(...)` trực tiếp mà không qua object outer** — inner class (không static)
  **luôn** cần một outer instance đứng trước: `outerObject.new InnerClass(...)`, hoặc tạo từ bên
  trong chính outer class như code mẫu.
- **Dùng inner class khi không thực sự cần truy cập dữ liệu outer** — gây phức tạp không cần thiết
  (bắt buộc phải có outer instance mới tạo được). Nếu class lồng không cần gì từ outer, dùng
  `static class` sẽ đơn giản hơn.
- **Cố gọi `new PhuongThucThanhToan(...)`** — enum không bao giờ `new` được từ bên ngoài, kể cả khi
  có constructor với tham số. Danh sách hằng số khai báo sẵn là cách duy nhất có object của enum đó.
- **Quên `abstract` cho phương thức chung khi dùng constant-specific class body** — nếu enum có
  phương thức mà **mọi** hằng số phải tự override riêng (như `xuLy` ở trên), phương thức đó phải
  khai báo `abstract` trong thân enum, nếu không trình biên dịch không bắt buộc từng hằng số phải
  cung cấp thân hàm riêng.
