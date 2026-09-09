# Chương 9 — Code mẫu: mảng

## `ArrayBasicsDemo.java`

```
javac ArrayBasicsDemo.java
java ArrayBasicsDemo
```

Minh hoạ: khai báo/khởi tạo mảng, duyệt bằng `for` có chỉ số và `for-each`, mảng là kiểu **tham
chiếu** (gán mảng cho biến khác không sao chép nội dung), sao chép thật sự bằng `Arrays.copyOf`,
in mảng đúng cách bằng `Arrays.toString`, sắp xếp bằng `Arrays.sort`.

Dòng bị comment `// System.out.println(diem[10]);` minh hoạ lỗi truy cập ngoài phạm vi — thử bỏ
comment dòng đó và chạy lại để tự thấy `ArrayIndexOutOfBoundsException` (chương trình sẽ dừng đột
ngột tại đó, các dòng in phía trước vẫn đã chạy xong).

## `Matrix2DDemo.java`

```
javac Matrix2DDemo.java
java Matrix2DDemo
```

Minh hoạ mảng hai chiều: khai báo, gán giá trị từng ô bằng vòng lặp lồng nhau, khởi tạo trực tiếp
với giá trị có sẵn, và mảng không đều (jagged array) — mỗi hàng có số cột khác nhau.
