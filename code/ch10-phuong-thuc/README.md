# Chương 10 — Code mẫu: phương thức tĩnh

```
javac MethodsDemo.java
java MethodsDemo
```

Minh hoạ: phương thức không tham số/không trả về (`void`), phương thức có tham số và giá trị trả
về, overloading (`add` với 3 phiên bản khác tham số), phạm vi biến (biến `x` trong `main` và biến
`x` trong `scopeDemo` là hai biến hoàn toàn độc lập dù trùng tên), và điểm quan trọng nhất: Java
truyền tham số **theo giá trị** — với kiểu nguyên thủy, sửa tham số bên trong phương thức không ảnh
hưởng biến gốc bên ngoài; nhưng với mảng (kiểu tham chiếu), sửa **nội dung** bên trong phương thức
lại ảnh hưởng ra ngoài, vì cả hai đều trỏ tới cùng một mảng trong bộ nhớ.
