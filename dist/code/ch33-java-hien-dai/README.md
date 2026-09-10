# Chương 33 — Code mẫu: Java hiện đại

```
javac *.java
java VarDemo
java RecordDemo
java SealedDemo
```

- `VarDemo.java` — `var` suy luận kiểu, kiểu vẫn cố định sau khi suy luận (không phải kiểu động),
  và các giới hạn của `var` (chỉ dùng cho biến cục bộ có khởi tạo ngay).
- `RecordDemo.java` — `record` tự sinh constructor/getter/`equals`/`hashCode`/`toString`, so sánh
  với việc tự viết tay ở Chương 20; compact constructor để kiểm tra hợp lệ.
- `SealedDemo.java` — `sealed interface` giới hạn danh sách lớp con hợp lệ (`permits`), kết hợp
  pattern matching cho `switch` (Java 21+) để xử lý từng loại mà không cần `instanceof` + ép kiểu
  thủ công như Chương 17, và không cần nhánh `default` vì trình biên dịch biết chắc danh sách đầy
  đủ.
