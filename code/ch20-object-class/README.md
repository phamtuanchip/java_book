# Chương 20 — Code mẫu: lớp Object

```
javac *.java
java Main
```

`SanPham.java` override `toString()`, `equals()`, `hashCode()` (theo đúng quy tắc: hai object
`equals()` bằng nhau phải có `hashCode()` giống nhau), và implements `Comparable<SanPham>` để định
nghĩa thứ tự sắp xếp tự nhiên (theo giá). `Main.java` minh hoạ:

- `println(a)` tự động gọi `toString()`.
- `a == b` (false, khác object) khác `a.equals(b)` (true, nội dung giống nhau).
- `Arrays.sort(danhSach)` dùng `compareTo()` đã định nghĩa trong `SanPham`.
- Dùng `Comparator` (anonymous class) khi cần sắp xếp theo tiêu chí **khác** với thứ tự tự nhiên,
  mà không cần sửa class `SanPham`.

Cú pháp `Comparable<SanPham>`/`Comparator<SanPham>` (chữ trong dấu `< >`) là generics — sẽ học đầy
đủ ở Chương 22; ở đây chỉ cần biết nó nghĩa là "so sánh dành riêng cho kiểu `SanPham`".
