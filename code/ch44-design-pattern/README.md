# Chương 44 — Code mẫu: design pattern cơ bản

```
javac *.java
java SingletonDemo
java FactoryDemo
java StrategyDemo
java ObserverDemo
```

- `SingletonDemo.java` — `CauHinhUngDung` chỉ có đúng một instance, constructor `private`, truy
  cập qua `getInstance()`.
- `FactoryDemo.java` — `ThongBaoFactory.tao(loai)` gom logic tạo object vào một chỗ, code gọi không
  cần biết chi tiết class cụ thể nào đang được tạo.
- `StrategyDemo.java` — `DonHang` có thể đổi `ChienLuocGiamGia` bất kỳ lúc nào, kể cả bằng lambda
  (Chương 30) cho chiến lược đơn giản, không cần tạo hẳn class riêng.
- `ObserverDemo.java` — `CoPhieu` (subject) tự động thông báo cho danh sách `NguoiQuanSat`
  (observer) đã đăng ký mỗi khi giá thay đổi.
