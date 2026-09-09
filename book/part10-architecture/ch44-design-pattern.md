# Chương 44 — Design pattern cơ bản

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu design pattern là gì — không phải "luật bắt buộc", mà là **giải pháp đã được đúc kết** cho
  các vấn đề thiết kế lặp đi lặp lại.
- Nhận diện và cài đặt được 4 pattern cơ bản, phổ biến nhất: Singleton, Factory, Strategy, Observer.
- Biết khi nào **nên** và **không nên** dùng mỗi pattern — tránh áp dụng máy móc.

Code mẫu đầy đủ: [`code/ch44-design-pattern/`](../../code/ch44-design-pattern/).

## Design pattern là gì?

Qua nhiều thập kỷ, cộng đồng lập trình nhận ra một số **vấn đề thiết kế lặp đi lặp lại** trong rất
nhiều dự án khác nhau, và đúc kết ra các **giải pháp chung** đã được kiểm chứng — gọi là **design
pattern** (mẫu thiết kế). Đây **không phải** đoạn code sao chép dán được, mà là **ý tưởng cấu trúc**
bạn tự áp dụng vào tình huống cụ thể của mình. Biết design pattern giúp bạn: (1) giải quyết vấn đề
nhanh hơn (không phải "phát minh lại bánh xe"), và (2) **giao tiếp hiệu quả hơn** với lập trình
viên khác — nói "dùng Observer ở đây" truyền đạt được rất nhiều ý nghĩa chỉ trong một từ.

## Singleton — đảm bảo chỉ có một instance

```java
class CauHinhUngDung {
    private static final CauHinhUngDung INSTANCE = new CauHinhUngDung();

    private CauHinhUngDung() { /* ... */ } // constructor PRIVATE

    public static CauHinhUngDung getInstance() {
        return INSTANCE;
    }
}
```

Dùng khi bạn cần **đảm bảo chỉ có đúng một** object của một class tồn tại trong suốt vòng đời
chương trình — ví dụ cấu hình ứng dụng, kết nối tới một tài nguyên chia sẻ duy nhất. Cách cài đặt
trên (tạo `INSTANCE` sẵn khi class được nạp) an toàn với đa luồng (Chương 38) mà **không cần**
`synchronized`, vì JVM tự đảm bảo việc nạp class (và khởi tạo field `static final`) chỉ xảy ra một
lần, thread-safe theo đặc tả ngôn ngữ.

**Cẩn trọng khi dùng**: Singleton dễ bị **lạm dụng** thành "biến toàn cục" (global state) — nhiều
Singleton trong một hệ thống lớn có thể khiến code các phần khác nhau **ngầm phụ thuộc** vào nhau
qua trạng thái chia sẻ, khó kiểm thử (Chương 41 — vì mỗi test không dễ có một `CauHinhUngDung`
"sạch" riêng) và khó suy luận. Chỉ dùng khi thực sự có **đúng một** thứ như vậy trong bản chất bài
toán, không dùng chỉ vì "tiện truy cập từ mọi nơi".

## Factory — gom logic tạo object

```java
class ThongBaoFactory {
    static ThongBao tao(String loai) {
        return switch (loai) {
            case "email" -> new ThongBaoEmail();
            case "sms" -> new ThongBaoSms();
            default -> throw new IllegalArgumentException("Loai khong ho tro: " + loai);
        };
    }
}
```

Dùng khi việc **quyết định tạo object loại cụ thể nào** khá phức tạp, hoặc có thể tạo bằng nhiều
cách/nhiều loại con — thay vì rải `new ThongBaoEmail()`/`new ThongBaoSms()` khắp nơi trong code gọi
(khiến mọi nơi gọi đều phải biết hết các lớp con cụ thể tồn tại), gom logic đó vào **một chỗ**. Nếu
sau này thêm loại thông báo mới, chỉ cần sửa **đúng một chỗ** (bên trong Factory), mọi nơi gọi
`ThongBaoFactory.tao(...)` không cần sửa gì.

## Strategy — hoán đổi thuật toán lúc chạy

```java
interface ChienLuocGiamGia {
    double apDung(double giaGoc);
}

class DonHang {
    private ChienLuocGiamGia chienLuoc;

    double tinhGiaCuoiCung() {
        return chienLuoc.apDung(giaGoc);
    }
}
```

Dùng khi có **nhiều cách** thực hiện cùng một việc (ở đây: tính giá sau giảm giá), và bạn muốn
**chọn/đổi** cách thực hiện **lúc chạy**, không cố định lúc viết code. So với viết một khối
`if/else`/`switch` dài liệt kê mọi loại giảm giá ngay trong `DonHang` (khiến `DonHang` phải "biết"
về mọi loại chiến lược, và phải sửa `DonHang` mỗi khi thêm loại mới), Strategy tách mỗi cách xử lý
thành một class riêng implements chung một interface — `DonHang` chỉ cần biết "có một
`ChienLuocGiamGia` nào đó", không cần biết là loại nào cụ thể (đây chính là **đa hình**, Chương 17,
áp dụng vào một tình huống thiết kế cụ thể).

Với chiến lược đơn giản (một dòng logic), dùng thẳng **lambda** (Chương 30) thay vì tạo hẳn một
class riêng — như minh hoạ ở cuối `StrategyDemo.java` (`gia -> gia * 0.5`).

## Observer — thông báo tự động khi trạng thái thay đổi

```java
interface NguoiQuanSat {
    void nhanThongBao(double giaMoi);
}

class CoPhieu {
    private List<NguoiQuanSat> danhSachQuanSat = new ArrayList<>();

    void dangKy(NguoiQuanSat nqs) {
        danhSachQuanSat.add(nqs);
    }

    void capNhatGia(double giaMoi) {
        this.gia = giaMoi;
        for (NguoiQuanSat nqs : danhSachQuanSat) {
            nqs.nhanThongBao(giaMoi);
        }
    }
}
```

Dùng khi một object (**subject**, ở đây là `CoPhieu`) cần **thông báo** cho một số lượng **không cố
định, có thể thay đổi** các object khác (**observer**) mỗi khi trạng thái của nó thay đổi — mà
`CoPhieu` **không cần biết trước** (lúc viết code) sẽ có bao nhiêu, hay những loại `NguoiQuanSat` cụ
thể nào sẽ đăng ký theo dõi nó. Đăng ký (`dangKy`) và huỷ đăng ký (`huyDangKy`) có thể thực hiện
**bất kỳ lúc nào** trong lúc chương trình chạy.

Pattern này là nền tảng của rất nhiều hệ thống lớn hơn: xử lý sự kiện giao diện người dùng (nhấn
nút → thông báo cho các "listener" đã đăng ký), hệ thống pub/sub trong kiến trúc phân tán.

## Nguyên tắc quan trọng nhất: không lạm dụng pattern

Đây là điều cần khắc sâu hơn cả 4 pattern cụ thể: **design pattern là công cụ giải quyết vấn đề cụ
thể, không phải mục tiêu để "chứng tỏ" bạn biết pattern**. Áp dụng pattern khi bài toán **thực sự
có** đặc điểm phù hợp (nhiều cách xử lý cần hoán đổi → Strategy; nhiều observer không xác định
trước → Observer...) — cố nhét pattern vào bài toán đơn giản không cần tới nó chỉ làm code **phức
tạp hơn** một cách không cần thiết, vi phạm trực tiếp nguyên tắc "giữ thiết kế đơn giản nhất có thể
cho vấn đề đang giải quyết" mà chính các pattern này hướng tới phục vụ.

## Bài tập

1. Viết một `HinhFactory` (tương tự `ThongBaoFactory`) tạo các loại `HinhHoc` từ Chương 18
   (`HinhTron`, `HinhChuNhat`) dựa trên tên loại truyền vào dạng chuỗi.
2. Mở rộng `StrategyDemo.java` thêm một chiến lược `GiamGiaTheoBac` (giảm % khác nhau tuỳ khoảng
   giá trị đơn hàng — ví dụ dưới 100k không giảm, 100k-500k giảm 5%, trên 500k giảm 10%).
3. Viết một `Observer` mới cho `ObserverDemo.java`: `LichSuGiaCoPhieu` ghi lại toàn bộ lịch sử các
   mức giá đã thông báo vào một `List<Double>`, có phương thức in ra toàn bộ lịch sử.

## Lỗi thường gặp

- **Dùng Singleton cho mọi class "chỉ cần một object" một cách máy móc**, kể cả khi không thực sự
  cần chia sẻ trạng thái toàn cục — làm code khó kiểm thử hơn không cần thiết. Cân nhắc có thực sự
  cần **đúng một** instance duy nhất trong toàn bộ chương trình hay không.
- **Factory chỉ có một loại object duy nhất để tạo** — không cần Factory, `new` trực tiếp là đủ.
  Factory chỉ thực sự có giá trị khi việc chọn loại cụ thể có logic đáng gom lại.
- **Strategy với chỉ một chiến lược duy nhất, không bao giờ đổi** — thêm interface không cần thiết,
  chỉ nên áp dụng khi thực sự có nhiều cách xử lý cần hoán đổi.
- **Observer nhưng subject và observer phụ thuộc chặt vào nhau** (observer cần biết quá nhiều chi
  tiết nội bộ của subject để xử lý đúng) — làm mất đi lợi ích tách rời (decoupling) chính là mục
  tiêu cốt lõi của pattern này.
- **Quên `huyDangKy` khi observer không còn cần theo dõi nữa** — nếu subject sống lâu hơn observer
  (ví dụ observer là một phần giao diện đã bị đóng), observer "chết" nhưng vẫn còn trong danh sách
  đăng ký gây rò rỉ bộ nhớ (memory leak) — vấn đề thực tế phổ biến khi dùng Observer pattern.
