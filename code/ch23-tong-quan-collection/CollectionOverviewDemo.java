import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

// Demo TONG QUAN - moi cau truc du lieu se hoc CHI TIET rieng o cac chuong sau
// (List: Chuong 24, Set: Chuong 25, Map: Chuong 26). O day chi de thay chung
// deu nam trong CUNG MOT he thong Collection Framework va dung khi nao.
public class CollectionOverviewDemo {
    public static void main(String[] args) {
        System.out.println("-- List: co THU TU, CHO PHEP trung lap --");
        List<String> danhSachMon = new ArrayList<>();
        danhSachMon.add("Toan");
        danhSachMon.add("Van");
        danhSachMon.add("Toan"); // them duoc "Toan" LAN NUA - List cho phep trung lap
        System.out.println(danhSachMon);

        System.out.println("-- Set: KHONG trung lap, khong dam bao thu tu --");
        Set<String> tapMon = new HashSet<>();
        tapMon.add("Toan");
        tapMon.add("Van");
        tapMon.add("Toan"); // KHONG them duoc lan 2 - Set tu dong loai trung lap
        System.out.println(tapMon + " (kich thuoc: " + tapMon.size() + ")");

        System.out.println("-- Map: luu theo CAP key-value --");
        Map<String, Integer> diemMon = new HashMap<>();
        diemMon.put("Toan", 9);
        diemMon.put("Van", 7);
        diemMon.put("Toan", 10); // GHI DE gia tri cu cua key "Toan", khong tao ban ghi moi
        System.out.println(diemMon);
        System.out.println("Diem Toan: " + diemMon.get("Toan"));

        System.out.println("-- Queue: xu ly theo thu tu VAO TRUOC RA TRUOC (FIFO) --");
        Queue<String> hangDoi = new LinkedList<>();
        hangDoi.offer("Khach 1");
        hangDoi.offer("Khach 2");
        hangDoi.offer("Khach 3");
        System.out.println("Phuc vu: " + hangDoi.poll()); // lay va xoa phan tu DAU tien
        System.out.println("Con lai trong hang: " + hangDoi);
    }
}
