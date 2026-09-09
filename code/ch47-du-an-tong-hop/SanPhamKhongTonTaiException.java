// Checked exception tu dinh nghia (Chuong 21) - loi nghiep vu, nguoi goi BAT BUOC
// phai xu ly
public class SanPhamKhongTonTaiException extends Exception {
    public SanPhamKhongTonTaiException(String maSanPham) {
        super("Khong tim thay san pham co ma: " + maSanPham);
    }
}
