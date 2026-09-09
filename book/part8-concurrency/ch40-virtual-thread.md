# Chương 40 — Giới thiệu Virtual Thread

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu vấn đề mà Virtual Thread (Java 21+) giải quyết: giới hạn số lượng thread thông thường của
  hệ thống.
- Tạo và dùng virtual thread bằng `Thread.ofVirtual()` và
  `Executors.newVirtualThreadPerTaskExecutor()`.
- Biết khi nào virtual thread thực sự hữu ích, khi nào không tạo ra khác biệt.

Code mẫu đầy đủ: [`code/ch40-virtual-thread/`](../../code/ch40-virtual-thread/). Cần **Java 21**
trở lên để chạy.

## Giới hạn của thread thông thường (platform thread)

Mọi `Thread` từ Chương 37-39 đến giờ đều là **platform thread** — mỗi platform thread tương ứng
**trực tiếp** với một thread thật của hệ điều hành bên dưới. Hệ điều hành chỉ quản lý hiệu quả được
một số lượng thread **giới hạn** (thường vài nghìn trên một máy chủ thông thường) — mỗi thread
chiếm bộ nhớ riêng cho call stack (nhắc lại Chương 29), và việc hệ điều hành chuyển đổi qua lại
giữa hàng nghìn thread cũng tốn chi phí đáng kể.

Vấn đề lộ rõ với các hệ thống cần xử lý **rất nhiều** tác vụ đang **chờ I/O** cùng lúc (chờ phản
hồi mạng, chờ đọc file, chờ truy vấn database) — ví dụ một server cần phục vụ 50.000 kết nối đồng
thời, mỗi kết nối phần lớn thời gian chỉ đang **chờ**, không thực sự tính toán gì. Tạo 50.000
platform thread cho tình huống này thường **không khả thi** về tài nguyên hệ thống.

## Virtual Thread — thread "nhẹ", do JVM quản lý

```java
Thread virtualThread = Thread.ofVirtual().start(() -> {
    System.out.println("Chay tren: " + Thread.currentThread());
});
virtualThread.join();
```

**Virtual thread** (Java 21+, chính thức ổn định) là một loại thread **do JVM tự quản lý**, không
ánh xạ trực tiếp 1-1 với thread hệ điều hành. JVM có thể tạo ra **hàng triệu** virtual thread mà
không gặp vấn đề tài nguyên như platform thread, vì nhiều virtual thread **chia sẻ chung** một số
lượng nhỏ platform thread thực sự bên dưới — khi một virtual thread đang **chờ** (ví dụ chờ I/O),
JVM "gỡ" nó khỏi platform thread bên dưới, để platform thread đó rảnh ra phục vụ virtual thread
khác đang cần chạy, rồi "gắn lại" khi virtual thread ban đầu có việc để tiếp tục.

Từ góc độ code, **cách viết gần như không khác gì** platform thread — đây là điểm thiết kế cố ý của
virtual thread: bạn không cần học lại mô hình lập trình mới.

## `Executors.newVirtualThreadPerTaskExecutor()`

```java
try (ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 10_000; i++) {
        pool.submit(() -> {
            Thread.sleep(100); // mo phong cho I/O
        });
    }
}
```

Khác với `Executors.newFixedThreadPool(4)` ở Chương 39 (giới hạn cố định số lượng thread thực sự
được tạo), `newVirtualThreadPerTaskExecutor()` tạo **một virtual thread mới cho mỗi tác vụ**, không
giới hạn số lượng cố định — vì virtual thread "rẻ" hơn platform thread rất nhiều, cách tiếp cận này
khả thi ở quy mô hàng chục nghìn tác vụ đồng thời mà `newFixedThreadPool` không đáp ứng nổi.

Từ Java 19, `ExecutorService` cũng implements `AutoCloseable` (Chương 21) — dùng được trong
`try-with-resources`, tự động chờ mọi tác vụ đã nộp hoàn thành rồi mới đóng pool khi ra khỏi khối
`try`, không cần tự gọi `shutdown()`/`awaitTermination()` tường minh như Chương 39.

`AtomicInteger` trong code mẫu (`demHoanThanh.incrementAndGet()`) là một biến đếm **an toàn với đa
luồng** có sẵn trong thư viện chuẩn — nhắc lại race condition ở Chương 38: dùng `int` thường và
`demHoanThanh++` trực tiếp ở đây sẽ gặp đúng vấn đề race condition đã học, `AtomicInteger` cung cấp
sẵn thao tác tăng giá trị **an toàn** mà không cần tự viết `synchronized`.

## Khi nào virtual thread thực sự hữu ích?

**Virtual thread giúp ích rõ rệt nhất với khối lượng lớn tác vụ dành phần lớn thời gian chờ I/O**
(mạng, file, database) — đúng như minh hoạ ở `ScaleComparisonDemo.java`. **Virtual thread KHÔNG
giúp tăng tốc** các tác vụ tính toán nặng CPU (ví dụ tính toán số học phức tạp liên tục, không chờ
đợi gì) — với loại tác vụ này, số lượng platform thread hiệu quả vẫn bị giới hạn bởi số lõi CPU
thực có trên máy, dùng virtual thread cho trường hợp này không mang lại lợi ích, đôi khi còn phức
tạp hoá code không cần thiết.

**Tóm gọn**: virtual thread giải quyết vấn đề *số lượng* thread đồng thời cho tác vụ chờ I/O nhiều,
không giải quyết vấn đề *tốc độ tính toán*.

## Bài tập

1. Chạy `ScaleComparisonDemo.java`, thử tăng `soTacVu` lên `100_000`, quan sát chương trình vẫn
   chạy được và đo thời gian hoàn thành.
2. Thử thay `Executors.newVirtualThreadPerTaskExecutor()` bằng
   `Executors.newFixedThreadPool(200)` (Chương 39) trong cùng bài toán 10.000 tác vụ — so sánh thời
   gian chạy giữa hai cách (với `soTacVu` lớn, pool cố định phải "xếp hàng" xử lý dần, trong khi
   virtual thread xử lý gần như đồng thời hết).
3. Viết một tác vụ tính toán nặng CPU đơn giản (ví dụ tính số Fibonacci lớn bằng vòng lặp, không có
   `sleep`/chờ I/O nào), so sánh thời gian chạy 8 tác vụ như vậy bằng virtual thread và bằng
   `newFixedThreadPool` bằng đúng số lõi CPU của máy bạn — quan sát virtual thread không tạo ra
   khác biệt đáng kể trong trường hợp này.

## Lỗi thường gặp

- **Dùng virtual thread cho tác vụ tính toán nặng CPU, mong đợi tăng tốc** — không có tác dụng như
  mong đợi, vì giới hạn thực sự nằm ở số lõi CPU, không phải số lượng thread có thể tạo ra.
- **Chạy code Chương này trên Java cũ hơn 21** — `Thread.ofVirtual()` và
  `newVirtualThreadPerTaskExecutor()` không tồn tại ở các bản Java cũ hơn, lỗi biên dịch
  `cannot find symbol`. Xác nhận `java -version` cho ra bản 21 trở lên trước khi chạy.
- **Đồng bộ hoá (`synchronized`, Chương 38) quá rộng trên virtual thread trong khối lượng lớn** —
  một số kỹ thuật đồng bộ hoá cũ có thể làm giảm bớt lợi ích của virtual thread trong tình huống cụ
  thể (chi tiết kỹ thuật sâu, nằm ngoài phạm vi sách nhập môn — chỉ cần biết đây là điều cần lưu ý
  khi làm việc ở quy mô rất lớn trong thực tế).
- **Tưởng virtual thread thay thế hoàn toàn `ExecutorService`/`CompletableFuture` (Chương 39)** —
  virtual thread là một **loại thread mới**, vẫn dùng chung với các API đã học ở Chương 39, không
  phải một mô hình lập trình hoàn toàn khác cần học lại từ đầu.
