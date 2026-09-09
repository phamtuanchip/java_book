# Phụ lục A — Bảng tổng hợp lỗi thường gặp & cách xử lý

Tra cứu nhanh theo **tên lỗi/triệu chứng**. Mỗi mục ghi rõ chương giải thích chi tiết, để quay lại
đọc kỹ nếu cần.

## Lỗi biên dịch (compile-time)

| Thông báo/triệu chứng | Nguyên nhân thường gặp | Chương |
|---|---|---|
| `class X is public, should be declared in a file named X.java` | Tên file không khớp tên class `public` | 3 |
| `';' expected` | Thiếu dấu chấm phẩy cuối câu lệnh | 3 |
| `Error: Main method not found` | Sai chữ ký `public static void main(String[] args)` | 3 |
| `incompatible types: possible lossy conversion` | Gán kiểu lớn (`double`) cho biến kiểu nhỏ (`int`) mà không ép kiểu | 5 |
| `cannot assign a value to final variable` | Gán lại giá trị cho biến `final` | 5 |
| `unreachable catch block` | Đặt `catch` lớp cha trước lớp con | 21 |
| `missing return statement` | Có nhánh logic không trả về giá trị dù kiểu trả về khác `void` | 10 |
| `variable might not have been initialized` | Dùng biến cục bộ trước khi gán giá trị lần đầu | 5, 10 |
| Lambda báo lỗi liên quan biến ngoài | Biến bị gán lại ở đâu đó (vi phạm "effectively final") | 30 |
| `X is abstract; cannot be instantiated` | Cố `new` trực tiếp một `abstract class` | 18 |
| `constructor ... cannot be applied to given types` | Quên gọi `super(...)` đúng tham số khi lớp cha không có constructor rỗng | 16 |
| `package ... does not exist` / `cannot find symbol` | Sai cấu trúc thư mục so với tên package, hoặc quên biên dịch đủ file | 15 |
| `could not resolve dependency` (Maven/Gradle) | Sai `groupId`/`artifactId`/`version` của thư viện | 42 |

## Exception lúc chạy (runtime) — theo tên

| Exception | Ý nghĩa | Nguyên nhân thường gặp | Chương |
|---|---|---|---|
| `NullPointerException` | Gọi phương thức/truy cập field trên biến `null` | Quên `new`, hoặc `Map.get()`/tìm kiếm trả về `null` | 13, 20, 26 |
| `ArrayIndexOutOfBoundsException` | Truy cập chỉ số mảng ngoài phạm vi `[0, length)` | Sai điều kiện vòng lặp (`<=` thay vì `<`) | 9 |
| `IndexOutOfBoundsException` | Tương tự trên, nhưng với `List` | `get`/`remove` sai chỉ số | 24 |
| `StringIndexOutOfBoundsException` | `substring`/`charAt` sai chỉ số | Tính sai vị trí bắt đầu/kết thúc | 11 |
| `ClassCastException` | Ép kiểu (downcasting) sai loại thực sự của object | Không kiểm tra `instanceof` trước khi ép kiểu | 17 |
| `ArithmeticException` | Chia số nguyên cho 0 | Thiếu kiểm tra mẫu số trước khi chia | 6, 21 |
| `NumberFormatException` | `Integer.parseInt`/`Double.parseDouble` với chuỗi không đúng định dạng số | Dữ liệu đầu vào không sạch (CSV, input người dùng) | 21, 35, 47 |
| `InputMismatchException` | `Scanner.nextInt()`/`nextDouble()` khi người dùng nhập sai kiểu | Không validate trước khi đọc | 5 |
| `ConcurrentModificationException` | Sửa collection trong lúc for-each đang duyệt nó | Dùng `Iterator.remove()` thay vì sửa trực tiếp | 26, 27 |
| `UnsupportedOperationException` | Sửa một collection/danh sách bất biến | `List.of(...)`, `Collections.unmodifiableList(...)`, field `record` | 24, 27, 33 |
| `IllegalStateException` | Gọi `Iterator.remove()` không đúng sau một lần `next()` | Gọi `remove()` hai lần liên tiếp, hoặc chưa gọi `next()` | 27 |
| `NoSuchElementException` | Gọi `Optional.get()` khi rỗng | Không kiểm tra `isPresent()`/dùng `orElse` trước | 32 |
| `StackOverflowError` | Đệ quy không có (hoặc sai) điều kiện dừng | Thiếu/base case sai trong hàm đệ quy | 29 |
| `IllegalThreadStateException` | Gọi `start()` hai lần trên cùng một `Thread` | Thread chỉ chạy được đúng một lần | 37 |
| `NotSerializableException` | Serialize object của class không `implements Serializable` | Quên khai báo, hoặc field tham chiếu tới class không serialize được | 36 |
| `InvalidClassException` | `serialVersionUID` không khớp khi đọc lại file `.ser` | Sửa cấu trúc class mà không cập nhật `serialVersionUID` | 36 |
| `ClassNotFoundException` | Thiếu thư viện trong classpath | Quên `-cp` trỏ tới file `.jar` cần thiết | 35, 41 |
| `SQLException: No suitable driver found` | Thiếu JDBC driver trong classpath | Quên thêm driver database vào `-cp`/dependency | 46 |

## Lỗi logic (không có exception, kết quả sai)

| Triệu chứng | Nguyên nhân thường gặp | Chương |
|---|---|---|
| Phép chia ra kết quả bị cắt, không có phần thập phân | Chia hai số `int`, phép chia xảy ra trước khi gán cho `double` | 6 |
| So sánh `String` bằng `==` "có lúc đúng có lúc sai" | Nên dùng `.equals()`, không dùng `==`, cho mọi so sánh nội dung `String` | 11 |
| Sửa object qua biến này ảnh hưởng biến khác | Gán tham chiếu (`b = a`) không sao chép, cả hai trỏ chung object | 9, 13 |
| Field không được gán đúng giá trị trong constructor | Quên `this.` khi tham số trùng tên field | 13 |
| `switch` chạy "thừa" qua nhánh không mong muốn | Quên `break` trong `switch` statement (fall-through) | 7 |
| `HashSet`/`HashMap` chứa "trùng lặp" dù nội dung giống hệt | Object chưa override `equals`/`hashCode` đúng cách | 20, 25, 26 |
| Kết quả tính toán đa luồng sai, khác nhau mỗi lần chạy | Race condition — nhiều thread cùng sửa dữ liệu chung không đồng bộ hoá | 38 |
| Chương trình treo vĩnh viễn, không exception | Deadlock — hai thread khoá chéo nhau | 38 |
| Dữ liệu bị tách sai cột khi đọc CSV | Giá trị chứa dấu phẩy bên trong, `split(",")` đơn giản không xử lý được | 35 |
| Truy vấn SQL trả về nhiều/sai dữ liệu bất thường | SQL injection — nối chuỗi trực tiếp dữ liệu không đáng tin cậy vào câu SQL | 46 |

## Quy trình gỡ lỗi tổng quát (khi chưa biết lỗi thuộc loại nào)

1. **Đọc kỹ thông báo lỗi** — tên exception, thông điệp, và **dòng code** được chỉ ra trong stack
   trace thường trỏ thẳng tới (hoặc rất gần) nguyên nhân.
2. **Tra bảng ở trên** theo tên exception/triệu chứng để biết nhóm nguyên nhân phổ biến.
3. **Dùng debugger** (Chương 4, 43) — đặt breakpoint gần dòng gây lỗi, Step Over/Into để quan sát
   giá trị biến thực tế so với kỳ vọng.
4. Nếu là lỗi logic không có exception, viết **unit test** (Chương 41) tái hiện tình huống lỗi ở
   quy mô nhỏ nhất có thể, dễ cô lập nguyên nhân hơn chạy cả chương trình lớn.
