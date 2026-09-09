# Chương 29 — Code mẫu: BFS & DFS

```
javac *.java
java BreadthFirstSearch
java DepthFirstSearch
java DepthFirstSearchIterative
```

Cả 3 file dùng chung một đồ thị mẫu (dạng cây nhị phân 7 đỉnh) để dễ đối chiếu kết quả:

- `BreadthFirstSearch.java` — duyệt theo chiều rộng, dùng `Queue` (FIFO). Kết quả: `0 1 2 3 4 5 6`.
- `DepthFirstSearch.java` — duyệt theo chiều sâu, cài đặt bằng **đệ quy**. Kết quả: `0 1 3 4 2 5 6`.
- `DepthFirstSearchIterative.java` — cùng DFS nhưng cài đặt **không đệ quy**, dùng `Deque` làm ngăn
  xếp (stack) thủ công — cho kết quả giống hệt bản đệ quy, minh hoạ BFS/DFS thực chất chỉ khác nhau
  ở cấu trúc dữ liệu dùng để "nhớ" đỉnh cần xét tiếp theo (Queue vs Stack).
