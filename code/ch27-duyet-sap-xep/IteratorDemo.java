import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {
    public static void main(String[] args) {
        List<Integer> danhSach = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));

        System.out.println("-- LOI: sua danh sach trong luc duyet for-each --");
        try {
            for (int so : danhSach) {
                if (so % 2 == 0) {
                    danhSach.remove(Integer.valueOf(so)); // xoa TRONG LUC duyet for-each
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Loi: " + e.getClass().getSimpleName()
                + " - KHONG duoc sua danh sach truc tiep trong for-each");
        }

        System.out.println("Danh sach hien tai (co the da bi loi giua chung): " + danhSach);

        System.out.println("-- DUNG: dung Iterator.remove() de xoa an toan trong luc duyet --");
        List<Integer> danhSach2 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));
        Iterator<Integer> it = danhSach2.iterator();
        while (it.hasNext()) {
            int so = it.next();
            if (so % 2 == 0) {
                it.remove(); // xoa AN TOAN qua chinh Iterator, khong nem loi
            }
        }
        System.out.println("Sau khi xoa so chan bang Iterator: " + danhSach2);
    }
}
