# Chương 3 — Chương trình Java đầu tiên

## Mục tiêu học

Sau chương này, bạn sẽ:

- Viết, biên dịch (`javac`) và chạy (`java`) được một chương trình Java bằng dòng lệnh, không cần
  IDE.
- Đọc hiểu từng dòng của một file `.java` tối thiểu: `class`, `public static void main`, vì sao
  cấu trúc đó bắt buộc phải có.
- Hiểu quy tắc đặt tên file phải khớp tên class public, và lý do đằng sau quy tắc đó.
- Biết cách nhận đối số dòng lệnh qua `String[] args`.

Code mẫu đầy đủ: [`code/ch03-hello-world/`](../../code/ch03-hello-world/).

## Viết chương trình đầu tiên

Tạo một file tên `HelloWorld.java`, nội dung:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Xin chao, Java!");
    }
}
```

Biên dịch và chạy bằng terminal (mở tại đúng thư mục chứa file):

```
javac HelloWorld.java
java HelloWorld
```

Kết quả:

```
Xin chao, Java!
```

Chuyện gì vừa xảy ra? `javac HelloWorld.java` gọi trình biên dịch, đọc file mã nguồn, sinh ra file
`HelloWorld.class` (bytecode) trong cùng thư mục. `java HelloWorld` gọi JVM nạp file `.class` đó
và chạy nó — chú ý lệnh thứ hai **không có đuôi `.java`**, vì bạn đang chạy bytecode đã biên dịch,
không phải mã nguồn.

## Giải phẫu từng dòng

```java
public class HelloWorld {
```

Mọi code Java (trừ vài trường hợp đặc biệt sẽ học sau) phải nằm trong một **class**. `HelloWorld`
là tên class. Từ khoá `public` nghĩa là class này có thể được truy cập từ bất kỳ đâu (sẽ học kỹ ở
Chương 14 — Encapsulation).

**Quy tắc bắt buộc**: nếu class được khai báo `public`, **tên file phải trùng chính xác tên class**
(phân biệt hoa/thường), cộng đuôi `.java`. Class `HelloWorld` phải nằm trong file `HelloWorld.java`
— không phải `helloworld.java` hay `Hello.java`. Trình biên dịch dùng tên file để biết class public
nào cần tìm; sai tên là lỗi biên dịch ngay lập tức.

```java
    public static void main(String[] args) {
```

Đây là **phương thức `main`** — điểm bắt đầu chạy chương trình. Khi bạn gõ `java HelloWorld`, JVM
tìm trong class `HelloWorld` một phương thức có **chính xác** chữ ký này để bắt đầu chạy. Từng từ
khoá có lý do tồn tại (sẽ hiểu sâu hơn ở Chương 10 và Chương 13, ở đây cứ ghi nhớ như một khuôn mẫu
cố định):

- `public` — JVM (từ bên ngoài class) phải gọi được phương thức này.
- `static` — JVM gọi `main` **mà chưa có object nào được tạo** (object là gì sẽ học ở Chương 13).
  `static` nghĩa là phương thức thuộc về class, không cần tạo object mới gọi được.
- `void` — `main` không trả về giá trị nào.
- `String[] args` — mảng chuỗi chứa các đối số dòng lệnh truyền vào khi chạy chương trình.

Đổi bất kỳ phần nào trong chữ ký này (ví dụ bỏ `static`, hay đổi tên tham số thành kiểu khác) đều
khiến JVM không tìm thấy điểm bắt đầu và báo lỗi `Error: Main method not found`.

```java
        System.out.println("Xin chao, Java!");
```

`System.out` là "cổng ra" chuẩn để in ra màn hình console. `println` in ra một dòng, tự động xuống
dòng ở cuối. Dấu `;` bắt buộc kết thúc mỗi câu lệnh trong Java — quên dấu `;` là lỗi cú pháp phổ
biến nhất với người mới.

## Nhận đối số dòng lệnh

Xem file `Greeter.java` trong code mẫu:

```java
public class Greeter {
    public static void main(String[] args) {
        String name = "hoc vien";
        if (args.length > 0) {
            name = args[0];
        }
        System.out.println("Xin chao, " + name + "!");
    }
}
```

Chạy `java Greeter Tuan` → `args` là mảng chứa `["Tuan"]`, `args[0]` là `"Tuan"`. Chạy `java Greeter`
(không truyền gì) → `args.length` bằng 0, dùng giá trị mặc định. Đây là cách chương trình dòng lệnh
nhận input mà chưa cần học đọc bàn phím (`Scanner` sẽ học ở Chương 5).

## Chạy nhanh không cần biên dịch riêng (Java 11+)

Với một file duy nhất, bạn có thể bỏ qua bước `javac` để thử nhanh:

```
java HelloWorld.java
```

Lệnh này biên dịch trong bộ nhớ rồi chạy ngay, không sinh ra file `.class` trên đĩa. Tiện cho việc
thử nghiệm nhanh, nhưng khi chương trình có nhiều file/nhiều class phụ thuộc nhau, bạn vẫn cần
`javac` (sẽ gặp ở các chương sau khi chương trình lớn dần).

## Bài tập

1. Sửa `HelloWorld.java` để in thêm dòng thứ hai là tên của bạn.
2. Thử xoá dấu `;` ở cuối dòng `System.out.println(...)`, biên dịch lại và đọc kỹ thông báo lỗi
   trình biên dịch đưa ra — tập làm quen đọc lỗi biên dịch, đây là kỹ năng dùng suốt quá trình học.
3. Sửa `Greeter.java` để nếu có từ 2 đối số trở lên, in ra lời chào cho **từng** đối số (gợi ý:
   dùng vòng lặp — nếu chưa học vòng lặp ở Chương 8, có thể tạm dùng `args[0]`, `args[1]` tay và
   quay lại bài tập này sau khi học xong Chương 8).

## Lỗi thường gặp

- **`error: class HelloWorld is public, should be declared in a file named HelloWorld.java`** —
  tên file không khớp tên class public. Đổi tên file hoặc tên class cho khớp nhau.
- **`Error: Main method not found in class HelloWorld`** — chữ ký `main` sai (thiếu `static`, sai
  kiểu tham số, viết hoa/thường sai tên `main`...). Đối chiếu lại đúng khuôn mẫu
  `public static void main(String[] args)`.
- **`';' expected`** — thiếu dấu chấm phẩy cuối câu lệnh. Trình biên dịch Java báo dòng và cột khá
  chính xác, đọc kỹ thông báo trước khi đoán mò.
- **`java: command not found` dù đã cài JDK** — xem lại Chương 2, phần cấu hình `PATH`.
- **Chạy `java HelloWorld.java` mà báo lỗi khác `java HelloWorld`** — hai lệnh không tương đương
  hoàn toàn khi chương trình có nhiều file; chạy bằng file nguồn đơn chỉ hỗ trợ đúng nghĩa "một
  file duy nhất".
