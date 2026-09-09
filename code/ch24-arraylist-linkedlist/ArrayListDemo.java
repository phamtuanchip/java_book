import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
    public static void main(String[] args) {
        // Khai bao qua INTERFACE (List) o ben trai, cai dat CU THE (ArrayList) o ben
        // phai - thoi quen tot, se giai thich trong chuong
        List<String> danhSach = new ArrayList<>();

        // Them phan tu - kich thuoc TU DONG tang, khac han mang co dinh o Chuong 9
        danhSach.add("An");
        danhSach.add("Binh");
        danhSach.add("Chi");
        System.out.println("Sau khi them: " + danhSach);

        // Them vao MOT VI TRI cu the
        danhSach.add(1, "Xen giua");
        System.out.println("Sau khi them vao vi tri 1: " + danhSach);

        // Doc phan tu theo chi so
        System.out.println("Phan tu tai vi tri 0: " + danhSach.get(0));

        // Sua phan tu tai mot vi tri
        danhSach.set(0, "An (da sua)");
        System.out.println("Sau khi sua: " + danhSach);

        // Xoa theo chi so
        danhSach.remove(1);
        System.out.println("Sau khi xoa vi tri 1: " + danhSach);

        // Xoa theo GIA TRI (can dung .equals(), xem Chuong 20)
        danhSach.remove("Chi");
        System.out.println("Sau khi xoa 'Chi': " + danhSach);

        // Kiem tra ton tai, lay kich thuoc
        System.out.println("Co chua 'Binh'? " + danhSach.contains("Binh"));
        System.out.println("So phan tu: " + danhSach.size());

        // Duyet bang for-each - giong het cach duyet mang o Chuong 9
        System.out.println("-- Duyet danh sach --");
        for (String ten : danhSach) {
            System.out.println("- " + ten);
        }

        // Chuyen doi qua lai voi mang
        String[] mang = danhSach.toArray(new String[0]);
        System.out.println("Chuyen thanh mang, phan tu 0: " + mang[0]);

        List<String> tuMang = List.of("X", "Y", "Z"); // danh sach BAT BIEN (immutable) tu Java 9+
        System.out.println("List.of(): " + tuMang);
        // tuMang.add("W"); // <-- se nem UnsupportedOperationException neu bo comment, vi List.of() BAT BIEN
    }
}
