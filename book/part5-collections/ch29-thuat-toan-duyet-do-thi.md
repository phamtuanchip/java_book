# Chương 29 — Duyệt đồ thị/cây: BFS & DFS

## Mục tiêu học

Sau chương này, bạn sẽ:

- Biểu diễn được đồ thị/cây trong Java bằng danh sách kề (adjacency list).
- Cài đặt BFS (duyệt theo chiều rộng) dùng `Queue`.
- Cài đặt DFS (duyệt theo chiều sâu) dùng đệ quy, và hiểu **đệ quy** là gì (khái niệm mới, lần đầu
  xuất hiện trong sách).
- Nhận ra BFS và DFS thực chất chỉ khác nhau ở **cấu trúc dữ liệu** dùng để ghi nhớ thứ tự xử lý
  (Queue vs Stack).

Code mẫu đầy đủ: [`code/ch29-thuat-toan-duyet-do-thi/`](../../code/ch29-thuat-toan-duyet-do-thi/).

## Biểu diễn đồ thị bằng danh sách kề

**Đồ thị** (graph) gồm các **đỉnh** (node/vertex) và **cạnh** (edge) nối giữa chúng. Cách biểu diễn
phổ biến nhất trong code: **danh sách kề** (adjacency list) — với mỗi đỉnh, lưu danh sách các đỉnh
nó nối trực tiếp tới:

```java
List<List<Integer>> doThi = new ArrayList<>();
for (int i = 0; i < soDinh; i++) {
    doThi.add(new ArrayList<>());
}
doThi.get(0).add(1); // dinh 0 noi toi dinh 1
doThi.get(0).add(2); // dinh 0 noi toi dinh 2
```

`doThi.get(i)` trả về danh sách các đỉnh **kề** (nối trực tiếp) với đỉnh `i`. Cây (tree) — cấu trúc
bạn có thể quen thuộc hơn (thư mục lồng nhau, cây gia phả...) — thực chất chỉ là một dạng **đặc
biệt** của đồ thị (không có chu trình, mỗi đỉnh chỉ có đúng một đỉnh cha), nên mọi thuật toán duyệt
đồ thị trong chương này áp dụng được trực tiếp cho cây.

## BFS (Breadth-First Search) — duyệt theo chiều rộng

```java
private static void bfs(List<List<Integer>> doThi, int dinhBatDau) {
    boolean[] daTham = new boolean[doThi.size()];
    Queue<Integer> hangDoi = new LinkedList<>();

    daTham[dinhBatDau] = true;
    hangDoi.offer(dinhBatDau);

    while (!hangDoi.isEmpty()) {
        int dinh = hangDoi.poll();
        System.out.print(dinh + " ");

        for (int dinhKe : doThi.get(dinh)) {
            if (!daTham[dinhKe]) {
                daTham[dinhKe] = true;
                hangDoi.offer(dinhKe);
            }
        }
    }
}
```

BFS thăm **tất cả đỉnh ở cùng khoảng cách** (số cạnh) từ đỉnh bắt đầu, trước khi sang khoảng cách xa
hơn — giống cách sóng lan toả đều ra mọi hướng từ một điểm ném xuống nước. Với cây ví dụ trong code
mẫu (đỉnh 0 là gốc, 1-2 là con của 0, 3-4-5-6 là cháu), BFS cho kết quả `0 1 2 3 4 5 6` — đúng
**từng tầng** một.

Cơ chế: dùng `Queue` (FIFO, Chương 23) để ghi nhớ các đỉnh **đã phát hiện nhưng chưa xử lý** — mỗi
lần lấy một đỉnh ra khỏi hàng đợi để xử lý, đồng thời **thêm mọi đỉnh kề chưa thăm** của nó vào
cuối hàng đợi. Vì hàng đợi luôn xử lý phần tử **cũ nhất** trước, các đỉnh ở tầng gần hơn (được thêm
vào trước) luôn được xử lý trước các đỉnh ở tầng xa hơn.

Mảng `daTham` (visited) **bắt buộc phải có** — nếu không, với đồ thị có chu trình (một đỉnh có
đường quay lại chính nó qua các cạnh khác), thuật toán sẽ **lặp vô hạn**, liên tục thăm lại các
đỉnh đã thăm.

## DFS (Depth-First Search) — duyệt theo chiều sâu, và khái niệm đệ quy

```java
private static void dfs(List<List<Integer>> doThi, int dinh, boolean[] daTham) {
    daTham[dinh] = true;
    System.out.print(dinh + " ");

    for (int dinhKe : doThi.get(dinh)) {
        if (!daTham[dinhKe]) {
            dfs(doThi, dinhKe, daTham); // phuong thuc TU GOI LAI CHINH NO
        }
    }
}
```

DFS đi **thật sâu theo một nhánh trước**, chỉ quay lại thử nhánh khác khi không còn đường đi tiếp —
giống cách bạn dò một mê cung: đi theo một lối tới khi bí, mới lùi lại thử lối khác. Với cùng cây
ví dụ, DFS cho kết quả `0 1 3 4 2 5 6` — đi hết nhánh trái (0→1→3, lùi lại →4) rồi mới sang nhánh
phải (0→2→5, lùi lại →6).

### Đệ quy (recursion) là gì?

Đây là **lần đầu tiên** sách dùng kỹ thuật này: `dfs(...)` **tự gọi lại chính nó** bên trong thân
của nó — gọi là **đệ quy**. Mỗi lần `dfs` gọi tiếp `dfs` cho một đỉnh kề, một "phiên bản mới" của
phương thức bắt đầu chạy (với `dinh` là tham số khác), phiên bản gọi nó tạm "treo" lại, đợi phiên
bản mới chạy xong hoàn toàn rồi mới tiếp tục vòng lặp `for` của chính nó. Java tự quản lý việc "treo
và tiếp tục" này bằng một cấu trúc bên trong gọi là **call stack** (ngăn xếp lời gọi) — mỗi lần gọi
đệ quy chồng thêm một "lớp" mới lên ngăn xếp, lớp đó chỉ được gỡ ra khi lời gọi đó **hoàn toàn kết
thúc** (chạy hết thân phương thức, hoặc gặp `return`).

Đệ quy luôn cần một **điều kiện dừng** — ở đây, điều kiện `if (!daTham[dinhKe])` đảm bảo `dfs`
không bao giờ tự gọi lại cho một đỉnh **đã thăm**, nên cuối cùng sẽ hết đỉnh mới để gọi tiếp và tự
dừng. Thiếu điều kiện dừng đúng đắn sẽ gây đệ quy **vô hạn**, cuối cùng ném lỗi
`StackOverflowError` (ngăn xếp lời gọi đầy, không phải lỗi bạn tự bắt bằng `try/catch` thông
thường — đây là dấu hiệu gần như chắc chắn của lỗi logic đệ quy).

## DFS không dùng đệ quy — làm rõ mối quan hệ BFS/DFS

```java
Deque<Integer> nganXep = new ArrayDeque<>(); // dung lam STACK (LIFO)
nganXep.push(dinhBatDau);
while (!nganXep.isEmpty()) {
    int dinh = nganXep.pop(); // lay dinh THEM VAO GAN DAY NHAT
    // ...
}
```

Mọi thuật toán đệ quy đều **viết lại được** dưới dạng vòng lặp thường, dùng một cấu trúc dữ liệu
tường minh thay cho call stack ngầm định của Java. Với DFS, cấu trúc đó là **ngăn xếp (stack, LIFO
— Vào Sau Ra Trước)**: `push` thêm vào "đỉnh" ngăn xếp, `pop` lấy ra đúng phần tử **vừa thêm gần
nhất**.

So sánh với BFS: **BFS dùng Queue (FIFO), DFS dùng Stack (LIFO)** — đây là khác biệt cốt lõi duy
nhất giữa hai thuật toán, phần logic còn lại (đánh dấu đã thăm, duyệt đỉnh kề) gần như giống hệt
nhau. Hiểu rõ điểm này giúp bạn nhớ và cài đặt lại cả hai thuật toán dễ dàng hơn nhiều so với học
thuộc lòng từng đoạn code riêng biệt.

## Bài tập

1. Thêm một đỉnh thứ 8 vào đồ thị mẫu, nối với đỉnh 3, chạy lại cả BFS và DFS, dự đoán kết quả
   trước khi chạy để kiểm tra.
2. Viết một đồ thị **có chu trình** (ví dụ thêm cạnh nối đỉnh 6 quay lại đỉnh 0), chạy BFS/DFS,
   xác nhận thuật toán vẫn dừng đúng nhờ mảng `daTham` (không bị lặp vô hạn).
3. Viết một biến thể BFS trả về **khoảng cách** (số cạnh) từ đỉnh bắt đầu tới từng đỉnh khác, thay
   vì chỉ in ra thứ tự duyệt (gợi ý: dùng thêm một mảng `int[] khoangCach`, cập nhật
   `khoangCach[dinhKe] = khoangCach[dinh] + 1` mỗi khi thêm `dinhKe` vào hàng đợi).

## Lỗi thường gặp

- **Quên đánh dấu `daTham`, hoặc đánh dấu sai thời điểm** — gây duyệt lặp lại vô hạn với đồ thị có
  chu trình, hoặc duyệt trùng đỉnh không cần thiết. Trong BFS, nên đánh dấu **ngay khi thêm vào
  hàng đợi** (không phải khi lấy ra xử lý) để tránh thêm trùng một đỉnh nhiều lần trước khi nó được
  xử lý.
- **`StackOverflowError` khi DFS đệ quy trên đồ thị/cây rất sâu** — call stack có giới hạn kích
  thước; với dữ liệu đủ lớn (hàng chục nghìn tầng sâu trở lên), bản đệ quy có thể tràn ngăn xếp.
  Bản không đệ quy (dùng `Deque` làm stack tường minh) không gặp giới hạn này vì ngăn xếp tự cấp
  phát trên heap thay vì call stack cố định.
- **Nhầm lẫn BFS luôn "tốt hơn" hoặc "đúng hơn" DFS (hoặc ngược lại)** — cả hai chỉ là hai chiến
  lược duyệt khác nhau, phù hợp cho bài toán khác nhau: BFS thường dùng khi cần tìm đường đi
  **ngắn nhất** (tính theo số cạnh) trong đồ thị không trọng số; DFS thường dùng khi cần khám phá
  **toàn bộ** một nhánh trước khi xét nhánh khác (ví dụ: kiểm tra chu trình, sắp xếp tô-pô).
- **Trong bản DFS không đệ quy, quên đảo ngược thứ tự khi `push` các đỉnh kề** — vì Stack là LIFO,
  nếu không đảo ngược thứ tự thêm vào, thứ tự duyệt cuối cùng sẽ **ngược lại** so với bản đệ quy
  (dù vẫn là DFS hợp lệ, chỉ khác thứ tự cụ thể) — xem chi tiết cách xử lý trong
  `DepthFirstSearchIterative.java`.
