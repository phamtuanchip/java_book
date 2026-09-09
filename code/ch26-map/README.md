# Chương 26 — Code mẫu: HashMap, TreeMap, LinkedHashMap

```
javac MapDemo.java
java MapDemo
```

Thao tác cơ bản (`put`/`get`/`remove`/`containsKey`/`getOrDefault`), 3 cách duyệt `Map`
(`keySet()`, `values()`, `entrySet()`), so sánh `HashMap`/`LinkedHashMap`/`TreeMap`, và bài toán
kinh điển "đếm số lần xuất hiện" — viết theo cách dài (kiểm tra `containsKey` rồi cộng dồn) và cách
gọn hơn bằng `merge()` (dùng method reference `Integer::sum`, sẽ học kỹ ở Chương 30 — ở đây chỉ cần
hiểu nó cộng dồn giá trị).
