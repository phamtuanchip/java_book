# Chương 47 — Dự án tổng hợp: Quản lý kho hàng (console)

Ứng dụng console quản lý kho hàng, áp dụng lại toàn bộ kiến thức từ Phần 2 đến Phần 10.

## Chạy chương trình chính

```
javac *.java
java Main
```

Chương trình đọc dữ liệu từ `kho_hang.csv` nếu file tồn tại (từ lần chạy trước), hoặc tạo dữ liệu
mẫu ban đầu nếu chưa có. Khi thoát (chọn `0`), dữ liệu được lưu lại vào `kho_hang.csv` — thử chạy
lại chương trình để xác nhận dữ liệu được giữ nguyên giữa các lần chạy.

## Chạy test (cần `junit-platform-console-standalone`, xem Chương 41)

```
javac -cp junit-platform-console-standalone-1.10.2.jar *.java
java -jar junit-platform-console-standalone-1.10.2.jar -cp . --select-class KhoHangTest
```

## Kiến thức được áp dụng trong dự án

| File | Chương liên quan |
|---|---|
| `SanPham.java` | Class, encapsulation (13-14), `equals`/`hashCode`/`toString` (20) |
| `SanPhamKhongTonTaiException.java`, `SoLuongKhongDuException.java` | Custom checked exception (21) |
| `KhoHang.java` | `Map`/`LinkedHashMap` (26), Stream API (31), File I/O (34), CSV (35), SRP (45) |
| `Main.java` | `Scanner`, vòng lặp, `switch` expression (7), multi-catch (21) |
| `KhoHangTest.java` | Unit test JUnit 5 (41), thiết kế code dễ test |

## Gợi ý mở rộng (không bắt buộc)

- Thay lưu trữ CSV bằng JDBC + H2 (Chương 46) thay vì file.
- Thêm đa luồng (Chương 37-40): ví dụ tự động lưu định kỳ trên một thread riêng.
- Áp dụng thêm design pattern (Chương 44): Strategy cho các cách tính giảm giá khác nhau khi xuất
  kho số lượng lớn.
