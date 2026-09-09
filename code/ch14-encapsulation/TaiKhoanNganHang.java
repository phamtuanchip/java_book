public class TaiKhoanNganHang {
    // private: KHONG code ben ngoai class nay truy cap truc tiep duoc field
    private String chuTaiKhoan;
    private double soDu;

    public TaiKhoanNganHang(String chuTaiKhoan, double soDuBanDau) {
        this.chuTaiKhoan = chuTaiKhoan;
        // Kiem tra hop le ngay tu luc khoi tao, khong chi luc nap/rut sau nay.
        // Cach xu ly "dung chuan" hon (nem exception) se hoc o Chuong 21 - o day
        // tam thoi ep ve 0 va canh bao ra console.
        if (soDuBanDau < 0) {
            System.out.println("Canh bao: so du ban dau am, tu dong dat ve 0");
            this.soDu = 0;
        } else {
            this.soDu = soDuBanDau;
        }
    }

    // Getter: cho phep DOC gia tri tu ben ngoai, nhung khong cho SUA truc tiep
    public String getChuTaiKhoan() {
        return chuTaiKhoan;
    }

    public double getSoDu() {
        return soDu;
    }

    // "Cong" duy nhat de thay doi soDu tu ben ngoai - luon di qua kiem tra hop le
    public void napTien(double soTien) {
        if (soTien <= 0) {
            System.out.println("Loi: so tien nap phai lon hon 0");
            return;
        }
        soDu += soTien;
        System.out.println("Da nap " + soTien + ", so du moi: " + soDu);
    }

    public void rutTien(double soTien) {
        if (soTien <= 0) {
            System.out.println("Loi: so tien rut phai lon hon 0");
            return;
        }
        if (soTien > soDu) {
            System.out.println("Loi: so du khong du (hien co " + soDu + ", muon rut " + soTien + ")");
            return;
        }
        soDu -= soTien;
        System.out.println("Da rut " + soTien + ", so du moi: " + soDu);
    }

    // KHONG co setSoDu(double) - co tinh khong cho phep gan thang so du tu ben ngoai,
    // moi thay doi phai di qua napTien/rutTien de dam bao luon hop le
}
