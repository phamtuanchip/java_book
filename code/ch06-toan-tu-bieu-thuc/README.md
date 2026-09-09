# Chương 6 — Code mẫu: toán tử và biểu thức

```
javac OperatorsDemo.java
java OperatorsDemo
```

Chạy và đối chiếu từng dòng output với phần giải thích trong `book/part1-fundamentals/ch06-toan-tu-bieu-thuc.md`,
đặc biệt chú ý:

- `a / b` với `a`, `b` là `int` cho kết quả **số nguyên** (cắt phần dư), khác với `17.0 / 5`.
- `x++` (post-increment) trả về giá trị **trước khi** tăng, `++m` (pre-increment) trả về giá trị
  **sau khi** tăng — hai dòng in ra minh hoạ rõ khác biệt này.
- Đoạn short-circuit: `mang.length > 0 && mang[0] == 1` không bao giờ đánh giá `mang[0]` vì
  `mang.length > 0` đã là `false` — nếu Java không "tắt sớm" (short-circuit), dòng này sẽ ném lỗi
  truy cập mảng rỗng.
