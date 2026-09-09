# Chương 19 — Code mẫu: lớp lồng, anonymous class, enum nâng cao

```
javac *.java
java Main
```

- `DoiBongDa.java` — minh hoạ **inner class** (`CauThu`, gắn với một object `DoiBongDa` cụ thể,
  truy cập trực tiếp field outer) và **static nested class** (`ThongKe`, độc lập, không cần object
  outer).
- `HanhDong.java` + phần "Anonymous class" trong `Main.java` — tạo object implements interface
  ngay tại chỗ, không đặt tên class riêng.
- `HanhTinh.java` — enum nâng cao có field, constructor, phương thức riêng (khác enum liệt kê tên
  đơn giản).
- `PhuongThucThanhToan.java` — enum với phương thức `abstract`, mỗi hằng số tự cung cấp thân hàm
  riêng (constant-specific class body).
