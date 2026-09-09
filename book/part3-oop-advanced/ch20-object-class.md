# Chương 20 — Lớp Object: equals/hashCode/toString

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu mọi class trong Java đều ngầm kế thừa từ `Object`, và biết 3 phương thức quan trọng nhất nó
  cung cấp: `toString()`, `equals()`, `hashCode()`.
- Override `toString()` để in object có ý nghĩa.
- Override `equals()` và `hashCode()` đúng quy tắc để so sánh object theo **nội dung**.
- Phân biệt `Comparable` (thứ tự tự nhiên, định nghĩa trong chính class) và `Comparator` (thứ tự
  tuỳ chọn, định nghĩa riêng bên ngoài).

Code mẫu đầy đủ: [`code/ch20-object-class/`](../../code/ch20-object-class/).

> Chương này dùng cú pháp generics (`Comparable<SanPham>`) trước khi Chương 22 dạy đầy đủ về
> generics — ở đây chỉ cần hiểu `<SanPham>` nghĩa là "phiên bản dành riêng cho kiểu `SanPham`",
> chưa cần hiểu cơ chế bên trong.

## Mọi class đều kế thừa từ `Object`

Bạn chưa từng viết `class SanPham extends Object`, nhưng thực tế **mọi class trong Java**, nếu
không `extends` class nào khác, **tự động kế thừa từ `class Object`** — lớp cha chung của toàn bộ
hệ thống kiểu trong Java. `Object` cung cấp sẵn một số phương thức mà mọi object nào cũng có, quan
trọng nhất: `toString()`, `equals(Object)`, `hashCode()`.

## `toString()` — biểu diễn object dưới dạng chuỗi

Nhắc lại từ Chương 9: in một mảng trực tiếp cho ra chuỗi vô nghĩa như `[I@1b6d3586`. Chuyện tương
tự xảy ra với object thường:

```java
SanPham sp = new SanPham("But bi", 5000);
System.out.println(sp); // mac dinh: SanPham@1b6d3586 - vo nghia
```

Đó là hành vi **mặc định** của `toString()` kế thừa từ `Object` — nó in ra tên class cộng một mã
định danh bộ nhớ, không nói lên gì về nội dung object. **Override lại** để có kết quả hữu ích:

```java
@Override
public String toString() {
    return "SanPham{ten='" + ten + "', gia=" + gia + "}";
}
```

Sau khi override, `System.out.println(sp)` và cả nối chuỗi `"" + sp` **tự động gọi**
`sp.toString()` — bạn không bao giờ cần gọi `.toString()` tường minh trong những trường hợp này,
Java tự làm điều đó.

## `equals()` — so sánh nội dung, đúng cách

Nhắc lại từ Chương 11 và 13: `==` với kiểu tham chiếu so sánh **có phải cùng một object trong bộ
nhớ hay không**. Hành vi mặc định của `equals()` kế thừa từ `Object` cũng làm **đúng như vậy**
(tương đương `==`) — nếu bạn không override, `equals()` không giúp gì hơn `==` cả. Muốn so sánh
theo **nội dung**, phải tự override:

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SanPham sp = (SanPham) o;
    return Double.compare(sp.gia, gia) == 0 && Objects.equals(ten, sp.ten);
}
```

Khuôn mẫu chuẩn cần nhớ:

1. **Kiểm tra tham chiếu giống hệt** (`this == o`) — nếu đúng, chắc chắn bằng nhau, trả `true` ngay
   không cần so sánh gì thêm (tối ưu, cũng đúng logic).
2. **Kiểm tra `null` và kiểu** (`o == null || getClass() != o.getClass()`) — nếu `o` là `null` hoặc
   thuộc class khác hoàn toàn, chắc chắn không bằng nhau.
3. **Ép kiểu** (`(SanPham) o`) — an toàn vì bước 2 đã đảm bảo `o` đúng là `SanPham`.
4. **So sánh từng field có ý nghĩa** — dùng `Objects.equals(a, b)` cho field kiểu tham chiếu (tự xử
   lý an toàn cả trường hợp `null`, tương đương `a == null ? b == null : a.equals(b)` nhưng ngắn
   gọn hơn và không có nguy cơ `NullPointerException`), dùng `Double.compare`/`==` trực tiếp cho
   field kiểu nguyên thủy.

## `hashCode()` — luôn override cùng `equals()`

```java
@Override
public int hashCode() {
    return Objects.hash(ten, gia);
}
```

**Quy tắc bắt buộc, không có ngoại lệ**: nếu `a.equals(b)` trả về `true`, thì **bắt buộc**
`a.hashCode() == b.hashCode()` cũng phải đúng. Vì sao quy tắc này quan trọng: các cấu trúc dữ liệu
như `HashMap`, `HashSet` (sẽ học ở Chương 25-26) dùng `hashCode()` để **tìm nhanh** object trong tập
hợp, rồi mới dùng `equals()` để xác nhận chính xác — nếu hai object "bằng nhau" theo `equals()`
nhưng có `hashCode()` khác nhau, các cấu trúc dữ liệu đó sẽ **hoạt động sai** (không tìm thấy object
lẽ ra phải tìm thấy), dù code biên dịch và chạy không báo lỗi gì. Đây là lỗi rất khó phát hiện nếu
không biết trước quy tắc này.

**Ghi nhớ**: `equals()` và `hashCode()` **luôn đi thành cặp** — override một trong hai mà không
override cái kia gần như luôn là lỗi.

## `Comparable` — thứ tự tự nhiên, định nghĩa trong chính class

```java
public class SanPham implements Comparable<SanPham> {
    @Override
    public int compareTo(SanPham kia) {
        return Double.compare(this.gia, kia.gia);
    }
}
```

`Comparable<T>` là interface chuẩn của Java, chỉ có một phương thức `compareTo(T kia)` — trả về số
**âm** nếu object hiện tại "nhỏ hơn" `kia`, số **dương** nếu "lớn hơn", `0` nếu "bằng nhau" (theo
tiêu chí bạn tự định nghĩa). Khi class implements `Comparable`, các phương thức thư viện chuẩn như
`Arrays.sort(mang)` **tự động dùng** `compareTo()` này để sắp xếp mà không cần bạn chỉ định gì
thêm — đây gọi là **thứ tự tự nhiên (natural ordering)** của kiểu đó (giống cách `Arrays.sort` trên
`int[]` tự hiểu sắp xếp tăng dần theo giá trị số).

## `Comparator` — thứ tự tuỳ chọn, định nghĩa riêng bên ngoài

Vấn đề: `SanPham` chỉ có **một** thứ tự tự nhiên (theo giá). Nếu đôi khi bạn cần sắp theo **tên**
thay vì giá, không thể sửa `compareTo()` (chỉ được định nghĩa một lần duy nhất trong class). Giải
pháp: `Comparator<T>` — một interface **riêng biệt**, định nghĩa thứ tự **bên ngoài** class, có thể
tạo nhiều Comparator khác nhau cho cùng một kiểu:

```java
Comparator<SanPham> theoTen = new Comparator<SanPham>() {
    @Override
    public int compare(SanPham sp1, SanPham sp2) {
        return sp1.getTen().compareTo(sp2.getTen());
    }
};
Arrays.sort(danhSach, theoTen); // truyen THEM Comparator, ghi de thu tu tu nhien
```

`String` cũng implements `Comparable<String>` sẵn (thứ tự tự nhiên: theo bảng chữ cái/mã Unicode),
đó là lý do `sp1.getTen().compareTo(sp2.getTen())` gọi được trực tiếp.

**Tóm tắt khác biệt**: `Comparable` — class **tự định nghĩa** đúng **một** thứ tự tự nhiên của
chính nó (implements trong class đó). `Comparator` — thứ tự **tuỳ chọn**, định nghĩa **bên ngoài**,
có thể có **nhiều Comparator khác nhau** cho cùng một kiểu, không cần sửa class gốc. Chương 27 sẽ
học sâu hơn về `Comparator` (kể cả cách viết ngắn gọn hơn nhiều bằng lambda từ Chương 30, thay vì
anonymous class dài dòng như ở đây).

## Bài tập

1. Thêm `toString()`, `equals()`, `hashCode()` cho class `SanPham`/`SinhVien` bạn đã viết ở các
   chương trước (Chương 13/14), theo đúng khuôn mẫu trong chương này.
2. Cho class đó implements `Comparable`, định nghĩa một thứ tự tự nhiên hợp lý.
3. Viết thêm ít nhất một `Comparator` khác cho cùng class đó, sắp xếp theo tiêu chí khác thứ tự tự
   nhiên.
4. Giải thích bằng lời: vì sao nếu chỉ override `equals()` mà quên override `hashCode()`, một
   `HashSet` (sẽ học ở Chương 25) có thể chứa **hai object trùng lặp** dù `equals()` giữa chúng trả
   về `true`? (Chưa cần code, chỉ cần suy luận dựa trên phần "hashCode()" vừa học — sẽ kiểm chứng
   lại bằng code thật ở Chương 25.)

## Lỗi thường gặp

- **Override `equals()` mà quên override `hashCode()`** (hoặc ngược lại) — vi phạm quy tắc bắt
  buộc, gây lỗi khó phát hiện khi dùng với `HashMap`/`HashSet`. IDE hiện đại (như IntelliJ) thường
  cảnh báo ngay khi bạn chỉ override một trong hai.
- **So sánh `o == null || getClass() != o.getClass()` bị bỏ sót** trong `equals()` — dẫn tới
  `NullPointerException` hoặc `ClassCastException` khi so sánh với `null` hoặc object khác kiểu.
- **Dùng `==` thay vì `Double.compare`/`.equals()` khi so sánh field kiểu `double`/`float` trong
  `equals()`** — số thực có các trường hợp đặc biệt (như `NaN`) khiến `==` cho kết quả không trực
  quan; `Double.compare` xử lý đúng các trường hợp đó.
- **Quên `@Override` khi override `equals(Object o)`, vô tình viết `equals(SanPham o)`** — đây
  không còn là override `Object.equals`, mà là một phương thức **overload** hoàn toàn khác (nhắc
  lại phân biệt overriding/overloading ở Chương 16) — các thư viện chuẩn gọi `equals(Object)` sẽ
  không bao giờ dùng tới phiên bản overload sai này. Luôn dùng `@Override` để trình biên dịch bắt
  lỗi này ngay.
