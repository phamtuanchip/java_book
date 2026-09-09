# Chương 45 — Code mẫu: SOLID

```
javac *.java
java SrpDemo
java OcpDemo
java LspDemo
java IspDemo
java DipDemo
```

Mỗi file minh hoạ **một** nguyên lý, theo cấu trúc "TRƯỚC (vi phạm)" rồi "SAU (tuân thủ)" ngay
trong cùng file, để đối chiếu trực tiếp:

- `SrpDemo.java` — Single Responsibility: tách `NhanVien`/`BaoCaoNhanVien`/`LuuTruBaoCao`.
- `OcpDemo.java` — Open/Closed: dùng đa hình (`Hinh` abstract, Chương 18) thay vì `if/else` theo
  chuỗi loại.
- `LspDemo.java` — Liskov Substitution: minh hoạ lỗi kinh điển `HinhVuong extends HinhChuNhat`.
- `IspDemo.java` — Interface Segregation: tách interface "béo" thành các interface nhỏ
  (`CoTheIn`/`CoTheScan`/`CoTheFax`).
- `DipDemo.java` — Dependency Inversion: `DichVuThongBao` phụ thuộc vào interface `KenhGui`, không
  phụ thuộc trực tiếp class cụ thể.
