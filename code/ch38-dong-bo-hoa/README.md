# Chương 38 — Code mẫu: đồng bộ hoá

```
javac *.java
java RaceConditionDemo
java SynchronizedDemo
java LockDemo
java DeadlockDemo
```

- `RaceConditionDemo.java` — 10 thread cùng tăng một biến `int` **không** đồng bộ hoá — kết quả
  thực tế gần như luôn **nhỏ hơn** kết quả mong đợi, và khác nhau giữa các lần chạy. Đây là
  **race condition**.
- `SynchronizedDemo.java` — sửa đúng bằng `synchronized` (cả phương thức lẫn khối lệnh) — kết quả
  luôn đúng, mọi lần chạy.
- `LockDemo.java` — cách tương đương dùng `ReentrantLock`, minh hoạ `tryLock()`.
- `DeadlockDemo.java` — minh hoạ deadlock có chủ đích (hai thread khoá chéo nhau) — chương trình sẽ
  "treo" thật sự trong vài giây trước khi tự thoát cưỡng bức bằng `System.exit(0)` để không làm
  treo terminal của bạn.
