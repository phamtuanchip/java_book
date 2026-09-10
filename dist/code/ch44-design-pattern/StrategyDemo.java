public class StrategyDemo {
    // Strategy: dinh nghia MOT HO cac thuat toan/quy tac co the HOAN DOI cho nhau
    // TAI THOI DIEM CHAY, thay vi viet if/else/switch dai dang de chon quy tac
    interface ChienLuocGiamGia {
        double apDung(double giaGoc);
    }

    static class KhongGiamGia implements ChienLuocGiamGia {
        @Override
        public double apDung(double giaGoc) {
            return giaGoc;
        }
    }

    static class GiamGiaPhanTram implements ChienLuocGiamGia {
        private double phanTram;

        GiamGiaPhanTram(double phanTram) {
            this.phanTram = phanTram;
        }

        @Override
        public double apDung(double giaGoc) {
            return giaGoc * (1 - phanTram / 100);
        }
    }

    static class GiamGiaCoDinh implements ChienLuocGiamGia {
        private double soTienGiam;

        GiamGiaCoDinh(double soTienGiam) {
            this.soTienGiam = soTienGiam;
        }

        @Override
        public double apDung(double giaGoc) {
            return Math.max(0, giaGoc - soTienGiam);
        }
    }

    static class DonHang {
        private double giaGoc;
        private ChienLuocGiamGia chienLuoc; // co the DOI chien luoc BAT KY LUC NAO

        DonHang(double giaGoc, ChienLuocGiamGia chienLuoc) {
            this.giaGoc = giaGoc;
            this.chienLuoc = chienLuoc;
        }

        void datChienLuoc(ChienLuocGiamGia chienLuocMoi) {
            this.chienLuoc = chienLuocMoi;
        }

        double tinhGiaCuoiCung() {
            return chienLuoc.apDung(giaGoc);
        }
    }

    public static void main(String[] args) {
        DonHang donHang = new DonHang(500_000, new KhongGiamGia());
        System.out.println("Khong giam gia: " + donHang.tinhGiaCuoiCung());

        donHang.datChienLuoc(new GiamGiaPhanTram(10));
        System.out.println("Giam 10%: " + donHang.tinhGiaCuoiCung());

        donHang.datChienLuoc(new GiamGiaCoDinh(50_000));
        System.out.println("Giam 50.000 co dinh: " + donHang.tinhGiaCuoiCung());

        System.out.println("-- Dung lambda (Chuong 30) thay vi tao class rieng cho chien luoc don gian --");
        donHang.datChienLuoc(gia -> gia * 0.5); // "giam gia soc" 50%, viet truc tiep bang lambda
        System.out.println("Giam soc 50% (lambda): " + donHang.tinhGiaCuoiCung());
    }
}
