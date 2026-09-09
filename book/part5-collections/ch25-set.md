# Chương 25 — HashSet, TreeSet, LinkedHashSet

## Mục tiêu học

Sau chương này, bạn sẽ:

- Dùng `HashSet` để lưu tập hợp không trùng lặp, hiệu năng cao.
- Dùng `LinkedHashSet` khi cần giữ đúng thứ tự đã thêm vào.
- Dùng `TreeSet` khi cần tập hợp luôn được sắp xếp tự động.
- Thực hiện các phép toán tập hợp: hợp, giao, hiệu.
- Hiểu **sâu hơn** vì sao `equals()`/`hashCode()` (Chương 20) bắt buộc phải override đúng cách khi
  dùng object tự định nghĩa trong `HashSet`.

Code mẫu đầy đủ: [`code/ch25-set/`](../../code/ch25-set/).

## `HashSet` — nhanh nhất, không đảm bảo thứ tự

```java
Set<String> hashSet = new HashSet<>();
hashSet.add("Cam");
hashSet.add("Tao");
hashSet.add("Cam"); // trung lap - bi bo qua am tham, KHONG bao loi
System.out.println(hashSet.size()); // 2, khong phai 3
```

`HashSet` dùng cơ chế **băm (hashing)** dựa trên `hashCode()` của phần tử để quyết định vị trí lưu
trữ nội bộ — nhờ đó, kiểm tra "phần tử đã tồn tại chưa" (`contains`, và cả `add` tự kiểm tra trùng)
**cực nhanh** (gần như không phụ thuộc số lượng phần tử đã có), nhanh hơn hẳn việc dò tuần tự trong
`ArrayList`. Đánh đổi: **không đảm bảo bất kỳ thứ tự nào** khi duyệt — thứ tự phần tử khi in ra
`HashSet` có thể khác thứ tự bạn `add` vào, và không nên dựa vào bất kỳ thứ tự cụ thể nào quan sát
được (nó có thể khác nhau giữa các lần chạy hoặc phiên bản Java khác nhau).

## `LinkedHashSet` — giữ đúng thứ tự thêm vào

```java
Set<String> linkedHashSet = new LinkedHashSet<>();
linkedHashSet.add("Cam");
linkedHashSet.add("Tao");
linkedHashSet.add("Buoi");
System.out.println(linkedHashSet); // luon la [Cam, Tao, Buoi] - dung thu tu da them
```

`LinkedHashSet` là biến thể của `HashSet`, giữ thêm một danh sách liên kết nội bộ để nhớ đúng thứ
tự các phần tử được thêm vào, đồng thời **vẫn giữ được** tốc độ kiểm tra trùng lặp gần như `HashSet`
(chỉ chậm hơn một chút do phải duy trì thêm thông tin thứ tự). Dùng khi bạn cần **cả hai**: không
trùng lặp **và** giữ thứ tự.

## `TreeSet` — tự động sắp xếp

```java
Set<String> treeSet = new TreeSet<>();
treeSet.add("Cam");
treeSet.add("Tao");
treeSet.add("Buoi");
System.out.println(treeSet); // luon la [Buoi, Cam, Tao] - theo thu tu BANG CHU CAI
```

`TreeSet` luôn giữ phần tử theo **thứ tự đã sắp xếp**, dựa trên `Comparable` (Chương 20 — thứ tự tự
nhiên) của kiểu phần tử, hoặc một `Comparator` tuỳ chỉnh truyền vào lúc tạo (`new TreeSet<>(comparator)`).
Vì phải duy trì thứ tự sắp xếp liên tục, `TreeSet` **chậm hơn** `HashSet`/`LinkedHashSet` cho thao
tác thêm/kiểm tra (`O(log n)` thay vì gần như `O(1)` — sẽ hiểu rõ hơn ký hiệu độ phức tạp này ở
Chương 28), nhưng đổi lại luôn duyệt được theo thứ tự mà không cần tự sắp xếp thêm bước nào.

## Chọn loại `Set` nào?

| Cần gì? | Chọn |
|---|---|
| Chỉ cần không trùng lặp, không quan tâm thứ tự, ưu tiên tốc độ | `HashSet` |
| Không trùng lặp **và** giữ đúng thứ tự thêm vào | `LinkedHashSet` |
| Không trùng lặp **và** luôn cần duyệt theo thứ tự sắp xếp | `TreeSet` |

## Các phép toán tập hợp

```java
Set<Integer> tapA = new HashSet<>(Arrays.asList(1, 2, 3, 4));
Set<Integer> tapB = new HashSet<>(Arrays.asList(3, 4, 5, 6));

Set<Integer> hop = new HashSet<>(tapA);
hop.addAll(tapB);      // PHEP HOP: {1,2,3,4,5,6}

Set<Integer> giao = new HashSet<>(tapA);
giao.retainAll(tapB);  // PHEP GIAO: {3,4} - chi giu phan tu CO O CA HAI

Set<Integer> hieu = new HashSet<>(tapA);
hieu.removeAll(tapB);  // PHEP HIEU: {1,2} - giu phan tu CHI CO O tapA
```

Lưu ý quan trọng: `addAll`/`retainAll`/`removeAll` **sửa trực tiếp** tập hợp đang gọi — đó là lý do
code trên luôn tạo `new HashSet<>(tapA)` (bản sao) trước khi thao tác, để không làm thay đổi `tapA`
gốc ngoài ý muốn.

## Vì sao `equals()`/`hashCode()` bắt buộc phải đúng khi dùng object tự định nghĩa trong `Set`

Đây là ứng dụng thực tế quan trọng nhất của quy tắc đã học ở Chương 20. `HashSet` xác định "phần tử
này đã tồn tại chưa" bằng cách: (1) tính `hashCode()` để tìm nhanh tới đúng "khu vực" có thể chứa
phần tử trùng, (2) trong khu vực đó, dùng `equals()` để so sánh chính xác từng phần tử. **Nếu bạn
không override `equals()`/`hashCode()`**, hai object có nội dung giống hệt nhau vẫn được coi là
**khác nhau** (theo hành vi mặc định của `Object` — tương đương `==`), và `HashSet` sẽ **không loại
được trùng lặp theo nội dung** dù bạn mong đợi điều đó:

```java
Set<DiemToaDo> tap = new HashSet<>();
tap.add(new DiemToaDo(1, 2));
tap.add(new DiemToaDo(1, 2)); // NEU DiemToaDo KHONG override equals/hashCode:
System.out.println(tap.size()); // ra 2, KHONG phai 1 nhu ban co the mong doi!
```

Chạy `SetDemo.java` để thấy trực tiếp sự khác biệt này giữa hai class gần như giống hệt nhau, chỉ
khác ở việc có override `equals`/`hashCode` hay không.

## Bài tập

1. Viết chương trình đọc một câu (dùng `Scanner.nextLine()`, tách bằng `split(" ")` như Chương 11),
   dùng `HashSet<String>` để đếm số từ **khác nhau** (không tính trùng lặp) trong câu đó.
2. Dùng `TreeSet<Integer>` để tự động có được danh sách các số duy nhất, đã sắp xếp, từ một mảng
   `int[]` có nhiều giá trị trùng lặp (gợi ý: thêm từng phần tử mảng vào `TreeSet`, sau đó duyệt
   `TreeSet` để có kết quả).
3. Viết một class `SanPham` (tương tự Chương 20) với `equals`/`hashCode` dựa trên **mã sản phẩm**
   (không dựa trên tên/giá), thêm nhiều object `SanPham` có cùng mã nhưng giá khác nhau vào một
   `HashSet` — quan sát chỉ giữ lại được một object đầu tiên cho mỗi mã trùng.

## Lỗi thường gặp

- **Dùng object tự định nghĩa trong `HashSet` mà quên override `equals`/`hashCode`** — lỗi phổ biến
  và khó nhận ra nhất chương này, vì chương trình **không báo lỗi gì**, chỉ đơn giản chứa nhiều
  phần tử trùng lặp hơn mong đợi. Luôn tự hỏi: object dùng trong `Set` đã override đúng cặp
  `equals`/`hashCode` chưa?
- **Kỳ vọng `HashSet` giữ thứ tự đã thêm vào** — dùng `LinkedHashSet` nếu cần điều đó.
- **Sửa `tapA` gốc ngoài ý muốn khi tính phép giao/hiệu** — quên tạo bản sao trước khi gọi
  `retainAll`/`removeAll`, làm mất dữ liệu gốc. Luôn `new HashSet<>(tapGoc)` trước khi thao tác nếu
  cần giữ nguyên tập gốc.
- **Dùng `TreeSet` với kiểu phần tử không implements `Comparable`, và không truyền `Comparator`** —
  ném `ClassCastException` lúc chạy ngay khi `add` phần tử thứ hai (`TreeSet` cần so sánh được các
  phần tử để biết thứ tự sắp xếp).
