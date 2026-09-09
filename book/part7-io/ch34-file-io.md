# Chương 34 — File I/O: java.io và java.nio.file

## Mục tiêu học

Sau chương này, bạn sẽ:

- Đọc/ghi file bằng cách cổ điển (`java.io`: `BufferedReader`/`BufferedWriter`).
- Đọc/ghi file bằng cách hiện đại, ngắn gọn hơn (`java.nio.file`: `Files`, `Path`).
- Hiểu `IOException` là checked exception (nhắc lại Chương 21), và luôn dùng `try-with-resources`
  khi làm việc với file.

Code mẫu đầy đủ: [`code/ch34-file-io/`](../../code/ch34-file-io/).

## Cách cổ điển: `java.io`

```java
try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
    writer.write("Dong thu nhat");
    writer.newLine();
    writer.write("Dong thu hai");
} catch (IOException e) {
    System.out.println("Loi khi ghi file: " + e.getMessage());
}
```

`FileWriter` mở file để ghi; bọc thêm `BufferedWriter` bên ngoài để **ghi theo vùng đệm** (buffer)
— gom nhiều thao tác ghi nhỏ lại rồi ghi xuống đĩa một lần, hiệu quả hơn nhiều so với ghi trực tiếp
từng ký tự/dòng một xuống đĩa (thao tác đĩa vật lý chậm hơn thao tác bộ nhớ rất nhiều lần).
`writer.newLine()` xuống dòng — khác với `println` (Chương 3), `write` **không tự động** xuống
dòng, phải gọi `newLine()` tường minh.

**`IOException`** — nhắc lại từ Chương 21: đây là **checked exception**, vì thao tác file có thể
thất bại vì nhiều lý do nằm ngoài tầm kiểm soát của code (hết dung lượng ổ đĩa, mất quyền ghi, file
bị chương trình khác khoá...) — Java buộc bạn phải chủ động xử lý khả năng này, không cho phép bỏ
qua.

```java
try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
    String dong;
    while ((dong = reader.readLine()) != null) {
        System.out.println(dong);
    }
} catch (IOException e) {
    System.out.println("Loi khi doc file: " + e.getMessage());
}
```

Mẫu `while ((dong = reader.readLine()) != null)` là khuôn mẫu **kinh điển** để đọc file theo từng
dòng trong Java: `readLine()` trả về `null` khi đã đọc hết file — vòng lặp vừa **gán** giá trị đọc
được cho `dong`, vừa **kiểm tra** điều kiện dừng trong cùng một biểu thức.

## Cách hiện đại: `java.nio.file`

```java
Path duongDan = Path.of("data.txt");

Files.write(duongDan, List.of("Dong 1", "Dong 2", "Dong 3"));       // ghi TOAN BO 1 lan
List<String> cacDong = Files.readAllLines(duongDan);                 // doc TOAN BO thanh List<String>
String noiDung = Files.readString(duongDan);                         // doc TOAN BO thanh 1 String (Java 11+)
```

`java.nio.file` (New I/O, hoàn thiện dần từ Java 7 đến Java 11) cung cấp cách viết **ngắn gọn hơn
nhiều** cho các tác vụ file phổ biến — không cần tự quản lý vòng lặp đọc từng dòng hay
`BufferedReader`/`BufferedWriter` thủ công cho những trường hợp đơn giản. `Path` là kiểu hiện đại
thay thế `File` (lớp cũ từ `java.io`), dùng `Path.of(...)` để tạo.

### Ghi thêm vào cuối file (append)

```java
Files.writeString(duongDan, "\nDong moi", StandardOpenOption.APPEND);
```

Mặc định, `Files.write`/`Files.writeString` **ghi đè toàn bộ** nội dung file cũ. Truyền thêm
`StandardOpenOption.APPEND` để **thêm vào cuối** thay vì ghi đè.

### Các thao tác tiện ích khác

```java
Files.exists(duongDan);  // file co ton tai khong?
Files.delete(duongDan);  // xoa file
```

## Khi nào chọn cách nào?

| Tình huống | Nên chọn |
|---|---|
| File nhỏ, đọc/ghi toàn bộ nội dung một lần | `java.nio.file` (`Files.readAllLines`/`readString`/`write`) — ngắn gọn nhất |
| File lớn, cần xử lý từng dòng khi đọc (không muốn nạp cả file vào bộ nhớ) | `java.io` (`BufferedReader`) hoặc `Files.lines(...)` (trả về `Stream<String>`, Chương 31) |
| Code cũ đã có sẵn, cần đọc hiểu/bảo trì | `java.io` — vẫn rất phổ biến trong code hiện có |

## Bài tập

1. Viết chương trình đọc một file văn bản (tự tạo file mẫu vài dòng), đếm tổng số dòng và tổng số
   ký tự, dùng cả hai cách (`java.io` và `java.nio.file`) để so sánh độ ngắn gọn.
2. Viết chương trình ghi danh sách điểm số (nhập từ `Scanner`, Chương 5) ra file, mỗi điểm một
   dòng, dùng `Files.write`.
3. Viết chương trình đọc lại file điểm số ở bài 2, tính điểm trung bình (gợi ý: đọc bằng
   `Files.readAllLines`, dùng `Stream` — Chương 31 — để `map` từng dòng `String` sang `Double` rồi
   `reduce`/tính trung bình).

## Lỗi thường gặp

- **`FileNotFoundException`** (một loại `IOException`) khi đọc file không tồn tại — kiểm tra
  `Files.exists(...)` trước, hoặc bắt exception và xử lý phù hợp (ví dụ tạo file mới nếu hợp lý).
- **Quên `try-with-resources`, tự quản lý `close()` thủ công rồi quên gọi khi có lỗi giữa chừng** —
  dẫn tới rò rỉ tài nguyên (file handle không được giải phóng). Luôn ưu tiên `try-with-resources`
  (Chương 21) với `java.io`.
- **Ghi đè nhầm file quan trọng vì quên `Files.write` mặc định ghi đè toàn bộ nội dung cũ** — dùng
  `StandardOpenOption.APPEND` nếu ý định là thêm vào, không phải thay thế.
- **Đọc file rất lớn bằng `Files.readAllLines`/`readString` (nạp toàn bộ vào bộ nhớ cùng lúc)** —
  với file quá lớn so với bộ nhớ máy, có thể gây `OutOfMemoryError`. Với file lớn, ưu tiên đọc theo
  dòng (`BufferedReader` hoặc `Files.lines(...)` trả về Stream, xử lý tuần tự không nạp hết vào bộ
  nhớ cùng lúc).
- **Đường dẫn file tương đối (`"data.txt"`) chạy đúng trong IDE nhưng lỗi khi chạy bằng dòng lệnh từ
  thư mục khác** — đường dẫn tương đối luôn tính từ **thư mục làm việc hiện tại** (nơi bạn gõ lệnh
  `java`), không phải thư mục chứa file `.java`/`.class`. Dùng đường dẫn tuyệt đối nếu cần chắc
  chắn vị trí file, hoặc luôn chạy từ đúng thư mục dự kiến.
