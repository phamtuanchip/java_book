# Chương 43 — Debug & logging

## Mục tiêu học

Sau chương này, bạn sẽ:

- Dùng debugger trong IDE thành thạo hơn nữa (mở rộng từ Chương 4): watch expression, conditional
  breakpoint, Step Into/Over/Out.
- Hiểu vì sao `System.out.println` không đủ tốt để theo dõi chương trình chạy trong thực tế.
- Dùng `java.util.logging` để ghi log có mức độ (level), có sẵn trong JDK không cần thư viện ngoài.
- Biết về SLF4J + Logback/Log4j2 — bộ công cụ logging phổ biến trong dự án thực tế.

Code mẫu đầy đủ: [`code/ch43-debug-logging/`](../../code/ch43-debug-logging/).

## Debug nâng cao trong IDE

Chương 4 đã giới thiệu breakpoint, Step Over, Step Into. Vài kỹ thuật debug nâng cao hữu ích thêm:

- **Step Out (Shift+F8)** — khi đang Step Into bên trong một phương thức, Step Out chạy nốt phần
  còn lại của phương thức đó rồi quay về nơi gọi nó, không cần Step Over từng dòng còn lại.
- **Watch expression** — thêm một biểu thức tuỳ ý (không chỉ biến đơn lẻ, ví dụ `mang.length - i`)
  vào panel Debug để IDE tự tính và hiển thị giá trị hiện tại mỗi lần dừng lại.
- **Conditional breakpoint** — chuột phải vào breakpoint, thêm điều kiện (ví dụ `i == 3`) — chương
  trình chỉ thực sự dừng lại khi điều kiện đó đúng, rất hữu ích khi lỗi chỉ xảy ra ở một vòng lặp cụ
  thể trong hàng nghìn lần lặp, tránh phải bấm Step Over hàng nghìn lần.

Thực hành trực tiếp với `BuggyProgram.java` (xem hướng dẫn từng bước trong README code mẫu) — đây
là kỹ năng chỉ thành thạo qua thực hành, không chỉ đọc lý thuyết.

## Vì sao `System.out.println` không đủ cho theo dõi chương trình thực tế

Từ đầu sách, bạn dùng `System.out.println` để vừa hiển thị kết quả **cho người dùng**, vừa để
**debug** khi cần xem giá trị biến. Cách này ổn cho bài tập nhỏ, nhưng có vấn đề khi chương trình
lớn/chạy lâu dài:

- **Không có mức độ ưu tiên** — không phân biệt được "thông tin bình thường" với "lỗi nghiêm trọng
  cần chú ý ngay", tất cả đều in y hệt nhau.
- **Không tắt/bật được theo nhóm** — muốn tạm ẩn bớt các dòng debug chi tiết mà vẫn giữ lại cảnh báo
  quan trọng, phải tự tay xoá/thêm lại từng dòng `println`.
- **Không có ngữ cảnh tự động** (thời gian, tên class/phương thức đang chạy, tên thread nếu đa
  luồng — Chương 37) — phải tự viết thêm vào mỗi dòng nếu cần.
- **Khó quản lý ở quy mô lớn** — hệ thống chạy hàng ngày, hàng tuần cần ghi lại lịch sử hoạt động
  có tổ chức (thường vào file, không phải chỉ hiện trên console rồi mất đi).

**Logging** (ghi log) là giải pháp chuyên dụng cho vấn đề này.

## `java.util.logging` — có sẵn trong JDK

```java
import java.util.logging.Logger;

Logger logger = Logger.getLogger(LoggingDemo.class.getName());

logger.fine("Chi tiet go loi");      // muc THAP nhat trong vi du nay
logger.info("Thong tin binh thuong");
logger.warning("Co the co van de");
logger.severe("Loi nghiem trong");    // muc CAO nhat
```

Các mức độ (level) từ thấp đến cao: `FINE` (chi tiết debug) → `INFO` (thông tin bình thường) →
`WARNING` (cảnh báo, chương trình vẫn chạy tiếp được) → `SEVERE` (lỗi nghiêm trọng). Có thể **lọc**
theo mức tối thiểu:

```java
logger.setLevel(Level.WARNING); // chi ghi WARNING tro len, INFO/FINE bi AN
```

Đây chính là lợi ích cốt lõi so với `println`: khi triển khai thực tế, bạn có thể **tắt** log chi
tiết (`FINE`/`INFO`) để giảm nhiễu, chỉ giữ lại `WARNING`/`SEVERE` — mà **không cần sửa code**, chỉ
cần đổi cấu hình.

### Ghi log kèm exception

```java
try {
    // ...
} catch (ArithmeticException e) {
    logger.log(Level.SEVERE, "Loi khi tinh toan", e);
}
```

`logger.log(level, thongDiep, exception)` ghi lại **cả** thông điệp mô tả **lẫn** toàn bộ stack
trace của exception — hữu ích hơn nhiều so với chỉ `System.out.println(e.getMessage())` (chỉ có
thông điệp ngắn, mất hết thông tin về **nơi** lỗi xảy ra trong code).

## SLF4J + Logback/Log4j2 — bộ công cụ logging phổ biến trong thực tế

`java.util.logging` có sẵn trong JDK, nhưng phần lớn dự án Java thực tế dùng **SLF4J** (một
"giao diện" logging chung, không tự ghi log) kết hợp với một **cài đặt cụ thể** như **Logback**
hoặc **Log4j2** (thực sự ghi log ra file/console theo định dạng cấu hình được). Ý tưởng SLF4J tương
tự interface/implementation đã học ở Chương 18: code của bạn chỉ gọi qua API chung của SLF4J, không
phụ thuộc trực tiếp cài đặt cụ thể nào — có thể đổi Logback sang Log4j2 mà không cần sửa code gọi
log.

```java
// Voi SLF4J (can khai bao dependency qua Maven/Gradle - Chuong 42)
private static final Logger logger = LoggerFactory.getLogger(MyClass.class);
logger.info("He thong khoi dong xong sau {} ms", thoiGianKhoiDong);
```

Cú pháp `{}` là chỗ trống được điền giá trị tự động (tránh phải nối chuỗi `+` thủ công như
`println`), và **chỉ tính toán chuỗi thật sự khi cần ghi** (nếu mức log đó đang bị tắt, SLF4J không
lãng phí công sức tạo chuỗi). Việc thiết lập SLF4J/Logback cần khai báo dependency qua Maven/Gradle
(Chương 42) — sách giới thiệu ở đây để bạn nhận diện được khi gặp trong code thực tế/dự án nhóm,
không đi sâu cấu hình chi tiết (nằm ngoài phạm vi sách nhập môn).

## Bài tập

1. Mở `BuggyProgram.java` trong IDE, làm theo đúng các bước trong README để tìm và sửa lỗi bằng
   debugger — không được nhìn trước vào code để "biết trước" lỗi ở đâu, thực hành đúng quy trình.
2. Thêm logging (`java.util.logging`) vào một chương trình bạn đã viết ở chương trước (ví dụ
   `TaiKhoanNganHang` — Chương 14), ghi `INFO` khi giao dịch thành công, `WARNING` khi giao dịch bị
   từ chối (số dư không đủ...).
3. Thử đặt conditional breakpoint trong một vòng lặp `for` chạy 100 lần, chỉ dừng khi biến đếm bằng
   một giá trị cụ thể — xác nhận chương trình chạy nhanh qua 99 lần đầu và chỉ dừng đúng lần bạn
   chỉ định.

## Lỗi thường gặp

- **Để lại nhiều dòng `System.out.println` debug trong code sau khi đã sửa xong lỗi** — làm nhiễu
  output thực tế của chương trình, dễ quên xoá. Cân nhắc dùng logging (dễ tắt hàng loạt) thay vì
  `println` ngay từ đầu cho các thông tin chẩn đoán.
- **Debug bằng Run thay vì Debug** — breakpoint không có tác dụng gì nếu chạy ở chế độ Run thông
  thường (nhắc lại Chương 4).
- **Log ở mức `SEVERE` cho những tình huống không thực sự nghiêm trọng** (hoặc ngược lại, log lỗi
  thực sự nghiêm trọng ở mức `FINE`) — làm log mất tác dụng phân loại mức độ ưu tiên, khó lọc ra vấn
  đề thực sự quan trọng khi hệ thống có vấn đề.
- **Log thông tin nhạy cảm** (mật khẩu, số thẻ tín dụng, token...) — log thường được lưu trữ lâu
  dài và nhiều người có thể xem được, đây là lỗi bảo mật nghiêm trọng thường gặp trong thực tế. Luôn
  cân nhắc kỹ dữ liệu nào an toàn để ghi log.
