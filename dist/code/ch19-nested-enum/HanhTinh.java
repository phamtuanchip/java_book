// Enum nang cao: co field, constructor, va phuong thuc rieng - khac han enum "danh
// sach ten suong" don gian nhu enum NgayTrongTuan { THU_HAI, THU_BA, ... }
public enum HanhTinh {
    // Moi hang duoi day la MOT HANG SO enum, tu goi constructor tuong ung voi
    // tham so truyen vao - CHAY DUNG MOT LAN cho moi hang so, luc class duoc nap
    TRAI_DAT(5.976e24, 6.37814e6),
    SAO_HOA(6.421e23, 3.3972e6),
    SAO_MOC(1.9e27, 7.1492e7);

    private static final double HANG_SO_HAP_DAN = 6.67300E-11;

    private final double khoiLuong; // kg
    private final double banKinh;   // met

    // Constructor cua enum LUON la private (ngam dinh, khong can ghi 'private') -
    // KHONG the tu 'new HanhTinh(...)' o ben ngoai, cac hang so o tren la CACH DUY
    // NHAT de tao instance cua enum nay
    HanhTinh(double khoiLuong, double banKinh) {
        this.khoiLuong = khoiLuong;
        this.banKinh = banKinh;
    }

    public double tinhTrongLuongBeMat() {
        return HANG_SO_HAP_DAN * khoiLuong / (banKinh * banKinh);
    }
}
