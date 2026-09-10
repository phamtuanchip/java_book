public class OcpDemo {
    // TRUOC (VI PHAM OCP): moi khi them MOT LOAI HINH MOI, BAT BUOC phai SUA HAM
    // NAY (them 1 nhanh else if) - class "dong" voi thay doi thay vi "mo"
    static double tinhDienTichViPham(String loaiHinh, double a, double b) {
        if (loaiHinh.equals("hinh_chu_nhat")) {
            return a * b;
        } else if (loaiHinh.equals("hinh_tron")) {
            return Math.PI * a * a; // 'a' la ban kinh, 'b' khong dung toi
        }
        // them hinh tam giac? PHAI SUA ham nay them 1 nhanh else if nua...
        throw new IllegalArgumentException("Khong ho tro: " + loaiHinh);
    }

    // SAU (TUAN THU OCP): "MO" cho mo rong (them lop con moi), "DONG" cho sua doi
    // (khong can sua code da co) - dung dung y tuong Chuong 18 (abstract class)
    abstract static class Hinh {
        abstract double tinhDienTich();
    }

    static class HinhChuNhat extends Hinh {
        double a, b;
        HinhChuNhat(double a, double b) { this.a = a; this.b = b; }
        @Override double tinhDienTich() { return a * b; }
    }

    static class HinhTron extends Hinh {
        double banKinh;
        HinhTron(double banKinh) { this.banKinh = banKinh; }
        @Override double tinhDienTich() { return Math.PI * banKinh * banKinh; }
    }

    // THEM HINH MOI - KHONG can sua bat ky code CU NAO da co o tren
    static class HinhTamGiac extends Hinh {
        double day, chieuCao;
        HinhTamGiac(double day, double chieuCao) { this.day = day; this.chieuCao = chieuCao; }
        @Override double tinhDienTich() { return day * chieuCao / 2; }
    }

    static double tongDienTich(Hinh[] danhSach) { // ham nay KHONG BAO GIO can sua khi them hinh moi
        double tong = 0;
        for (Hinh h : danhSach) {
            tong += h.tinhDienTich();
        }
        return tong;
    }

    public static void main(String[] args) {
        System.out.println("-- Vi pham OCP --");
        System.out.println(tinhDienTichViPham("hinh_chu_nhat", 4, 6));

        System.out.println("-- Tuan thu OCP --");
        Hinh[] danhSachHinh = {
            new HinhChuNhat(4, 6),
            new HinhTron(3),
            new HinhTamGiac(5, 4) // hinh MOI, ham tongDienTich KHONG can sua
        };
        System.out.println("Tong dien tich: " + tongDienTich(danhSachHinh));
    }
}
