public class Main {
    public static void main(String[] args) {
        System.out.println("-- Inner class (khong static) --");
        DoiBongDa doi = new DoiBongDa("FC Java");
        doi.themCauThu("Nguyen Van A", 10);
        doi.themCauThu("Tran Van B", 7);
        doi.inDoiHinh();

        // Tao inner class tu BEN NGOAI outer class - can qua object outer truoc,
        // cu phap: outerObject.new InnerClass(...)
        DoiBongDa.CauThu ctRieng = doi.new CauThu("Le Van C", 9);
        ctRieng.gioiThieu();

        System.out.println("-- Static nested class --");
        DoiBongDa.ThongKe thongKe = new DoiBongDa.ThongKe(10, 18);
        System.out.println("Trung binh ban thang/tran: " + thongKe.trungBinhBanThangMoiTran());

        System.out.println("-- Anonymous class --");
        // Tao mot object implements HanhDong "ngay tai cho", khong can dat ten class rieng
        HanhDong chao = new HanhDong() {
            @Override
            public void thucHien() {
                System.out.println("Xin chao tu anonymous class!");
            }
        };
        chao.thucHien();

        // Anonymous class thuong dung khi CHI can dung MOT LAN, khong can tai su dung
        thucHienHanhDong(new HanhDong() {
            @Override
            public void thucHien() {
                System.out.println("Hanh dong duoc truyen truc tiep vao tham so!");
            }
        });

        System.out.println("-- Enum nang cao: HanhTinh --");
        for (HanhTinh h : HanhTinh.values()) {
            System.out.println(h + ": trong luong be mat = " + h.tinhTrongLuongBeMat());
        }

        System.out.println("-- Enum voi phuong thuc rieng tung hang so --");
        PhuongThucThanhToan.TIEN_MAT.xuLy(100_000);
        PhuongThucThanhToan.THE_TIN_DUNG.xuLy(100_000);
        PhuongThucThanhToan.CHUYEN_KHOAN.xuLy(100_000);
    }

    static void thucHienHanhDong(HanhDong hd) {
        hd.thucHien();
    }
}
