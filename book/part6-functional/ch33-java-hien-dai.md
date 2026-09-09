# Chương 33 — Java hiện đại: var, record, sealed, pattern matching

## Mục tiêu học

Sau chương này, bạn sẽ:

- Dùng `var` đúng chỗ, hiểu nó không phải "kiểu động".
- Dùng `record` thay cho class dữ liệu bất biến, tránh code lặp lại đã phải tự viết ở Chương 20.
- Hiểu `sealed` giới hạn tường minh danh sách lớp con hợp lệ.
- Dùng pattern matching cho `switch` (Java 21+) — cách viết hiện đại thay cho `instanceof` + ép
  kiểu thủ công đã học ở Chương 17.

Code mẫu đầy đủ: [`code/ch33-java-hien-dai/`](../../code/ch33-java-hien-dai/).

> Đây là chương tổng hợp các tính năng hiện đại của Java (11-21) mà các chương trước đã vài lần
> nhắc trước tên và hẹn giải thích đầy đủ ở đây — bao gồm `record` (nhắc ở Chương 20),
> `sealed`/pattern matching cho `switch` (nhắc ở Chương 17).

## `var` — suy luận kiểu cục bộ (Java 10+)

```java
var ten = "Nguyen Van A";        // suy luan la String
var tuoi = 25;                    // suy luan la int
var danhSach = new ArrayList<String>(); // suy luan la ArrayList<String>
```

**Hiểu lầm phổ biến nhất cần tránh ngay**: `var` **không** biến Java thành ngôn ngữ kiểu động
(dynamic typing) như JavaScript/Python. Kiểu của biến vẫn **hoàn toàn cố định**, được trình biên
dịch **suy luận và gán chết** ngay tại dòng khai báo, dựa trên giá trị khởi tạo — bạn chỉ đơn giản
không phải **gõ ra** kiểu đó. Gán lại giá trị **khác kiểu** cho biến `var` sau đó vẫn là lỗi biên
dịch, y hệt như khai báo kiểu tường minh.

`var` hữu ích nhất khi kiểu vế phải đã **rõ ràng và dài dòng**, tránh lặp lại thừa thãi:

```java
Map<String, List<Integer>> banDo = new HashMap<String, List<Integer>>(); // lap lai kieu 2 LAN
var banDo = new HashMap<String, List<Integer>>();                          // gon hon, van RO RANG kieu gi
```

**Giới hạn của `var`**: chỉ dùng được cho **biến cục bộ có giá trị khởi tạo ngay tại chỗ khai báo**
— không dùng được cho field của class, tham số phương thức, hay khai báo biến không gán giá trị
ngay. Cũng nên **tránh lạm dụng** `var` khi kiểu vế phải không rõ ràng ngay từ tên biến/giá trị
(ví dụ `var ketQua = xuLy();` — nếu không đọc kỹ `xuLy()` trả về gì, code khó hiểu hơn so với ghi rõ
kiểu tường minh).

## `record` — class dữ liệu bất biến, không code lặp lại

Nhắc lại Chương 20: viết một class dữ liệu đúng cách đòi hỏi tự viết constructor, getter, `equals`,
`hashCode`, `toString` — khá nhiều code lặp đi lặp lại cho mọi class dữ liệu tương tự. `record`
(Java 16+) tự động sinh **toàn bộ** phần đó chỉ từ một dòng khai báo:

```java
record DiemToaDo(int x, int y) {
}
```

Dòng trên **tự động có sẵn**:

```java
DiemToaDo d = new DiemToaDo(3, 4);
d.x();          // getter TU SINH, KHONG co tien to "get" (khac quy uoc getter thong thuong)
d.y();
d.toString();   // "DiemToaDo[x=3, y=4]"
d.equals(...);  // so sanh theo NOI DUNG (tung field), giong het khuon mau Chuong 20
d.hashCode();   // nhat quan voi equals(), dung quy tac
```

`record` **luôn bất biến (immutable)** — không có setter, không có cách nào gán lại `x`/`y` sau khi
tạo object. Đây là lựa chọn thiết kế có chủ đích: `record` dành riêng cho các trường hợp bạn cần
một "gói dữ liệu" cố định, không cần (và không nên) thay đổi sau khi tạo.

### Compact constructor — kiểm tra hợp lệ

```java
record KhoangCach(double giaTri) {
    KhoangCach { // "compact constructor" - KHONG lap lai danh sach tham so trong ngoac
        if (giaTri < 0) {
            throw new IllegalArgumentException("Khoang cach khong the am");
        }
    }
}
```

Nếu cần kiểm tra tính hợp lệ (như validation ở setter Chương 14), `record` cho phép viết **compact
constructor** — không cần lặp lại `(double giaTri)` và `this.giaTri = giaTri;` (Java tự thêm phần
gán field ở cuối, sau đoạn kiểm tra bạn viết).

### Khi nào dùng `record`, khi nào dùng class thường?

Dùng `record` khi class **chỉ đơn thuần chứa dữ liệu bất biến** (tương tự các ví dụ `SanPham` ở
Chương 20, 31 — nếu viết lại bằng `record` sẽ ngắn hơn hẳn). Dùng class thường (Chương 13) khi cần
**field có thể thay đổi** (như `TaiKhoanNganHang` ở Chương 14, số dư thay đổi theo giao dịch), hoặc
cần logic kế thừa phức tạp (record không `extends` được class khác, dù vẫn `implements` được
interface).

## `sealed` — giới hạn tường minh danh sách lớp con (Java 17+)

```java
sealed interface HinhDang permits HinhTron, HinhVuong, HinhTamGiac {
}

record HinhTron(double banKinh) implements HinhDang { }
record HinhVuong(double canh) implements HinhDang { }
record HinhTamGiac(double day, double chieuCao) implements HinhDang { }
```

Nhắc lại Chương 18: `interface`/abstract class thông thường cho phép **bất kỳ** class nào
implements/extends nó, kể cả những class được viết ở nơi hoàn toàn khác, sau này. `sealed` **giới
hạn tường minh**: chỉ đúng các class liệt kê trong `permits` mới được phép implements/extends —
không class nào khác được phép, kể cả trong tương lai (trừ khi sửa lại chính khai báo `permits`).

## Pattern matching cho `switch` — kết hợp với `sealed`

```java
static double tinhDienTich(HinhDang hinh) {
    return switch (hinh) {
        case HinhTron h -> Math.PI * h.banKinh() * h.banKinh();
        case HinhVuong h -> h.canh() * h.canh();
        case HinhTamGiac h -> h.day() * h.chieuCao() / 2;
    };
}
```

So sánh với cách làm ở Chương 17 (`instanceof` + ép kiểu thủ công từng `if`), pattern matching cho
`switch` (Java 21+) gộp tất cả thành **một khối gọn gàng**: mỗi `case Kiểu bien -> ...` tự động
kiểm tra kiểu **và** ép kiểu vào `bien` cùng lúc.

**Lợi ích lớn nhất khi kết hợp với `sealed`**: trình biên dịch **biết chắc** danh sách `permits`
là đầy đủ mọi khả năng, nên **không đòi hỏi nhánh `default`** — nếu bạn (hoặc đồng nghiệp) sau này
thêm một `record HinhChuNhat implements HinhDang` mới vào `permits`, nhưng **quên** thêm `case`
tương ứng trong `tinhDienTich`, trình biên dịch sẽ **báo lỗi ngay** (switch không còn bao phủ đủ
mọi trường hợp) — thay vì để lỗi âm thầm lọt qua tới lúc chạy (như sẽ xảy ra nếu dùng
`if/else`/`instanceof` thông thường, dễ quên bổ sung nhánh mới).

## Bài tập

1. Viết `record NguoiDung(String ten, int tuoi)`, thêm compact constructor kiểm tra `tuoi >= 0`.
2. Thêm `HinhChuNhat` vào `permits` của `HinhDang` trong code mẫu, cập nhật `tinhDienTich` — quan
   sát nếu bạn **quên** cập nhật `tinhDienTich`, trình biên dịch báo lỗi ngay ("switch không bao
   phủ đủ mọi trường hợp").
3. Viết lại bài tập Chương 20 (class `SanPham` với `equals`/`hashCode`/`toString` tự viết tay) bằng
   `record`, so sánh độ dài code giữa hai cách.

## Lỗi thường gặp

- **Tưởng `var` cho phép đổi kiểu biến sau này** — kiểu vẫn cố định ngay từ lúc khai báo, `var` chỉ
  là cú pháp tắt, không phải kiểu động.
- **Dùng `var` cho biến không có giá trị khởi tạo ngay, hoặc cho field/tham số** — không hợp lệ,
  lỗi biên dịch. `var` chỉ dùng cho biến cục bộ có khởi tạo tại chỗ.
- **Cố sửa field của `record` sau khi tạo** — không có setter, đây là thiết kế cố ý (bất biến), lỗi
  biên dịch nếu cố gán trực tiếp (`d.x = 10;` không tồn tại cú pháp này với `record`).
- **Cố tạo một class implements một `sealed` interface mà không có trong `permits`** — lỗi biên
  dịch. Phải sửa `permits` của interface gốc để thêm class mới vào danh sách được phép.
- **Quên rằng pattern matching switch không cần `default` chỉ khi switch trên kiểu `sealed` đầy đủ
  mọi nhánh** — với các kiểu khác (không `sealed`), vẫn cần `default` hoặc bao phủ đủ mọi trường
  hợp có thể, nếu không trình biên dịch báo lỗi thiếu nhánh xử lý.
