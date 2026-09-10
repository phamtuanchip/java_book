# Chương 37 — Code mẫu: Thread cơ bản

```
javac *.java
java ExtendsThreadDemo
java RunnableDemo
java ThreadLifecycleDemo
```

- `ExtendsThreadDemo.java` — tạo thread bằng cách `extends Thread`, override `run()`.
- `RunnableDemo.java` — cách được khuyến dùng hơn: `implements Runnable`, truyền vào `Thread`; kèm
  ví dụ dùng lambda (Chương 30) vì `Runnable` là functional interface.
- `ThreadLifecycleDemo.java` — quan sát `getState()` qua các giai đoạn (`NEW` → `RUNNABLE` →
  `TIMED_WAITING` khi `sleep` → `TERMINATED`), và minh hoạ thứ tự chạy giữa các thread độc lập
  **không được đảm bảo** — chạy nhiều lần để tự thấy thứ tự in ra "A"/"B" có thể khác nhau.
