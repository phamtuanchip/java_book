// LUU Y: file nay CAN thu vien Gson (gson-2.10.1.jar hoac tuong tu) de bien dich va
// chay - KHONG tu chay duoc chi voi javac/java thuan nhu moi chuong khac trong sach.
// Xem huong dan tai/them vao classpath trong README.md cua chuong nay.
//
// Day CHINH LA van de ma Chuong 42 (Maven/Gradle) se giai quyet: quan ly thu vien
// ben ngoai bang tay (tai file .jar, nho duong dan, ghep vao -cp) rat phien phuc khi
// du an co nhieu thu vien - build tool tu dong hoa toan bo qua trinh nay.

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonExample {
    record SinhVien(String ten, int tuoi, double diemTrungBinh) {
    }

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        SinhVien sv = new SinhVien("Nguyen Van A", 20, 8.5);

        // Doi tuong Java -> chuoi JSON (serialization)
        String json = gson.toJson(sv);
        System.out.println("JSON tu Gson:");
        System.out.println(json);

        // Chuoi JSON -> doi tuong Java (deserialization) - Gson TU DONG anh xa cac
        // truong (field) theo dung TEN, khong can tu viet code parse thu cong nhu
        // SimpleJsonWriter.java
        String jsonDauVao = "{\"ten\":\"Tran Thi B\",\"tuoi\":21,\"diemTrungBinh\":9.0}";
        SinhVien svDaDoc = gson.fromJson(jsonDauVao, SinhVien.class);
        System.out.println("Doc lai tu JSON: " + svDaDoc);
    }
}
