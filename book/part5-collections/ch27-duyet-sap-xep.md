# Chương 27 — Duyệt & sắp xếp: Iterator, Comparator nâng cao

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu vì sao sửa collection trong lúc duyệt bằng for-each gây lỗi, và cách khắc phục bằng
  `Iterator`.
- Dùng `Comparator.comparing()`, `thenComparing()`, `reversed()` — cách viết hiện đại, ngắn gọn hơn
  nhiều so với anonymous class đã học ở Chương 20.
- Dùng các phương thức tiện ích của lớp `Collections`: sắp xếp, đảo ngược, tìm max/min, xáo trộn,
  tạo danh sách bất biến.

Code mẫu đầy đủ: [`code/ch27-duyet-sap-xep/`](../../code/ch27-duyet-sap-xep/).

> Chương này dùng cú pháp method reference (`NguoiDung::getTuoi`) trước khi Chương 30 dạy đầy đủ —
> ở đây chỉ cần hiểu nó nghĩa là "lấy giá trị trả về của phương thức đó để dùng làm tiêu chí".

## `ConcurrentModificationException` — vì sao xảy ra

```java
List<Integer> danhSach = new ArrayList<>(List.of(1, 2, 3, 4, 5));
for (int so : danhSach) {
    if (so % 2 == 0) {
        danhSach.remove(Integer.valueOf(so)); // SUA danh sach TRONG LUC duyet
    }
}
// ConcurrentModificationException!
```

Vòng lặp for-each (Chương 9) thực chất được Java "dịch ngầm" thành việc dùng một **Iterator** —
một object di chuyển tuần tự qua từng phần tử, tự theo dõi "tôi đang ở vị trí nào". Khi bạn
`remove()` trực tiếp trên `danhSach` (không qua Iterator) trong lúc Iterator đang duyệt dở, cấu
trúc dữ liệu bên trong bị thay đổi bất ngờ so với những gì Iterator đang mong đợi — Java phát hiện
sự "mất đồng bộ" này và chủ động ném `ConcurrentModificationException` để cảnh báo, thay vì để
chương trình chạy tiếp với hành vi khó đoán (có thể bỏ sót phần tử, hoặc lỗi nghiêm trọng hơn).

## `Iterator` — cách sửa collection an toàn trong lúc duyệt

```java
Iterator<Integer> it = danhSach.iterator();
while (it.hasNext()) {
    int so = it.next();
    if (so % 2 == 0) {
        it.remove(); // xoa AN TOAN, Iterator TU CAP NHAT trang thai noi bo
    }
}
```

`Iterator<T>` có 3 phương thức chính: `hasNext()` (còn phần tử để duyệt tiếp không), `next()` (lấy
phần tử tiếp theo, đồng thời di chuyển Iterator tới), `remove()` (xoá **đúng phần tử vừa lấy ra bởi
`next()` gần nhất**, an toàn vì Iterator tự cập nhật trạng thái nội bộ đồng thời). Đây là cách
**duy nhất an toàn** để xoá phần tử trong lúc đang duyệt collection — nếu cần xoá theo điều kiện
trong lúc duyệt, luôn dùng `Iterator` trực tiếp thay vì for-each.

## `Comparator.comparing()` — cách viết hiện đại

Nhắc lại Chương 20: viết `Comparator` bằng anonymous class khá dài dòng. Từ Java 8, có cách viết
ngắn gọn hơn nhiều:

```java
List<NguoiDung> danhSach = ...;
danhSach.sort(Comparator.comparing(NguoiDung::getTuoi));
```

`Comparator.comparing(NguoiDung::getTuoi)` tạo ra một `Comparator<NguoiDung>` so sánh dựa trên giá
trị trả về của `getTuoi()` — ngắn gọn hơn hẳn so với việc tự viết anonymous class implements
`compare(...)` như ở Chương 20.

### `thenComparing()` — sắp xếp theo nhiều tiêu chí

```java
danhSach.sort(
    Comparator.comparing(NguoiDung::getTen)
              .thenComparing(NguoiDung::getTuoi)
);
```

Sắp xếp trước theo `getTen()`; khi hai phần tử có **tên bằng nhau**, dùng tiếp `getTuoi()` để phân
định thứ tự giữa chúng. Có thể nối tiếp nhiều `thenComparing()` nếu cần nhiều tiêu chí phân định
dần.

### `reversed()` — đảo ngược thứ tự

```java
danhSach.sort(Comparator.comparing(NguoiDung::getTuoi).reversed());
```

Đảo ngược kết quả so sánh của `Comparator` đứng trước nó — sắp xếp giảm dần thay vì tăng dần.

## Lớp tiện ích `Collections`

Khác với `Collection` (interface chung của `List`/`Set`/`Queue`, chú ý không có "s" ở cuối),
`Collections` (có "s") là một **class tiện ích**, chứa toàn các phương thức `static` thao tác trên
collection:

```java
Collections.sort(danhSachSo);          // sap xep tang dan (thu tu tu nhien)
Collections.reverse(danhSachSo);        // dao nguoc thu tu hien tai
Collections.max(danhSachSo);            // gia tri lon nhat
Collections.min(danhSachSo);            // gia tri nho nhat
Collections.shuffle(danhSachSo);        // xao tron ngau nhien
Collections.unmodifiableList(danhSach); // tao mot "khung nhin" BAT BIEN cua danh sach
```

`Collections.sort(list)` là cách viết **trước Java 8** (khi chưa có `list.sort(...)`) — cả hai cách
đều còn dùng phổ biến, bạn sẽ gặp cả hai trong code thực tế; `list.sort(comparator)` (gọi trực tiếp
trên object `List`) là cách viết được khuyến khích hơn cho code mới vì gọn hơn.

`Collections.unmodifiableList(...)` tạo ra một **view bất biến** — không phải bản sao, mà là một
"lớp bọc" chặn mọi thao tác sửa đổi (`add`, `remove`...) qua tham chiếu đó, ném
`UnsupportedOperationException` nếu cố sửa. Lưu ý: danh sách **gốc** đứng sau view này vẫn sửa được
bình thường qua tham chiếu gốc — view chỉ chặn thao tác qua **chính tham chiếu bất biến** đó.

## Bài tập

1. Viết vòng lặp dùng `Iterator` để xoá mọi phần tử `String` có độ dài nhỏ hơn 3 ký tự khỏi một
   `List<String>`.
2. Với danh sách `NguoiDung` trong code mẫu, viết một `Comparator` sắp xếp theo tuổi **giảm dần**,
   nếu tuổi bằng nhau thì theo tên **tăng dần** (kết hợp `reversed()` và `thenComparing()`).
3. Dùng `Collections.max()` với một `Comparator` tuỳ chỉnh (có overload `max(collection, comparator)`)
   để tìm người dùng **lớn tuổi nhất** trong danh sách mà không cần tự viết vòng lặp so sánh thủ
   công.

## Lỗi thường gặp

- **`ConcurrentModificationException`** — sửa collection trực tiếp trong lúc duyệt for-each. Dùng
  `Iterator.remove()` thay thế.
- **Gọi `it.remove()` mà chưa gọi `it.next()` trước đó (hoặc gọi hai lần `remove()` liên tiếp)** —
  ném `IllegalStateException`. `remove()` luôn phải theo ngay sau đúng một lần `next()`.
- **Nhầm `Collection` (interface) với `Collections` (class tiện ích, có "s")** — hai cái tên rất dễ
  gõ nhầm, ý nghĩa hoàn toàn khác nhau.
- **Cố sửa danh sách qua tham chiếu trả về từ `Collections.unmodifiableList(...)`** — ném
  `UnsupportedOperationException` có chủ đích, không phải bug.
- **Quên rằng `Comparator.comparing(...)` cần method reference hoặc lambda hợp lệ** — nếu phương
  thức tham chiếu (`NguoiDung::getTuoi`) không trả về kiểu có thể so sánh được (không implements
  `Comparable`), trình biên dịch báo lỗi — sẽ hiểu rõ hơn cơ chế này sau khi học lambda ở Chương 30.
