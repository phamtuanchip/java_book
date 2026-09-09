# Chương 35 — Xử lý JSON/CSV

## Mục tiêu học

Sau chương này, bạn sẽ:

- Đọc/ghi file CSV bằng các công cụ đã học (`String.split`, `StringBuilder`), không cần thư viện
  ngoài.
- Hiểu cấu trúc JSON, tự viết được một bộ sinh JSON đơn giản.
- Biết cách dùng thư viện Gson để đọc/ghi JSON đúng chuẩn, và hiểu vì sao quản lý thư viện ngoài
  bằng tay khá phiền phức — điều Chương 42 sẽ giải quyết triệt để.

Code mẫu đầy đủ: [`code/ch35-json-csv/`](../../code/ch35-json-csv/).

## CSV — định dạng đơn giản, không cần thư viện

CSV (Comma-Separated Values) là định dạng văn bản đơn giản: mỗi dòng là một bản ghi, các giá trị
cách nhau bởi dấu phẩy. Với dữ liệu đơn giản (không có dấu phẩy bên trong giá trị), bạn hoàn toàn tự
đọc/ghi được chỉ bằng kiến thức đã học:

```java
// Ghi
StringBuilder noiDung = new StringBuilder();
noiDung.append("ten,tuoi,diemTrungBinh\n");
for (SinhVien sv : danhSach) {
    noiDung.append(sv.ten()).append(",").append(sv.tuoi()).append(",").append(sv.diemTrungBinh()).append("\n");
}
Files.writeString(duongDan, noiDung.toString());

// Doc
List<String> cacDong = Files.readAllLines(duongDan);
for (int i = 1; i < cacDong.size(); i++) { // bo qua dong header
    String[] cot = cacDong.get(i).split(",");
    String ten = cot[0];
    int tuoi = Integer.parseInt(cot[1]);
    double diem = Double.parseDouble(cot[2]);
    // ...
}
```

Dòng đầu tiên (header) thường ghi tên các cột, và khi đọc lại bạn bắt đầu duyệt từ dòng **thứ hai**
(`i = 1`) để bỏ qua nó. Với dữ liệu thực tế phức tạp hơn (giá trị chứa dấu phẩy, xuống dòng bên
trong một trường...), cần thư viện chuyên dụng (ví dụ Apache Commons CSV) thay vì tự viết `split`
đơn giản — nhưng với phần lớn bài tập và dữ liệu đơn giản, cách trên là đủ dùng.

## JSON — cấu trúc và cách tự viết đơn giản

JSON (JavaScript Object Notation, dù tên có "JavaScript" nhưng là định dạng trung lập, dùng được
với mọi ngôn ngữ) là định dạng phổ biến nhất để trao đổi dữ liệu có cấu trúc giữa các hệ thống —
ứng dụng web, API, file cấu hình đều thường dùng JSON.

```json
{
  "ten": "Nguyen Van A",
  "tuoi": 20,
  "diemTrungBinh": 8.5
}
```

Cấu trúc cơ bản, dễ liên tưởng tới các kiểu dữ liệu Java đã học:

- **Object** — `{ "key": value, ... }` — giống `Map<String, ...>` (Chương 26).
- **Array** — `[ value, value, ... ]` — giống `List` (Chương 24).
- **Kiểu giá trị**: chuỗi (`"..."`), số, `true`/`false`, `null`, hoặc lồng thêm object/array khác.

Tự viết một bộ sinh JSON đơn giản (chỉ để hiểu cấu trúc, chưa xử lý đầy đủ các trường hợp đặc biệt
như ký tự cần escape trong chuỗi):

```java
static String toJson(SinhVien sv) {
    return "{"
        + "\"ten\":\"" + sv.ten() + "\","
        + "\"tuoi\":" + sv.tuoi() + ","
        + "\"diemTrungBinh\":" + sv.diemTrongBinh()
        + "}";
}
```

**Đây là cách để hiểu định dạng, không phải cách nên dùng trong dự án thật** — tự viết code nối
chuỗi thủ công như trên rất dễ sai (quên escape ký tự đặc biệt, dấu phẩy thừa/thiếu ở object rỗng...)
khi dữ liệu phức tạp hơn một chút. Dự án thực tế luôn dùng thư viện chuyên dụng.

## Dùng thư viện Gson

```java
Gson gson = new GsonBuilder().setPrettyPrinting().create();

String json = gson.toJson(sv);                        // Java object -> chuoi JSON
SinhVien svDaDoc = gson.fromJson(jsonDauVao, SinhVien.class); // chuoi JSON -> Java object
```

Gson (thư viện mã nguồn mở của Google) tự động **ánh xạ** giữa object Java và JSON dựa trên **tên
field** — bạn không cần tự viết code nối chuỗi hay parse thủ công như `SimpleJsonWriter` ở trên,
Gson xử lý đúng mọi trường hợp phức tạp (escape ký tự, object/array lồng nhau, kiểu dữ liệu khác
nhau...) mà bạn không cần tự lo.

## Vấn đề: làm sao "có" được thư viện Gson?

Khác với mọi thứ bạn dùng từ đầu sách (`String`, `ArrayList`, `Scanner`...) — những thứ này nằm
**sẵn trong JDK**, không cần cài đặt gì thêm — Gson là thư viện **của bên thứ ba**, không đi kèm
JDK. Muốn dùng nó, phải:

1. Tải file `.jar` chứa thư viện (từ trang chính thức, hoặc kho lưu trữ như Maven Central).
2. Thêm đường dẫn tới file `.jar` đó vào **classpath** khi biên dịch và chạy — tham số `-cp` của
   `javac`/`java` (xem chi tiết trong README code mẫu).

Với **một** thư viện, việc này còn quản lý được thủ công. Nhưng dự án thực tế thường dùng **hàng
chục** thư viện, mỗi thư viện lại phụ thuộc vào các thư viện khác nữa (gọi là "dependency của
dependency") — quản lý tất cả bằng tay gần như bất khả thi. Đây chính xác là vấn đề **Maven** và
**Gradle** (Chương 42) sinh ra để giải quyết: khai báo thư viện cần dùng trong một file cấu hình,
công cụ tự động tải về đúng phiên bản và xử lý toàn bộ classpath cho bạn.

## Bài tập

1. Mở rộng `CsvDemo.java` để ghi thêm một cột `xepLoai` (tính từ `diemTrungBinh`, dùng lại logic
   Chương 7), đọc lại và xác nhận dữ liệu đúng.
2. Mở rộng `SimpleJsonWriter.java` để sinh JSON cho một **danh sách** `SinhVien` (dạng JSON array
   `[ {...}, {...} ]`), dùng Stream (Chương 31) để nối các object JSON lại với nhau.
3. (Nếu đã tải được Gson) Thử `gson.toJson(...)` với một `List<SinhVien>`, quan sát Gson tự sinh ra
   đúng định dạng JSON array mà không cần bạn tự viết logic nối chuỗi nào.

## Lỗi thường gặp

- **Dùng `split(",")` với dữ liệu CSV có giá trị chứa dấu phẩy bên trong** (ví dụ địa chỉ
  `"123 Đường ABC, Quận 1"`) — bị tách sai thành nhiều cột hơn dự kiến. Với dữ liệu CSV thực tế
  phức tạp, cần thư viện CSV chuyên dụng thay vì `split` đơn giản.
- **Tự viết JSON bằng nối chuỗi cho dữ liệu có ký tự đặc biệt** (dấu `"`, xuống dòng, ký tự Unicode
  trong chuỗi) — sinh ra JSON không hợp lệ, không parse lại được đúng. Đây chính là lý do nên dùng
  thư viện thật (Gson) cho bất kỳ việc gì ngoài mục đích học tập.
- **`ClassNotFoundException: com.google.gson.Gson`** khi chạy `GsonExample.java` — quên thêm
  `-cp` trỏ tới file `.jar` đã tải, hoặc dùng sai dấu phân cách classpath (`;` trên Windows, `:` trên
  macOS/Linux).
- **`NumberFormatException`** khi `Integer.parseInt`/`Double.parseDouble` một cột CSV không đúng
  định dạng số (ví dụ ô trống, hoặc lẫn ký tự chữ) — luôn cân nhắc `try/catch` (Chương 21) khi dữ
  liệu đầu vào không đảm bảo sạch tuyệt đối.
