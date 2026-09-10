public class SingletonDemo {
    // Singleton: dam bao CHI CO DUNG MOT object cua class nay ton tai trong suot
    // chuong trinh, va cung cap MOT DIEM TRUY CAP DUY NHAT toi object do
    static class CauHinhUngDung {
        // instance duy nhat, tao SAN khi class duoc nap (cach "eager initialization" -
        // don gian va AN TOAN VOI DA LUONG (Chuong 38) MA KHONG can synchronized)
        private static final CauHinhUngDung INSTANCE = new CauHinhUngDung();

        private String tenUngDung;
        private int soLanKhoiTao = 0;

        // Constructor PRIVATE - khong ai ben ngoai 'new CauHinhUngDung()' duoc,
        // day la diem MAU CHOT cua Singleton
        private CauHinhUngDung() {
            tenUngDung = "Sach Java";
            soLanKhoiTao++;
            System.out.println("CauHinhUngDung duoc khoi tao (lan " + soLanKhoiTao + ")");
        }

        // Diem truy cap DUY NHAT toi instance
        public static CauHinhUngDung getInstance() {
            return INSTANCE;
        }

        public String getTenUngDung() {
            return tenUngDung;
        }

        public void setTenUngDung(String ten) {
            this.tenUngDung = ten;
        }
    }

    public static void main(String[] args) {
        CauHinhUngDung cauHinh1 = CauHinhUngDung.getInstance();
        CauHinhUngDung cauHinh2 = CauHinhUngDung.getInstance();

        System.out.println("cauHinh1 == cauHinh2? " + (cauHinh1 == cauHinh2)); // true - CUNG mot object

        cauHinh1.setTenUngDung("Java Tu Co Ban Den Nang Cao");
        System.out.println("Doc qua cauHinh2 (chua tung goi setTenUngDung): " + cauHinh2.getTenUngDung());
        System.out.println("(vi ca hai deu tro toi CUNG mot object, sua qua bien nao cung nhu nhau)");
    }
}
