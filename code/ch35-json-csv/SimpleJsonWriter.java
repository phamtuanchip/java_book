// Minh hoa CAU TRUC JSON va cach TU VIET mot bo sinh JSON rat don gian, CHUA XU LY
// het moi truong hop (ky tu dac biet trong chuoi, mang long nhau...) - muc dich la
// HIEU dinh dang JSON truoc khi dung thu vien that (xem GsonExample.java)
public class SimpleJsonWriter {
    record SinhVien(String ten, int tuoi, double diemTrungBinh) {
    }

    static String toJson(SinhVien sv) {
        // JSON: { "key": value, "key": value, ... }
        // Chuoi (String) dat trong nhay kep, so KHONG can nhay kep
        return "{"
            + "\"ten\":\"" + sv.ten() + "\","
            + "\"tuoi\":" + sv.tuoi() + ","
            + "\"diemTrungBinh\":" + sv.diemTrungBinh()
            + "}";
    }

    public static void main(String[] args) {
        SinhVien sv = new SinhVien("Nguyen Van A", 20, 8.5);
        String json = toJson(sv);
        System.out.println("JSON tu viet tay:");
        System.out.println(json);

        System.out.println();
        System.out.println("-- Cau truc JSON --");
        System.out.println("Object: { \"key\": value, ... }  - giong Map trong Java (Chuong 26)");
        System.out.println("Array : [ value, value, ... ]     - giong List trong Java (Chuong 24)");
        System.out.println("Kieu du lieu: chuoi (\"...\"),  so,  true/false,  null,  object,  array");
    }
}
