# Chương 36 — Serialization cơ bản

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu serialization là gì, dùng `Serializable` để ghi/đọc object xuống/từ file.
- Dùng `serialVersionUID` và `transient` đúng cách.
- Biết khi nào **nên** và **không nên** dùng serialization kiểu Java gốc trong dự án thực tế.

Code mẫu đầy đủ: [`code/ch36-serialization/`](../../code/ch36-serialization/).

## Serialization là gì?

**Serialization** (tuần tự hoá) là quá trình chuyển một **object đang tồn tại trong bộ nhớ** thành
một dạng có thể **lưu trữ** (xuống file) hoặc **truyền đi** (qua mạng), rồi khôi phục lại
(**deserialization**) thành object y hệt trạng thái ban đầu ở nơi khác/thời điểm khác. Bạn đã thấy
một dạng serialization ở Chương 35 (chuyển object thành JSON) — Java còn có cơ chế serialization
**riêng, gắn liền với ngôn ngữ**, dùng định dạng nhị phân của chính nó (không phải văn bản như
JSON).

## `Serializable` — interface đánh dấu

```java
public class NguoiDung implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ten;
    private int tuoi;
}
```

`Serializable` (trong `java.io`) là một **marker interface** — không có phương thức nào cần
override (khác với mọi interface đã học từ Chương 18), nó chỉ đơn thuần "đánh dấu" cho JVM biết
class này được phép serialize. Cố serialize một object của class **không** implements
`Serializable` sẽ ném `NotSerializableException` lúc chạy.

## Ghi và đọc object

```java
try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
    out.writeObject(nguoiDung);
}

NguoiDung ndDaDoc;
try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.ser"))) {
    ndDaDoc = (NguoiDung) in.readObject(); // BAT BUOC ep kieu - readObject() tra ve Object
}
```

`ObjectOutputStream.writeObject(...)` chuyển toàn bộ trạng thái của object (giá trị mọi field không
`transient` — xem phần dưới) thành dạng nhị phân, ghi xuống file. `ObjectInputStream.readObject()`
đọc ngược lại, khôi phục một object **mới**, có cùng giá trị field như object gốc lúc ghi —
`readObject()` trả về kiểu `Object` (Chương 20), nên luôn cần ép kiểu về đúng loại mong đợi.

## `serialVersionUID` — "phiên bản" của cấu trúc class

```java
private static final long serialVersionUID = 1L;
```

Khi đọc lại một file đã serialize từ trước, JVM kiểm tra `serialVersionUID` của class **hiện tại**
có khớp với giá trị được lưu trong file hay không — nếu không khớp, ném
`InvalidClassException`. Nếu bạn **không** khai báo `serialVersionUID` tường minh, Java **tự tính**
một giá trị dựa trên cấu trúc class hiện tại (tên field, kiểu, phương thức...) — hệ quả: chỉ cần
**sửa nhỏ** class sau này (thêm một field mới chẳng hạn), giá trị tự tính đó **thay đổi**, khiến
mọi file `.ser` cũ (serialize từ phiên bản class trước khi sửa) **không đọc lại được nữa**. Luôn
khai báo `serialVersionUID` tường minh (một số bất kỳ, thường bắt đầu từ `1L`) để chủ động kiểm
soát khi nào coi là "không tương thích", thay vì để Java tự quyết định.

## `transient` — loại trừ field khỏi serialization

```java
private transient String matKhauTamThoi;
```

Field đánh dấu `transient` **không được lưu** khi serialize — sau khi đọc lại (deserialize), field
đó luôn có giá trị mặc định (`null` với kiểu tham chiếu, `0`/`false` với kiểu nguyên thủy), bất kể
giá trị lúc ghi là gì. Dùng cho: dữ liệu **nhạy cảm** không nên lưu trữ lâu dài (mật khẩu tạm,
token phiên đăng nhập), hoặc dữ liệu **không có ý nghĩa** khi khôi phục ở ngữ cảnh khác (kết nối
mạng đang mở, file handle, các loại "tài nguyên sống" — Chương 21 — không thể "đóng băng" rồi khôi
phục nguyên trạng).

## Khi nào NÊN và KHÔNG NÊN dùng Java serialization

**Java serialization (`Serializable`) ngày càng ít được khuyến khích dùng trong dự án thực tế
hiện đại**, vì một số lý do quan trọng:

- **Định dạng nhị phân riêng của Java** — chỉ chương trình Java khác mới đọc lại được, không trao
  đổi được với hệ thống viết bằng ngôn ngữ khác (JSON ở Chương 35 không có hạn chế này — bất kỳ
  ngôn ngữ nào cũng đọc/ghi được JSON).
- **Vấn đề bảo mật đã biết** — `readObject()` có thể bị lợi dụng để thực thi mã độc nếu chương
  trình deserialize dữ liệu từ nguồn **không đáng tin cậy** (ví dụ nhận dữ liệu serialize từ người
  dùng qua mạng) — đây là một trong những lớp lỗ hổng bảo mật Java nổi tiếng nhất trong lịch sử,
  từng ảnh hưởng nhiều hệ thống lớn.
- **Khó quản lý phiên bản** — như đã thấy với `serialVersionUID`, thay đổi cấu trúc class theo thời
  gian dễ gây lỗi tương thích ngược với dữ liệu cũ.

**Vẫn hợp lý để dùng** trong các trường hợp hẹp: lưu trạng thái tạm thời **nội bộ** trong cùng một
hệ thống Java (cache, session tạm), hoặc học tập/thử nghiệm nhanh không cần trao đổi dữ liệu ra
ngoài. Với hầu hết nhu cầu lưu trữ/trao đổi dữ liệu thực tế (file cấu hình, API, cơ sở dữ liệu),
**JSON (Chương 35) hoặc lưu vào cơ sở dữ liệu (Chương 46 — JDBC) là lựa chọn phù hợp hơn nhiều.**

## Bài tập

1. Chạy `SerializationDemo.java`, xác nhận `matKhauTamThoi` là `null` sau khi đọc lại — thử xoá từ
   khoá `transient` khỏi field đó, chạy lại, quan sát giờ nó **được** lưu và khôi phục đúng.
2. Thử đổi `serialVersionUID` thành một giá trị khác **sau khi** đã ghi file `.ser` (không xoá file
   cũ), chạy lại phần đọc — quan sát `InvalidClassException` xảy ra.
3. Viết một class `DonHang` chứa một field `List<String>` các sản phẩm — xác nhận `List` (interface
   chuẩn của Java, và `ArrayList` — cài đặt cụ thể của nó) cũng implements `Serializable` sẵn, nên
   serialize được bình thường mà không cần thêm gì đặc biệt.

## Lỗi thường gặp

- **Quên `implements Serializable`** — `NotSerializableException` lúc chạy khi cố `writeObject`.
- **Không khai báo `serialVersionUID`, sửa class sau đó, không đọc lại được file cũ** — luôn khai
  báo tường minh ngay từ đầu, xem phần giải thích ở trên.
- **Cố serialize object có field tham chiếu tới một object của class KHÔNG implements
  `Serializable`** — ném `NotSerializableException`, chỉ ra đúng class gây lỗi trong thông báo. Mọi
  field tham chiếu (không phải `transient`) cũng phải trỏ tới object serialize được.
- **Deserialize dữ liệu từ nguồn không tin cậy** (nhận qua mạng từ người dùng bên ngoài) — rủi ro
  bảo mật nghiêm trọng đã nêu ở trên; không bao giờ làm điều này trong hệ thống thực tế mà không có
  các lớp phòng vệ bổ sung nghiêm ngặt (nằm ngoài phạm vi sách nhập môn này).
