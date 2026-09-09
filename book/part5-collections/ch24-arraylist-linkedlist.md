# Chương 24 — ArrayList & LinkedList

## Mục tiêu học

Sau chương này, bạn sẽ:

- Dùng thành thạo `ArrayList`: thêm, đọc, sửa, xóa, duyệt.
- Hiểu vì sao nên khai báo biến bằng kiểu **interface** (`List<T>`) thay vì kiểu cài đặt cụ thể
  (`ArrayList<T>`).
- Hiểu cơ chế bên trong khiến `ArrayList` và `LinkedList` có đặc tính hiệu năng khác nhau, chọn
  đúng loại cho từng tình huống.

Code mẫu đầy đủ: [`code/ch24-arraylist-linkedlist/`](../../code/ch24-arraylist-linkedlist/).

## `ArrayList` — danh sách động dựa trên mảng

```java
List<String> danhSach = new ArrayList<>();
danhSach.add("An");
danhSach.add("Binh");
danhSach.get(0);          // "An"
danhSach.set(0, "An 2");  // sua phan tu vi tri 0
danhSach.remove(0);       // xoa theo CHI SO
danhSach.remove("Binh");  // xoa theo GIA TRI (dung .equals())
danhSach.size();          // so phan tu HIEN TAI
danhSach.contains("An");  // kiem tra ton tai
```

`ArrayList` giải quyết đúng hạn chế lớn nhất của mảng thuần (Chương 9): **kích thước tự động thay
đổi** khi bạn `add`/`remove`, không cần biết trước số lượng phần tử.

### Vì sao khai báo `List<String> danhSach = new ArrayList<>();` thay vì `ArrayList<String> danhSach = ...`?

Đây là thói quen rất phổ biến và nên theo trong code Java thực tế: khai báo **kiểu biến** bằng
**interface** (`List`), chỉ dùng **kiểu cài đặt cụ thể** (`ArrayList`) ở phía bên phải khi tạo
object. Lý do: phần lớn code của bạn chỉ cần các thao tác **chung** mà mọi `List` đều có (`add`,
`get`, `size`...), không quan tâm cụ thể là `ArrayList` hay `LinkedList` bên dưới. Nếu sau này bạn
đổi ý muốn dùng `LinkedList` thay vì `ArrayList` (vì lý do hiệu năng — xem phần dưới), bạn chỉ cần
sửa **đúng một chỗ** (`new ArrayList<>()` → `new LinkedList<>()`), mọi chỗ khác dùng biến
`danhSach` không cần sửa gì — đây chính là ứng dụng thực tế của **đa hình** (Chương 17) và
**abstraction** (nhắc ở Chương 12/18): lập trình theo interface, không lập trình theo cài đặt cụ
thể.

## Cơ chế bên trong `ArrayList`

`ArrayList` **thực chất được cài đặt bằng một mảng bên trong** — khi mảng đó đầy và bạn `add` thêm,
`ArrayList` tự tạo một mảng **mới lớn hơn** (thường gấp khoảng 1.5 lần), sao chép toàn bộ phần tử
cũ sang, rồi mới thêm phần tử mới. Quá trình này diễn ra **âm thầm**, bạn không cần biết chi tiết để
dùng `ArrayList`, nhưng hiểu nó giải thích được đặc tính hiệu năng:

- Truy cập theo chỉ số (`get(i)`) — **rất nhanh**, vì mảng bên dưới cho phép nhảy thẳng tới đúng vị
  trí trong bộ nhớ.
- Thêm/xóa ở **cuối** danh sách — nhanh (thường không cần dịch chuyển gì).
- Thêm/xóa ở **đầu hoặc giữa** danh sách — **chậm dần khi danh sách lớn**, vì phải **dịch chuyển**
  mọi phần tử phía sau vị trí đó sang một ô để lấy chỗ trống (hoặc lấp chỗ trống khi xóa).

## `LinkedList` — danh sách liên kết

```java
LinkedList<String> danhSach = new LinkedList<>();
danhSach.addFirst("A");   // them vao DAU - RAT NHANH
danhSach.addLast("D");    // them vao CUOI - RAT NHANH
danhSach.getFirst();
danhSach.getLast();
danhSach.removeFirst();
```

`LinkedList` cài đặt theo cơ chế **khác hẳn**: mỗi phần tử là một "nút" (node) riêng lẻ trong bộ
nhớ, **tự giữ liên kết** (con trỏ) tới nút trước và nút sau nó — không có mảng liên tục nào cả.

```mermaid
graph LR
    A["Nut: A"] --> B["Nut: B"]
    B --> C["Nut: C"]
    C --> D["Nut: D"]
```

Hệ quả hiệu năng **ngược lại** với `ArrayList`:

- Thêm/xóa ở **đầu hoặc giữa** danh sách — **rất nhanh**, chỉ cần nối lại vài liên kết xung quanh vị
  trí đó, **không** cần dịch chuyển bất kỳ phần tử nào khác.
- Truy cập theo chỉ số (`get(i)`) — **chậm hơn** `ArrayList` đáng kể, vì phải **đi từng bước** qua
  các liên kết từ đầu (hoặc cuối, tuỳ vị trí gần đầu hay cuối hơn) cho tới đúng vị trí `i`, không
  nhảy thẳng được như mảng.

Chạy `LinkedListDemo.java` để thấy chênh lệch thời gian **thực tế** khi thêm 100.000 phần tử vào
đầu danh sách — `LinkedList` nhanh hơn `ArrayList` rất nhiều trong tình huống này.

## Chọn `ArrayList` hay `LinkedList`?

| Tình huống | Nên chọn |
|---|---|
| Chủ yếu đọc/truy cập theo chỉ số, ít thêm/xóa ở giữa | `ArrayList` |
| Thêm/xóa thường xuyên ở đầu hoặc giữa danh sách | `LinkedList` |
| Không chắc, hoặc danh sách nhỏ | `ArrayList` (mặc định thực tế phổ biến nhất — đơn giản, hiệu năng tốt trong đa số trường hợp thông thường) |

**Lời khuyên thực dụng cho người mới**: mặc định dùng `ArrayList` trừ khi bạn có lý do cụ thể (đã
đo hoặc phân tích) cho thấy cần đặc tính của `LinkedList`. Phần lớn code Java thực tế dùng
`ArrayList` nhiều hơn hẳn `LinkedList`.

## Bài tập

1. Viết chương trình dùng `ArrayList<Integer>` lưu điểm số nhập từ bàn phím (dùng vòng lặp
   `while` kết hợp `Scanner`, dừng khi người dùng nhập `-1`), sau đó in ra điểm trung bình.
2. Viết phương thức `xoaTrungLap(List<String> danhSach)` loại bỏ các phần tử trùng lặp, giữ lại thứ
   tự xuất hiện đầu tiên (gợi ý: dùng `contains()` để kiểm tra trước khi thêm vào danh sách kết
   quả).
3. Chạy thử `LinkedListDemo.java` với `soLuong = 20_000` thay vì `100_000` thêm vào **cuối** danh
   sách (`add(danhSach.size(), i)` hoặc đơn giản là `add(i)`) thay vì đầu — so sánh kết quả, giải
   thích vì sao chênh lệch giữa `ArrayList` và `LinkedList` không còn rõ rệt như khi thêm vào đầu.

## Lỗi thường gặp

- **`IndexOutOfBoundsException`** khi `get`/`set`/`remove` với chỉ số ngoài phạm vi `[0, size())`
  — giống hệt lỗi mảng ở Chương 9, nhưng tên exception khác (`ArrayIndexOutOfBoundsException` dành
  cho mảng thuần, `IndexOutOfBoundsException` dành cho `List`).
- **Gọi `remove(int)` khi thực ra muốn xóa theo giá trị `Integer`** — với `List<Integer>`,
  `remove(0)` xóa **phần tử ở chỉ số 0**, không phải xóa giá trị `0` — vì có overload
  `remove(int index)` và `remove(Object o)` cùng tồn tại (Chương 10). Dùng `remove(Integer.valueOf(0))`
  nếu thật sự muốn xóa theo giá trị `0`.
- **Cố `add`/`remove` trên danh sách tạo bằng `List.of(...)`** — `List.of()` tạo danh sách **bất
  biến (immutable)**, mọi thao tác sửa đổi ném `UnsupportedOperationException` lúc chạy. Dùng
  `new ArrayList<>(List.of(...))` nếu cần một danh sách khởi tạo sẵn giá trị nhưng vẫn sửa được.
- **Dùng `LinkedList` mặc định "vì nghe nói nhanh hơn" mà không xét thao tác thực tế cần làm** —
  như bảng so sánh ở trên, `LinkedList` chỉ nhanh hơn cho một số thao tác cụ thể (đầu/giữa danh
  sách); với `get(i)` ngẫu nhiên, `ArrayList` nhanh hơn nhiều. Chọn dựa trên thao tác bạn thực sự
  dùng nhiều nhất, không dựa trên cảm tính.
