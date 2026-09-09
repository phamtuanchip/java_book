public class NhanVienQuanLy extends NhanVien {
    private double phuCapQuanLy;

    public NhanVienQuanLy(String ten, double luongCoBan, double phuCapQuanLy) {
        super(ten, luongCoBan);
        this.phuCapQuanLy = phuCapQuanLy;
    }

    @Override
    public double tinhLuong() {
        // Goi lai phien ban CUA LOP CHA bang super.tinhLuong(), roi cong them
        // thay vi viet lai "luongCoBan" tu dau - tranh trung lap logic
        return super.tinhLuong() + phuCapQuanLy;
    }
}
