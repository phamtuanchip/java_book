# Chương 27 — Code mẫu: Iterator, Comparator nâng cao, Collections

## `IteratorDemo.java`

```
javac IteratorDemo.java
java IteratorDemo
```

Minh hoạ trực tiếp `ConcurrentModificationException` khi sửa danh sách trong lúc duyệt bằng
for-each, và cách khắc phục đúng bằng `Iterator.remove()`.

## `ComparatorDemo.java` (cần `NguoiDung.java`)

```
javac *.java
java ComparatorDemo
```

`Comparator.comparing(NguoiDung::getTuoi)` và các phương thức `thenComparing`/`reversed` — cú pháp
`NguoiDung::getTuoi` là **method reference**, sẽ học đầy đủ ở Chương 30; ở đây chỉ cần hiểu nó nghĩa
là "dùng giá trị trả về của `getTuoi()` để so sánh". Phần cuối minh hoạ các phương thức tiện ích
của lớp `Collections`: `sort`, `reverse`, `max`, `min`, `shuffle`, `unmodifiableList`.
