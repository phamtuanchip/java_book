# Chương 30 — Lambda expression & functional interface

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu **functional interface** là gì, viết được lambda expression thay thế anonymous class.
- Dùng thành thạo 5 functional interface có sẵn trong `java.util.function`.
- Dùng **method reference** — cách viết gọn hơn nữa cho một số trường hợp lambda phổ biến.
- Hiểu quy tắc "effectively final" khi lambda dùng biến từ phạm vi bên ngoài.

Code mẫu đầy đủ: [`code/ch30-lambda/`](../../code/ch30-lambda/).

## Từ anonymous class tới lambda

Nhắc lại Chương 19, tạo object implements interface "ngay tại chỗ":

```java
PhepToan cong = new PhepToan() {
    @Override
    public int apDung(int a, int b) {
        return a + b;
    }
};
```

Khi interface đó **chỉ có đúng một phương thức abstract** (gọi là **functional interface**), phần
lớn đoạn code trên (`new PhepToan() { @Override public int apDung(...) { ... } }`) là **thừa thãi
về mặt hình thức** — trình biên dịch thừa sức tự suy luận ra bạn đang implement phương thức nào.
**Lambda expression** loại bỏ hoàn toàn phần thừa đó:

```java
PhepToan cong = (a, b) -> a + b;
```

Đọc là: "nhận vào `a, b`, trả về `a + b`". Cú pháp: `(tham số) -> biểu thức_hoặc_khối_lệnh`.

```java
@FunctionalInterface
interface PhepToan {
    int apDung(int a, int b);
}
```

`@FunctionalInterface` là annotation **tuỳ chọn nhưng nên dùng** — báo cho trình biên dịch biết
"interface này được thiết kế để dùng với lambda"; nếu ai đó vô tình thêm phương thức abstract thứ
hai vào interface (phá vỡ điều kiện "chỉ đúng một phương thức abstract"), trình biên dịch **báo lỗi
ngay** thay vì để lỗi âm thầm lan rộng ra mọi chỗ đang dùng lambda với interface đó.

### Lambda nhiều dòng

```java
PhepToan congCoLog = (a, b) -> {
    System.out.println("Dang cong " + a + " va " + b);
    return a + b;
};
```

Khi thân lambda có **nhiều hơn một câu lệnh**, cần dấu ngoặc nhọn `{ }` và `return` tường minh
(giống thân một phương thức bình thường) — khác với lambda một dòng (`(a, b) -> a + b`), nơi giá
trị của biểu thức **tự động** là giá trị trả về, không cần viết `return`.

## Quy tắc "effectively final"

```java
int heSo = 100;
PhepToan nhanHeSo = (a, b) -> (a + b) * heSo;
```

Lambda **dùng được** biến cục bộ từ phạm vi bao quanh nó (`heSo`), nhưng với điều kiện: biến đó
phải là **effectively final** — nghĩa là, dù không bắt buộc ghi từ khoá `final`, biến đó **không
được gán lại giá trị** ở bất kỳ đâu sau khi khai bao, trong toàn bộ phạm vi chứa nó. Nếu bạn gán lại
`heSo = 200;` ở bất kỳ đâu sau đó, trình biên dịch báo lỗi ngay tại **lambda** đang dùng `heSo`,
dù dòng gán lại nằm ở chỗ khác. Lý do kỹ thuật: lambda có thể được lưu trữ và gọi **muộn hơn nhiều**
so với chỗ nó được tạo ra (thậm chí sau khi phương thức chứa nó đã kết thúc chạy) — Java đảm bảo an
toàn bằng cách **chỉ cho lambda "chụp ảnh" giá trị tại thời điểm tạo**, không cho phép giá trị đó
thay đổi sau này để tránh mơ hồ.

## `java.util.function` — các functional interface có sẵn

Phần lớn trường hợp dùng lambda trong thực tế **không cần tự định nghĩa interface riêng** như
`PhepToan` — thư viện chuẩn đã cung cấp sẵn các functional interface tổng quát trong package
`java.util.function`:

| Interface | Chữ ký | Ý nghĩa |
|---|---|---|
| `Predicate<T>` | `boolean test(T t)` | Kiểm tra điều kiện, trả về đúng/sai |
| `Function<T, R>` | `R apply(T t)` | Nhận vào kiểu `T`, trả về kiểu `R` (khác kiểu) |
| `Consumer<T>` | `void accept(T t)` | Nhận vào, thực hiện hành động, không trả về gì |
| `Supplier<T>` | `T get()` | Không nhận gì, tạo/trả về một giá trị |
| `BiFunction<T, U, R>` | `R apply(T t, U u)` | Giống `Function` nhưng nhận **hai** tham số |

```java
Predicate<Integer> laSoChan = n -> n % 2 == 0;
Function<String, Integer> doDaiChuoi = s -> s.length();
Consumer<String> inHoaToanBo = s -> System.out.println(s.toUpperCase());
Supplier<String> taoChaoMung = () -> "Chao mung!";
BiFunction<Integer, Integer, Integer> congHaiSo = (a, b) -> a + b;
```

Bạn đã **thực chất dùng qua** một functional interface có sẵn từ Chương 27:
`Comparator<T>` — có đúng một phương thức abstract (`compare`), nên `Comparator.comparing(...)`
nhận được lambda/method reference thay vì phải viết anonymous class dài dòng.

## Method reference — gọn hơn nữa

Khi thân lambda chỉ đơn giản là **gọi lại một phương thức đã tồn tại sẵn**, có thể viết gọn hơn
bằng method reference, cú pháp `Kiểu::tenPhuongThuc`:

```java
// Loai 1: tham chieu phuong thuc STATIC
BiFunction<Integer, Integer, Integer> cong = Integer::sum;      // thay cho (a, b) -> Integer.sum(a, b)

// Loai 2: tham chieu phuong thuc INSTANCE cua MOT object cu the
String ten = "Xin chao";
Supplier<String> layHoa = ten::toUpperCase;                     // thay cho () -> ten.toUpperCase()

// Loai 3: tham chieu phuong thuc INSTANCE, ap dung TREN THAM SO duoc truyen vao
Function<String, Integer> layDoDai = String::length;            // thay cho s -> s.length()

// Loai 4: tham chieu CONSTRUCTOR
Supplier<List<Integer>> taoMoi = ArrayList::new;                 // thay cho () -> new ArrayList<>()
```

Method reference **không phải cú pháp mới về bản chất** — nó chỉ là cách viết tắt cho một dạng
lambda rất phổ biến (lambda mà thân của nó chỉ gọi đúng một phương thức có sẵn, không thêm logic
gì khác). Dùng khi nó làm code **rõ ràng hơn**; nếu lambda cần thêm logic (dù chỉ một chút), viết
lambda tường minh vẫn thường dễ đọc hơn ép dùng method reference.

## Bài tập

1. Viết lại `PhepToan` để có thêm phiên bản `chia` bằng lambda, xử lý (in cảnh báo, trả về `0`)
   khi chia cho 0 thay vì để `ArithmeticException` (Chương 21) xảy ra.
2. Dùng `Predicate<String>` kiểm tra một chuỗi có phải palindrome hay không (tận dụng lại logic đã
   viết ở bài tập Chương 11), áp dụng nó để lọc một `List<String>` (dùng vòng lặp với `test(...)`,
   chưa cần Stream — sẽ học cách lọc gọn hơn nhiều ở Chương 31).
3. Viết 3 method reference khác nhau cho cùng một `Function<String, String>`: một tham chiếu tới
   `String::toUpperCase`, một tới `String::trim`, thử áp dụng lần lượt và so sánh kết quả.

## Lỗi thường gặp

- **Cố dùng lambda cho interface có từ 2 phương thức abstract trở lên** — lỗi biên dịch, vì trình
  biên dịch không biết lambda tương ứng với phương thức nào. Chỉ functional interface (đúng một
  phương thức abstract) mới dùng được lambda.
- **Gán lại giá trị biến ngoài sau khi đã dùng trong lambda** — vi phạm "effectively final", lỗi
  biên dịch tại chính lambda. Nếu cần một giá trị "thay đổi được" mà lambda vẫn thấy, cần dùng cấu
  trúc khác (ví dụ một mảng một phần tử, hoặc field của object) — kỹ thuật nâng cao, ít cần tới ở
  giai đoạn học này.
- **Quên `return` trong lambda nhiều dòng** — khác với lambda một dòng (tự động trả về giá trị biểu
  thức), lambda dùng `{ }` **bắt buộc** `return` tường minh nếu functional interface yêu cầu trả về
  giá trị.
- **Method reference không khớp chữ ký functional interface mong đợi** — ví dụ cố dùng
  `String::length` (nhận 0 tham số ngoài chính `this`, trả `int`) cho một `BiFunction` (mong đợi
  nhận 2 tham số) — lỗi biên dịch báo rõ không khớp kiểu, đọc kỹ thông báo lỗi để đối chiếu đúng
  loại method reference (1 trong 4 loại đã học) phù hợp với ngữ cảnh.
