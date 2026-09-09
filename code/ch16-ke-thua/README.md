# Chương 16 — Code mẫu: kế thừa

```
javac *.java
java Main
```

`NhanVien` là lớp cha, `NhanVienBanHang` và `NhanVienQuanLy` kế thừa (`extends`) từ nó, mỗi lớp con
override `tinhLuong()` theo quy tắc lương riêng, đồng thời tái sử dụng field/phương thức chung
(`ten`, `luongCoBan`, `getTen()`) mà không phải viết lại. `NhanVienQuanLy.tinhLuong()` minh hoạ gọi
`super.tinhLuong()` để tái sử dụng logic của lớp cha thay vì chép lại.
