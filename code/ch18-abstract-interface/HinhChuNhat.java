public class HinhChuNhat extends HinhHoc implements CoTheVe, CoTheSoSanhDienTich {
    private double chieuDai;
    private double chieuRong;

    public HinhChuNhat(double chieuDai, double chieuRong) {
        super("Hinh chu nhat");
        this.chieuDai = chieuDai;
        this.chieuRong = chieuRong;
    }

    @Override
    public double tinhDienTich() {
        return chieuDai * chieuRong;
    }

    @Override
    public double tinhChuVi() {
        return 2 * (chieuDai + chieuRong);
    }

    @Override
    public void ve() {
        System.out.println("[ ]   (mot hinh chu nhat " + chieuDai + "x" + chieuRong + ")");
    }

    @Override
    public boolean dienTichLonHon(HinhHoc kia) {
        return this.tinhDienTich() > kia.tinhDienTich();
    }
}
