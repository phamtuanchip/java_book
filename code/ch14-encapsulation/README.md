# Chương 14 — Code mẫu: Encapsulation

```
javac *.java
java Main
```

Hai class minh hoạ hai phong cách đóng gói phổ biến:

- `TaiKhoanNganHang.java` — **không có setter**, mọi thay đổi số dư phải đi qua `napTien`/`rutTien`,
  luôn kiểm tra hợp lệ (không cho rút quá số dư, không cho nạp số âm). Đây là phong cách "chỉ mở
  đúng những cổng thao tác hợp lý", không cho phép gán tuỳ ý.
- `NguoiDung.java` — dùng cặp getter/setter cổ điển, nhưng **setter luôn kiểm tra hợp lệ** trước
  khi gán, từ chối và giữ nguyên giá trị cũ nếu dữ liệu mới không hợp lệ.

`Main.java` thử cả các trường hợp hợp lệ lẫn không hợp lệ để thấy rõ field được bảo vệ ra sao. Dòng
bị comment `// tk.soDu = -999_999_999;` minh hoạ: nếu bỏ comment, trình biên dịch báo lỗi ngay vì
`soDu` là `private` — đây chính là tác dụng của đóng gói, lỗi bị chặn **lúc biên dịch**, không phải
đợi tới lúc chạy mới phát hiện.
