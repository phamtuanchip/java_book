# Chương 42 — Code mẫu: Maven và Gradle

Hai project **tương đương nhau về chức năng** (in JSON của một `SinhVien` bằng Gson, có test JUnit
5), một viết theo cấu trúc Maven, một theo Gradle — để đối chiếu trực tiếp cách khai báo dependency
và cấu trúc thư mục.

## `maven-demo/` — cần cài Maven

```
cd maven-demo
mvn compile        # bien dich
mvn test           # chay test
mvn exec:java -Dexec.mainClass=com.sach.vidu.App   # chay chuong trinh (can plugin exec, hoac dung 'mvn package' roi chay jar)
```

## `gradle-demo/` — cần cài Gradle (hoặc dùng Gradle Wrapper trong dự án thật)

```
cd gradle-demo
gradle build       # bien dich + test + dong goi
gradle test        # chi chay test
gradle run         # chay chuong trinh (nho plugin 'application')
```

**Lưu ý**: hai project này minh hoạ cấu trúc và cú pháp cấu hình — cần cài Maven hoặc Gradle trên
máy để chạy thật (xem hướng dẫn cài đặt trong nội dung chương). So sánh với Chương 35 (phải tự tải
`gson.jar` và ghép `-cp` thủ công) và Chương 41 (phải tự tải `junit-platform-console-standalone.jar`)
— ở đây bạn chỉ cần khai báo dependency trong `pom.xml`/`build.gradle`, công cụ lo phần còn lại.
