# Chương 43 — Code mẫu: Debug & logging

## `BuggyProgram.java` — thực hành debug trong IDE

Chương trình có lỗi cố ý (`ArrayIndexOutOfBoundsException`). Mở trong IntelliJ IDEA:

1. Đặt breakpoint tại dòng `tong += mang[i];`.
2. Chạy ở chế độ **Debug** (🐞), không phải Run.
3. Dùng **Step Over (F8)** để chạy từng vòng lặp, quan sát giá trị `i` và `mang.length` trong panel
   Variables.
4. Nhận ra: khi `i == mang.length` (tức `i == 5`), vòng lặp vẫn chạy tiếp (vì điều kiện `i <=
   mang.length`), gây truy cập `mang[5]` — vượt quá chỉ số hợp lệ (0-4).
5. Sửa `<=` thành `<`, chạy lại (Run bình thường), xác nhận kết quả đúng là `8.0`.

```
javac BuggyProgram.java
java BuggyProgram
```

## `LoggingDemo.java` — chạy trực tiếp, dùng `java.util.logging` có sẵn trong JDK

```
javac LoggingDemo.java
java LoggingDemo
```

Minh hoạ các mức độ log (`fine`/`info`/`warning`/`severe`), ghi log kèm exception, và lọc bớt log
theo mức độ tối thiểu (`setLevel`).
