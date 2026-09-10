# Chương 7 — Code mẫu: if/else, switch

## `GradeClassifier.java`

```
javac GradeClassifier.java
java GradeClassifier
```

Nhập một điểm số (0-10), chương trình dùng chuỗi `if/else if/else` để xếp loại, và toán tử ba ngôi
(ternary) để rút gọn quyết định Đạt/Không đạt. Thử nhập `8.5`, `9.5`, `4.0` để thấy các nhánh khác
nhau.

## `DayOfWeekSwitch.java`

```
javac DayOfWeekSwitch.java
java DayOfWeekSwitch
```

So sánh 3 cách dùng `switch`:

1. `switch` statement kiểu cũ, cần `break` ở mỗi nhánh.
2. `switch` expression kiểu mới (`->`), trả giá trị trực tiếp, không cần `break`.
3. Gộp nhiều `case` dùng chung một khối xử lý (tính số ngày trong tháng).

Thử đổi giá trị `thu` hoặc `thang` ở đầu `main` rồi chạy lại để xem các nhánh khác.
