# Chương 5 — Biến, kiểu dữ liệu nguyên thủy, ép kiểu

## Mục tiêu học

Sau chương này, bạn sẽ:

- Khai báo và dùng được biến với 8 kiểu dữ liệu nguyên thủy (primitive type) của Java.
- Hiểu sự khác nhau giữa kiểu nguyên thủy và kiểu tham chiếu (sẽ đào sâu ở Chương 13, ở đây chỉ
  cần biết chúng khác nhau).
- Khai báo hằng số bằng `final`, biết vì sao nên dùng hằng số thay vì "số ma thuật" rải trong code.
- Hiểu ép kiểu tự động (widening) và ép kiểu thủ công (narrowing), và rủi ro mất dữ liệu khi ép
  kiểu.
- Đọc dữ liệu người dùng nhập từ bàn phím bằng `Scanner`.

Code mẫu đầy đủ: [`code/ch05-bien-kieu-du-lieu/`](../../code/ch05-bien-kieu-du-lieu/).

## Biến là gì?

Biến (variable) là một **vùng nhớ có tên**, dùng để lưu một giá trị và có thể thay đổi giá trị đó
theo thời gian chạy chương trình. Trong Java, khai báo biến luôn cần nói rõ **kiểu dữ liệu**
(Java là ngôn ngữ **kiểu tĩnh** — statically typed: kiểu của biến được xác định lúc biên dịch, và
không đổi kiểu trong suốt vòng đời biến đó).

```java
int tuoi = 25;
```

Cú pháp: `<kiểu dữ liệu> <tên biến> = <giá trị>;`. `int` là kiểu, `tuoi` là tên biến, `25` là giá
trị khởi tạo.

**Quy ước đặt tên biến** trong Java: dùng `camelCase` (chữ đầu thường, các từ ghép sau viết hoa chữ
đầu) — ví dụ `soHocSinh`, `diemTrungBinh`. Đây là quy ước cộng đồng Java tuân theo nghiêm ngặt,
không tuân theo sẽ khiến code bạn "trông lạ" với người khác đọc.

## 8 kiểu dữ liệu nguyên thủy

Java có đúng 8 kiểu nguyên thủy (primitive type) — đây là các kiểu **không phải object**, lưu trực
tiếp giá trị (khác với kiểu tham chiếu như `String`, sẽ học kỹ lý do khác biệt ở Chương 13):

| Kiểu | Kích thước | Phạm vi giá trị | Ví dụ |
|---|---|---|---|
| `byte` | 8 bit | -128 đến 127 | `byte b = 100;` |
| `short` | 16 bit | -32,768 đến 32,767 | `short s = 1200;` |
| `int` | 32 bit | khoảng ±2.1 tỷ | `int i = 98_000_000;` |
| `long` | 64 bit | khoảng ±9.2 tỷ tỷ | `long l = 7_800_000_000L;` |
| `float` | 32 bit | số thực, độ chính xác đơn | `float f = 8.5f;` |
| `double` | 64 bit | số thực, độ chính xác kép | `double d = 3.14159;` |
| `char` | 16 bit | một ký tự Unicode | `char c = 'J';` |
| `boolean` | 1 bit (logic) | `true` hoặc `false` | `boolean done = false;` |

Vài lưu ý quan trọng:

- **Mặc định dùng `int` cho số nguyên, `double` cho số thực** — trừ khi có lý do cụ thể (tiết kiệm
  bộ nhớ với mảng cực lớn, hay số vượt phạm vi `int`). Người mới không cần cân nhắc `byte`/`short`
  trong phần lớn trường hợp.
- Số nguyên literal vượt phạm vi `int` **bắt buộc** có hậu tố `L` (ví dụ `7_800_000_000L`) — thiếu
  `L` trình biên dịch báo lỗi vì mặc định hiểu literal đó là `int`.
- Số thực literal mặc định là `double`; muốn gán cho `float` phải thêm hậu tố `f` (ví dụ `8.5f`) —
  vì gán trực tiếp `double` vào biến `float` là ép kiểu thu hẹp (narrowing), không hợp lệ nếu không
  ép kiểu rõ ràng.
- Dùng dấu gạch dưới `_` để phân tách số cho dễ đọc: `98_000_000` — trình biên dịch bỏ qua dấu `_`,
  hoàn toàn tương đương `98000000`, chỉ để mắt người đọc dễ hơn.
- `char` dùng nháy đơn `'J'`, khác với chuỗi ký tự dùng nháy kép `"J"` (chuỗi sẽ học ở Chương 11).

## Hằng số với `final`

```java
final double LAI_SUAT_CO_DINH = 0.05;
```

Từ khoá `final` khiến biến **chỉ gán được giá trị một lần** — gán lại lần thứ hai là lỗi biên dịch.
Quy ước đặt tên hằng số: viết hoa toàn bộ, cách nhau bằng `_` (ví dụ `MAX_SO_LUONG`,
`LAI_SUAT_CO_DINH`). Dùng hằng số thay vì viết thẳng con số ("magic number") rải rác trong code
giúp code dễ đọc hơn (đọc `LAI_SUAT_CO_DINH` hiểu ngay ý nghĩa, đọc `0.05` một mình thì không) và
dễ sửa hơn (đổi giá trị một chỗ duy nhất thay vì tìm sửa khắp file).

## Ép kiểu (type casting)

### Ép kiểu tự động (widening) — an toàn

Khi gán giá trị từ kiểu **nhỏ hơn** sang kiểu **lớn hơn**, Java tự động chuyển đổi, không cần cú
pháp gì thêm, vì không có nguy cơ mất dữ liệu:

```java
int soNguyen = 10;
double soThuc = soNguyen; // int -> double, tu dong, an toan
```

Thứ tự từ nhỏ đến lớn (mỗi kiểu widening được sang mọi kiểu bên phải nó):
`byte → short → int → long → float → double`.

### Ép kiểu thủ công (narrowing) — có thể mất dữ liệu

Chiều ngược lại (kiểu lớn sang kiểu nhỏ) **không** tự động — bạn phải viết rõ ràng bằng cú pháp
`(kiểu đích)`, để xác nhận bạn hiểu và chấp nhận rủi ro mất dữ liệu:

```java
double gia = 19.99;
int giaLamTron = (int) gia; // ket qua la 19 - CAT phan thap phan, khong lam tron
```

**Hiểu lầm rất phổ biến**: `(int) gia` **không làm tròn** — nó **cắt bỏ hoàn toàn** phần thập
phân. `(int) 19.99` cho ra `19`, không phải `20`. Muốn làm tròn đúng nghĩa, dùng
`Math.round(gia)` (sẽ gặp lại khi học thư viện `Math` chi tiết hơn ở các chương sau).

Ép kiểu số quá lớn vào kiểu quá nhỏ còn gây ra **tràn số (overflow)** — không chỉ mất phần thập
phân mà giá trị có thể sai lệch hoàn toàn:

```java
int soLon = 130;
byte soBiTran = (byte) soLon; // KHONG ra 130 - byte chi chua duoc toi da 127
```

Chạy thử trong code mẫu để thấy `soBiTran` thực sự in ra giá trị âm — vì `byte` "vòng lại" khi vượt
quá phạm vi biểu diễn của nó (cơ chế này gọi là *integer overflow*, liên quan cách máy tính biểu
diễn số nguyên trong bộ nhớ, không nằm trong phạm vi chương này).

## Đọc dữ liệu người dùng nhập vào với `Scanner`

Để chương trình tương tác được (thay vì chỉ in ra giá trị cố định), dùng lớp `Scanner`:

```java
import java.util.Scanner;

public class TemperatureConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhap nhiet do theo do C: ");
        double celsius = scanner.nextDouble();

        double fahrenheit = celsius * 9 / 5 + 32;
        System.out.println(celsius + " do C = " + fahrenheit + " do F");

        scanner.close();
    }
}
```

- `import java.util.Scanner;` — nạp lớp `Scanner` từ thư viện chuẩn để dùng được trong file này
  (khái niệm `import`/package sẽ học kỹ ở Chương 15).
- `new Scanner(System.in)` — tạo một `Scanner` đọc từ bàn phím (`System.in` là "cổng vào" chuẩn,
  đối lập với `System.out` đã dùng ở Chương 3).
- `scanner.nextDouble()` — đợi người dùng gõ một số thực rồi nhấn Enter, trả về giá trị đó dưới
  dạng `double`. Có các phương thức tương tự: `nextInt()`, `nextLine()` (đọc cả dòng, kể cả có dấu
  cách — sẽ dùng nhiều ở Chương 11 khi làm việc với `String`).
- `scanner.close()` — đóng scanner khi dùng xong, giải phóng tài nguyên (thói quen tốt, dù chương
  trình nhỏ ở đây không ảnh hưởng nhiều nếu quên).

## Bài tập

1. Viết chương trình khai báo biến `double luongThang` và biến `final int SO_THANG_MOT_NAM = 12`,
   tính và in ra tổng thu nhập một năm.
2. Sửa `TemperatureConverter.java` thành chương trình đổi ngược: nhập độ F, in ra độ C
   (`celsius = (fahrenheit - 32) * 5 / 9`).
3. Giải thích bằng lời (không chạy thử trước): `(int) 7.9` cho ra kết quả gì? `(int) -7.9` cho ra
   kết quả gì? Sau đó chạy thử để kiểm tra dự đoán.
4. Thử gán `float f = 3.14;` (không có hậu tố `f` ở cuối số) — đọc lỗi biên dịch, giải thích vì sao
   lỗi xảy ra dựa trên phần "ép kiểu thủ công" ở trên.

## Lỗi thường gặp

- **`incompatible types: possible lossy conversion from double to int`** — bạn đang gán trực tiếp
  giá trị `double`/`float` cho biến `int` mà không ép kiểu. Thêm `(int)` phía trước nếu chấp nhận
  mất phần thập phân, hoặc đổi kiểu biến sang `double`.
- **`error: incompatible types: possible lossy conversion from double to float`** khi viết
  `float f = 3.14;`** — literal số thực mặc định là `double`; thêm hậu tố `f`: `float f = 3.14f;`.
- **Ép kiểu `(int)` xong tưởng đã làm tròn** — nhắc lại: `(int)` cắt bỏ phần thập phân, không làm
  tròn. Dùng `Math.round(...)` nếu cần làm tròn đúng nghĩa.
- **Gán lại giá trị cho biến `final`** — báo lỗi `cannot assign a value to final variable`. Đây là
  hành vi đúng theo thiết kế, không phải bug: `final` tồn tại chính để ngăn việc này.
- **`InputMismatchException` khi chạy `Scanner`** — người dùng nhập chữ trong khi chương trình gọi
  `nextDouble()`/`nextInt()`. Đây là một dạng lỗi runtime (exception) — cách xử lý đúng cách sẽ học
  ở Chương 21.
