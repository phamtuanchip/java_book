# Chương 45 — Nguyên lý SOLID

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu và áp dụng được 5 nguyên lý SOLID — bộ nguyên tắc thiết kế OOP kinh điển, thường được hỏi
  trong phỏng vấn và dùng làm chuẩn tham chiếu khi review code trong công việc thực tế.
- Nhận diện code vi phạm từng nguyên lý, và biết cách tái cấu trúc (refactor) để tuân thủ.

Code mẫu đầy đủ: [`code/ch45-solid/`](../../code/ch45-solid/).

SOLID là 5 chữ cái đầu của 5 nguyên lý, do Robert C. Martin ("Uncle Bob") tổng hợp. Đây **không
phải quy tắc máy móc phải áp dụng mọi lúc** — chương 44 đã nhấn mạnh "không lạm dụng pattern", tinh
thần đó cũng áp dụng ở đây: SOLID là **kim chỉ nam** giúp nhận diện code có dấu hiệu thiết kế kém,
không phải luật phải tuân theo tuyệt đối trong mọi dòng code.

## S — Single Responsibility Principle (Nguyên lý đơn nhiệm)

**Một class chỉ nên có đúng một lý do để thay đổi.**

```java
// VI PHAM: mot class lam CA BA viec
class NhanVienViPham {
    String taoBaoCao() { ... }      // ly do thay doi 1: doi dinh dang bao cao
    void luuVaoFile(String d) { ... } // ly do thay doi 2: doi noi luu tru
}
```

Nếu một class đảm nhận nhiều trách nhiệm không liên quan (lưu dữ liệu, tính toán báo cáo, ghi
file...), **mỗi lý do thay đổi khác nhau đều buộc phải sửa cùng một class** — tăng rủi ro một thay
đổi nhỏ (ví dụ đổi định dạng báo cáo) vô tình ảnh hưởng phần không liên quan (dữ liệu nhân viên).
Giải pháp: tách thành các class riêng, mỗi class **một trách nhiệm rõ ràng** (`NhanVien`,
`BaoCaoNhanVien`, `LuuTruBaoCao`).

## O — Open/Closed Principle (Nguyên lý đóng/mở)

**Class nên mở cho việc mở rộng, nhưng đóng cho việc sửa đổi.**

```java
// VI PHAM: them loai hinh MOI phai SUA ham nay
double tinhDienTich(String loaiHinh, ...) {
    if (loaiHinh.equals("hinh_chu_nhat")) { ... }
    else if (loaiHinh.equals("hinh_tron")) { ... }
    // them hinh moi -> PHAI sua ham nay
}
```

Nhắc lại Chương 17-18: dùng **đa hình** thay cho `if/else`/`switch` liệt kê loại — mỗi loại hình là
một lớp con override `tinhDienTich()` riêng. Thêm loại hình mới = **thêm một class mới**, không cần
**sửa** bất kỳ code cũ nào đã hoạt động đúng (và đã được test — Chương 41) từ trước. "Đóng cho sửa
đổi" giảm rủi ro làm hỏng chức năng đang hoạt động khi mở rộng hệ thống.

## L — Liskov Substitution Principle (Nguyên lý thay thế Liskov)

**Object của lớp con phải thay thế được object của lớp cha mà không làm sai lệch hành vi mong đợi.**

```java
class HinhVuongViPham extends HinhChuNhatViPham {
    @Override void datChieuDai(double d) {
        this.chieuDai = d;
        this.chieuRong = d; // BAT NGO thay doi ca chieuRong!
    }
}
```

Đây là ví dụ kinh điển: về mặt toán học, hình vuông "là một" hình chữ nhật đặc biệt — có vẻ hợp lý
để `HinhVuong extends HinhChuNhat`. Nhưng `HinhChuNhat` "hứa hẹn" một hành vi ngầm định: đổi chiều
dài **không ảnh hưởng** chiều rộng. `HinhVuong` (để giữ tính chất "hình vuông") buộc phải **phá vỡ**
lời hứa ngầm đó — bất kỳ code nào viết đúng cho `HinhChuNhat` (như `kiemTraDienTich` trong code
mẫu) có thể cho kết quả **sai lệch bất ngờ** khi nhận vào một `HinhVuong`. Vi phạm LSP thường là
dấu hiệu **quan hệ kế thừa được chọn sai** — giải pháp ở đây: không ép `HinhVuong` kế thừa
`HinhChuNhat`, để cả hai độc lập cùng implements một interface `HinhDung` chung.

**Bài học tổng quát**: kế thừa nên phản ánh đúng quan hệ "là một" **theo hành vi**, không chỉ theo
trực giác/toán học bề ngoài. Nếu lớp con phải "ghi đè" hành vi theo cách khiến code viết đúng cho
lớp cha hoạt động sai, đó là dấu hiệu vi phạm LSP.

## I — Interface Segregation Principle (Nguyên lý phân tách interface)

**Không nên buộc một class implements những phương thức nó không dùng tới.**

```java
// VI PHAM: interface "beo", may in don gian buoc phai co ca scan()/fax()
interface MayVanPhongDaNangViPham {
    void in(String noiDung);
    void scan(String noiDung);
    void fax(String noiDung);
}
```

Một interface quá lớn, gộp nhiều khả năng không liên quan, buộc **mọi** class implements nó phải
cung cấp thân hàm cho **mọi** phương thức — kể cả những phương thức nó không thực sự hỗ trợ (dẫn
tới thân hàm rỗng vô nghĩa, hoặc ném `UnsupportedOperationException`, cả hai đều là dấu hiệu thiết
kế kém). Giải pháp: tách thành các interface **nhỏ, tập trung** (`CoTheIn`, `CoTheScan`,
`CoTheFax`), mỗi class chỉ implements đúng những interface phản ánh đúng khả năng thật của nó.

## D — Dependency Inversion Principle (Nguyên lý đảo ngược phụ thuộc)

**Module cấp cao không nên phụ thuộc trực tiếp vào module cấp thấp — cả hai nên phụ thuộc vào một
sự trừu tượng (abstraction) chung.**

```java
// VI PHAM: DichVuThongBao phu thuoc TRUC TIEP vao EmailSenderCuThe
class DichVuThongBaoViPham {
    private EmailSenderCuThe sender = new EmailSenderCuThe();
}
```

```java
// TUAN THU: ca hai phu thuoc vao interface KenhGui chung
class DichVuThongBao {
    private KenhGui kenh;
    DichVuThongBao(KenhGui kenh) { this.kenh = kenh; } // "tiem" phu thuoc tu ben ngoai
}
```

Đây chính là ứng dụng cụ thể của ý tưởng Strategy (Chương 44) và "lập trình theo interface" (Chương
24) vào mối quan hệ giữa các **thành phần lớn** của hệ thống: thay vì `DichVuThongBao` (logic
nghiệp vụ quan trọng, "cấp cao") tự tạo (`new`) trực tiếp một `EmailSenderCuThe` cụ thể ("cấp
thấp", chi tiết cài đặt), nó chỉ phụ thuộc vào **interface** `KenhGui` — và **nhận** cài đặt cụ thể
từ **bên ngoài** (qua constructor — kỹ thuật này gọi là **dependency injection**, "tiêm phụ thuộc").
Kết quả: đổi kênh gửi (Email → SMS) **không cần sửa** `DichVuThongBao` — đúng tinh thần OCP đã học
ở trên, áp dụng ở tầm module lớn hơn.

## SOLID và các chương trước — một bức tranh thống nhất

Nếu bạn thấy các nguyên lý SOLID "quen quen" — đúng vậy: chúng chính là sự **hệ thống hoá** những ý
tưởng đã rải rác xuất hiện xuyên suốt sách, từ đa hình (Chương 17), interface (Chương 18), tới
design pattern (Chương 44). SOLID không giới thiệu khái niệm hoàn toàn mới, mà đưa ra **5 góc nhìn
có tên gọi rõ ràng** để đánh giá một thiết kế OOP có "khoẻ mạnh" hay không — hữu ích nhất khi dùng
làm **danh sách kiểm tra (checklist)** lúc review code của chính bạn hoặc đồng nghiệp.

## Bài tập

1. Tìm trong code bạn đã viết ở các bài tập trước (bất kỳ chương nào) một chỗ có thể vi phạm SRP
   (một class làm quá nhiều việc không liên quan) — thử tách lại theo đúng tinh thần chương này.
2. Viết một ví dụ vi phạm OCP của riêng bạn (một hàm `switch` theo loại, dễ phải sửa khi thêm loại
   mới), rồi refactor sang dùng đa hình.
3. Giải thích bằng lời (không cần code): tại sao ví dụ `HinhVuong`/`HinhChuNhat` vi phạm LSP lại
   **không** xảy ra nếu cả hai chỉ implements chung một interface `HinhDung` có mỗi
   `tinhDienTich()`, thay vì kế thừa lẫn nhau?

## Lỗi thường gặp

- **Áp dụng SOLID cứng nhắc cho mọi đoạn code, kể cả code rất đơn giản** — tách một class 5 dòng
  thành 3 class riêng "cho đúng SRP" là phản tác dụng nếu độ phức tạp thực tế không cần tới. SOLID
  phục vụ việc **quản lý độ phức tạp khi nó thực sự tồn tại**, không phải mục tiêu tự thân.
- **Nhầm lẫn "một class một phương thức" với Single Responsibility** — SRP nói về "một lý do để
  thay đổi", không phải "chỉ một phương thức". Một class có nhiều phương thức vẫn tuân thủ SRP nếu
  tất cả phương thức đó phục vụ **cùng một trách nhiệm** gắn kết chặt chẽ.
- **Tạo interface cho mọi class "phòng khi cần sau này"** — vi phạm ngược lại tinh thần đơn giản:
  chỉ tạo trừu tượng hoá (interface, DIP) khi **thực sự có từ hai cài đặt cụ thể trở lên**, hoặc có
  lý do rõ ràng cần thay thế được trong tương lai gần — không phải "cứ thêm interface cho chắc".
- **Cố nhớ thuộc lòng 5 chữ SOLID mà không hiểu bản chất từng nguyên lý** — hiểu **vì sao** mỗi
  nguyên lý tồn tại (vấn đề gì nó giải quyết) quan trọng hơn nhiều so với thuộc lòng định nghĩa —
  quay lại đọc phần ví dụ "TRƯỚC/SAU" trong code mẫu bất cứ khi nào cần nhớ lại.
