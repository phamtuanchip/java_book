import java.util.Objects;

// Ap dung Chuong 13-14 (class, encapsulation) va Chuong 20 (equals/hashCode/toString)
public class SanPham {
    private String maSanPham;
    private String ten;
    private int soLuong;
    private double gia;

    public SanPham(String maSanPham, String ten, int soLuong, double gia) {
        this.maSanPham = maSanPham;
        this.ten = ten;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public String getTen() {
        return ten;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void datSoLuong(int soLuongMoi) {
        this.soLuong = soLuongMoi;
    }

    public double getGia() {
        return gia;
    }

    public void datGia(double giaMoi) {
        this.gia = giaMoi;
    }

    public double tinhTongGiaTri() {
        return soLuong * gia;
    }

    @Override
    public String toString() {
        return String.format("%-8s %-20s SL:%-6d Gia:%,-12.0f Tong:%,.0f",
            maSanPham, ten, soLuong, gia, tinhTongGiaTri());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SanPham)) return false;
        SanPham sp = (SanPham) o;
        return Objects.equals(maSanPham, sp.maSanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSanPham);
    }

    // Chuyen doi qua lai voi dinh dang CSV (Chuong 35) - dung de luu/doc file
    public String toCsvLine() {
        return maSanPham + "," + ten + "," + soLuong + "," + gia;
    }

    public static SanPham tuCsvLine(String dong) {
        String[] cot = dong.split(",");
        return new SanPham(cot[0], cot[1], Integer.parseInt(cot[2]), Double.parseDouble(cot[3]));
    }
}
