# Chương 18 — Code mẫu: Abstract class & Interface

```
javac *.java
java Main
```

- `HinhHoc.java` — abstract class, có phương thức `abstract` (`tinhDienTich`, `tinhChuVi` — bắt
  buộc lớp con override) trộn lẫn với phương thức thường đã có sẵn thân hàm (`inThongTin`).
- `CoTheVe.java` — interface với phương thức abstract mặc định (`ve()`), một `default` method
  (`veDamNet()` — có sẵn thân hàm, lớp implements kế thừa luôn), và một `static` method
  (`inHuongDan()` — gọi qua tên interface).
- `CoTheSoSanhDienTich.java` — interface thứ hai, minh hoạ một class `implements` được **nhiều**
  interface cùng lúc (`HinhTron`, `HinhChuNhat` implements cả `CoTheVe` lẫn
  `CoTheSoSanhDienTich`), điều `extends` (kế thừa class) không cho phép.
- `Main.java` — minh hoạ không thể `new` trực tiếp abstract class, đa hình qua abstract class
  (giống Chương 17 nhưng với `HinhHoc`), gọi default/static method của interface.
