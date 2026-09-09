# Chương 12 — Vì sao cần OOP

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu lối viết code **thủ tục** (procedural) mà bạn đã dùng từ Chương 3 đến 11 gặp vấn đề gì khi
  chương trình lớn dần.
- Hiểu ý tưởng cốt lõi của lập trình **hướng đối tượng** (OOP): gộp dữ liệu và hành vi thao tác trên
  dữ liệu đó vào cùng một đơn vị.
- Biết tên và ý nghĩa sơ lược của 4 trụ cột OOP — sẽ học chi tiết từng cái trong các chương tiếp
  theo (Chương 13-20), chương này chỉ xây dựng bức tranh tổng quan.

Chương này **không có code mẫu riêng** — đây là chương chuyển tiếp tư duy, chuẩn bị cho Chương 13
bắt đầu viết class đầu tiên.

## Lối viết code bạn đã quen: thủ tục (procedural)

Từ Chương 3 đến giờ, mọi chương trình bạn viết đều có dạng: dữ liệu nằm trong các biến rời rạc, và
các phương thức `static` thao tác lên những biến đó. Ví dụ hãy tưởng tượng bạn cần quản lý thông
tin một sinh viên — theo lối viết đã học:

```java
String tenSinhVien = "Nguyen Van A";
int[] diemCacMon = {8, 7, 9};

// Muon tinh diem trung binh, viet mot phuong thuc static rieng
static double tinhDiemTrungBinh(int[] diem) {
    int tong = 0;
    for (int d : diem) {
        tong += d;
    }
    return (double) tong / diem.length;
}
```

Cách này **hoạt động tốt** với chương trình nhỏ. Nhưng thử hình dung bạn cần quản lý **100 sinh
viên**, mỗi người có tên, danh sách điểm, ngày sinh, lớp học... Vấn đề bắt đầu xuất hiện:

- Bạn phải tạo **rất nhiều mảng song song** (`String[] ten`, `int[][] diem`, `String[] lop`...) và
  tự đảm bảo chỉ số `i` ở mọi mảng luôn khớp đúng cùng một sinh viên — chỉ cần một chỗ lệch chỉ số
  là dữ liệu sai lệch hoàn toàn mà không có cách nào trình biên dịch phát hiện giúp bạn.
- Dữ liệu (`tenSinhVien`, `diemCacMon`) và hành vi thao tác lên nó (`tinhDiemTrungBinh`) nằm **tách
  rời nhau** — không có gì trong code nói rõ "hàm này thuộc về, và chỉ nên dùng với, dữ liệu sinh
  viên". Bất kỳ đoạn code nào ở bất kỳ đâu cũng có thể sửa trực tiếp `diemCacMon` mà không qua kiểm
  soát nào, kể cả gán giá trị vô lý (điểm âm, điểm > 10).

## Ý tưởng cốt lõi của OOP: gộp dữ liệu + hành vi vào một đơn vị

Lập trình hướng đối tượng giải quyết đúng vấn đề trên bằng cách tạo ra một **khuôn mẫu** (gọi là
**class**, học chi tiết ở Chương 13) mô tả một sinh viên gồm **cả dữ liệu lẫn các thao tác hợp lệ
trên dữ liệu đó**, gộp chung một chỗ:

```java
class SinhVien {
    String ten;
    int[] diem;

    double tinhDiemTrungBinh() {
        int tong = 0;
        for (int d : diem) {
            tong += d;
        }
        return (double) tong / diem.length;
    }
}
```

Từ khuôn mẫu (class) đó, bạn tạo ra nhiều **đối tượng** (object) — mỗi object là một sinh viên cụ
thể, tự mang theo dữ liệu riêng của nó:

```java
SinhVien sv1 = new SinhVien(); // sinh vien thu nhat
SinhVien sv2 = new SinhVien(); // sinh vien thu hai, HOAN TOAN doc lap voi sv1
sv1.ten = "Nguyen Van A";
sv2.ten = "Tran Thi B";
System.out.println(sv1.tinhDiemTrungBinh()); // moi object tu goi phuong thuc voi DU LIEU CUA CHINH NO
```

Không còn mảng song song, không còn nguy cơ lệch chỉ số — mỗi `SinhVien` tự mang theo đúng dữ liệu
của nó, và phương thức `tinhDiemTrungBinh()` gắn chặt với đúng dữ liệu đó. Đây chính xác là nội
dung Chương 13 sẽ hướng dẫn viết chi tiết, cú pháp đầy đủ.

## Bốn trụ cột của OOP (tổng quan)

Toàn bộ Phần 2 và Phần 3 của sách xoay quanh 4 ý tưởng nền tảng này. Ở đây chỉ giới thiệu tên và ý
nghĩa sơ lược — mỗi cái sẽ có một hoặc nhiều chương riêng đi sâu:

1. **Encapsulation (đóng gói)** — Chương 14. Giấu chi tiết dữ liệu bên trong object, chỉ cho phép
   truy cập/thay đổi thông qua các "cổng" được kiểm soát (phương thức) — ví dụ ngăn không cho gán
   điểm số âm cho sinh viên, thay vì để bất kỳ đoạn code nào cũng gán trực tiếp giá trị bất kỳ.
2. **Inheritance (kế thừa)** — Chương 16. Cho phép một class **tái sử dụng** dữ liệu và hành vi của
   một class khác, rồi mở rộng hoặc điều chỉnh thêm — ví dụ `SinhVienDaiHoc` và `SinhVienCaoHoc` có
   thể cùng kế thừa từ một `SinhVien` chung, tránh viết lặp lại phần giống nhau.
3. **Polymorphism (đa hình)** — Chương 17. Cho phép xử lý nhiều loại object **khác nhau** thông qua
   **cùng một cách gọi**, mỗi loại tự "biết" cách phản ứng đúng theo bản chất riêng của nó — ví dụ
   một danh sách chứa cả `SinhVienDaiHoc` và `SinhVienCaoHoc`, gọi cùng một phương thức
   `tinhHocPhi()` trên từng phần tử, nhưng mỗi loại tự tính ra kết quả khác nhau theo đúng quy tắc
   của loại đó.
4. **Abstraction (trừu tượng hoá)** — Chương 18. Định nghĩa **"cái gì cần làm được"** mà không quy
   định chi tiết **"làm như thế nào"**, cho phép nhiều class khác nhau cùng tuân theo một "hợp
   đồng" chung — ví dụ định nghĩa rằng "mọi hình dạng đều tính được diện tích", mà không quan tâm
   hình tròn hay hình vuông tính diện tích bằng công thức gì.

Bốn ý tưởng này **không tách rời nhau** — chúng phối hợp với nhau trong hầu hết code OOP thực tế.
Đừng cố học thuộc lòng định nghĩa ở giai đoạn này; hãy đọc tiếp các chương sau, viết code thực tế,
rồi quay lại đọc lại đoạn tổng quan này — lúc đó các khái niệm sẽ trở nên rõ ràng và cụ thể hơn
nhiều so với đọc định nghĩa suông.

## OOP không phải "cách làm đúng duy nhất"

Một lưu ý quan trọng để tránh hiểu sai: OOP là **một công cụ tư duy**, không phải chân lý tuyệt đối.
Có những bài toán/ngôn ngữ phù hợp hơn với lối viết khác (lập trình hàm — functional programming,
sẽ chạm tới ở Phần 6 khi học lambda/stream). Java **buộc** bạn viết theo OOP ở mức cấu trúc cơ bản
(mọi thứ đều nằm trong class), nên việc hiểu OOP vững chắc là điều kiện bắt buộc để viết Java hiệu
quả — đó là lý do phần lớn số chương còn lại của sách xoay quanh chủ đề này.

## Bài tập

1. Viết lại (chỉ trên giấy hoặc mô tả bằng lời, chưa cần chạy được) ý tưởng một class `SanPham` có
   dữ liệu gì (tên, giá, số lượng tồn kho...) và những hành vi/phương thức nào hợp lý nên gắn liền
   với nó (tính tổng giá trị tồn kho, giảm giá...).
2. Tự giải thích lại bằng lời của bạn: vì sao gộp dữ liệu và hành vi vào cùng một đơn vị (class)
   lại giảm được nguy cơ lỗi so với dùng nhiều mảng song song?
3. Đọc lại đoạn "Bốn trụ cột của OOP" — ghi chú lại câu hỏi nào bạn còn mơ hồ, giữ lại để đối chiếu
   sau khi học xong Chương 13-18.
