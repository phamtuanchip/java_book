# Chương 41 — Code mẫu: JUnit 5

`Calculator.java` là class cần kiểm thử (chỉ chứa logic tính toán thuần tuý, không phụ thuộc I/O
— thiết kế dễ test). `CalculatorTest.java` là bộ test JUnit 5 tương ứng.

## Chạy test — cần tải `junit-platform-console-standalone`

Không có Maven/Gradle (Chương 42), chạy JUnit "trần" cần tải file
[junit-platform-console-standalone](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/)
(một file `.jar` gộp sẵn mọi thứ cần thiết để chạy test JUnit 5 từ dòng lệnh):

```
javac -cp junit-platform-console-standalone-1.10.2.jar Calculator.java CalculatorTest.java
java -jar junit-platform-console-standalone-1.10.2.jar -cp . --select-class CalculatorTest
```

Kết quả mong đợi: cả 6 test đều `PASSED` (hiển thị dạng cây kết quả trong console).

**Đây lại là đúng vấn đề đã gặp ở Chương 35 (Gson)** — quản lý một thư viện test bằng tay đã hơi
phiền, dự án thực tế còn cần nhiều thư viện hơn thế. Từ Chương 42, project sẽ chuyển sang dùng
Maven/Gradle, và chạy test chỉ còn cần một lệnh duy nhất (`mvn test`), không cần tải/quản lý `.jar`
thủ công nữa.
