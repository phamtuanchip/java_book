// extends MOT abstract class, implements HAI interface cung luc - da ke thua interface
// la hop le trong Java, da ke thua CLASS thi khong
public class HinhTron extends HinhHoc implements CoTheVe, CoTheSoSanhDienTich {
    private double banKinh;

    public HinhTron(double banKinh) {
        super("Hinh tron");
        this.banKinh = banKinh;
    }

    @Override
    public double tinhDienTich() {
        return Math.PI * banKinh * banKinh;
    }

    @Override
    public double tinhChuVi() {
        return 2 * Math.PI * banKinh;
    }

    @Override
    public void ve() {
        System.out.println("O O   (mot hinh tron ban kinh " + banKinh + ")");
    }

    @Override
    public boolean dienTichLonHon(HinhHoc kia) {
        return this.tinhDienTich() > kia.tinhDienTich();
    }
}
