public class Utils {
    // Generic method: <T> khai bao TRUOC kieu tra ve, chi co hieu luc TRONG PHAM VI
    // phuong thuc nay - khac voi generic class, KHONG can tao object cu the moi dung duoc
    public static <T> void inMoiPhanTu(T[] mang) {
        for (T phanTu : mang) {
            System.out.println("- " + phanTu);
        }
    }

    // Wildcard '? extends Number': chap nhan HopChua cua BAT KY kieu con nao cua
    // Number (HopChua<Integer>, HopChua<Double>...), khong chi rieng HopChua<Number>
    public static void inGiaTriSo(HopChua<? extends Number> hop) {
        Number giaTri = hop.layNoiDung(); // CHI DOC duoc, khong the datNoiDung(...) qua tham chieu nay
        System.out.println("Gia tri so: " + giaTri);
    }
}
