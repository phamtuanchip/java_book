# Chương 36 — Code mẫu: Serialization

```
javac *.java
java SerializationDemo
```

`NguoiDung implements Serializable`, có `serialVersionUID` khai báo tường minh và field
`matKhauTamThoi` đánh dấu `transient`. `SerializationDemo.java` ghi object xuống file `.ser`
(`ObjectOutputStream`), đọc lại (`ObjectInputStream`) — xác nhận `matKhauTamThoi` là `null` sau khi
đọc lại, vì field `transient` không được lưu.
