# Chương 32 — Code mẫu: Optional

```
javac OptionalDemo.java
java OptionalDemo
```

So sánh cách xử lý giá trị "có thể không tồn tại" theo kiểu cũ (trả về `null`, dễ quên kiểm tra,
dòng bị comment minh hoạ `NullPointerException` nếu bỏ comment) với `Optional`: tạo (`of`/`empty`/
`ofNullable`), kiểm tra (`isPresent`/`isEmpty`), lấy giá trị an toàn (`orElse`/`orElseGet`), thực
hiện hành động có điều kiện (`ifPresent`/`ifPresentOrElse`), biến đổi giá trị bên trong (`map`), và
mẫu thiết kế phương thức trả về `Optional<T>` thay vì `T` có nguy cơ `null`.
