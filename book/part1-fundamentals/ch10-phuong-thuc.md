# Chương 10 — Phương thức (method) tĩnh

## Mục tiêu học

Sau chương này, bạn sẽ:

- Viết được phương thức tĩnh (`static`) có/không tham số, có/không giá trị trả về.
- Dùng overloading (nạp chồng) đúng cách — nhiều phương thức cùng tên, khác tham số.
- Hiểu phạm vi biến (scope) — biến khai báo trong một phương thức không "nhìn thấy được" từ
  phương thức khác.
- Hiểu chính xác Java truyền tham số **theo giá trị** (pass-by-value) — kể cả với kiểu tham chiếu
  như mảng, và vì sao điều này hay bị hiểu nhầm.

Code mẫu đầy đủ: [`code/ch10-phuong-thuc/`](../../code/ch10-phuong-thuc/).

## Vì sao cần phương thức?

Bạn đã viết `main` chứa toàn bộ code từ Chương 3. Khi chương trình lớn dần, gộp mọi thứ vào một
`main` khiến code dài, lặp lại, khó đọc. **Phương thức** (method) là cách gói một đoạn logic có
tên riêng, có thể **gọi lại nhiều lần** từ nhiều chỗ khác nhau mà không cần chép lại code.

```java
static int add(int a, int b) {
    return a + b;
}
```

Cấu trúc: `<phạm vi> static <kiểu trả về> <tên>(<tham số>) { ... }`.

- `static` — phương thức thuộc về **class**, gọi được trực tiếp qua tên class
  (`MethodsDemo.add(3, 5)`) mà không cần tạo object (khái niệm object sẽ học ở Chương 13 — vì sao
  chương này chỉ dạy phương thức `static`: tránh nhắc tới object trước khi bạn hiểu nó là gì. Từ
  Chương 13 trở đi, phần lớn phương thức bạn viết **sẽ không còn** `static`).
- `int` (trước tên phương thức) — **kiểu dữ liệu trả về**. Nếu phương thức không trả về gì, dùng
  `void` (đã thấy ở `main` từ Chương 3).
- `(int a, int b)` — danh sách **tham số** (parameter), mỗi tham số cần khai báo kiểu dữ liệu.
- `return a + b;` — trả giá trị về nơi gọi. Phương thức có kiểu trả về khác `void` **bắt buộc**
  phải có `return` ở mọi nhánh có thể chạy tới cuối phương thức, nếu không trình biên dịch báo lỗi.

Gọi phương thức:

```java
int tong = add(3, 5); // tong = 8
```

## Tham số (parameter) và đối số (argument)

Phân biệt hai từ hay dùng lẫn: **tham số** (parameter) là biến khai báo trong định nghĩa phương
thức (`a`, `b` ở trên); **đối số** (argument) là giá trị cụ thể truyền vào lúc gọi (`3`, `5`).

## Overloading (nạp chồng phương thức)

```java
static int add(int a, int b) { return a + b; }
static int add(int a, int b, int c) { return a + b + c; }
static double add(double a, double b) { return a + b; }
```

**Overloading** là việc định nghĩa nhiều phương thức **cùng tên** nhưng khác nhau về **số lượng
tham số** hoặc **kiểu tham số** (chỉ khác kiểu trả về thôi thì **không đủ** — trình biên dịch không
phân biệt được, sẽ báo lỗi trùng khai báo). Khi gọi `add(...)`, trình biên dịch tự chọn đúng phiên
bản dựa trên số lượng và kiểu đối số bạn truyền vào — việc này xảy ra **lúc biên dịch**, không phải
lúc chạy.

Overloading giúp code gọi tự nhiên hơn (`add(3, 5)` và `add(3, 5, 7)` đều đọc hợp lý), thay vì phải
đặt tên khác nhau như `addTwo`, `addThree`. Bạn sẽ thấy overloading dùng rất nhiều trong thư viện
chuẩn Java, ví dụ `println(int)`, `println(String)`, `println(double)`... đều là các bản overload
của cùng một phương thức `println`.

## Phạm vi biến (scope)

```java
static void scopeDemo() {
    int x = 999;
    System.out.println(x);
}

public static void main(String[] args) {
    int x = 100;
    scopeDemo();
    System.out.println(x); // van la 100, khong bi anh huong boi scopeDemo
}
```

Biến khai báo bên trong một phương thức **chỉ tồn tại và chỉ "nhìn thấy được"** trong phạm vi
phương thức đó (cụ thể hơn: trong khối `{ }` nó được khai báo). Hai biến `x` ở trên là **hai biến
hoàn toàn độc lập**, dù trùng tên — trùng tên biến giữa các phương thức khác nhau không gây xung
đột, vì phạm vi của chúng tách biệt nhau. Khi phương thức kết thúc chạy, mọi biến cục bộ khai báo
bên trong nó bị giải phóng khỏi bộ nhớ.

## Truyền tham số: pass-by-value

Đây là khái niệm hay gây hiểu nhầm nhất chương này. **Java luôn truyền tham số theo giá trị
(pass-by-value)** — không có ngoại lệ. Nhưng hệ quả khác nhau tuỳ kiểu dữ liệu:

### Với kiểu nguyên thủy — sửa tham số không ảnh hưởng biến gốc

```java
static void tangGap10(int n) {
    n = n * 10;
}

int soGoc = 10;
tangGap10(soGoc);
System.out.println(soGoc); // van la 10, KHONG doi
```

Khi gọi `tangGap10(soGoc)`, Java **sao chép giá trị** của `soGoc` vào tham số cục bộ `n` bên trong
phương thức. `n` và `soGoc` là hai vùng nhớ độc lập; sửa `n` không ảnh hưởng gì tới `soGoc`.

### Với mảng (kiểu tham chiếu) — sửa nội dung bên trong ảnh hưởng ra ngoài

```java
static void nhanDoiPhanTu(int[] mang) {
    for (int i = 0; i < mang.length; i++) {
        mang[i] = mang[i] * 2;
    }
}

int[] arr = {1, 2, 3};
nhanDoiPhanTu(arr);
System.out.println(Arrays.toString(arr)); // [2, 4, 6] - CO doi!
```

Điều này **không mâu thuẫn** với "Java luôn pass-by-value" — thứ được sao chép ở đây là **giá trị
của tham chiếu** (địa chỉ trỏ tới mảng trong bộ nhớ), không phải nội dung mảng. Tham số `mang` bên
trong phương thức và biến `arr` bên ngoài là hai biến độc lập, nhưng **cả hai cùng trỏ tới đúng một
mảng** — sửa phần tử qua biến nào cũng thấy thay đổi qua biến kia, vì chỉ có một mảng duy nhất tồn
tại trong bộ nhớ. Khái niệm này sẽ trở nên rõ ràng và tự nhiên hơn nhiều khi học object ở Chương 13
— mảng thực chất hoạt động giống hệt object về mặt tham chiếu.

## Bài tập

1. Viết phương thức `static boolean isPrime(int n)` kiểm tra số nguyên tố, gọi thử với vài giá trị
   trong `main`.
2. Viết 3 phiên bản overload của phương thức `max`: `max(int, int)`, `max(int, int, int)`, và
   `max(double, double)`.
3. Dự đoán (không chạy thử trước) kết quả của đoạn code sau, sau đó chạy để kiểm tra:
   ```java
   static void doiTen(String ten) {
       ten = "Da doi";
   }
   String ten = "Ten goc";
   doiTen(ten);
   System.out.println(ten);
   ```
   Gợi ý: `String` là kiểu tham chiếu nhưng bất biến (immutable) — kết quả sẽ giải thích trọn vẹn ở
   Chương 11, thử tự suy luận trước dựa trên phần "pass-by-value" vừa học.

## Lỗi thường gặp

- **`missing return statement`** — phương thức khai báo kiểu trả về khác `void` nhưng có nhánh
  logic (ví dụ trong `if`) không có `return`. Đảm bảo **mọi đường chạy có thể tới cuối phương thức**
  đều có `return`.
- **Overload chỉ khác kiểu trả về, giữ nguyên tham số** — báo lỗi biên dịch trùng khai báo. Kiểu
  trả về không được tính khi trình biên dịch phân biệt các bản overload.
- **Tưởng sửa tham số nguyên thủy bên trong phương thức sẽ ảnh hưởng biến gốc bên ngoài** — xem lại
  phần pass-by-value ở trên, đây là hiểu lầm phổ biến nhất chương này.
- **Trùng tên biến giữa các phương thức rồi lo lắng bị xung đột** — không xảy ra, phạm vi biến của
  mỗi phương thức độc lập hoàn toàn (trừ biến `static` cấp class — sẽ học ở Chương 13).
