# Chương 24 — Code mẫu: ArrayList & LinkedList

## `ArrayListDemo.java`

```
javac ArrayListDemo.java
java ArrayListDemo
```

Các thao tác cơ bản với `ArrayList`: thêm (cuối/giữa), đọc, sửa, xóa (theo chỉ số/theo giá trị),
kiểm tra tồn tại, duyệt, chuyển đổi qua lại với mảng, và `List.of()` tạo danh sách bất biến.

## `LinkedListDemo.java`

```
javac LinkedListDemo.java
java LinkedListDemo
```

Các phương thức riêng của `LinkedList` (`addFirst`/`addLast`/`getFirst`/`getLast`/`removeFirst`),
và đo thời gian thực tế so sánh thêm 100.000 phần tử vào **đầu** danh sách giữa `ArrayList` (chậm
dần, phải dịch chuyển toàn bộ phần tử còn lại mỗi lần) và `LinkedList` (nhanh, chỉ nối lại một liên
kết).
