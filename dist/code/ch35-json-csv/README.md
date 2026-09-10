# Chương 35 — Code mẫu: JSON/CSV

## `CsvDemo.java` — chạy trực tiếp, không cần gì thêm

```
javac CsvDemo.java
java CsvDemo
```

Ghi/đọc file CSV chỉ bằng `String.split(",")` và `StringBuilder` — không cần thư viện ngoài.

## `SimpleJsonWriter.java` — chạy trực tiếp, không cần gì thêm

```
javac SimpleJsonWriter.java
java SimpleJsonWriter
```

Tự viết một bộ sinh chuỗi JSON rất đơn giản, để hiểu **cấu trúc** JSON trước khi dùng thư viện thật.

## `GsonExample.java` — CẦN tải thư viện Gson riêng

File này **không** biên dịch được chỉ với `javac` thuần — cần tải
[gson-2.10.1.jar](https://search.maven.org/artifact/com.google.code.gson/gson/2.10.1/jar) (hoặc
bản mới hơn) và thêm vào classpath:

```
javac -cp gson-2.10.1.jar GsonExample.java
java -cp .;gson-2.10.1.jar GsonExample        # Windows (PowerShell/cmd dùng ';')
java -cp .:gson-2.10.1.jar GsonExample         # macOS/Linux (dùng ':')
```

Đây **chính là** sự phiền phức mà Chương 42 (Maven/Gradle) sẽ giải quyết — tự động tải và quản lý
thư viện, không cần tải file `.jar` thủ công hay tự nhớ đường dẫn classpath.
