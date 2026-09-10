# Chương 31 — Code mẫu: Stream API

```
javac *.java
java StreamBasicsDemo
java ReduceCollectDemo
```

- `StreamBasicsDemo.java` — so sánh vòng lặp thủ công với Stream (`filter`/`map`/`collect`), các
  thao tác kết thúc phổ biến (`count`, `anyMatch`, `allMatch`, `findFirst`, `sorted`), và xác nhận
  Stream không sửa danh sách gốc.
- `SanPham.java` + `ReduceCollectDemo.java` — `reduce()` để gộp thành một giá trị, và các
  `Collectors` phổ biến: `toMap`, `groupingBy`, `summingDouble`, `joining`.
