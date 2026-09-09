public class SanPham {
    private String ten;
    private String loai;
    private double gia;

    public SanPham(String ten, String loai, double gia) {
        this.ten = ten;
        this.loai = loai;
        this.gia = gia;
    }

    public String getTen() {
        return ten;
    }

    public String getLoai() {
        return loai;
    }

    public double getGia() {
        return gia;
    }

    @Override
    public String toString() {
        return ten + "(" + loai + ", " + gia + ")";
    }
}
