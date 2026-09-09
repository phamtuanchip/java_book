# Chương 14 — Encapsulation (đóng gói)

## Mục tiêu học

Sau chương này, bạn sẽ:

- Hiểu 4 mức truy cập (access modifier) của Java: `public`, `private`, `protected`,
  package-private, và dùng đúng chúng cho field/phương thức.
- Viết được getter/setter, hiểu vì sao setter nên kiểm tra tính hợp lệ của dữ liệu.
- Thiết kế class theo nguyên tắc "chỉ mở đúng cổng cần thiết", thay vì để mọi field public.
- Biết khi nào nên **không** cung cấp setter, chỉ cung cấp các phương thức thao tác có kiểm soát.

Code mẫu đầy đủ: [`code/ch14-encapsulation/`](../../code/ch14-encapsulation/).

## Vấn đề với field `public`

Ở Chương 13, `SinhVien.ten` và `SinhVien.diem` không có access modifier riêng (mặc định là
package-private, xem phần dưới) — trong ví dụ `Main.java` của chương đó, bạn từng gán trực tiếp
`sv3.diem = new int[]{6, 7};`. Điều này **hoạt động được**, nhưng ẩn chứa rủi ro: **không có gì
ngăn code ở đâu đó gán giá trị vô lý**, ví dụ:

```java
sv3.diem = new int[]{-5, 15, 999}; // diem am, diem > 10 - HOAN TOAN hop le ve mat cu phap!
```

Không có lỗi biên dịch, không có cảnh báo — chương trình chạy "bình thường" với dữ liệu vô nghĩa,
lỗi chỉ lộ ra khi bạn tính điểm trung bình ra một con số kỳ quặc, và lúc đó rất khó truy ngược lại
xem chỗ nào đã gán sai. Đây chính là vấn đề **encapsulation** giải quyết.

## Bốn mức truy cập của Java

| Modifier | Trong cùng class | Trong cùng package | Ở class con (khác package) | Ở mọi nơi khác |
|---|---|---|---|---|
| `private` | ✅ | ❌ | ❌ | ❌ |
| *(không ghi gì — package-private)* | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

Khái niệm "package" sẽ học kỹ ở Chương 15, "class con"/kế thừa sẽ học ở Chương 16 — ở chương này,
tập trung vào hai mức quan trọng nhất khi mới bắt đầu: **`private`** (giấu hoàn toàn, chỉ dùng được
bên trong class) và **`public`** (mở hoàn toàn, dùng được từ mọi nơi).

**Nguyên tắc thiết kế mặc định**: field nên là `private`. Chỉ mở `public` cho phương thức nào bạn
**chủ động muốn** cho phép gọi từ bên ngoài.

## Getter và Setter

Khi field là `private`, code bên ngoài class không đọc/ghi trực tiếp được nữa. Muốn cho phép đọc,
cung cấp **getter**; muốn cho phép ghi (có kiểm soát), cung cấp **setter**:

```java
public class NguoiDung {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            System.out.println("Canh bao: email khong hop le, giu nguyen gia tri cu");
            return;
        }
        this.email = email;
    }
}
```

Điểm mấu chốt: **setter không chỉ đơn thuần gán giá trị** — nó là nơi đặt các quy tắc kiểm tra tính
hợp lệ. Mọi chỗ trong chương trình muốn đổi `email` của một `NguoiDung` đều **buộc phải đi qua**
`setEmail`, nghĩa là **buộc phải đi qua** kiểm tra hợp lệ đó — không có đường tắt nào khác để gán
trực tiếp một giá trị bậy vào field, vì field đã là `private`.

Quy ước đặt tên: `get<TenField>()` cho getter, `set<TenField>(...)` cho setter (viết hoa chữ đầu
tên field). Với `boolean`, quy ước thường dùng `is<TenField>()` thay vì `get` (ví dụ
`isHopLe()`) — sẽ gặp lại quy ước này trong thư viện chuẩn Java.

## Khi nào KHÔNG nên cung cấp setter

Không phải mọi field đều cần setter. Xem lại `TaiKhoanNganHang.java` trong code mẫu:

```java
public class TaiKhoanNganHang {
    private double soDu;

    public double getSoDu() {
        return soDu;
    }

    public void napTien(double soTien) {
        if (soTien <= 0) { /* tu choi */ return; }
        soDu += soTien;
    }

    public void rutTien(double soTien) {
        if (soTien <= 0 || soTien > soDu) { /* tu choi */ return; }
        soDu -= soTien;
    }

    // CO CHU DICH: khong co setSoDu(double)
}
```

Nếu class này có `setSoDu(double)`, bất kỳ đoạn code nào cũng có thể gán thẳng một số dư bất kỳ,
bỏ qua hoàn toàn logic nghiệp vụ (không được âm, thay đổi phải thông qua giao dịch nạp/rút cụ thể).
Bằng cách **chỉ cung cấp** `napTien`/`rutTien` — hai phương thức mô tả đúng những **hành động hợp lệ
trong thực tế** — class tự đảm bảo số dư luôn ở trạng thái hợp lý, không cần "hy vọng" người gọi
code cẩn thận.

**Nguyên tắc thiết kế**: đặt câu hỏi "trong thực tế, dữ liệu này được phép thay đổi theo những cách
nào?" — rồi chỉ cung cấp đúng những phương thức phản ánh các cách đó, thay vì mặc định thêm getter/
setter cho mọi field.

## Bài tập

1. Refactor lại class `SanPham` bạn viết ở bài tập Chương 13: chuyển field sang `private`, thêm
   getter cho `ten`/`gia`/`soLuong`, thêm setter cho `gia` với điều kiện không cho gán giá trị âm.
2. Thêm phương thức `nhapKho(int soLuongThem)` và `xuatKho(int soLuongXuat)` vào `SanPham`, đảm bảo
   `xuatKho` từ chối nếu số lượng xuất vượt quá tồn kho hiện có — theo đúng tinh thần của
   `TaiKhoanNganHang.napTien`/`rutTien`.
3. Giải thích bằng lời: vì sao dòng `tk.soDu = -999_999_999;` (đã comment sẵn trong `Main.java`)
   gây lỗi **biên dịch** chứ không phải lỗi lúc chạy? Điều này khác gì so với việc chỉ đơn giản
   "hy vọng" không ai viết dòng code đó?

## Lỗi thường gặp

- **Thêm getter/setter cho mọi field theo phản xạ, kể cả khi không cần** — nếu một field không bao
  giờ cần đọc hoặc sửa từ bên ngoài, đừng thêm getter/setter cho nó. Encapsulation không chỉ là
  "field private + getter/setter", mà là **suy nghĩ có chủ đích** field nào thực sự cần lộ ra.
- **Setter chỉ gán giá trị, không kiểm tra gì** — làm mất hết ý nghĩa của việc dùng `private` +
  setter, tương đương với field `public` nhưng dài dòng hơn. Luôn cân nhắc điều kiện hợp lệ nào cần
  kiểm tra.
- **Getter trả về trực tiếp một mảng/object `private` mà không sao chép** — người gọi `getDiem()`
  nhận về **chính tham chiếu** tới mảng nội bộ, có thể sửa nó từ bên ngoài dù field là `private`,
  vô hiệu hoá tác dụng bảo vệ (bài toán này gọi là "rò rỉ tham chiếu" — sẽ quay lại kỹ hơn khi học
  về bất biến hoá object nâng cao). Cách khắc phục cơ bản: trả về bản sao (`Arrays.copyOf(...)`) 
  thay vì trả thẳng tham chiếu nội bộ, khi field là kiểu tham chiếu có thể thay đổi được.
- **Nhầm lẫn `private` nghĩa là "không ai truy cập được kể cả để đọc"** — `private` chỉ giới hạn
  truy cập **trực tiếp từ bên ngoài class**; bên trong chính class đó (mọi phương thức, kể cả
  constructor), field `private` truy cập bình thường không hạn chế gì.
