# Chương 6 — Toán tử và biểu thức

## Mục tiêu học

Sau chương này, bạn sẽ:

- Dùng đúng các nhóm toán tử: số học, so sánh, logic, tăng/giảm.
- Phân biệt phép chia số nguyên và chia số thực — lỗi rất phổ biến với người mới.
- Hiểu khác biệt giữa `x++` và `++x`.
- Hiểu cơ chế **short-circuit evaluation** của `&&` và `||`, và vì sao nó quan trọng không chỉ về
  hiệu năng mà cả về tránh lỗi runtime.
- Đọc đúng độ ưu tiên toán tử (operator precedence) trong biểu thức phức tạp.

Code mẫu đầy đủ: [`code/ch06-toan-tu-bieu-thuc/`](../../code/ch06-toan-tu-bieu-thuc/).

## Toán tử số học

`+  -  *  /  %` — cộng, trừ, nhân, chia, chia lấy dư. Cẩn thận với phép chia:

```java
int a = 17, b = 5;
System.out.println(a / b);     // 3 - chia SO NGUYEN, cat phan du, KHONG lam tron
System.out.println(a % b);     // 2 - phan du cua phep chia (17 = 5*3 + 2)
System.out.println(17.0 / 5);  // 3.4 - chia SO THUC vi co mot toan hang la double
```

**Quy tắc quan trọng nhất phần này**: nếu **cả hai toán hạng** của phép `/` đều là kiểu số nguyên
(`int`, `long`...), kết quả là số nguyên, phần dư bị cắt bỏ hoàn toàn — dù bạn có gán kết quả cho
biến `double` thì phép chia vẫn đã bị cắt **trước khi** gán:

```java
double ketQua = 17 / 5; // ket qua la 3.0, KHONG phai 3.4 - phep chia da xay ra tren so nguyen truoc
double ketQuaDung = 17.0 / 5; // 3.4 - dung vi ep mot toan hang thanh double truoc khi chia
```

Muốn chia ra kết quả thực, phải đảm bảo **ít nhất một** toán hạng là `double`/`float` (bằng cách
viết literal có dấu chấm thập phân, hoặc ép kiểu một biến sang `double`).

## Toán tử tăng/giảm: `++` và `--`

```java
int x = 5;
int y = x++; // y nhan gia tri CU cua x (5), SAU DO x moi tang len 6
// x = 6, y = 5

int m = 5;
int n = ++m; // m tang len 6 TRUOC, n nhan gia tri MOI (6)
// m = 6, n = 6
```

- `x++` (post-increment): dùng giá trị hiện tại trước, rồi mới tăng.
- `++x` (pre-increment): tăng trước, rồi mới dùng giá trị mới.

Khi đứng một mình trên một dòng riêng (`dem++;`), hai cách viết cho kết quả giống hệt nhau. Khác
biệt chỉ lộ ra khi bạn dùng **kết quả** của biểu thức tăng/giảm đó ngay trong cùng dòng (như ví dụ
trên) — tình huống này dễ gây nhầm lẫn, nên nhiều lập trình viên tránh viết `x++`/`++x` lồng trong
biểu thức phức tạp, tách thành hai dòng riêng cho rõ ràng.

## Toán tử so sánh

`>  <  >=  <=  ==  !=` — trả về `boolean` (`true`/`false`). Lưu ý `==` so sánh **giá trị** với kiểu
nguyên thủy (`int`, `double`...), nhưng với kiểu tham chiếu (như `String`) `==` lại so sánh khác —
đây là một trong những lỗi kinh điển nhất của người mới học Java, sẽ giải thích kỹ và cách so sánh
đúng ở Chương 11 (String) và Chương 20 (`equals`).

## Toán tử logic

`&&` (AND — cả hai đều đúng), `||` (OR — ít nhất một đúng), `!` (NOT — phủ định):

```java
boolean coVe = true;
boolean duTuoi = false;
coVe && duTuoi   // false - can CA HAI dieu kien
coVe || duTuoi   // true  - can IT NHAT MOT dieu kien
!coVe             // false - phu dinh cua true
```

### Short-circuit evaluation — cơ chế "tắt sớm"

`&&` và `||` trong Java **không luôn đánh giá cả hai vế**:

- `&&`: nếu vế trái đã `false`, Java **không đánh giá vế phải nữa** — vì kết quả chắc chắn `false`
  rồi, không cần kiểm tra thêm.
- `||`: nếu vế trái đã `true`, Java **không đánh giá vế phải** — kết quả chắc chắn `true` rồi.

Đây không chỉ là tối ưu hiệu năng — nó thường được dùng **cố ý** để tránh lỗi runtime:

```java
int[] mang = {};
if (mang.length > 0 && mang[0] == 1) {
    // ...
}
```

Nếu `mang` rỗng, `mang.length > 0` là `false`, Java dừng lại ngay, **không bao giờ chạy tới**
`mang[0]` — nếu chạy tới, truy cập phần tử thứ 0 của mảng rỗng sẽ ném lỗi (Chương 9 sẽ học về mảng,
Chương 21 học cách xử lý những lỗi kiểu này). Đây là mẫu (pattern) rất phổ biến: "kiểm tra điều
kiện an toàn trước, rồi mới kiểm tra điều kiện phụ thuộc vào nó" — luôn đặt điều kiện an toàn ở
**vế trái** của `&&`.

## Độ ưu tiên toán tử (operator precedence)

Giống toán học phổ thông, Java có thứ tự ưu tiên: `*`, `/`, `%` được tính **trước** `+`, `-`; các
toán tử so sánh được tính trước toán tử logic; ngoặc đơn `()` luôn được tính trước tiên và có thể
dùng để ép thứ tự bạn muốn:

```java
int ketQua = 2 + 3 * 4;         // 14, khong phai 20 - * tinh truoc +
int ketQuaCoNgoac = (2 + 3) * 4; // 20 - ngoac don thay doi thu tu tinh
```

**Lời khuyên thực tế**: đừng cố nhớ hết bảng độ ưu tiên đầy đủ (có hàng chục toán tử với các mức ưu
tiên khác nhau). Khi biểu thức có từ 2 toán tử khác nhóm trở lên, **cứ thêm ngoặc đơn** cho rõ ràng
— code rõ ràng quan trọng hơn code ngắn gọn nhưng khó đọc.

## Bài tập

1. Không chạy code, dự đoán kết quả của `10 % 3`, `-10 % 3`, `10 % -3`, sau đó chạy thử để kiểm
   tra dự đoán (dấu của phép `%` trong Java theo dấu của số bị chia — số đứng trước).
2. Viết biểu thức tính trung bình cộng của 3 số nguyên `a`, `b`, `c`, đảm bảo kết quả là số thực
   chính xác (không bị cắt do chia số nguyên).
3. Giải thích: đoạn code `if (danhSach != null && danhSach.size() > 0)` dựa vào cơ chế nào trong
   chương này để không bị lỗi khi `danhSach` là `null`? (Sẽ hiểu đầy đủ khái niệm `null` ở Chương 13,
   ở đây chỉ cần suy luận dựa trên short-circuit evaluation vừa học.)

## Lỗi thường gặp

- **Chia hai số nguyên ra kết quả sai (bị cắt) dù gán cho biến `double`** — phép chia số nguyên
  xảy ra **trước** khi gán; xem lại phần "Toán tử số học" ở trên, ép ít nhất một toán hạng sang
  `double` trước khi chia.
- **Nhầm `=` (gán) với `==` (so sánh)** trong điều kiện `if` — ví dụ viết `if (x = 5)` thay vì
  `if (x == 5)`. Với kiểu `boolean`, Java **không cho phép** biên dịch lỗi này (khác với C/C++), vì
  `x = 5` trả về `int` chứ không phải `boolean` — nhưng nếu biến là `boolean`, lỗi này biên dịch
  được và rất khó phát hiện, cần cẩn thận đọc lại code.
- **Dùng `==` để so sánh `String`** — sẽ giải thích kỹ ở Chương 11, ghi nhớ trước: dùng
  `.equals()`, không dùng `==`, cho `String`.
- **Viết biểu thức phức tạp không có ngoặc, đọc lại không nhớ thứ tự tính** — thêm ngoặc đơn, đừng
  ngại code dài hơn vài ký tự.
