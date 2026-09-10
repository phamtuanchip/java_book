# Chương 28 — Code mẫu: thuật toán tìm kiếm

```
javac *.java
java LinearSearch
java BinarySearch
java JumpSearch
java InterpolationSearch
java SoSanhHieuNang
```

Bốn thuật toán tìm kiếm cơ bản, mỗi file có `main` riêng chạy được độc lập với ví dụ nhỏ.
`SoSanhHieuNang.java` đo thời gian thực tế của cả 4 thuật toán trên cùng một mảng 2 triệu phần tử
đã sắp xếp, tìm một phần tử gần cuối mảng (trường hợp xấu cho Linear Search) — chạy để tự thấy
chênh lệch tốc độ giữa `O(n)` và `O(log n)` không chỉ là lý thuyết suông.
