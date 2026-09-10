# Chương 46 — Code mẫu: JDBC cơ bản

**Cần tải JDBC driver của H2** (cơ sở dữ liệu nhúng, chạy trong bộ nhớ, không cần cài server riêng)
để biên dịch và chạy — tải
[h2-2.2.224.jar](https://search.maven.org/artifact/com.h2database/h2/2.2.224/jar) (hoặc bản mới
hơn):

```
javac -cp h2-2.2.224.jar JdbcBasicsDemo.java
java -cp .;h2-2.2.224.jar JdbcBasicsDemo        # Windows
java -cp .:h2-2.2.224.jar JdbcBasicsDemo         # macOS/Linux

javac -cp h2-2.2.224.jar PreparedStatementDemo.java
java -cp .;h2-2.2.224.jar PreparedStatementDemo
```

Nếu đã cài Maven/Gradle (Chương 42), thêm dependency
`com.h2database:h2:2.2.224` vào `pom.xml`/`build.gradle` và chạy qua build tool sẽ tiện hơn nhiều
so với tự ghép `-cp` — đúng như bài học đã rút ra từ Chương 35/41/42.

- `JdbcBasicsDemo.java` — kết nối, tạo bảng, thêm dữ liệu, truy vấn và đọc kết quả bằng
  `Statement`/`ResultSet`.
- `PreparedStatementDemo.java` — minh hoạ trực tiếp lỗ hổng **SQL injection** khi nối chuỗi SQL thủ
  công, và cách `PreparedStatement` ngăn chặn nó.
