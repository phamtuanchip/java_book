# Phụ lục B — Tài liệu tham khảo & nguồn học thêm

## Tài liệu chính thức

- **Java SE Documentation** — [docs.oracle.com/en/java](https://docs.oracle.com/en/java/) — tài
  liệu chính thức của Oracle, gồm Java Language Specification và JavaDoc của toàn bộ thư viện chuẩn
  (`java.lang`, `java.util`, `java.io`...). Đây là nguồn **đáng tin cậy nhất** khi cần tra cứu chính
  xác một class/phương thức làm gì — thói quen tra JavaDoc trực tiếp (thay vì chỉ hỏi công cụ AI
  hay tìm trên diễn đàn) là kỹ năng quan trọng nên rèn từ sớm.
- **OpenJDK** — [openjdk.org](https://openjdk.org/) — dự án mã nguồn mở là nền tảng của hầu hết các
  bản JDK miễn phí hiện nay (kể cả Eclipse Temurin đã dùng ở Chương 2).
- **Maven Central** — [search.maven.org](https://search.maven.org/) — kho lưu trữ thư viện trung
  tâm, tra cứu `groupId`/`artifactId`/`version` chính xác của bất kỳ thư viện nào trước khi khai báo
  dependency (Chương 42).

## Công cụ đã dùng trong sách

- **Eclipse Temurin (Adoptium)** — [adoptium.net](https://adoptium.net) — bản JDK dùng xuyên suốt
  sách (Chương 2).
- **IntelliJ IDEA** — [jetbrains.com/idea](https://www.jetbrains.com/idea/) — IDE minh hoạ chính
  (Chương 4), bản Community miễn phí đủ dùng cho toàn bộ nội dung sách.
- **Apache Maven** — [maven.apache.org](https://maven.apache.org/) và **Gradle** —
  [gradle.org](https://gradle.org/) — hai build tool đã học ở Chương 42.
- **JUnit 5** — [junit.org/junit5](https://junit.org/junit5/) — framework kiểm thử dùng ở Chương 41,
  47.
- **Gson** — [github.com/google/gson](https://github.com/google/gson) — thư viện xử lý JSON dùng ở
  Chương 35, 42.
- **H2 Database** — [h2database.com](https://www.h2database.com/) — cơ sở dữ liệu nhúng dùng để học
  JDBC ở Chương 46.

## Hướng đi tiếp theo sau khi đọc xong sách

Sách này dừng lại ở nền tảng Java SE vững chắc — đủ để bắt đầu học các hướng chuyên sâu hơn tuỳ mục
tiêu của bạn:

- **Phát triển backend/web** — học **Spring Framework**/**Spring Boot**
  ([spring.io](https://spring.io/)) — framework phổ biến nhất cho ứng dụng Java doanh nghiệp, xây
  dựng trực tiếp trên nền OOP, Collection, Stream, generics đã học trong sách này.
- **Phát triển Android** — Android SDK dùng Java (và Kotlin) làm ngôn ngữ chính — xem tài liệu chính
  thức tại [developer.android.com](https://developer.android.com/).
- **Xử lý dữ liệu lớn/hệ thống phân tán** — các hệ thống như Apache Kafka, Apache Spark, Hadoop đều
  viết bằng/chạy trên JVM, dùng nhiều tới đa luồng (Phần 8) và kiến trúc (Phần 10).
- **Đào sâu JVM** — tìm hiểu về garbage collector, JIT compiler, tối ưu hiệu năng — hữu ích khi làm
  việc với hệ thống quy mô lớn, nơi hiểu cơ chế bên dưới JVM (đã giới thiệu sơ lược ở Chương 1) trở
  nên quan trọng.

## Thói quen học tập nên giữ sau khi đọc xong sách

- **Đọc lại JavaDoc trước khi đoán** — khi không chắc một phương thức thư viện chuẩn hoạt động thế
  nào, tra cứu trực tiếp thay vì đoán dựa trên tên gọi.
- **Viết code, không chỉ đọc** — mọi chương trong sách đều có code mẫu và bài tập; kỹ năng lập trình
  chỉ hình thành qua thực hành, đọc lý thuyết một mình không đủ.
- **Đọc code người khác viết** — tìm các dự án mã nguồn mở Java trên GitHub phù hợp trình độ, đọc
  cách người khác tổ chức code thực tế — đối chiếu với nguyên tắc đã học (SOLID, design pattern —
  Chương 44-45) trong ngữ cảnh một dự án lớn thật sự.
- **Quay lại các chương OOP nền tảng (Phần 2-3) khi thấy mơ hồ** — đây là phần khó nhất và quan
  trọng nhất của sách; hiểu chưa vững ở giai đoạn đầu sẽ gây khó khăn dồn tích khi học các phần sau
  (đặc biệt Phần 6, 10) — không ngại đọc lại.
