# Chương 15 — Package & import

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu package là gì, vì sao Java cần nó khi project có nhiều class.
- Khai báo đúng `package` trong file `.java`, biết quy tắc thư mục phải khớp tên package.
- Dùng `import` để dùng class từ package khác.
- Hiểu lại rõ hơn mức truy cập **package-private** đã nhắc sơ ở Chương 14, qua ví dụ cụ thể.
- Biết quy ước đặt tên package theo tên miền đảo ngược.

Code mẫu đầy đủ: [`code/ch15-package-import/`](../../code/ch15-package-import/).

## Package là gì, vì sao cần?

Từ Chương 3 đến giờ, mọi class bạn viết đều nằm trong **"gói mặc định"** (default package) — không
khai báo `package` gì cả. Cách này ổn với project nhỏ vài file, nhưng có vấn đề khi project lớn
dần: **tên class phải là duy nhất trong toàn bộ project**. Hai lập trình viên trong cùng team, mỗi
người viết một class tên `Utils`, sẽ xung đột ngay nếu cùng nằm trong gói mặc định.

**Package** là cách Java tổ chức class thành các "thư mục" logic, giải quyết đúng vấn đề đó — hai
class trùng tên vẫn tồn tại được miễn là nằm trong package khác nhau (`com.truong.Utils` khác với
`com.congty.Utils`, dù cùng tên lớp `Utils`).

## Khai báo package

```java
package com.example.school;

public class SinhVien {
    // ...
}
```

Dòng `package ...;` **phải là dòng code đầu tiên** trong file (chỉ có thể đứng sau comment, không
được đứng sau bất kỳ dòng code nào khác).

### Quy tắc bắt buộc: thư mục phải khớp tên package

Nếu class khai báo `package com.example.school;`, file chứa nó **phải** nằm trong đường dẫn thư
mục `com/example/school/` (mỗi dấu `.` trong tên package tương ứng một cấp thư mục). Đây là quy
tắc bắt buộc, tương tự quy tắc "tên file phải khớp tên class public" đã học ở Chương 3 — trình biên
dịch dựa vào cấu trúc thư mục để tìm và tổ chức các class theo đúng package của chúng.

```
com/example/school/SinhVien.java   ->  package com.example.school;
```

## `import` — dùng class từ package khác

```java
package com.example.app;

import com.example.school.SinhVien;

public class Main {
    public static void main(String[] args) {
        SinhVien sv = new SinhVien("Nguyen Van A");
        // ...
    }
}
```

`import` cho phép bạn viết tên class ngắn gọn (`SinhVien`) thay vì phải viết tên đầy đủ kèm package
mỗi lần dùng (`com.example.school.SinhVien`). Về bản chất, `import` chỉ là một "khai báo tắt" — bạn
vẫn có thể dùng tên đầy đủ mà không cần `import` gì cả:

```java
com.example.school.SinhVien sv = new com.example.school.SinhVien("Nguyen Van A");
```

Nhưng cách viết tắt với `import` gần như luôn được ưu tiên vì ngắn gọn, dễ đọc hơn nhiều.

**Lưu ý**: các class trong package `java.lang` (như `String`, `System`, `Math`...) được **tự động
import**, không cần khai báo `import` gì — đây là lý do bạn dùng `String` thoải mái từ Chương 5 mà
chưa từng viết `import java.lang.String;`. Mọi package khác trong thư viện chuẩn (`java.util.Scanner`
đã dùng ở Chương 5, `java.util.Arrays` ở Chương 9) đều cần `import` tường minh.

## Package-private — nhắc lại và làm rõ

Ở Chương 14, bảng 4 mức truy cập có nhắc tới **package-private** (không ghi access modifier gì) —
giờ bạn đã hiểu package là gì, ý nghĩa của mức này trở nên rõ ràng: một class/field/phương thức
không có modifier chỉ truy cập được từ **các class khác trong cùng package**, hoàn toàn "vô hình"
với package khác — kể cả khi package khác có `import` cũng không giúp gì, vì bản thân class đó
không được phép nhìn thấy từ bên ngoài package của nó.

Xem `MaSinhVienGenerator.java` trong code mẫu — class này **không có `public`**, chỉ
`SinhVien.java` (cùng package `com.example.school`) gọi được nó; `Main.java` (package
`com.example.app` khác) không thể, dù có cố `import` cũng không được (chính `import` cũng đòi hỏi
class đích phải `public` mới nhập được từ package khác).

**Ứng dụng thực tế của package-private**: đây là cách gói gọn các class "chi tiết cài đặt nội bộ"
mà bạn không muốn code bên ngoài package biết tới hay phụ thuộc vào — chỉ những gì thật sự cần dùng
từ bên ngoài mới nên là `public`. Đây là **encapsulation ở cấp độ package**, mở rộng ý tưởng đã học
ở Chương 14 (encapsulation ở cấp độ field) lên quy mô lớn hơn.

## Quy ước đặt tên package

Cộng đồng Java quy ước đặt tên package theo **tên miền đảo ngược** của tổ chức sở hữu code, viết
thường toàn bộ: công ty sở hữu tên miền `example.com` đặt package bắt đầu bằng `com.example.*`.
Quy ước này giúp package của các tổ chức khác nhau không trùng nhau trên phạm vi toàn cầu (vì tên
miền vốn đã là duy nhất). Với project cá nhân/học tập, quy ước phổ biến là dùng tên riêng của bạn,
ví dụ `com.tenban.tenproject`.

## Bài tập

1. Tạo package `com.tenban.hinhhoc` chứa một class `HinhTron` (có field `banKinh`, phương thức
   `tinhDienTich()`), và package `com.tenban.app` chứa `Main` gọi tới `HinhTron` qua `import`.
2. Thử xoá dòng `import` trong `Main.java` của bài tập 1, thay bằng gọi tên đầy đủ có package
   (`com.tenban.hinhhoc.HinhTron`), xác nhận vẫn biên dịch và chạy được — chứng minh `import` chỉ
   là cách viết tắt, không phải yêu cầu bắt buộc để dùng được class.
3. Trong code mẫu, thử đổi `MaSinhVienGenerator` từ package-private sang `public`, biên dịch lại,
   rồi bỏ comment dòng gọi trực tiếp từ `Main.java` — xác nhận giờ đã gọi được, để thấy rõ tác dụng
   của từ khoá `public`.

## Lỗi thường gặp

- **`error: class SinhVien is public, should be declared in a file named SinhVien.java`** hoặc lỗi
  tương tự về vị trí file — thư mục chứa file không khớp tên package khai báo. Kiểm tra lại đường
  dẫn thư mục đúng theo từng cấp của tên package.
- **`package com.example.school does not exist` hoặc `cannot find symbol`** khi biên dịch — thường
  do biên dịch thiếu file, hoặc chạy `javac` không đúng từ thư mục gốc chứa `com/`. Xem lại phần
  "Biên dịch và chạy" trong README code mẫu.
- **Chạy `java SinhVien` thay vì `java com.example.school.SinhVien`** — khi class thuộc một
  package, phải chạy bằng **tên đầy đủ kèm package**, không dùng được tên ngắn.
- **Cố `import` một class package-private từ package khác** — báo lỗi biên dịch vì class đó "không
  tồn tại" theo nghĩa nhìn thấy được từ package hiện tại. Chỉ `public` class mới import được từ
  package khác.
