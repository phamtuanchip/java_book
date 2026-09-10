public class PrimitiveTypesDemo {
    public static void main(String[] args) {
        // So nguyen
        byte tuoi = 25;
        short soHocSinh = 1200;
        int danSo = 98_000_000;
        long soNguoiDungInternet = 7_800_000_000L;

        // So thap phan
        float diemTB = 8.5f;
        double pi = 3.14159265358979;

        // Ky tu va logic
        char kyTuDauTien = 'J';
        boolean daHoanThanh = false;

        System.out.println("tuoi = " + tuoi);
        System.out.println("soHocSinh = " + soHocSinh);
        System.out.println("danSo = " + danSo);
        System.out.println("soNguoiDungInternet = " + soNguoiDungInternet);
        System.out.println("diemTB = " + diemTB);
        System.out.println("pi = " + pi);
        System.out.println("kyTuDauTien = " + kyTuDauTien);
        System.out.println("daHoanThanh = " + daHoanThanh);

        // Hang so - khong the gan lai gia tri sau khi khoi tao
        final double LAI_SUAT_CO_DINH = 0.05;
        System.out.println("LAI_SUAT_CO_DINH = " + LAI_SUAT_CO_DINH);

        // Ep kieu tu dong (widening) - an toan, khong mat du lieu
        int soNguyen = 10;
        double soThucTuDong = soNguyen; // int -> double, tu dong
        System.out.println("soThucTuDong = " + soThucTuDong);

        // Ep kieu thu cong (narrowing) - co the mat du lieu, phai ep ro rang
        double gia = 19.99;
        int giaLamTron = (int) gia; // cat phan thap phan, KHONG lam tron
        System.out.println("giaLamTron = " + giaLamTron);

        // Vi du tran so khi ep kieu qua nho
        int soLon = 130;
        byte soBiTran = (byte) soLon; // byte chi chua duoc -128..127
        System.out.println("soBiTran = " + soBiTran);
    }
}
