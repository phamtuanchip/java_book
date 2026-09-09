# Chương 15 — Code mẫu: package & import

Cấu trúc thư mục **phải khớp chính xác** với tên package:

```
ch15-package-import/
└── com/example/school/SinhVien.java            (package com.example.school;)
└── com/example/school/MaSinhVienGenerator.java (package com.example.school;)
└── com/example/app/Main.java                   (package com.example.app;)
```

## Biên dịch và chạy

Chạy từ đúng thư mục `ch15-package-import/` (thư mục gốc chứa `com/`):

```
javac com/example/school/*.java com/example/app/*.java
java com.example.app.Main
```

Lưu ý: khi chạy chương trình có package, gọi `java` bằng **tên class đầy đủ kèm package**
(`com.example.app.Main`), không phải chỉ `Main`.

## Thử lỗi package-private

Trong `com/example/app/Main.java`, bỏ comment dòng gọi
`com.example.school.MaSinhVienGenerator.sinhMa("test");`, biên dịch lại và đọc lỗi — class
`MaSinhVienGenerator` không có `public` nên không "nhìn thấy" được từ package khác.
