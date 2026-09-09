# Chương 3 — Code mẫu: Hello World

## Chạy `HelloWorld.java`

```
javac HelloWorld.java
java HelloWorld
```

Kết quả mong đợi:

```
Xin chao, Java!
```

## Chạy `Greeter.java` (có đối số dòng lệnh)

```
javac Greeter.java
java Greeter Tuan
```

Kết quả mong đợi:

```
Xin chao, Tuan!
Day la doi so dong lenh nhan duoc: 1 doi so.
```

Thử chạy `java Greeter` (không truyền đối số) để xem `args.length` bằng 0 và giá trị mặc định
"hoc vien" được dùng.

## Chạy bằng file nguồn đơn (Java 11+, không cần `javac` riêng)

Từ Java 11, với chương trình chỉ có **một file** duy nhất, có thể chạy thẳng không cần biên dịch
riêng:

```
java HelloWorld.java
```

Lệnh này biên dịch trong bộ nhớ rồi chạy ngay — tiện để thử nhanh, nhưng không sinh ra file
`.class`.
