public class DoiBongDa {
    private String tenDoi;
    private CauThu[] danhSachCauThu = new CauThu[11];
    private int soCauThuHienTai = 0;

    public DoiBongDa(String tenDoi) {
        this.tenDoi = tenDoi;
    }

    public CauThu themCauThu(String ten, int soAo) {
        CauThu ct = new CauThu(ten, soAo); // tao INNER CLASS tu BEN TRONG outer class
        danhSachCauThu[soCauThuHienTai] = ct;
        soCauThuHienTai++;
        return ct;
    }

    public void inDoiHinh() {
        System.out.println("Doi hinh " + tenDoi + ":");
        for (int i = 0; i < soCauThuHienTai; i++) {
            danhSachCauThu[i].gioiThieu();
        }
    }

    // INNER CLASS (khong 'static'): moi object CauThu LUON gan lien voi mot object
    // DoiBongDa cu the, va co the truy cap TRUC TIEP field cua outer class (tenDoi)
    // ma khong can truyen tham so rieng
    public class CauThu {
        private String ten;
        private int soAo;

        public CauThu(String ten, int soAo) {
            this.ten = ten;
            this.soAo = soAo;
        }

        public void gioiThieu() {
            // 'tenDoi' o day la field cua OUTER class DoiBongDa - inner class
            // "nhin thay" duoc no ma khong can DoiBongDa.this.tenDoi (mac du viet
            // day du nhu vay cung hop le va doi khi can thiet de het nham lan)
            System.out.println("  #" + soAo + " " + ten + " - doi " + tenDoi);
        }
    }

    // STATIC NESTED CLASS: KHONG gan voi mot object DoiBongDa cu the nao, hoat dong
    // nhu mot class binh thuong, chi la "dong goi" ben trong DoiBongDa cho gon vi no
    // chi lien quan toi DoiBongDa
    public static class ThongKe {
        public int tongSoTran;
        public int tongBanThang;

        public ThongKe(int tongSoTran, int tongBanThang) {
            this.tongSoTran = tongSoTran;
            this.tongBanThang = tongBanThang;
        }

        public double trungBinhBanThangMoiTran() {
            return tongSoTran == 0 ? 0 : (double) tongBanThang / tongSoTran;
        }
    }
}
