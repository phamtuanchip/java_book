import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapDemo {
    public static void main(String[] args) {
        System.out.println("-- Thao tac co ban voi HashMap --");
        Map<String, Integer> diem = new HashMap<>();
        diem.put("Toan", 9);
        diem.put("Van", 7);
        diem.put("Anh", 8);
        diem.put("Toan", 10); // GHI DE gia tri cu cua key "Toan"

        System.out.println("Diem Toan: " + diem.get("Toan"));
        System.out.println("Diem mon khong ton tai: " + diem.get("Ly")); // tra ve null, KHONG loi
        System.out.println("Diem mon khong ton tai (co gia tri mac dinh): " + diem.getOrDefault("Ly", 0));
        System.out.println("Co mon Van khong? " + diem.containsKey("Van"));
        System.out.println("So mon: " + diem.size());

        diem.remove("Anh");
        System.out.println("Sau khi xoa Anh: " + diem);

        System.out.println("-- Duyet Map: 3 cach pho bien --");
        System.out.println("Cach 1: duyet keySet()");
        for (String mon : diem.keySet()) {
            System.out.println("  " + mon);
        }

        System.out.println("Cach 2: duyet values()");
        for (int diemSo : diem.values()) {
            System.out.println("  " + diemSo);
        }

        System.out.println("Cach 3: duyet entrySet() - lay CA key lan value cung luc");
        for (Map.Entry<String, Integer> entry : diem.entrySet()) {
            System.out.println("  " + entry.getKey() + " = " + entry.getValue());
        }

        System.out.println("-- LinkedHashMap: giu dung thu tu them vao --");
        Map<String, Integer> diemTheoThuTu = new LinkedHashMap<>();
        diemTheoThuTu.put("Toan", 9);
        diemTheoThuTu.put("Van", 7);
        diemTheoThuTu.put("Anh", 8);
        System.out.println(diemTheoThuTu); // luon in ra dung thu tu: Toan, Van, Anh

        System.out.println("-- TreeMap: TU DONG sap xep theo key --");
        Map<String, Integer> diemSapXep = new TreeMap<>();
        diemSapXep.put("Toan", 9);
        diemSapXep.put("Van", 7);
        diemSapXep.put("Anh", 8);
        System.out.println(diemSapXep); // luon in ra theo thu tu bang chu cai cua KEY: Anh, Toan, Van

        System.out.println("-- Bai toan pho bien: dem so lan xuat hien --");
        String[] tuUngDung = {"java", "python", "java", "javascript", "java", "python"};
        Map<String, Integer> demSoLan = new HashMap<>();
        for (String tu : tuUngDung) {
            // Cach LAM DAI: kiem tra ton tai truoc roi moi cong don
            if (demSoLan.containsKey(tu)) {
                demSoLan.put(tu, demSoLan.get(tu) + 1);
            } else {
                demSoLan.put(tu, 1);
            }
        }
        System.out.println("Dem bang cach dai: " + demSoLan);

        Map<String, Integer> demSoLanGon = new HashMap<>();
        for (String tu : tuUngDung) {
            // Cach GON HON: merge() tu xu ly ca hai truong hop trong MOT dong.
            // 'Integer::sum' la METHOD REFERENCE (hoc ky o Chuong 30) - tam hieu don
            // gian: "neu key da co gia tri, cong don gia tri cu voi gia tri moi (1)"
            demSoLanGon.merge(tu, 1, Integer::sum);
        }
        System.out.println("Dem bang merge(): " + demSoLanGon);
    }
}
