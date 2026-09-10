# Chương 5 — Code mẫu: biến, kiểu dữ liệu, ép kiểu

## `PrimitiveTypesDemo.java`

```
javac PrimitiveTypesDemo.java
java PrimitiveTypesDemo
```

In ra giá trị của từng kiểu nguyên thủy, minh hoạ ép kiểu tự động (widening), ép kiểu thủ công
(narrowing, cắt phần thập phân), và hiện tượng tràn số khi ép kiểu số quá lớn vào kiểu quá nhỏ
(`byte soBiTran = (byte) 130` không ra `130`, xem chương để hiểu vì sao).

## `TemperatureConverter.java`

```
javac TemperatureConverter.java
java TemperatureConverter
```

Chương trình hỏi nhiệt độ theo độ C, đọc từ bàn phím bằng `Scanner`, tính và in ra độ F. Nhập ví dụ
`37` sẽ ra kết quả tương ứng `98.6`.
