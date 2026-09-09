# Chương 46 — Kết nối cơ sở dữ liệu: JDBC cơ bản

## Mục tiêu học

Sau chương này, bạn sẽ:

- Kết nối được tới cơ sở dữ liệu quan hệ từ Java bằng JDBC.
- Thực thi câu lệnh SQL (tạo bảng, thêm, truy vấn dữ liệu) và đọc kết quả bằng `ResultSet`.
- Hiểu lỗ hổng **SQL injection** là gì, và dùng `PreparedStatement` để phòng tránh — đây là kỹ
  năng **bắt buộc phải nắm vững** trước khi viết bất kỳ code tương tác database thực tế nào.

Code mẫu đầy đủ: [`code/ch46-jdbc/`](../../code/ch46-jdbc/).

## JDBC là gì?

**JDBC** (Java Database Connectivity) là API chuẩn của Java để giao tiếp với cơ sở dữ liệu quan hệ
(MySQL, PostgreSQL, Oracle, H2...). Mỗi loại cơ sở dữ liệu có một **driver** riêng (một thư viện
`.jar` — nhắc lại khái niệm dependency ở Chương 35/42) cài đặt các interface chuẩn của JDBC, nên
code Java của bạn viết theo **cùng một API** dù đang giao tiếp với loại cơ sở dữ liệu nào — chỉ cần
đổi driver và connection URL, phần lớn code còn lại giữ nguyên.

Chương này dùng **H2** — một cơ sở dữ liệu **nhúng** (embedded), chạy trực tiếp trong bộ nhớ của
chương trình Java, không cần cài đặt/khởi động một server database riêng biệt như MySQL/PostgreSQL —
lý tưởng để học và thử nghiệm nhanh mà không cần thiết lập môi trường phức tạp.

## Kết nối cơ sở dữ liệu

```java
String url = "jdbc:h2:mem:testdb";
try (Connection conn = DriverManager.getConnection(url, "sa", "")) {
    // ...
}
```

`DriverManager.getConnection(url, user, password)` mở một kết nối. `Connection` là
`AutoCloseable` (Chương 21) — luôn dùng `try-with-resources` để đảm bảo kết nối được đóng đúng
cách, tránh rò rỉ tài nguyên (một vấn đề nghiêm trọng nếu chương trình mở nhiều kết nối mà không
đóng, dần dần làm cạn kiệt tài nguyên hệ thống/database).

## Thực thi SQL và đọc kết quả với `Statement`

```java
try (Statement stmt = conn.createStatement()) {
    stmt.execute("CREATE TABLE sinh_vien (id INT PRIMARY KEY, ten VARCHAR(100), tuoi INT)");
    stmt.execute("INSERT INTO sinh_vien VALUES (1, 'Nguyen Van A', 20)");

    try (ResultSet rs = stmt.executeQuery("SELECT id, ten, tuoi FROM sinh_vien")) {
        while (rs.next()) {
            int id = rs.getInt("id");
            String ten = rs.getString("ten");
            System.out.println(id + " - " + ten);
        }
    }
}
```

- `execute(...)` — chạy câu lệnh không cần lấy kết quả dạng bảng (tạo bảng, thêm/sửa/xoá dữ liệu).
- `executeQuery(...)` — chạy câu `SELECT`, trả về `ResultSet` — đại diện cho **kết quả dạng bảng**.
- `rs.next()` — di chuyển "con trỏ" tới **dòng tiếp theo** của kết quả, trả về `false` khi hết dữ
  liệu (mẫu quen thuộc, tương tự `readLine()` ở Chương 34). `rs.getInt("id")`/`rs.getString("ten")`
  đọc giá trị của **cột** tương ứng trong dòng hiện tại.

## SQL injection — lỗ hổng nghiêm trọng cần tránh

```java
// SAI - RUI RO SQL INJECTION
String tenNguoiDung = layTuNguoiDungNhap(); // vi du: "admin' OR '1'='1"
String sql = "SELECT * FROM nguoi_dung WHERE ten = '" + tenNguoiDung + "'";
```

Nếu `tenNguoiDung` là dữ liệu do **người dùng nhập** (không đáng tin cậy), và bạn **nối chuỗi trực
tiếp** vào câu SQL, kẻ tấn công có thể nhập một giá trị được **thiết kế đặc biệt** để thay đổi hoàn
toàn ý nghĩa câu lệnh SQL. Với ví dụ trên, nhập `admin' OR '1'='1` biến câu lệnh thành:

```sql
SELECT * FROM nguoi_dung WHERE ten = 'admin' OR '1'='1'
```

`'1'='1'` **luôn đúng**, khiến điều kiện `WHERE` trở nên vô nghĩa — câu lệnh trả về **toàn bộ**
bảng `nguoi_dung`, thay vì đúng một dòng như dự định (trong tình huống thực tế nghiêm trọng hơn,
kỹ thuật này có thể dùng để bỏ qua xác thực đăng nhập, đọc trộm dữ liệu, hoặc thậm chí xoá/sửa dữ
liệu tuỳ ý). Đây gọi là **SQL injection** — một trong những lỗ hổng bảo mật web/ứng dụng phổ biến
và nguy hiểm nhất trong lịch sử lập trình.

## `PreparedStatement` — cách phòng tránh đúng cách

```java
String sql = "SELECT * FROM nguoi_dung WHERE ten = ?";
try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setString(1, tenNguoiDung); // gia tri duoc truyen RIENG, KHONG noi vao chuoi SQL
    try (ResultSet rs = pstmt.executeQuery()) {
        // ...
    }
}
```

Dấu `?` là **tham số giữ chỗ** (placeholder) — giá trị thật được truyền **riêng biệt** qua
`setString(vi_tri, gia_tri)` (tương tự `setInt`, `setDouble`... tuỳ kiểu dữ liệu), **không bao giờ**
được database hiểu là một phần **cấu trúc** câu lệnh SQL, dù giá trị đó chứa ký tự đặc biệt gì đi
nữa (`'`, `;`, `--`...). Database driver tự đảm bảo tách biệt hoàn toàn giữa "cấu trúc câu lệnh" và
"dữ liệu", loại bỏ hoàn toàn khả năng SQL injection theo cách trên.

**Quy tắc bắt buộc, không có ngoại lệ**: **luôn** dùng `PreparedStatement` với tham số `?` cho bất
kỳ giá trị nào **có nguồn gốc từ bên ngoài** (người dùng nhập, dữ liệu từ file/API...) khi xây dựng
câu lệnh SQL. Không bao giờ nối chuỗi trực tiếp giá trị không đáng tin cậy vào câu SQL, dù chỉ để
"viết nhanh cho tiện" trong lúc học hay thử nghiệm — đây là thói quen cần hình thành **ngay từ
đầu**, không để dành "sửa sau" trong dự án thực tế.

`PreparedStatement` còn xử lý đúng các ký tự đặc biệt trong dữ liệu (như dấu nháy đơn trong tên
`"O'Brien"`, xem `PreparedStatementDemo.java`) mà không cần bạn tự "escape" thủ công — nối chuỗi
thủ công với dữ liệu chứa dấu nháy đơn thường gây **lỗi cú pháp SQL**, ngoài rủi ro bảo mật.

## Bài tập

1. Mở rộng `JdbcBasicsDemo.java` thêm câu lệnh `UPDATE` (sửa tuổi một sinh viên) và `DELETE` (xoá
   một sinh viên), dùng `PreparedStatement` cho cả hai.
2. Viết phương thức `List<String> timSinhVienTheoTen(Connection conn, String tuKhoa)` dùng
   `PreparedStatement` với `LIKE ?` (tìm kiếm gần đúng theo tên), truyền `"%" + tuKhoa + "%"` làm
   tham số.
3. Thử tự chạy lại thí nghiệm SQL injection trong `PreparedStatementDemo.java` với vài giá trị đầu
   vào "độc hại" khác (tự nghĩ thêm), xác nhận `PreparedStatement` luôn trả về đúng kết quả mong
   đợi (0 hoặc đúng số bản ghi khớp thật sự).

## Lỗi thường gặp

- **Nối chuỗi trực tiếp dữ liệu người dùng vào câu SQL** — lỗ hổng SQL injection, lỗi nghiêm trọng
  nhất có thể mắc phải trong chương này. Luôn dùng `PreparedStatement`.
- **Quên đóng `Connection`/`Statement`/`ResultSet`** — rò rỉ tài nguyên, dần dần làm cạn kiệt số kết
  nối khả dụng của database. Luôn dùng `try-with-resources` (Chương 21).
- **`SQLException: No suitable driver found`** — thiếu driver JDBC tương ứng trong classpath (quên
  `-cp` trỏ tới file `.jar` của H2, hoặc chưa khai báo dependency đúng trong Maven/Gradle).
- **Đánh chỉ số tham số `?` sai vị trí trong `PreparedStatement`** (`setString`/`setInt` với chỉ số
  sai) — chỉ số bắt đầu từ **1**, không phải `0` (khác với chỉ số mảng/`List` đã quen từ Chương 9,
  24) — gán sai vị trí dẫn tới lỗi hoặc dữ liệu bị gán nhầm cột.
- **Không đóng `ResultSet` trước khi chạy truy vấn khác trên cùng `Statement`** — một số driver
  database yêu cầu đóng `ResultSet` hiện tại trước khi tái sử dụng cùng `Statement` cho câu lệnh
  khác; dùng `try-with-resources` lồng nhau đúng cách (như trong code mẫu) tránh được vấn đề này.
