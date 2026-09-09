public class NhanVien {
    protected String ten;
    protected double luongCoBan;

    public NhanVien(String ten, double luongCoBan) {
        this.ten = ten;
        this.luongCoBan = luongCoBan;
    }

    public String getTen() {
        return ten;
    }

    // Phuong thuc nay se duoc cac lop con GHI DE (override) o Chuong 17.
    // O day, hanh vi mac dinh: luong = luong co ban, khong co gi them.
    public double tinhLuong() {
        return luongCoBan;
    }

    public void inThongTin() {
        System.out.println(ten + " (" + getClass().getSimpleName() + "): luong = " + tinhLuong());
    }
}
