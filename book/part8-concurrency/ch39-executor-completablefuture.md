# Chương 39 — ExecutorService & CompletableFuture

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu vì sao tự tạo `Thread` thủ công cho từng tác vụ nhỏ không hiệu quả, và `ExecutorService`
  giải quyết vấn đề đó thế nào.
- Dùng `Future` để lấy kết quả từ một tác vụ chạy bất đồng bộ.
- Xây dựng pipeline xử lý bất đồng bộ bằng `CompletableFuture`.

Code mẫu đầy đủ: [`code/ch39-executor-completablefuture/`](../../code/ch39-executor-completablefuture/).

## Vấn đề với tự tạo Thread cho mọi tác vụ

Chương 37-38 tạo `Thread` trực tiếp cho từng tác vụ. Với **số lượng tác vụ nhỏ**, cách này ổn. Nhưng
với hệ thống xử lý **hàng nghìn tác vụ nhỏ liên tục** (ví dụ server xử lý nhiều yêu cầu cùng lúc),
tạo một `Thread` mới cho mỗi tác vụ rất **tốn kém**: mỗi thread chiếm một vùng bộ nhớ riêng (cho
call stack — nhắc lại Chương 29), và bản thân việc tạo/huỷ thread cũng có chi phí xử lý riêng.

## `ExecutorService` — thread pool tái sử dụng

```java
ExecutorService pool = Executors.newFixedThreadPool(4); // chi tao DUNG 4 thread

for (int i = 0; i < 10; i++) {
    pool.submit(() -> { /* tac vu */ });
}

pool.shutdown();
pool.awaitTermination(5, TimeUnit.SECONDS);
```

`ExecutorService` quản lý một **"hồ bơi" (pool) thread cố định**, tái sử dụng cho nhiều tác vụ liên
tiếp thay vì tạo mới mỗi lần — `submit(...)` nộp một tác vụ (`Runnable` hoặc `Callable`, xem phần
dưới) vào hàng đợi, một trong các thread có sẵn trong pool sẽ nhận và chạy nó khi rảnh.

- `shutdown()` — báo hiệu "không nhận tác vụ mới nữa", nhưng vẫn để các tác vụ **đã nộp** chạy hết.
- `awaitTermination(...)` — chờ tối đa một khoảng thời gian cho mọi tác vụ đã nộp chạy xong, trả về
  `true`/`false` tuỳ đã xong kịp hay chưa.

Với ví dụ 10 tác vụ, pool 4 thread: chỉ **4 thread thực sự được tạo**, mỗi thread xử lý tuần tự vài
tác vụ trong hàng đợi — tiết kiệm hơn hẳn tạo 10 thread riêng biệt.

## `Callable` và `Future` — tác vụ có giá trị trả về

Nhắc lại Chương 37: `Runnable.run()` không trả về giá trị gì. `Callable<T>` là phiên bản **có giá
trị trả về**:

```java
Callable<Integer> tinhToan = () -> {
    Thread.sleep(1000);
    return 42;
};

Future<Integer> ketQuaTuongLai = pool.submit(tinhToan);
System.out.println("Da nop tac vu, main tiep tuc chay ngay...");

int ketQua = ketQuaTuongLai.get(); // CHAN (block) o day, cho toi khi co ket qua
```

`submit(...)` trả về **ngay lập tức** một `Future<T>` — một "giấy hẹn" đại diện cho kết quả **sẽ
có trong tương lai**, không phải kết quả thật ngay lúc đó. Thread gọi `submit` **không bị chặn**,
tiếp tục chạy code tiếp theo bình thường. Chỉ khi gọi `.get()`, nếu tác vụ **chưa** chạy xong, thread
gọi `get()` mới thực sự **dừng lại chờ** cho tới khi có kết quả.

## `CompletableFuture` — xây dựng pipeline bất đồng bộ

`CompletableFuture` (Java 8+) mở rộng ý tưởng của `Future`, cho phép **nối tiếp nhiều bước xử lý**
theo phong cách gần giống Stream (Chương 31), mà không cần tự gọi `.get()` chặn giữa chừng từng
bước:

```java
CompletableFuture<Integer> pipeline = CompletableFuture
    .supplyAsync(() -> layDuLieuTho())   // chay BAT DONG BO tren mot thread khac
    .thenApply(giaTri -> giaTri * 2)      // BIEN DOI ket qua, giong map() cua Stream
    .thenApply(giaTri -> giaTri + 5);      // noi tiep them mot buoc bien doi nua

int ketQua = pipeline.get(); // chi CHO o buoc CUOI CUNG, khi thuc su can ket qua
```

`supplyAsync(...)` khởi động một tác vụ chạy bất đồng bộ (trên một thread pool mặc định do JVM quản
lý). Mỗi `thenApply(...)` nối thêm **một bước xử lý**, chỉ chạy **sau khi** bước trước đó đã có kết
quả — nhưng bản thân việc **nối các bước** không chặn thread gọi nó; toàn bộ pipeline chỉ thực sự
"chờ" khi bạn gọi `.get()` ở cuối.

### `thenCombine()` — kết hợp kết quả của hai future độc lập

```java
CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> tinhA());
CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> tinhB());

CompletableFuture<Integer> tongHop = f1.thenCombine(f2, (a, b) -> a + b);
```

`f1` và `f2` chạy **song song** (không tuần tự) — `thenCombine` chờ **cả hai** hoàn thành rồi mới
áp dụng hàm kết hợp. Đây là cách viết tự nhiên để tận dụng chạy song song nhiều tác vụ độc lập,
không cần tự quản lý `Thread`/`join()` thủ công như Chương 37.

### `exceptionally()` — xử lý lỗi trong pipeline bất đồng bộ

```java
CompletableFuture<Integer> coLoi = CompletableFuture
    .supplyAsync(() -> { throw new RuntimeException("Loi!"); })
    .exceptionally(loi -> {
        System.out.println("Da bat duoc loi: " + loi.getMessage());
        return -1; // gia tri THAY THE khi co loi
    });
```

Nếu bất kỳ bước nào trong pipeline ném exception, các bước `thenApply` tiếp theo **bị bỏ qua**,
pipeline "nhảy thẳng" tới khối `exceptionally` gần nhất (tương tự cách `try/catch` — Chương 21 —
bỏ qua phần còn lại của `try` khi có lỗi) — `exceptionally` cung cấp giá trị thay thế để pipeline
vẫn có kết quả cuối cùng thay vì crash hoàn toàn.

## Bài tập

1. Viết chương trình dùng `ExecutorService` với pool 3 thread, xử lý 20 "tác vụ" (mỗi tác vụ chỉ in
   ra số thứ tự và `Thread.sleep(100)`), đo tổng thời gian chạy bằng `System.nanoTime()` (Chương 6),
   so sánh với thời gian ước tính nếu chạy tuần tự hoàn toàn (20 × 100ms).
2. Viết pipeline `CompletableFuture` mô phỏng: "tải dữ liệu người dùng" → "tính điểm trung bình từ
   dữ liệu đó" → "định dạng kết quả thành chuỗi", mỗi bước `Thread.sleep` một chút để mô phỏng độ
   trễ thực tế.
3. Thử `thenCombine` với ba `CompletableFuture` độc lập thay vì hai (gợi ý: kết hợp từng cặp, hoặc
   tra cứu `CompletableFuture.allOf(...)`).

## Lỗi thường gặp

- **Quên gọi `shutdown()` trên `ExecutorService`** — các thread trong pool tiếp tục sống (chờ tác
  vụ mới), khiến chương trình **không tự kết thúc** dù mọi việc đã xong (JVM đợi mọi non-daemon
  thread kết thúc trước khi thoát).
- **Gọi `.get()` ngay sau `submit()`/`supplyAsync()` mà không tận dụng thời gian chờ để làm việc
  khác** — về kỹ thuật không sai, nhưng làm mất hoàn toàn lợi ích của việc chạy bất đồng bộ (tương
  đương lại chạy tuần tự như không dùng Future).
- **Không xử lý exception trong `CompletableFuture`** — nếu thiếu `exceptionally` (hoặc
  `handle`/`whenComplete`), lỗi trong pipeline sẽ được "gói" lại và chỉ lộ ra khi gọi `.get()` dưới
  dạng `ExecutionException` bọc lỗi gốc bên trong — dễ bỏ sót nếu không chủ động kiểm tra.
- **Chọn kích thước thread pool tuỳ tiện** — pool quá nhỏ khiến tác vụ phải chờ lâu trong hàng đợi;
  pool quá lớn (nhiều hơn hẳn số lõi CPU thực có) không giúp nhanh hơn mà còn tốn overhead quản lý
  thread. Với tác vụ chủ yếu chờ I/O (mạng, file), pool có thể lớn hơn số lõi CPU khá nhiều; với
  tác vụ tính toán nặng CPU, thường nên gần bằng số lõi CPU thực tế của máy.
