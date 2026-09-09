# Chương 41 — Unit test với JUnit 5

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu unit test là gì, vì sao viết test quan trọng không kém viết code chính.
- Viết được test bằng JUnit 5: `@Test`, `assertEquals`, `assertThrows`.
- Dùng `@BeforeEach` để chuẩn bị trạng thái sạch cho mỗi test.
- Hiểu tư duy "thiết kế dễ test" — tách logic khỏi I/O.

Code mẫu đầy đủ: [`code/ch41-junit/`](../../code/ch41-junit/).

## Unit test là gì, vì sao cần?

Từ đầu sách, bạn kiểm tra code "đúng" bằng cách chạy chương trình và **tự mắt** xem kết quả in ra
có đúng không. Cách này có vấn đề rõ ràng khi chương trình lớn dần: mỗi lần sửa code, bạn phải **tự
kiểm tra lại thủ công** mọi tính năng để chắc chắn không làm hỏng gì — tốn thời gian, dễ bỏ sót, và
không lặp lại được một cách đáng tin cậy.

**Unit test** là code **tự động kiểm tra** một đơn vị nhỏ của chương trình (thường là một phương
thức) có hoạt động đúng như mong đợi hay không — viết một lần, chạy lại **bất kỳ lúc nào** (đặc
biệt sau mỗi lần sửa code) chỉ bằng một lệnh, không cần tự tay kiểm tra lại bằng mắt.

## Viết test đầu tiên với JUnit 5

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
    @Test
    void testCongHaiSoDuong() {
        Calculator calculator = new Calculator();
        double ketQua = calculator.cong(2, 3);
        assertEquals(5, ketQua);
    }
}
```

- **Quy ước đặt tên**: class test tên `<TenLopCanTest>Test` (`CalculatorTest` cho `Calculator`) —
  không bắt buộc về mặt kỹ thuật, nhưng là quy ước gần như phổ quát trong cộng đồng Java.
- `@Test` — annotation đánh dấu một phương thức là **một test case**, JUnit tự nhận diện và chạy
  nó khi bạn chạy bộ test.
- `assertEquals(mongDoi, thucTe)` — khẳng định hai giá trị **phải bằng nhau**; nếu không, test
  **thất bại (fail)**, JUnit báo rõ giá trị mong đợi và giá trị thực tế khác nhau ra sao.

## `@BeforeEach` — chuẩn bị trạng thái sạch cho mỗi test

```java
private Calculator calculator;

@BeforeEach
void setUp() {
    calculator = new Calculator();
}
```

Phương thức đánh dấu `@BeforeEach` chạy **trước mỗi** phương thức `@Test` trong cùng class — đảm
bảo mỗi test bắt đầu với một object `calculator` **hoàn toàn mới**, không bị ảnh hưởng bởi bất kỳ
thay đổi trạng thái nào từ test chạy trước đó. Đây là nguyên tắc quan trọng: **mỗi test phải độc
lập hoàn toàn** với các test khác — thứ tự chạy test không nên ảnh hưởng tới kết quả.

## `assertThrows` — kiểm tra exception được ném đúng cách

```java
@Test
void testChiaChoKhong() {
    ArithmeticException loi = assertThrows(
        ArithmeticException.class,
        () -> calculator.chia(5, 0)
    );
    assertEquals("Khong the chia cho 0", loi.getMessage());
}
```

`assertThrows(KieuException.class, lambda)` (Chương 30) xác nhận đoạn code trong lambda **thực sự
ném ra đúng loại exception mong đợi** — nếu không ném gì cả, hoặc ném **sai loại** exception, test
thất bại. Đây là cách kiểm thử **cả những tình huống lỗi** (Chương 21) một cách có hệ thống, không
chỉ kiểm tra "đường đi đúng" (happy path).

## `@DisplayName` — mô tả test dễ đọc hơn

```java
@Test
@DisplayName("Chia cho 0 phai nem ArithmeticException")
void testChiaChoKhong() { ... }
```

Tuỳ chọn, nhưng hữu ích: tên hiển thị trong báo cáo kết quả test rõ ràng hơn tên phương thức (vốn
thường bị giới hạn theo quy tắc đặt tên biến/phương thức của Java, không có dấu cách hay ký tự đặc
biệt).

## Thiết kế code "dễ test"

So sánh `Calculator` trong code mẫu chương này (chỉ chứa phương thức tính toán thuần tuý) với việc
viết logic đó **lẫn trực tiếp** vào một chương trình đọc `Scanner`/in `System.out` — nếu logic tính
toán và logic nhập/xuất trộn lẫn vào nhau, viết test cho riêng phần tính toán trở nên khó khăn hơn
nhiều (phải giả lập input bàn phím, bắt output console...). **Tách biệt logic nghiệp vụ khỏi I/O**
(đọc/ghi file, console, mạng...) là một nguyên tắc thiết kế quan trọng, không chỉ giúp code rõ ràng
hơn mà còn giúp viết test dễ dàng hơn hẳn — bạn sẽ gặp lại tư duy này khi học kiến trúc phần mềm ở
Chương 44-45.

## Bài tập

1. Viết `SinhVienTest` kiểm thử class `SinhVien` (Chương 13): test `tinhDiemTrungBinh()` với danh
   sách điểm rỗng (kết quả mong đợi `0.0`), và với vài điểm cụ thể.
2. Viết test cho phương thức `isPrime` bạn đã viết ở bài tập Chương 10, kiểm tra cả trường hợp số
   nguyên tố, không phải nguyên tố, và trường hợp biên (0, 1, số âm).
3. Viết một test **cố ý sai** (ví dụ `assertEquals(6, calculator.cong(2, 3))` — sai vì kết quả đúng
   là `5`), chạy để tự thấy JUnit báo lỗi thế nào, đọc kỹ thông báo "expected/actual" để quen mặt
   định dạng báo lỗi.

## Lỗi thường gặp

- **Test phụ thuộc lẫn nhau** (test B chỉ chạy đúng nếu test A chạy trước và để lại trạng thái nào
  đó) — vi phạm nguyên tắc độc lập, gây lỗi khó hiểu khi JUnit chạy test theo thứ tự khác (JUnit
  **không đảm bảo** thứ tự chạy test mặc định). Luôn dùng `@BeforeEach` để đảm bảo trạng thái sạch.
- **Chỉ test "đường đi đúng", quên test trường hợp lỗi/biên** — ví dụ chỉ test `chia(5, 2)` mà quên
  test `chia(5, 0)`. Luôn nghĩ tới các trường hợp biên: giá trị `0`, âm, rỗng, `null`, giá trị cực
  lớn/nhỏ.
- **Viết test rồi không bao giờ chạy lại** — mất hết giá trị của việc có test tự động. Test chỉ hữu
  ích khi được chạy thường xuyên (lý tưởng: sau mỗi lần sửa code, hoặc tự động qua CI — nằm ngoài
  phạm vi sách này).
- **So sánh số thực (`double`) bằng `assertEquals` không có sai số cho phép** — số thực có thể có
  sai số làm tròn rất nhỏ (ví dụ `0.1 + 0.2` không chính xác bằng `0.3` tuyệt đối ở tầng bit); JUnit
  có overload `assertEquals(mongDoi, thucTe, saiSoChoPhep)` dành riêng cho `double`, nên dùng khi so
  sánh kết quả phép tính số thực phức tạp.
