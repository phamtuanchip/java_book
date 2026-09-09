# Chương 38 — Đồng bộ hoá: synchronized, Lock

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu **race condition** là gì, tự tay tái hiện được lỗi này.
- Sửa race condition bằng `synchronized` (phương thức và khối lệnh).
- Dùng `ReentrantLock` như một lựa chọn thay thế linh hoạt hơn `synchronized`.
- Hiểu **deadlock** là gì, nguyên nhân gây ra nó, và cách phòng tránh cơ bản.

Code mẫu đầy đủ: [`code/ch38-dong-bo-hoa/`](../../code/ch38-dong-bo-hoa/).

## Race condition — khi nhiều thread cùng sửa chung dữ liệu

```java
static int dem = 0;

// 10 thread, moi thread chay: for (...) { dem++; }
```

Trực giác thông thường: nếu 10 thread mỗi thread tăng `dem` đúng 100.000 lần, kết quả cuối cùng
phải là `1.000.000`. **Thực tế không phải vậy** — chạy `RaceConditionDemo.java` để tự thấy kết quả
thường **nhỏ hơn** con số mong đợi, và **khác nhau** giữa các lần chạy.

Nguyên nhân: `dem++` **trông như một thao tác**, nhưng thực chất gồm **ba bước** ở tầng thấp hơn:
(1) đọc giá trị hiện tại của `dem`, (2) cộng thêm 1, (3) ghi giá trị mới trở lại. Nếu hai thread
cùng thực hiện ba bước này **xen kẽ nhau** (ví dụ cả hai cùng đọc được giá trị `5` trước khi thread
nào kịp ghi lại `6`), cả hai đều tính ra `6` và ghi đè lẫn nhau — kết quả là chỉ tăng được **một
lần** dù cả hai thread đều đã thực thi `dem++`. Hiện tượng này gọi là **race condition** ("chạy đua
điều kiện") — kết quả phụ thuộc vào **thứ tự ngẫu nhiên** các thread được CPU cấp thời gian chạy
xen kẽ nhau (nhắc lại Chương 37: thứ tự này **không được đảm bảo**).

## `synchronized` — đảm bảo chỉ một thread vào một lúc

### Trên phương thức

```java
static synchronized void tangDem() {
    dem++;
}
```

`synchronized` trên một phương thức đảm bảo: **tại một thời điểm, chỉ đúng một thread** được phép
đang thực thi bên trong phương thức đó — mọi thread khác muốn gọi cùng phương thức phải **chờ** tới
khi thread đang chạy thoát ra. Điều này loại bỏ hoàn toàn tình huống "đọc-sửa-ghi xen kẽ" đã gây ra
race condition ở trên.

### Trên một khối lệnh

```java
Object khoa = new Object(); // object bat ky, dung lam "ma khoa" chung

synchronized (khoa) {
    dem++;
}
```

Thay vì khoá **toàn bộ phương thức**, `synchronized (khoa) { ... }` chỉ khoá **đoạn code cụ thể**
bên trong khối `{ }`, dùng `khoa` (một object bất kỳ) làm "chìa khoá chung" — mọi khối
`synchronized (khoa)` ở bất kỳ đâu trong chương trình, dùng **cùng object `khoa`**, đều loại trừ
lẫn nhau (chỉ một thread vào được tại một thời điểm). Cách này linh hoạt hơn: chỉ khoá đúng phần
code thực sự cần thiết, để phần còn lại của phương thức (nếu có) vẫn chạy song song bình thường,
không bị chặn không cần thiết.

## `ReentrantLock` — thay thế linh hoạt hơn cho `synchronized`

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

Lock khoa = new ReentrantLock();

khoa.lock();
try {
    dem++;
} finally {
    khoa.unlock(); // BAT BUOC trong finally - dam bao LUON mo khoa, ke ca khi co loi
}
```

`ReentrantLock` (trong `java.util.concurrent.locks`) làm việc tương tự `synchronized`, nhưng linh
hoạt hơn ở vài điểm: `unlock()` phải gọi **tường minh** (không tự động như `synchronized` khi thoát
khối lệnh) — đây là lý do **bắt buộc** đặt trong `finally` (Chương 21), để đảm bảo khoá luôn được
giải phóng dù có exception xảy ra ở giữa. Đổi lại, `ReentrantLock` cung cấp thêm khả năng như
`tryLock()` — thử xin khoá mà **không chờ vô thời hạn**, trả về `true`/`false` ngay lập tức tuỳ có
lấy được khoá hay không, hữu ích khi bạn muốn "thử rồi bỏ cuộc" thay vì chờ mãi mãi.

**Lời khuyên thực dụng**: `synchronized` đơn giản hơn, đủ dùng cho phần lớn tình huống thông
thường; chỉ cần tới `ReentrantLock` khi thực sự cần các tính năng nâng cao của nó (`tryLock`, khoá
có thể ngắt được, nhiều điều kiện chờ...).

## Deadlock — hai (hoặc nhiều) thread khoá chéo nhau

```java
// Thread-1: giu khoaA, roi CHO khoaB
synchronized (khoaA) {
    synchronized (khoaB) { ... }
}

// Thread-2: giu khoaB, roi CHO khoaA
synchronized (khoaB) {
    synchronized (khoaA) { ... }
}
```

Nếu Thread-1 giữ `khoaA` và đang chờ `khoaB`, **đồng thời** Thread-2 giữ `khoaB` và đang chờ
`khoaA` — cả hai sẽ **chờ nhau mãi mãi**, không bên nào chịu nhường trước. Đây gọi là **deadlock**
("khoá chết") — chương trình **treo vĩnh viễn**, không có exception nào được ném ra để báo hiệu, chỉ
đơn giản là không bao giờ tiến triển thêm được nữa.

Chạy `DeadlockDemo.java` để tự thấy tình huống này xảy ra thật (chương trình tự thoát cưỡng bức sau
vài giây để không làm treo terminal của bạn, nhưng hai thread deadlock thực sự **không bao giờ** tự
kết thúc nếu không có sự can thiệp đó).

### Cách phòng tránh deadlock cơ bản

**Nguyên tắc quan trọng nhất**: luôn xin (lock) các khoá theo **cùng một thứ tự cố định** ở mọi nơi
trong chương trình. Nếu cả Thread-1 và Thread-2 đều xin `khoaA` **trước**, `khoaB` **sau** (thay vì
Thread-2 xin ngược lại như ví dụ trên), deadlock kiểu này không thể xảy ra — một trong hai thread sẽ
luôn xin được `khoaA` trước, thread còn lại buộc phải chờ, nhưng không có tình huống chờ vòng tròn
lẫn nhau.

## Bài tập

1. Sửa `DeadlockDemo.java` để cả hai thread xin khoá theo **cùng thứ tự** (`khoaA` trước,
   `khoaB` sau), chạy lại và xác nhận không còn deadlock, chương trình kết thúc bình thường.
2. Viết một class `TaiKhoanNganHang` (tương tự Chương 14) với phương thức `rutTien`/`napTien` dùng
   `synchronized`, rồi cho nhiều thread cùng gọi `rutTien`/`napTien` đồng thời trên cùng một tài
   khoản — xác nhận số dư cuối cùng luôn đúng, không bị race condition.
3. Thử bỏ `synchronized` khỏi bài tập 2, chạy lại nhiều lần, quan sát số dư cuối cùng có thể sai
   lệch — đối chiếu trực tiếp với `RaceConditionDemo.java`.

## Lỗi thường gặp

- **Chỉ đồng bộ hoá MỘT trong nhiều chỗ cùng sửa dữ liệu chung** — ví dụ `tangDem()` có
  `synchronized` nhưng vẫn còn chỗ khác trong code đọc/sửa trực tiếp `dem` mà không qua đồng bộ hoá
  — race condition vẫn xảy ra qua "cửa" không được bảo vệ đó. Mọi điểm truy cập dữ liệu dùng chung
  giữa nhiều thread đều cần đồng bộ hoá nhất quán.
- **Dùng hai object khoá khác nhau (`khoa1`, `khoa2`) ở hai chỗ lẽ ra phải loại trừ lẫn nhau** — nếu
  hai khối `synchronized` dùng khoá khác nhau, chúng **không loại trừ nhau**, cả hai vẫn chạy được
  đồng thời — mất tác dụng đồng bộ hoá dù trông có vẻ đã "khoá" cẩn thận.
- **Quên `unlock()` khi dùng `ReentrantLock` (đặc biệt khi có exception)** — khoá bị giữ vĩnh viễn,
  mọi thread khác chờ mãi mãi (tương tự deadlock). Luôn đặt `unlock()` trong `finally`.
- **Đồng bộ hoá quá rộng** (khoá cả một phương thức lớn, trong khi chỉ một dòng thực sự cần bảo vệ)
  — làm giảm hiệu năng đa luồng không cần thiết, vì các thread khác phải chờ lâu hơn mức cần thiết.
  Cân nhắc dùng khối `synchronized` nhỏ, đúng phạm vi thay vì cả phương thức.
- **Xin khoá theo thứ tự không nhất quán ở các chỗ khác nhau trong code** — nguyên nhân phổ biến
  nhất gây deadlock trong thực tế. Luôn thống nhất thứ tự xin khoá xuyên suốt chương trình.
