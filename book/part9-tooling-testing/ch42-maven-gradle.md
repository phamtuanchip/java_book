# Chương 42 — Build tool: Maven và Gradle

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu vấn đề Maven/Gradle giải quyết (đã gặp trực tiếp ở Chương 35, 41).
- Đọc hiểu cấu trúc thư mục chuẩn của một project Maven/Gradle.
- Khai báo dependency trong `pom.xml` (Maven) và `build.gradle` (Gradle).
- Chạy build, test, và chương trình bằng lệnh của build tool thay vì `javac`/`java` thủ công.

Code mẫu đầy đủ: [`code/ch42-maven-gradle/`](../../code/ch42-maven-gradle/).

## Nhắc lại vấn đề đã gặp

Ở Chương 35 (dùng Gson) và Chương 41 (dùng JUnit), bạn đã phải tự tải file `.jar`, tự nhớ đường dẫn,
tự ghép vào `-cp`. Với một thư viện, việc đó còn chịu được. Với **nhiều** thư viện — mỗi thư viện có
thể phụ thuộc thêm các thư viện khác nữa ("dependency của dependency") — quản lý thủ công gần như
không khả thi. **Maven** và **Gradle** giải quyết đúng vấn đề này: khai báo thư viện cần dùng trong
một file cấu hình, công cụ **tự động tải về** (từ kho lưu trữ trung tâm, mặc định là Maven Central)
đúng phiên bản, xử lý toàn bộ classpath, và cung cấp lệnh chuẩn để biên dịch/test/đóng gói/chạy.

## Cài đặt

- **Maven**: tải từ [maven.apache.org](https://maven.apache.org/download.cgi), giải nén, thêm thư
  mục `bin` vào `PATH` (giống cách cấu hình `PATH` cho JDK ở Chương 2). Kiểm tra: `mvn -version`.
- **Gradle**: tải từ [gradle.org](https://gradle.org/install/), tương tự. Kiểm tra: `gradle -version`.
  (Trong dự án Gradle thực tế, thường dùng **Gradle Wrapper** — một script đi kèm project tự tải
  đúng phiên bản Gradle cần thiết, người dùng không cần tự cài Gradle riêng — nằm ngoài phạm vi demo
  đơn giản của chương này.)

Cả hai đều tích hợp sẵn trong IntelliJ IDEA (Chương 4) — khi tạo project mới, bạn có thể chọn ngay
Maven hoặc Gradle làm build tool thay vì "Java thuần" đã chọn từ đầu sách.

## Cấu trúc thư mục chuẩn

Cả Maven và Gradle đều theo (mặc định) cùng một quy ước cấu trúc thư mục:

```
project/
├── pom.xml (Maven) hoặc build.gradle (Gradle)   # file cau hinh
├── src/
│   ├── main/java/...    # code CHINH cua chuong trinh
│   └── test/java/...    # code TEST (Chuong 41)
```

So với cách tổ chức code tự do bạn dùng từ đầu sách (mọi file `.java` nằm chung một thư mục), cấu
trúc này **tách biệt rõ ràng** code chính và code test, và là quy ước **gần như mọi** project Java
thực tế đều tuân theo — giúp bất kỳ ai quen Maven/Gradle cũng lập tức biết tìm code ở đâu khi mở
một project Java mới, bất kể dự án đó là gì.

## Maven — `pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.sach</groupId>
    <artifactId>maven-demo</artifactId>
    <version>1.0.0</version>

    <dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

- `groupId`/`artifactId`/`version` — định danh **project của bạn** (giống ý tưởng tên miền đảo
  ngược ở Chương 15 khi đặt tên package).
- Mỗi `<dependency>` khai báo một thư viện cần dùng — `groupId`/`artifactId`/`version` xác định
  chính xác **thư viện nào, phiên bản nào**. `<scope>test</scope>` nghĩa là thư viện đó **chỉ cần**
  lúc chạy test (JUnit), không đóng gói kèm khi chạy chương trình thật.

Lệnh Maven cơ bản: `mvn compile` (biên dịch), `mvn test` (chạy test), `mvn package` (đóng gói thành
file `.jar`).

## Gradle — `build.gradle`

```groovy
plugins {
    id 'java'
    id 'application'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.google.code.gson:gson:2.10.1'
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
}

test {
    useJUnitPlatform()
}
```

Cùng ý tưởng như Maven, cú pháp gọn hơn (dùng Groovy hoặc Kotlin DSL thay vì XML). `implementation`
tương ứng dependency dùng lúc chạy chương trình chính, `testImplementation` tương ứng
`<scope>test</scope>` của Maven. Lệnh Gradle cơ bản: `gradle build` (biên dịch + test + đóng gói),
`gradle test`, `gradle run` (nếu có plugin `application`, như trong code mẫu).

## Maven hay Gradle?

| | Maven | Gradle |
|---|---|---|
| Cú pháp cấu hình | XML (`pom.xml`), tường minh, hơi dài dòng | Groovy/Kotlin DSL, ngắn gọn hơn, linh hoạt hơn (cho phép viết logic build tuỳ chỉnh) |
| Độ phổ biến | Rất phổ biến, đặc biệt hệ sinh thái Spring | Rất phổ biến, đặc biệt hệ sinh thái Android |
| Tốc độ build | Ổn định, đơn giản | Thường nhanh hơn với project lớn (cache build thông minh hơn) |

Không có lựa chọn "đúng tuyệt đối" — cả hai đều giải quyết cùng vấn đề, khác nhau chủ yếu về cú
pháp và triết lý thiết kế. Nhiều công ty/dự án chọn một trong hai theo thói quen của team hoặc theo
framework chính đang dùng (ví dụ dự án Spring Boot thường dùng Maven hoặc Gradle tuỳ đội, dự án
Android gần như luôn dùng Gradle).

## Từ đây trở đi

Từ chương này, các chương còn lại trong sách (Chương 43-47) có thể tham khảo cách tổ chức project
theo chuẩn Maven/Gradle khi cần dùng thư viện ngoài — nhưng để giữ mọi chương **tự chạy độc lập**
không đòi hỏi cài đặt phức tạp, phần lớn code mẫu vẫn ưu tiên chạy được bằng `javac`/`java` thuần
khi hợp lý, chỉ giới thiệu cấu trúc Maven/Gradle khi thư viện ngoài thực sự cần thiết (như JDBC
driver ở Chương 46).

## Bài tập

1. Cài Maven (hoặc Gradle), chạy `mvn test` (hoặc `gradle test`) trong `code/ch42-maven-gradle/maven-demo/`
   (hoặc `gradle-demo/`), xác nhận cả hai test trong `SinhVienTest` đều pass.
2. Thêm một dependency mới vào `pom.xml`/`build.gradle` (ví dụ Apache Commons Lang:
   `org.apache.commons:commons-lang3:3.14.0`), dùng thử một phương thức tiện ích của nó trong
   `App.java`.
3. Tạo một project Maven mới trong IntelliJ IDEA (File → New → Project → chọn Maven), so sánh cấu
   trúc thư mục IDE tự sinh ra với cấu trúc đã học trong chương.

## Lỗi thường gặp

- **`mvn`/`gradle` không nhận diện được (`command not found`)** — chưa thêm vào `PATH` đúng cách,
  xem lại phần "Cài đặt", tương tự cách khắc phục JDK ở Chương 2.
- **Khai báo sai `groupId`/`artifactId`/`version` của dependency** — Maven/Gradle báo lỗi không tìm
  thấy thư viện (`could not resolve dependency`). Kiểm tra chính xác thông tin trên trang chính
  thức của thư viện hoặc [Maven Central](https://search.maven.org).
- **Đặt file `.java` sai vị trí trong cấu trúc thư mục chuẩn** (ví dụ đặt ngoài `src/main/java`) —
  build tool không tìm thấy để biên dịch. Luôn tuân theo đúng cấu trúc `src/main/java/...`/
  `src/test/java/...`.
- **Trộn lẫn code chính và code test trong cùng thư mục `src/main/java`** — về mặt kỹ thuật vẫn
  biên dịch được, nhưng phá vỡ quy ước chuẩn, khiến `mvn package`/`gradle build` có thể vô tình đóng
  gói cả code test vào sản phẩm cuối cùng.
