import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetDemo {
    public static void main(String[] args) {
        System.out.println("-- HashSet: khong dam bao thu tu --");
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Cam");
        hashSet.add("Tao");
        hashSet.add("Buoi");
        hashSet.add("Cam"); // trung lap - bi bo qua am tham
        System.out.println(hashSet + " (kich thuoc: " + hashSet.size() + ")");

        System.out.println("-- LinkedHashSet: GIU DUNG thu tu da them vao --");
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("Cam");
        linkedHashSet.add("Tao");
        linkedHashSet.add("Buoi");
        System.out.println(linkedHashSet);

        System.out.println("-- TreeSet: TU DONG sap xep theo thu tu tu nhien --");
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Cam");
        treeSet.add("Tao");
        treeSet.add("Buoi");
        System.out.println(treeSet); // luon in ra theo thu tu bang chu cai: Buoi, Cam, Tao

        // Cac phep toan tap hop
        Set<Integer> tapA = new HashSet<>(java.util.Arrays.asList(1, 2, 3, 4));
        Set<Integer> tapB = new HashSet<>(java.util.Arrays.asList(3, 4, 5, 6));

        Set<Integer> hopChung = new HashSet<>(tapA);
        hopChung.addAll(tapB); // PHEP HOP (union)
        System.out.println("Hop cua A va B: " + hopChung);

        Set<Integer> giaoNhau = new HashSet<>(tapA);
        giaoNhau.retainAll(tapB); // PHEP GIAO (intersection)
        System.out.println("Giao cua A va B: " + giaoNhau);

        Set<Integer> hieuAB = new HashSet<>(tapA);
        hieuAB.removeAll(tapB); // PHEP HIEU (difference)
        System.out.println("Hieu A - B: " + hieuAB);

        // Vi sao can hashCode/equals dung (nhac lai Chuong 20)
        System.out.println("-- HashSet voi object tu dinh nghia --");
        Set<DiemToaDo> tapDiem = new HashSet<>();
        tapDiem.add(new DiemToaDo(1, 2));
        tapDiem.add(new DiemToaDo(1, 2)); // NOI DUNG giong het diem tren
        System.out.println("So diem trong Set (CO override equals/hashCode): " + tapDiem.size());

        Set<DiemToaDoKhongOverride> tapDiemSai = new HashSet<>();
        tapDiemSai.add(new DiemToaDoKhongOverride(1, 2));
        tapDiemSai.add(new DiemToaDoKhongOverride(1, 2)); // NOI DUNG giong het, nhung...
        System.out.println("So diem trong Set (KHONG override equals/hashCode): " + tapDiemSai.size()
            + " (! - HashSet coi la 2 phan tu KHAC NHAU vi dung dinh nghia == mac dinh cua Object)");
    }

    // Class nho de minh hoa - CO override equals/hashCode dung quy tac (Chuong 20)
    static class DiemToaDo {
        int x, y;

        DiemToaDo(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DiemToaDo)) return false;
            DiemToaDo d = (DiemToaDo) o;
            return x == d.x && y == d.y;
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(x, y);
        }
    }

    // Class GIONG HET class tren, nhung KHONG override equals/hashCode - dung
    // hanh vi mac dinh cua Object (so sanh nhu ==)
    static class DiemToaDoKhongOverride {
        int x, y;

        DiemToaDoKhongOverride(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
