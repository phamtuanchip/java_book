public class SoLuongKhongDuException extends Exception {
    public SoLuongKhongDuException(String maSanPham, int hienCo, int muonXuat) {
        super("San pham " + maSanPham + " chi con " + hienCo + ", khong du de xuat " + muonXuat);
    }
}
