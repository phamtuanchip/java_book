# Chương 34 — Code mẫu: File I/O

```
javac *.java
java ClassicIODemo
java ModernNioDemo
```

- `ClassicIODemo.java` — cách cổ điển (`java.io`): `BufferedWriter`/`BufferedReader`, đọc từng
  dòng bằng vòng lặp `while ((dong = reader.readLine()) != null)`, `try-with-resources` (Chương 21)
  để tự đóng file.
- `ModernNioDemo.java` — cách hiện đại (`java.nio.file`): `Files.write`/`Files.readAllLines`/
  `Files.readString`, ghi thêm vào cuối file (`APPEND`), kiểm tra tồn tại, xoá file.

Cả hai chương trình đều tự tạo file `.txt` khi chạy (đã thêm vào `.gitignore`, không commit vào
repo) — chạy thử, mở file bằng trình soạn thảo bất kỳ để xem nội dung thực tế đã ghi.
