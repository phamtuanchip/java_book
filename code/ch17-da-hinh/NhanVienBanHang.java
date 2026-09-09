public class NhanVienBanHang extends NhanVien {
    private double doanhSo;
    private static final double TY_LE_HOA_HONG = 0.05;

    public NhanVienBanHang(String ten, double luongCoBan, double doanhSo) {
        super(ten, luongCoBan); // BAT BUOC goi constructor lop cha DAU TIEN trong constructor lop con
        this.doanhSo = doanhSo;
    }

    // Ghi de (override) tinhLuong: luong = luong co ban + hoa hong tren doanh so
    @Override
    public double tinhLuong() {
        return luongCoBan + doanhSo * TY_LE_HOA_HONG;
    }

    public void capNhatDoanhSo(double doanhSoMoi) {
        this.doanhSo = doanhSoMoi;
    }
}
