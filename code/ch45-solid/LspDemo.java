public class LspDemo {
    // TRUOC (VI PHAM LSP): HinhVuongViPham extends HinhChuNhatViPham, "ghi de" ca
    // hai setter de dam bao canh luon bang nhau - NHUNG dieu nay pha vo ky vong
    // hop ly cua nguoi dung HinhChuNhatViPham (doi chieu rong KHONG anh huong chieu
    // dai) - lop con thay doi HANH VI ma lop cha da "hua"
    static class HinhChuNhatViPham {
        protected double chieuDai;
        protected double chieuRong;

        void datChieuDai(double d) { this.chieuDai = d; }
        void datChieuRong(double r) { this.chieuRong = r; }
        double tinhDienTich() { return chieuDai * chieuRong; }
    }

    static class HinhVuongViPham extends HinhChuNhatViPham {
        @Override
        void datChieuDai(double d) {
            this.chieuDai = d;
            this.chieuRong = d; // BAT NGO thay doi ca chieuRong!
        }

        @Override
        void datChieuRong(double r) {
            this.chieuRong = r;
            this.chieuDai = r; // BAT NGO thay doi ca chieuDai!
        }
    }

    // Ham nay viet cho HinhChuNhatViPham, ky vong HOP LY: doi rieng chieu dai
    // KHONG anh huong chieu rong
    static void kiemTraDienTich(HinhChuNhatViPham hcn) {
        hcn.datChieuRong(4);
        hcn.datChieuDai(5);
        // Voi HinhChuNhatViPham THAT: dien tich mong doi la 4 * 5 = 20
        System.out.println("Dien tich: " + hcn.tinhDienTich() + " (mong doi 20 neu la hinh chu nhat that)");
    }

    // SAU (TUAN THU LSP): KHONG ep HinhVuong "gia lam" HinhChuNhat - ca hai deu la
    // Hinh doc lap, khong co quan he ke thua GAY HIEU LAM ve hanh vi
    interface HinhDung {
        double tinhDienTich();
    }

    static class HinhChuNhat implements HinhDung {
        private double chieuDai, chieuRong;
        HinhChuNhat(double d, double r) { chieuDai = d; chieuRong = r; }
        @Override public double tinhDienTich() { return chieuDai * chieuRong; }
    }

    static class HinhVuong implements HinhDung {
        private double canh;
        HinhVuong(double canh) { this.canh = canh; }
        @Override public double tinhDienTich() { return canh * canh; }
    }

    public static void main(String[] args) {
        System.out.println("-- Vi pham LSP --");
        kiemTraDienTich(new HinhChuNhatViPham()); // dung nhu mong doi: 20
        kiemTraDienTich(new HinhVuongViPham());    // SAI mong doi! Vi HinhVuong thay doi hanh vi

        System.out.println("-- Tuan thu LSP: khong con hien tuong bat ngo --");
        HinhDung[] danhSach = { new HinhChuNhat(5, 4), new HinhVuong(4) };
        for (HinhDung h : danhSach) {
            System.out.println(h.getClass().getSimpleName() + ": " + h.tinhDienTich());
        }
    }
}
