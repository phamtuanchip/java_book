# Chương 7 — Cấu trúc điều khiển: if/else, switch

## Mục tiêu học

Sau chương này, bạn sẽ:

- Viết được rẽ nhánh với `if / else if / else`, hiểu rõ thứ tự kiểm tra điều kiện.
- Dùng toán tử ba ngôi (ternary `?:`) thay `if/else` khi chỉ cần gán một giá trị.
- Viết được `switch` statement kiểu cũ (kèm `break`) và `switch` expression kiểu mới (Java 14+),
  hiểu vì sao kiểu mới an toàn hơn.
- Hiểu hiện tượng "fall-through" trong `switch` cũ — khi nào nó là lỗi, khi nào nó được dùng có chủ
  đích.

Code mẫu đầy đủ: [`code/ch07-cau-truc-dieu-khien/`](../../code/ch07-cau-truc-dieu-khien/).

## `if / else if / else`

```java
if (diem >= 9.0) {
    xepLoai = "Xuat sac";
} else if (diem >= 8.0) {
    xepLoai = "Gioi";
} else if (diem >= 6.5) {
    xepLoai = "Kha";
} else {
    xepLoai = "Trung binh hoac Yeu";
}
```

Java kiểm tra các điều kiện **theo thứ tự từ trên xuống**, chạy vào nhánh đầu tiên có điều kiện
đúng rồi **bỏ qua mọi nhánh còn lại** — kể cả khi điều kiện của những nhánh sau đó cũng đúng. Đây
là lý do thứ tự các điều kiện trong chuỗi `if/else if` quan trọng: nếu bạn viết điều kiện
`diem >= 6.5` **trước** `diem >= 9.0`, một học sinh 9.5 điểm sẽ bị rơi vào nhánh "Kha" vì
`9.5 >= 6.5` đúng và được kiểm tra trước — đây là lỗi logic rất phổ biến, luôn sắp điều kiện từ
**chặt nhất đến lỏng nhất** khi các điều kiện có thể cùng đúng.

Dấu ngoặc nhọn `{ }` không bắt buộc nếu thân nhánh chỉ có một câu lệnh, nhưng **luôn nên dùng** —
thiếu ngoặc nhọn là nguồn lỗi kinh điển khi bạn thêm dòng thứ hai vào thân nhánh mà quên thêm
ngoặc, dòng mới sẽ chạy **vô điều kiện**, không còn thuộc về nhánh `if` nữa.

## Toán tử ba ngôi (ternary `?:`)

```java
String ketQua = (diem >= 5.0) ? "Dat" : "Khong dat";
```

Đọc là: nếu điều kiện đúng, lấy giá trị trước dấu `:`; nếu sai, lấy giá trị sau dấu `:`. Đây là
cách viết gọn của:

```java
String ketQua;
if (diem >= 5.0) {
    ketQua = "Dat";
} else {
    ketQua = "Khong dat";
}
```

Chỉ nên dùng ternary khi biểu thức **đơn giản, một dòng**. Lồng nhiều ternary vào nhau
(`a ? b : c ? d : e`) làm code khó đọc — trong trường hợp đó, dùng `if/else` hoặc `switch` tường
minh hơn.

## `switch` statement (kiểu truyền thống)

```java
switch (thu) {
    case 1:
        System.out.println("Thu Hai");
        break;
    case 2:
        System.out.println("Thu Ba");
        break;
    default:
        System.out.println("Ngay khong hop le");
}
```

`switch` so khớp giá trị của biến (`thu`) với từng `case`. **`break` bắt buộc phải có** ở cuối mỗi
nhánh — nếu thiếu, chương trình **tiếp tục chạy xuống nhánh `case` tiếp theo** dù điều kiện của
nhánh đó không khớp, hiện tượng gọi là **fall-through**:

```java
switch (thu) {
    case 1:
        System.out.println("Thu Hai");
        // QUEN break!
    case 2:
        System.out.println("Thu Ba");
        break;
}
```

Với `thu = 1`, đoạn trên in ra **cả hai dòng** "Thu Hai" và "Thu Ba" — vì thiếu `break`, luồng chạy
rơi tiếp xuống `case 2`. Đây là một trong những lỗi phổ biến và khó phát hiện nhất với người mới
dùng `switch` kiểu cũ (chương trình vẫn biên dịch được bình thường, không có cảnh báo).

Fall-through đôi khi được dùng **có chủ đích**, để gộp nhiều `case` xử lý chung một khối lệnh (xem
`DayOfWeekSwitch.java`, phần tính số ngày trong tháng — `case 1: case 3: case 5: ...` gộp các
tháng có 31 ngày dùng chung một `break` ở cuối).

## `switch` expression (Java 14+) — cách viết hiện đại, an toàn hơn

```java
String tenThu = switch (thu) {
    case 1 -> "Thu Hai";
    case 2 -> "Thu Ba";
    case 3 -> "Thu Tu";
    default -> "Ngay khong hop le";
};
```

Khác biệt cốt lõi so với `switch` statement:

- Dùng mũi tên `->` thay vì dấu `:`, **không cần `break`** — mỗi nhánh chỉ chạy đúng một biểu thức
  rồi kết thúc, **không có fall-through**. Đây là lý do nên ưu tiên dùng `switch` expression khi có
  thể: loại bỏ hẳn một nhóm lỗi mà `switch` statement cũ có thể gặp phải.
- `switch` expression **trả về giá trị trực tiếp** (có thể gán thẳng cho biến như trên), khác với
  `switch` statement chỉ thực thi các câu lệnh, không trả giá trị.
- Trình biên dịch bắt buộc bạn xử lý **đầy đủ mọi trường hợp** (yêu cầu có `default`, trừ khi
  `switch` trên kiểu `enum` liệt kê đủ mọi giá trị có thể — sẽ gặp lại khi học `enum` ở Chương 19).

**Lời khuyên**: với code mới (Java 14 trở lên), ưu tiên `switch` expression. Sách vẫn dạy
`switch` statement kiểu cũ vì bạn sẽ gặp rất nhiều code cũ ngoài thực tế dùng cú pháp này.

## Bài tập

1. Viết chương trình dùng `if/else if/else` phân loại BMI (chỉ số khối cơ thể) thành "Gầy",
   "Bình thường", "Thừa cân", "Béo phì" dựa trên các mốc chuẩn (tự tra cứu mốc BMI).
2. Cố tình xoá một dòng `break` trong `DayOfWeekSwitch.java` (không phải dòng trong nhóm gộp
   case có chủ đích), chạy lại và quan sát hiện tượng fall-through xảy ra thế nào.
3. Viết lại toàn bộ `switch` statement trong bài tập 2 dưới dạng `switch` expression, xác nhận
   không còn khả năng xảy ra lỗi fall-through nữa dù bạn cố tình bỏ sót.

## Lỗi thường gặp

- **Quên `break` trong `switch` statement, chạy sai kết quả (fall-through ngoài ý muốn)** — lỗi
  không được trình biên dịch cảnh báo, chỉ phát hiện được khi chạy và thấy kết quả in ra nhiều hơn
  mong đợi. Cách phòng ngừa tốt nhất: ưu tiên `switch` expression cho code mới.
- **Sắp thứ tự điều kiện `if/else if` sai (điều kiện lỏng đứng trước điều kiện chặt)** — dẫn tới
  rơi vào nhánh sai dù về mặt cú pháp code hoàn toàn hợp lệ. Xem lại phần "if/else if/else" ở trên.
- **Thiếu `{ }` khi thân nhánh có từ 2 dòng trở lên** — chỉ dòng ngay sau `if` thuộc về nhánh đó,
  các dòng sau chạy vô điều kiện. Luôn dùng `{ }` kể cả khi thân nhánh chỉ có một dòng, để tránh
  lỗi này khi code được sửa thêm sau này.
- **`switch` trên kiểu không hỗ trợ** (ví dụ `double`) — `switch` (cả hai kiểu) chỉ hỗ trợ
  `byte`/`short`/`char`/`int` (và kiểu wrapper tương ứng), `String`, và `enum` — không hỗ trợ
  `double`/`float`/`boolean`/`long`.
