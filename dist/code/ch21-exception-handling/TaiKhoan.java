public class TaiKhoan {
    private double soDu;

    public TaiKhoan(double soDuBanDau) {
        this.soDu = soDuBanDau;
    }

    public double getSoDu() {
        return soDu;
    }

    // 'throws SoDuKhongDuException' trong chu ky phuong thuc: BAT BUOC vi day la
    // checked exception - moi noi GOI phuong thuc nay phai try/catch hoac tiep tuc
    // khai bao 'throws' len tren
    public void rutTien(double soTien) throws SoDuKhongDuException {
        if (soTien > soDu) {
            double thieu = soTien - soDu;
            throw new SoDuKhongDuException(
                "Khong du so du: can " + soTien + ", chi co " + soDu, thieu);
        }
        soDu -= soTien;
    }
}
