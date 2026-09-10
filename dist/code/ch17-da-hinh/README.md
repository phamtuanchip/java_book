# Chương 17 — Code mẫu: đa hình

```
javac *.java
java Main
```

Tái sử dụng đúng 3 class `NhanVien`/`NhanVienBanHang`/`NhanVienQuanLy` từ Chương 16. Trọng tâm
chương này nằm ở `Main.java`:

- Mảng kiểu `NhanVien[]` chứa cả 3 loại object khác nhau (upcasting tự động).
- Vòng lặp gọi `tinhLuong()` **giống hệt nhau** trên mọi phần tử, nhưng mỗi phần tử tự chạy đúng
  phiên bản của lớp thật sự của nó — đây chính là đa hình.
- Minh hoạ downcasting có kiểm tra `instanceof` trước, và pattern matching cho `instanceof`
  (`nv3 instanceof NhanVienQuanLy nvql`) — cú pháp hiện đại gộp kiểm tra + ép kiểu.
- Hai dòng bị comment minh hoạ hai loại lỗi: gọi phương thức không có ở kiểu khai báo (lỗi biên
  dịch), và ép kiểu sai loại thật sự của object (lỗi runtime `ClassCastException`) — thử bỏ comment
  từng dòng để tự thấy.
