# Chương 22 — Generics

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu vấn đề generics giải quyết: dùng chung một class/phương thức cho nhiều kiểu dữ liệu mà vẫn
  an toàn về kiểu (type-safe).
- Viết được generic class và generic method.
- Dùng bounded type (`<T extends ...>`) khi cần giới hạn `T` phải là một loại cụ thể.
- Đọc hiểu wildcard (`? extends`, `? super`) khi gặp trong code/thư viện chuẩn — nền tảng quan
  trọng trước khi học Collection Framework ở Phần 5.

Code mẫu đầy đủ: [`code/ch22-generics/`](../../code/ch22-generics/).

> Bạn đã dùng generics từ Chương 20 (`Comparable<SanPham>`) mà chưa hiểu cơ chế — chương này giải
> thích đầy đủ cú pháp `< >` đó nghĩa là gì.

## Vấn đề generics giải quyết

Giả sử bạn cần một "cái hộp" chứa được một giá trị bất kỳ, không biết trước kiểu gì. Không có
generics, một cách làm (tồi) là dùng `Object`:

```java
public class HopChuaCu {
    private Object noiDung;

    public Object layNoiDung() {
        return noiDung;
    }
}
```

Vấn đề: `layNoiDung()` trả về `Object`, buộc người dùng phải **tự ép kiểu** mỗi lần lấy ra:

```java
HopChuaCu hop = new HopChuaCu();
hop.datNoiDung("Nguyen Van A");
String ten = (String) hop.layNoiDung(); // phai TU EP KIEU, de sai
```

Nếu ai đó vô tình đặt sai kiểu vào hộp (`hop.datNoiDung(123);`), trình biên dịch **không phát hiện
được gì cả** — lỗi chỉ lộ ra lúc **chạy**, dưới dạng `ClassCastException` (Chương 17), tại đúng chỗ
ép kiểu, có thể cách rất xa chỗ đã đặt sai giá trị vào — rất khó truy vết.

## Generic class — `<T>`

```java
public class HopChua<T> {
    private T noiDung;

    public HopChua(T noiDung) {
        this.noiDung = noiDung;
    }

    public T layNoiDung() {
        return noiDung;
    }
}
```

`<T>` ngay sau tên class khai báo **tham số kiểu** (type parameter) — `T` chỉ là một cái tên quy
ước (viết tắt của "Type"; các tên quy ước khác hay gặp: `E` cho Element, `K`/`V` cho Key/Value —
sẽ gặp lại ở Chương 26 khi học `Map`), sẽ được **thay thế bằng một kiểu cụ thể** khi bạn dùng class
này:

```java
HopChua<String> hopTen = new HopChua<>("Nguyen Van A");
HopChua<Integer> hopTuoi = new HopChua<>(25);

String ten = hopTen.layNoiDung(); // KHONG can ep kieu - trinh bien dich TU BIET la String
```

Cú pháp `new HopChua<>(...)` — cặp `<>` rỗng (gọi là "diamond operator", có từ Java 7) — cho phép
trình biên dịch **tự suy luận** kiểu generic từ ngữ cảnh (ở đây: từ kiểu khai báo biến `HopChua<String>`),
không cần viết lặp lại `new HopChua<String>(...)`.

**Lợi ích cốt lõi**: nếu bạn cố gán sai kiểu (`hopTuoi.datNoiDung("chuoi");` — trong khi `hopTuoi`
là `HopChua<Integer>`), trình biên dịch **báo lỗi ngay lúc biên dịch**, không đợi tới lúc chạy mới
phát hiện. Đây chính là giá trị lớn nhất của generics: chuyển lỗi từ "phát hiện lúc chạy" (tệ) sang
"phát hiện lúc biên dịch" (tốt hơn nhiều — bạn thấy lỗi ngay khi viết code, không phải đợi người
dùng gặp phải khi chương trình đã chạy).

## Generic method

```java
public static <T> void inMoiPhanTu(T[] mang) {
    for (T phanTu : mang) {
        System.out.println("- " + phanTu);
    }
}
```

Khai báo `<T>` **ngay trước kiểu trả về** — khác với generic class, tham số kiểu này chỉ có hiệu
lực **trong phạm vi phương thức**, không cần class chứa nó phải là generic class. Gọi được với bất
kỳ mảng kiểu nào:

```java
Utils.inMoiPhanTu(new String[]{"An", "Binh"});
Utils.inMoiPhanTu(new Integer[]{1, 2, 3});
```

## Bounded type — giới hạn `T` phải là một loại cụ thể

```java
public class HopSo<T extends Number> {
    private T giaTri;

    public double layGiaTriThuc() {
        return giaTri.doubleValue(); // hop le vi MOI Number deu co doubleValue()
    }
}
```

`<T extends Number>` giới hạn: `T` **chỉ được phép** là `Number` hoặc lớp con của `Number`
(`Integer`, `Double`, `Long`...) — cố dùng `HopSo<String>` sẽ là lỗi biên dịch, vì `String` không
kế thừa `Number`. Đổi lại, **bên trong** class, bạn được phép gọi các phương thức của `Number`
(như `doubleValue()`) trên biến kiểu `T`, dù không biết trước `T` cụ thể sẽ là `Integer` hay
`Double` — trình biên dịch biết chắc `T` **ít nhất** cũng là một `Number`, nên các phương thức của
`Number` chắc chắn tồn tại.

(Lưu ý từ khoá dùng luôn là `extends`, kể cả khi giới hạn theo interface — không có từ khoá
`implements` riêng trong ngữ cảnh bounded type.)

## Wildcard — `? extends`

```java
public static void inGiaTriSo(HopChua<? extends Number> hop) {
    Number giaTri = hop.layNoiDung();
    System.out.println("Gia tri so: " + giaTri);
}
```

Khác với bounded type (`<T extends Number>` — dùng khi khai báo **cả class**), wildcard `?` dùng
khi bạn chỉ cần khai báo kiểu cho **một tham số phương thức cụ thể**, chấp nhận "hộp chứa của **bất
kỳ** kiểu con nào của `Number`" — gọi được với cả `HopChua<Integer>` lẫn `HopChua<Double>`, mà
**không cần** viết riêng hai phiên bản overload cho từng kiểu.

`? extends Number` chỉ cho phép **đọc** (lấy giá trị ra, xem như `Number`), **không cho phép ghi**
(gọi `hop.datNoiDung(...)` qua tham chiếu kiểu `HopChua<? extends Number>` là lỗi biên dịch) — lý do
kỹ thuật: trình biên dịch không biết chính xác `T` là `Integer` hay `Double` hay kiểu con nào khác
của `Number`, nên không thể đảm bảo giá trị bạn định ghi vào có đúng kiểu hay không, và **chặn hẳn**
khả năng ghi để tránh sai kiểu tiềm ẩn.

### Wildcard — `? super` (chỉ cần biết tên, dùng nhiều ở Chương 27-31)

Ngược lại với `? extends`, `? super T` nghĩa là "kiểu `T` hoặc **bất kỳ lớp cha** nào của `T`",
thường dùng khi bạn cần **ghi** giá trị kiểu `T` vào, không cần đọc ra dùng ngay. Nguyên tắc ghi nhớ
kinh điển gọi là **PECS** (Producer Extends, Consumer Super): nếu tham số chỉ **cung cấp** dữ liệu
cho bạn đọc, dùng `extends`; nếu tham số chỉ **nhận** dữ liệu bạn đưa vào, dùng `super`. Sách sẽ gặp
lại wildcard cụ thể hơn khi học Stream API (Chương 31).

## Bài tập

1. Viết generic class `Cap<A, B>` (Pair) chứa hai giá trị kiểu khác nhau (`A` và `B`), có getter
   cho cả hai, và `toString()` in ra dạng `(giaTriA, giaTriB)`.
2. Viết generic method `<T> T layPhanTuCuoi(T[] mang)` trả về phần tử cuối cùng của mảng bất kỳ
   kiểu nào.
3. Thử tạo `HopSo<String>` (vi phạm bounded type), đọc lỗi biên dịch, xác nhận đúng như phần
   "Bounded type" giải thích.

## Lỗi thường gặp

- **Dùng generics với kiểu nguyên thủy** (`HopChua<int>`) — không hợp lệ, generics **chỉ hoạt động
  với kiểu tham chiếu**. Phải dùng **kiểu bọc (wrapper type)**: `HopChua<Integer>` thay vì
  `HopChua<int>` (Java tự động chuyển đổi qua lại giữa `int` và `Integer` — cơ chế gọi là
  "autoboxing/unboxing" — khi cần, không cần bạn tự ép kiểu tường minh).
- **Quên diamond operator, viết `new HopChua<String>()` dài dòng thay vì `new HopChua<>()`** — không
  sai, chỉ là thừa; `<>` để trình biên dịch tự suy luận là đủ trong hầu hết trường hợp.
- **Cố gọi `datNoiDung(...)` qua tham chiếu `? extends Number`** — lỗi biên dịch cố ý (xem phần
  wildcard `? extends` ở trên), không phải bug — đây là cách generics đảm bảo an toàn kiểu.
- **Nhầm generic method với generic class** — generic method khai báo `<T>` trước kiểu trả về, tồn
  tại độc lập, không đòi hỏi class chứa nó phải là generic class như `HopChua<T>`. Đọc kỹ chữ ký
  phương thức để phân biệt.
