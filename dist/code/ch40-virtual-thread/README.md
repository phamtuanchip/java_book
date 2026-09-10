# Chương 40 — Code mẫu: Virtual Thread

**Yêu cầu Java 21 trở lên** để chạy các ví dụ trong chương này.

```
javac *.java
java VirtualThreadBasicsDemo
java ScaleComparisonDemo
```

- `VirtualThreadBasicsDemo.java` — tạo virtual thread bằng `Thread.ofVirtual()`, so sánh với
  platform thread thông thường (Chương 37), kiểm tra bằng `isVirtual()`.
- `ScaleComparisonDemo.java` — chạy 10.000 tác vụ mô phỏng chờ I/O bằng
  `Executors.newVirtualThreadPerTaskExecutor()` — số lượng mà nếu dùng platform thread thông thường
  cho mỗi tác vụ một thread riêng sẽ dễ gây cạn kiệt tài nguyên hệ thống.
