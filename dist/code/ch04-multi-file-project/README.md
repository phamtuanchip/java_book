# Chương 4 — Code mẫu: project nhiều file

Chương trình gồm 2 file: `MathUtils.java` (các phương thức tiện ích) và `Main.java` (điểm bắt đầu
chạy, gọi sang `MathUtils`). Minh hoạ việc biên dịch nhiều file cùng lúc, và cách IntelliJ IDEA tự
làm việc này khi bạn bấm nút Run.

## Chạy bằng dòng lệnh

```
javac *.java
java Main
```

`javac *.java` biên dịch **tất cả** file `.java` trong thư mục cùng lúc — cần thiết vì `Main.java`
phụ thuộc vào `MathUtils.java`. Nếu chỉ chạy `javac Main.java`, trình biên dịch vẫn tự tìm và biên
dịch `MathUtils.java` vì phát hiện nó được gọi tới — nhưng thói quen tốt là biên dịch rõ ràng tất
cả file khi project có nhiều file.

## Chạy trong IntelliJ IDEA

Mở thư mục này như một project (hoặc tạo project mới rồi copy 2 file vào thư mục `src`), nhấn nút
▶ (Run) cạnh phương thức `main` trong `Main.java`. IDE tự biên dịch mọi file liên quan và chạy —
đây chính là điều IDE làm thay bạn so với gõ `javac`/`java` tay.

## Kết quả mong đợi

```
4 binh phuong la 16
7 binh phuong la 49
a co phai so chan? true
b co phai so chan? false
```
