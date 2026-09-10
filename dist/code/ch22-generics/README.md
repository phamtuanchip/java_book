# Chương 22 — Code mẫu: generics

```
javac *.java
java Main
```

- `HopChua.java` — generic class `HopChua<T>`, minh hoạ dùng với `String` và `Integer` mà không
  cần ép kiểu khi lấy giá trị ra, và trình biên dịch chặn ngay việc gán sai kiểu.
- `Utils.java` — generic method `inMoiPhanTu(T[] mang)` dùng chung cho mọi kiểu mảng, và wildcard
  `inGiaTriSo(HopChua<? extends Number> hop)` chấp nhận `HopChua` của bất kỳ kiểu con nào của
  `Number`.
- `HopSo.java` — bounded type `HopSo<T extends Number>`, cho phép gọi `giaTri.doubleValue()` bên
  trong class dù không biết trước `T` cụ thể là gì.

Hai dòng bị comment trong `Main.java` minh hoạ lỗi biên dịch khi vi phạm kiểu generic — thử bỏ
comment từng dòng để tự thấy thông báo lỗi.
