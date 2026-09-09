# Chương 2 — Cài đặt JDK và IDE

## Mục tiêu học

Sau chương này, bạn sẽ:

- Cài đặt được JDK trên máy (Windows, macOS, hoặc Linux).
- Kiểm tra được JDK đã cài đúng bằng dòng lệnh (`java -version`, `javac -version`).
- Chọn và cài một IDE phù hợp để viết code Java.
- Hiểu `PATH` và `JAVA_HOME` là gì, vì sao đôi khi cần cấu hình tay.

## Chọn bản JDK nào

Có nhiều nhà cung cấp bản build JDK (đều tuân theo cùng chuẩn OpenJDK): Oracle JDK, Eclipse
Temurin (Adoptium), Amazon Corretto, Azul Zulu... Với người mới học, khác biệt giữa các bản build
gần như không ảnh hưởng gì. Sách này khuyến nghị **Eclipse Temurin 21 (LTS)** vì miễn phí hoàn
toàn cho mọi mục đích sử dụng (kể cả thương mại) và không yêu cầu tài khoản để tải.

- Trang tải: [adoptium.net](https://adoptium.net) → chọn **Version 21 (LTS)**, đúng hệ điều hành
  và kiến trúc CPU (x64 hoặc ARM64) của máy bạn.

## Cài đặt theo hệ điều hành

### Windows

1. Tải file `.msi` từ Adoptium cho Windows x64.
2. Chạy trình cài đặt, ở bước tuỳ chọn (Custom Setup) đảm bảo bật **"Set JAVA_HOME variable"** và
   **"Add to PATH"** — hai tuỳ chọn này giúp bạn không phải tự cấu hình biến môi trường tay.
3. Mở lại **PowerShell hoặc Command Prompt mới** (bắt buộc mở cửa sổ mới để nạp biến môi trường
   vừa cài) và kiểm tra:

```
java -version
javac -version
```

Nếu thấy hiện ra số phiên bản (ví dụ `openjdk version "21.0.x"`), cài đặt đã thành công.

**Nếu vẫn báo `'java' is not recognized`**: trình cài đặt chưa thêm JDK vào `PATH`. Cách sửa thủ
công: mở **Settings → System → About → Advanced system settings → Environment Variables**, thêm
biến `JAVA_HOME` trỏ tới thư mục cài JDK (ví dụ `C:\Program Files\Eclipse Adoptium\jdk-21.0.x-hotspot`),
sau đó thêm `%JAVA_HOME%\bin` vào biến `Path`. Mở cửa sổ terminal mới rồi kiểm tra lại.

### macOS

Cách đơn giản nhất là dùng [Homebrew](https://brew.sh):

```
brew install --cask temurin@21
java -version
```

Nếu chưa có Homebrew, tải file `.pkg` từ Adoptium và chạy trình cài đặt như ứng dụng macOS thông
thường.

### Linux (Debian/Ubuntu)

```
sudo apt update
sudo apt install temurin-21-jdk
java -version
```

Nếu package `temurin-21-jdk` không có sẵn trong repository mặc định, làm theo hướng dẫn thêm
repository của Adoptium tại trang tải, hoặc tải file `.tar.gz` và giải nén thủ công, thêm đường
dẫn vào `PATH`.

## `PATH` và `JAVA_HOME` là gì?

- **`PATH`** là danh sách thư mục mà hệ điều hành tìm chương trình thực thi khi bạn gõ lệnh trong
  terminal. Khi bạn gõ `java`, hệ điều hành tìm file thực thi tên `java` trong từng thư mục liệt kê
  ở `PATH`. Nếu thư mục `bin` của JDK không nằm trong `PATH`, hệ thống báo "not recognized"/"command
  not found" dù JDK vẫn cài đúng trên máy.
- **`JAVA_HOME`** là một biến môi trường quy ước trỏ tới thư mục gốc cài JDK. Bản thân hệ điều hành
  không bắt buộc phải có biến này để chạy `java`, nhưng rất nhiều công cụ (Maven, Gradle, IDE) dựa
  vào `JAVA_HOME` để biết JDK nằm ở đâu — nên nên cấu hình đúng ngay từ đầu.

## Chọn IDE

IDE (Integrated Development Environment) là công cụ giúp viết code có gợi ý, kiểm tra lỗi ngay khi
gõ, chạy/debug bằng một nút bấm — thay vì phải gõ `javac`/`java` tay liên tục. Hai lựa chọn phổ
biến nhất:

| | IntelliJ IDEA Community | VS Code + Extension Pack for Java |
|---|---|---|
| Giá | Miễn phí | Miễn phí |
| Thế mạnh | Gợi ý code, refactor, debug mạnh nhất cho Java — chuẩn công nghiệp | Nhẹ, khởi động nhanh, dùng chung được cho nhiều ngôn ngữ khác |
| Phù hợp | Người tập trung học Java nghiêm túc, dự án lớn dần về sau | Người đã quen VS Code, muốn một công cụ dùng cho nhiều việc |

Sách này minh hoạ bằng **IntelliJ IDEA Community Edition** (miễn phí, tải tại
[jetbrains.com/idea](https://www.jetbrains.com/idea/download)) vì đây là công cụ phổ biến nhất
trong môi trường làm việc Java thực tế — nhưng mọi ví dụ trong sách đều chạy được bằng dòng lệnh
thuần (`javac`/`java`) nếu bạn chọn dùng VS Code hoặc trình soạn thảo khác.

Cài đặt IntelliJ IDEA Community: tải trình cài đặt cho hệ điều hành của bạn, chạy theo mặc định.
Không cần cấu hình gì thêm — IntelliJ sẽ tự phát hiện JDK đã cài ở bước trên khi bạn tạo project
mới (Chương 4 sẽ hướng dẫn tạo project đầu tiên trong IDE).

## Bài tập

1. Cài JDK 21 theo hướng dẫn ở trên, chạy `java -version` và `javac -version`, chụp lại kết quả.
2. Tìm trên máy bạn thư mục JDK vừa cài nằm ở đâu (đường dẫn cụ thể), xác nhận đó chính là giá trị
   nên gán cho `JAVA_HOME`.
3. Cài IntelliJ IDEA Community (hoặc VS Code + Extension Pack for Java nếu bạn thích công cụ nhẹ
   hơn).

## Lỗi thường gặp

- **`'java' is not recognized as an internal or external command` (Windows)** — JDK chưa được thêm
  vào `PATH`. Xem lại phần "Nếu vẫn báo..." ở trên.
- **Cài nhiều bản JDK cùng lúc, `java -version` báo bản cũ** — `PATH` đang trỏ tới bản JDK cũ trước
  bản mới. Kiểm tra thứ tự các đường dẫn trong biến `PATH`, đường dẫn nào đứng trước sẽ được ưu
  tiên tìm thấy trước.
- **macOS báo "java" không mở được vì không rõ nhà phát triển** — vào **System Settings → Privacy &
  Security**, cuộn xuống phần cảnh báo và chọn "Allow Anyway", sau đó thử lại.
- **Cài JDK xong nhưng terminal cũ vẫn báo lỗi** — terminal đang mở **trước khi** cài đặt cập nhật
  biến môi trường, nó không tự nạp lại. Đóng và mở terminal mới.
