# Chương 21 — Code mẫu: exception handling

```
javac *.java
java Main
```

Chạy tuần tự từng phần, đọc output song song với `book/part4-exceptions-generics/ch21-exception-handling.md`:

- `try/catch` cơ bản với unchecked exception (`ArithmeticException` từ chia cho 0).
- `finally` luôn chạy dù có lỗi hay không.
- Multi-catch (`catch (A | B e)`) bắt nhiều loại lỗi bằng một khối duy nhất.
- `SoDuKhongDuException` (checked exception tự định nghĩa) — `TaiKhoan.rutTien` khai báo
  `throws`, `Main` bắt buộc phải `try/catch` khi gọi.
- `try-with-resources` với `NguonTaiNguyen implements AutoCloseable` — tự động gọi `close()` khi ra
  khỏi khối `try`, kể cả khi có exception xảy ra bên trong.
