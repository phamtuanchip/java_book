# Chương 25 — Code mẫu: HashSet, TreeSet, LinkedHashSet

```
javac SetDemo.java
java SetDemo
```

So sánh 3 loại `Set`: `HashSet` (nhanh nhất, không đảm bảo thứ tự), `LinkedHashSet` (giữ đúng thứ
tự thêm vào), `TreeSet` (tự động sắp xếp). Minh hoạ các phép toán tập hợp (hợp/giao/hiệu) qua
`addAll`/`retainAll`/`removeAll`. Phần cuối minh hoạ trực tiếp hệ quả của Chương 20: `HashSet` với
object **có** override `equals`/`hashCode` đúng cách loại được trùng lặp theo nội dung; object
**không** override thì không loại được, dù nội dung giống hệt nhau.
