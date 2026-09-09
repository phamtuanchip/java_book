# Chương 9 — Mảng một chiều và nhiều chiều

## Mục tiêu học

Sau chương này, bạn sẽ:

- Khai báo, khởi tạo, đọc/ghi phần tử của mảng một chiều.
- Duyệt mảng bằng `for` có chỉ số và `for-each`, biết khi nào dùng cách nào.
- Hiểu mảng là **kiểu tham chiếu**, không phải kiểu nguyên thủy — hệ quả khi gán/truyền mảng.
- Làm việc với mảng hai chiều (ma trận) và mảng không đều (jagged array).
- Nhận diện và hiểu lỗi truy cập ngoài phạm vi (`ArrayIndexOutOfBoundsException`).

Code mẫu đầy đủ: [`code/ch09-mang/`](../../code/ch09-mang/).

## Khai báo và khởi tạo mảng

```java
int[] diem = new int[5]; // mang 5 phan tu kieu int, gia tri mac dinh la 0
diem[0] = 8;
diem[1] = 7;
```

`new int[5]` tạo một mảng có **kích thước cố định 5 phần tử**, đánh chỉ số từ `0` đến `4` (chỉ số
**luôn bắt đầu từ 0**, không phải 1 — đây là quy ước hầu hết ngôn ngữ lập trình dùng, khác thói
quen đếm 1, 2, 3... ngoài đời). Kích thước mảng **không đổi được** sau khi tạo — muốn "thêm phần
tử" thực chất phải tạo mảng mới lớn hơn và sao chép dữ liệu (Chương 24 sẽ giới thiệu `ArrayList`,
cấu trúc dữ liệu giải quyết đúng hạn chế này bằng cách tự động quản lý việc đó cho bạn).

Cách khởi tạo trực tiếp với giá trị có sẵn (kích thước tự suy ra từ số phần tử liệt kê):

```java
String[] ten = {"An", "Binh", "Chi", "Dung", "Em"};
```

Mọi kiểu — kể cả kiểu tham chiếu như `String` — đều tạo mảng được theo cùng cú pháp.

## Duyệt mảng

### `for` có chỉ số — khi cần biết vị trí phần tử

```java
for (int i = 0; i < diem.length; i++) {
    System.out.println(ten[i] + ": " + diem[i] + " diem");
}
```

`diem.length` (không có ngoặc đơn — khác với `String.length()` sẽ học ở Chương 11, đây là điểm dễ
nhầm) trả về số phần tử của mảng. Dùng `for` có chỉ số khi bạn cần chỉ số để làm việc khác (ví dụ ở
đây: dùng cùng chỉ số `i` để lấy tương ứng cả `ten[i]` lẫn `diem[i]` từ hai mảng song song).

### `for-each` — khi chỉ cần giá trị, không cần chỉ số

```java
int tong = 0;
for (int d : diem) {
    tong += d;
}
```

Đọc là "với mỗi `d` trong `diem`". Ngắn gọn và ít lỗi hơn (không có nguy cơ viết sai điều kiện biên
như `<=` thay vì `<`), nhưng **không cho biết chỉ số hiện tại** và **không sửa được phần tử gốc
trong mảng** qua biến `d` (`d` chỉ là bản sao giá trị tại mỗi lượt lặp, với mảng kiểu nguyên thủy).
Dùng `for-each` khi bạn chỉ cần đọc qua từng giá trị, không cần chỉ số.

## Mảng là kiểu tham chiếu

Đây là khái niệm quan trọng, sẽ đào sâu đầy đủ ở Chương 13 khi học về object — nhưng cần biết ngay
từ bây giờ vì rất dễ gây lỗi khó hiểu:

```java
int[] diemBanSao = diem;
diemBanSao[0] = 100;
System.out.println(diem[0]); // in ra 100, KHONG phai gia tri cu!
```

`diemBanSao = diem` **không sao chép nội dung mảng** — nó chỉ tạo ra một biến khác **cùng trỏ tới
mảng gốc trong bộ nhớ**. Sửa qua `diemBanSao` chính là sửa mảng gốc, vì cả hai biến trỏ tới cùng
một chỗ. Đây khác hẳn với biến kiểu nguyên thủy (`int a = b;` thực sự sao chép giá trị).

Muốn sao chép **thật sự** nội dung mảng, dùng `Arrays.copyOf`:

```java
import java.util.Arrays;

int[] diemSaoChepThat = Arrays.copyOf(diem, diem.length);
diemSaoChepThat[0] = -1;
// diem[0] khong doi, vi day la mang moc hoan toan doc lap
```

## In mảng đúng cách

```java
System.out.println(diem); // in ra thu gi do nhu [I@1b6d3586 - VO ICH
System.out.println(Arrays.toString(diem)); // in ra [8, 7, 9, 6, 10] - DUNG Y MUON
```

Mảng không override cách hiển thị dạng chuỗi mặc định (lý do kỹ thuật sẽ hiểu rõ ở Chương 20 khi
học `toString()`), nên `println` trực tiếp trên mảng cho ra một chuỗi mã định danh vô nghĩa với
người đọc. Luôn dùng `Arrays.toString(...)` (cho mảng một chiều) hoặc `Arrays.deepToString(...)`
(cho mảng nhiều chiều) khi cần in mảng ra để debug.

## Mảng hai chiều

```java
int[][] banCo = new int[3][4]; // 3 hang, 4 cot
banCo[1][2] = 99; // hang 1, cot 2 (chi so deu bat dau tu 0)
```

Về bản chất, mảng hai chiều trong Java là **mảng của các mảng** — `banCo` là một mảng gồm 3 phần
tử, mỗi phần tử lại là một mảng `int[4]`. Duyệt bằng vòng lặp lồng nhau:

```java
for (int hang = 0; hang < banCo.length; hang++) {
    for (int cot = 0; cot < banCo[hang].length; cot++) {
        // xu ly banCo[hang][cot]
    }
}
```

### Mảng không đều (jagged array)

Vì mảng 2 chiều thực chất là mảng của mảng, mỗi "hàng" có thể có **độ dài khác nhau**:

```java
int[][] mangKhongDeu = new int[3][];
mangKhongDeu[0] = new int[]{1};
mangKhongDeu[1] = new int[]{1, 2};
mangKhongDeu[2] = new int[]{1, 2, 3};
```

Khai báo `new int[3][]` tạo mảng ngoài có 3 phần tử, nhưng **chưa** tạo các mảng con bên trong
(mỗi phần tử tạm thời là `null` — khái niệm `null` sẽ học kỹ ở Chương 13) — bạn tự gán từng mảng
con với độ dài tuỳ ý. Đây là cấu trúc hữu ích khi dữ liệu tự nhiên không đều (ví dụ: số ngày trong
từng tháng của một năm).

## Bài tập

1. Viết chương trình tạo mảng 10 số nguyên ngẫu nhiên (`(int) (Math.random() * 100)`), in ra mảng,
   tìm và in giá trị lớn nhất, nhỏ nhất bằng vòng lặp.
2. Viết hàm (tạm thời viết trực tiếp trong `main`, học cách tách phương thức riêng ở Chương 10) đảo
   ngược thứ tự phần tử của một mảng `int[]` tại chỗ (không tạo mảng mới).
3. Tạo mảng hai chiều 3x3 đại diện một bàn cờ caro, gán giá trị `1` cho các ô trên đường chéo
   chính, in ra ma trận kết quả.
4. Bỏ comment dòng `diem[10]` trong `ArrayBasicsDemo.java`, chạy lại, đọc kỹ thông báo lỗi
   `ArrayIndexOutOfBoundsException` — nó cho biết chỉ số nào bị truy cập sai.

## Lỗi thường gặp

- **`ArrayIndexOutOfBoundsException`** — truy cập chỉ số `< 0` hoặc `>= length`. Nhắc lại: chỉ số
  hợp lệ của mảng `n` phần tử là từ `0` đến `n - 1`, **không phải** đến `n`.
- **Nhầm `.length` (thuộc tính, không ngoặc đơn) với `.length()` (phương thức của `String`, có
  ngoặc đơn)** — dùng sai gây lỗi biên dịch ngay lập tức, nhưng người mới hay mất thời gian tìm
  hiểu vì sao.
- **Tưởng gán mảng (`b = a`) là sao chép, sửa `b` rồi ngạc nhiên vì `a` cũng đổi theo** — xem lại
  phần "Mảng là kiểu tham chiếu" ở trên, dùng `Arrays.copyOf` khi cần bản sao thật sự độc lập.
- **In mảng trực tiếp bằng `println(mang)` rồi thắc mắc sao ra chuỗi kỳ lạ** — dùng
  `Arrays.toString(mang)`.
- **Quên rằng kích thước mảng cố định, cố "thêm phần tử" vào mảng đã đầy** — mảng không hỗ trợ thêm
  phần tử động; cần cấu trúc dữ liệu khác (`ArrayList`, Chương 24) nếu số lượng phần tử thay đổi
  trong lúc chạy.
