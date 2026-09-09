# Chương 4 — Làm quen IDE và giới thiệu Maven/Gradle

## Mục tiêu học

Sau chương này, bạn sẽ:

- Tạo được một project mới trong IntelliJ IDEA, chạy chương trình bằng nút Run thay vì gõ
  `javac`/`java` tay.
- Biết cách đặt breakpoint và debug từng bước cơ bản.
- Hiểu vì sao project nhiều file cần công cụ quản lý build, và Maven/Gradle giải quyết vấn đề gì
  (chưa cần cài đặt hay dùng thật — sẽ học sâu ở Chương 42).

Code mẫu: [`code/ch04-multi-file-project/`](../../code/ch04-multi-file-project/).

## Tạo project mới trong IntelliJ IDEA

1. Mở IntelliJ IDEA → **New Project**.
2. Chọn **Java** ở danh sách bên trái, đặt tên project (ví dụ `hello-intellij`).
3. Ở mục **JDK**, chọn bản JDK 21 đã cài ở Chương 2 (nếu IntelliJ không tự thấy, bấm **Add JDK...**
   và trỏ tới thư mục cài JDK).
4. Bỏ chọn (hoặc để mặc định, không ảnh hưởng ở giai đoạn này) tuỳ chọn tạo bằng Maven/Gradle — ở
   chương này ta tạo project Java thuần trước, chưa cần build tool.
5. IntelliJ tạo sẵn thư mục `src` — đây là nơi chứa toàn bộ file `.java` của bạn.

Chuột phải vào `src` → **New → Java Class**, đặt tên `Main`, IntelliJ tự sinh khung:

```java
public class Main {
    public static void main(String[] args) {

    }
}
```

Gõ `System.out.println("Hello");` vào bên trong `main`, bấm nút **▶ (Run)** ở góc trái dòng khai
báo `main` (hoặc bấm chuột phải trong file → Run 'Main.main()'). IntelliJ tự động biên dịch mọi
file `.java` liên quan (tương đương chạy `javac` rồi `java` bạn đã làm tay ở Chương 3) và hiện kết
quả ở panel **Run** phía dưới.

## Debug cơ bản

Debug là kỹ năng quan trọng nhất khi chương trình chạy sai mà bạn không hiểu vì sao. Ba thao tác
cần biết ngay từ đầu:

1. **Đặt breakpoint**: click vào lề trái cạnh số dòng — hiện một chấm đỏ. Chương trình sẽ **dừng
   lại** đúng tại dòng đó khi chạy tới, thay vì chạy xuyên suốt.
2. **Chạy ở chế độ Debug**: bấm nút 🐞 (Debug) thay vì ▶ (Run). Chương trình chạy tới breakpoint
   thì dừng, một panel hiện ra cho thấy giá trị **hiện tại** của mọi biến.
3. **Step Over (F8) / Step Into (F7)**: chạy tiếp từng dòng một, quan sát giá trị biến thay đổi ra
   sao sau mỗi dòng. Step Into đi vào bên trong một phương thức được gọi; Step Over chạy qua
   phương thức đó luôn mà không đi vào chi tiết bên trong.

Thử ngay với code mẫu: mở `Main.java` trong `code/ch04-multi-file-project/`, đặt breakpoint ở dòng
gọi `MathUtils.square(4)`, chạy Debug, dùng Step Into để nhảy vào bên trong `MathUtils.square` và
quan sát giá trị tham số `n`.

## Vì sao cần Maven/Gradle?

Với chương trình 1-2 file như hiện tại, gõ `javac *.java` là đủ. Nhưng một dự án thực tế thường:

- Có hàng trăm file `.java`, tổ chức theo nhiều thư mục con.
- Phụ thuộc vào các **thư viện bên ngoài** (ví dụ thư viện đọc JSON sẽ dùng ở Chương 35) — bạn
  không tự viết những thư viện đó, mà cần tải về đúng phiên bản và cho trình biên dịch biết chỗ
  tìm chúng.
- Cần các bước lặp lại: biên dịch, chạy test, đóng gói thành file phân phối (`.jar`).

**Maven** và **Gradle** là hai công cụ phổ biến nhất giải quyết đúng ba vấn đề đó: khai báo thư
viện cần dùng trong một file cấu hình (`pom.xml` với Maven, `build.gradle` với Gradle), công cụ tự
tải về và biên dịch đúng thứ tự phụ thuộc. IntelliJ IDEA có hỗ trợ tạo project Maven/Gradle sẵn
trong màn hình New Project ở bước trên — nhưng sách này **cố tình hoãn** việc dùng chúng tới
Chương 42, để bạn tập trung học ngôn ngữ Java trước, tránh bị công cụ làm rối ở giai đoạn nhập môn.
Toàn bộ code mẫu từ Chương 3 đến hết Phần 8 chạy được chỉ bằng `javac`/`java` thuần hoặc nút Run
của IDE, không cần Maven/Gradle.

## Bài tập

1. Tạo project IntelliJ mới, copy 2 file trong `code/ch04-multi-file-project/` vào, chạy bằng nút
   Run và xác nhận kết quả khớp với phần "Kết quả mong đợi" trong README của code mẫu.
2. Đặt breakpoint bên trong `MathUtils.isEven`, chạy Debug, dùng Step Over để xem giá trị `n % 2`
   được tính ra sao trước khi hàm trả về.
3. Thử xoá một dấu ngoặc `{` trong `Main.java` rồi bấm Run — quan sát cách IntelliJ báo lỗi *ngay
   khi gõ* (gạch đỏ dưới code) khác với việc phải đợi `javac` báo lỗi sau khi biên dịch.

## Lỗi thường gặp

- **IntelliJ không thấy JDK nào để chọn khi tạo project** — JDK chưa cài đúng cách ở Chương 2, hoặc
  IntelliJ chưa quét lại. Dùng nút **Add JDK...** và trỏ thủ công tới thư mục cài JDK.
- **Bấm Run nhưng không thấy nút ▶ cạnh `main`** — thường do chữ ký `main` sai (xem lại Chương 3),
  IntelliJ không nhận diện được đây là điểm chạy chương trình.
- **Đặt breakpoint nhưng chương trình chạy xuyên qua không dừng** — bạn đang bấm **Run** (▶) chứ
  không phải **Debug** (🐞). Chỉ chế độ Debug mới dừng lại ở breakpoint.
- **Copy code mẫu vào IntelliJ nhưng báo "class MathUtils is public, should be declared in a file
  named MathUtils.java"** — nhắc lại quy tắc ở Chương 3: tên file phải khớp tên class public, kể cả
  khi dùng IDE.
