# Chương 39 — Code mẫu: ExecutorService & CompletableFuture

```
javac *.java
java ExecutorServiceDemo
java FutureDemo
java CompletableFutureDemo
```

- `ExecutorServiceDemo.java` — thread pool cố định 4 thread xử lý 10 tác vụ, minh hoạ tái sử dụng
  thread thay vì tạo mới cho từng tác vụ.
- `FutureDemo.java` — `Callable` (có giá trị trả về, khác `Runnable` ở Chương 37), `submit()` trả
  về `Future` ngay lập tức, `get()` mới thực sự chờ kết quả.
- `CompletableFutureDemo.java` — xây dựng pipeline bất đồng bộ với `thenApply` (biến đổi, giống
  `map` của Stream — Chương 31), `thenCombine` (kết hợp hai future chạy song song), `exceptionally`
  (xử lý lỗi trong pipeline bất đồng bộ).
