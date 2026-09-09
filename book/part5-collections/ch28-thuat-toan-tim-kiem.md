# Chương 28 — Thuật toán tìm kiếm: Linear, Binary, Jump, Interpolation Search

## Mục tiêu học

Sau chương này, bạn sẽ:

- Cài đặt được 4 thuật toán tìm kiếm cơ bản trên mảng.
- Hiểu khái niệm **độ phức tạp thời gian** (time complexity, ký hiệu Big-O) ở mức đủ dùng, không
  cần nền tảng toán học sâu.
- Biết khi nào nên dùng thuật toán nào, dựa trên đặc điểm dữ liệu (đã sắp xếp hay chưa, phân bố đều
  hay không).

Code mẫu đầy đủ: [`code/ch28-thuat-toan-tim-kiem/`](../../code/ch28-thuat-toan-tim-kiem/).

## Độ phức tạp thời gian (Big-O) — hiểu ở mức thực dụng

Trước khi so sánh các thuật toán, cần một cách **đo lường** tốc độ không phụ thuộc vào máy tính cụ
thể đang chạy. **Big-O** mô tả tốc độ tăng của thời gian chạy **khi kích thước dữ liệu (`n`) tăng
lên**, không phải thời gian chạy chính xác tính bằng giây:

- **O(n)** — thời gian chạy tăng **tỷ lệ thuận** với `n`. Dữ liệu tăng gấp đôi, thời gian chạy tăng
  gấp đôi (trường hợp xấu nhất).
- **O(log n)** — thời gian chạy tăng **rất chậm** so với `n`. Dữ liệu tăng gấp đôi, thời gian chạy
  chỉ tăng thêm **một lượng cố định nhỏ** (không tăng gấp đôi). Với `n = 1,000,000`, `log₂n` chỉ
  khoảng `20` — chênh lệch với O(n) là rất lớn.
- **O(1)** — thời gian chạy **không đổi**, bất kể `n` lớn cỡ nào (ví dụ: `HashMap.get()` ở Chương 26,
  trung bình).

Không cần chứng minh toán học chặt chẽ ở giai đoạn này — chỉ cần hiểu: **số mũ hoặc bậc thấp hơn
trong Big-O luôn tốt hơn** khi `n` đủ lớn, dù đôi khi thuật toán "phức tạp hơn" (nhiều bước cài đặt
hơn) trong từng bước tính toán riêng lẻ.

## Linear Search — O(n)

```java
public static int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            return i;
        }
    }
    return -1;
}
```

Duyệt tuần tự từng phần tử, so sánh với `target`. **Đơn giản nhất, không đòi hỏi gì** về dữ liệu
đầu vào (mảng không cần sắp xếp). Trường hợp xấu nhất (phần tử cần tìm ở cuối, hoặc không tồn tại):
phải duyệt qua **toàn bộ** `n` phần tử — độ phức tạp **O(n)**.

## Binary Search — O(log n)

```java
public static int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

**Yêu cầu bắt buộc: mảng phải đã được sắp xếp tăng dần.** Ý tưởng: so sánh `target` với phần tử
**ở giữa** — nếu bằng, tìm thấy; nếu `target` lớn hơn, chắc chắn nó (nếu có) chỉ nằm ở **nửa bên
phải**, loại bỏ hẳn nửa bên trái khỏi phạm vi tìm kiếm tiếp theo (và ngược lại). Mỗi bước loại bỏ
**một nửa** không gian tìm kiếm còn lại — đây là lý do độ phức tạp chỉ **O(log n)**, nhanh hơn rất
nhiều so với Linear Search khi `n` lớn (với 1 triệu phần tử, Binary Search cần tối đa khoảng 20 lần
so sánh, Linear Search có thể cần tới 1 triệu lần).

`left + (right - left) / 2` (thay vì viết trực tiếp `(left + right) / 2`) là một chi tiết cài đặt
đáng chú ý: cách viết trực tiếp có nguy cơ **tràn số** (overflow, đã học ở Chương 5) nếu `left` và
`right` đều rất lớn (tổng của chúng vượt phạm vi `int`), dù hiếm gặp trong thực tế nhưng là thói
quen cài đặt an toàn nên biết.

## Jump Search — O(√n)

```java
int step = (int) Math.floor(Math.sqrt(n));
```

**Yêu cầu mảng đã sắp xếp**, nằm giữa Linear Search và Binary Search về ý tưởng: thay vì kiểm tra
từng phần tử một (Linear Search) hay chia đôi liên tục (Binary Search), Jump Search "nhảy" theo
từng khối kích thước `√n`, tới khi tìm thấy khối **có thể chứa** `target` (khối mà giá trị cuối
khối `≥ target`), rồi tìm kiếm tuyến tính **trong phạm vi khối đó**. Độ phức tạp **O(√n)** — chậm
hơn Binary Search nhưng nhanh hơn Linear Search. Trong thực tế ít khi dùng Jump Search thay Binary
Search (Binary Search gần như luôn tốt hơn khi có thể truy cập ngẫu nhiên vào mảng), nhưng nó minh
hoạ tốt ý tưởng "đánh đổi giữa hai thái cực" và hữu ích với một số cấu trúc dữ liệu chỉ hỗ trợ nhảy
theo khối hiệu quả hơn truy cập ngẫu nhiên từng phần tử.

## Interpolation Search — O(log log n) trung bình

```java
int pos = left + ((target - arr[left]) * (right - left)) / (arr[right] - arr[left]);
```

**Yêu cầu mảng đã sắp xếp**, hoạt động tốt nhất khi dữ liệu **phân bố đều** (uniformly distributed).
Thay vì luôn nhắm vào giữa như Binary Search, Interpolation Search **ước lượng** vị trí có khả năng
chứa `target` dựa trên tỷ lệ giữa giá trị `target` và khoảng giá trị `[arr[left], arr[right]]` —
giống cách con người tìm một cái tên trong danh bạ điện thoại giấy: nếu tìm tên bắt đầu bằng "Y",
bạn mở thẳng gần cuối cuốn sách, không mở đúng giữa rồi chia đôi dần như Binary Search.

Với dữ liệu phân bố đều, độ phức tạp trung bình đạt **O(log log n)** — nhanh hơn cả Binary Search.
Nhưng với dữ liệu phân bố **không đều** (ví dụ phần lớn giá trị dồn về một khoảng hẹp, vài giá trị
ngoại lệ rất xa), công thức ước lượng có thể sai lệch nhiều, khiến độ phức tạp xấu nhất tụt xuống
**O(n)** — kém tin cậy hơn Binary Search (luôn đảm bảo O(log n) bất kể phân bố dữ liệu ra sao). Đây
là lý do Binary Search vẫn là lựa chọn mặc định phổ biến hơn trong thực tế, dù Interpolation Search
có thể nhanh hơn trong điều kiện dữ liệu lý tưởng.

## Bảng so sánh

| Thuật toán | Yêu cầu dữ liệu | Độ phức tạp trung bình | Độ phức tạp xấu nhất |
|---|---|---|---|
| Linear Search | Không yêu cầu | O(n) | O(n) |
| Binary Search | Đã sắp xếp | O(log n) | O(log n) |
| Jump Search | Đã sắp xếp | O(√n) | O(√n) |
| Interpolation Search | Đã sắp xếp, phân bố đều | O(log log n) | O(n) |

## Bài tập

1. Chạy `SoSanhHieuNang.java`, thử tăng `kichThuoc` lên `20_000_000` — quan sát chênh lệch giữa
   Linear Search và các thuật toán còn lại càng rõ rệt hơn khi `n` càng lớn.
2. Sửa `target` trong `SoSanhHieuNang.java` thành một giá trị **không tồn tại** trong mảng (ví dụ
   một số lẻ, vì mảng mẫu chỉ chứa số chẵn) — quan sát cả 4 thuật toán đều trả về `-1`, và so sánh
   lại thời gian chạy trong trường hợp "không tìm thấy" (thường là trường hợp xấu nhất).
3. Tự cài đặt một biến thể của Binary Search **đệ quy** (gọi lại chính nó thay vì dùng `while`) —
   sách chưa dạy đệ quy chính thức, đây là bài tập mở rộng để tự khám phá; so sánh kết quả với bản
   `while` đã có.

## Lỗi thường gặp

- **Dùng Binary/Jump/Interpolation Search trên mảng CHƯA sắp xếp** — cả ba thuật toán này giả định
  ngầm dữ liệu đã sắp xếp; chạy trên mảng chưa sắp xếp cho kết quả **sai** (có thể báo "không tìm
  thấy" dù phần tử thực sự tồn tại) mà không có bất kỳ cảnh báo lỗi nào — luôn `Arrays.sort()`
  (Chương 9) trước nếu dữ liệu chưa chắc chắn đã sắp xếp.
- **Viết `(left + right) / 2` thay vì `left + (right - left) / 2`** trong Binary Search — nguy cơ
  tràn số với mảng cực lớn, dù hiếm gặp trong bài tập thông thường vẫn nên tập thói quen viết đúng.
- **Chọn Interpolation Search cho dữ liệu phân bố không đều rồi ngạc nhiên vì hiệu năng tệ hơn dự
  kiến** — nhắc lại: độ phức tạp xấu nhất của nó là O(n), không phải lúc nào cũng nhanh như quảng
  cáo lý thuyết. Với dữ liệu không rõ đặc tính phân bố, Binary Search là lựa chọn an toàn hơn.
- **Nhầm lẫn giá trị trả về `-1` (không tìm thấy) với một chỉ số hợp lệ** — luôn kiểm tra
  `if (index != -1)` trước khi dùng kết quả tìm kiếm để truy cập mảng, tránh
  `ArrayIndexOutOfBoundsException` (Chương 9).
