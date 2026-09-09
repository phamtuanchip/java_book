import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorDemo {
    public static void main(String[] args) {
        List<NguoiDung> danhSach = new ArrayList<>(List.of(
            new NguoiDung("Binh", 30),
            new NguoiDung("An", 25),
            new NguoiDung("An", 20),
            new NguoiDung("Chi", 25)
        ));

        System.out.println("-- Comparator.comparing(): sap xep theo MOT tieu chi --");
        // Comparator.comparing(...) ngan gon hon NHIEU so voi viet anonymous class
        // day du nhu o Chuong 20 - day la CACH VIET HIEN DAI, nen dung
        List<NguoiDung> theoTuoi = new ArrayList<>(danhSach);
        theoTuoi.sort(Comparator.comparing(NguoiDung::getTuoi));
        System.out.println(theoTuoi);

        System.out.println("-- thenComparing(): sap xep theo NHIEU tieu chi (tieu chi sau dung khi tieu chi truoc BANG NHAU) --");
        List<NguoiDung> theoTenRoiTuoi = new ArrayList<>(danhSach);
        theoTenRoiTuoi.sort(
            Comparator.comparing(NguoiDung::getTen)
                      .thenComparing(NguoiDung::getTuoi)
        );
        System.out.println(theoTenRoiTuoi);

        System.out.println("-- reversed(): dao nguoc thu tu --");
        List<NguoiDung> theoTuoiGiamDan = new ArrayList<>(danhSach);
        theoTuoiGiamDan.sort(Comparator.comparing(NguoiDung::getTuoi).reversed());
        System.out.println(theoTuoiGiamDan);

        System.out.println("-- Collections.sort() - cach cu, van con gap trong code cu --");
        List<Integer> danhSachSo = new ArrayList<>(List.of(5, 2, 8, 1, 9));
        Collections.sort(danhSachSo);
        System.out.println("Sau khi sort: " + danhSachSo);

        Collections.reverse(danhSachSo);
        System.out.println("Sau khi reverse: " + danhSachSo);

        System.out.println("Gia tri lon nhat: " + Collections.max(danhSachSo));
        System.out.println("Gia tri nho nhat: " + Collections.min(danhSachSo));

        Collections.shuffle(danhSachSo);
        System.out.println("Sau khi shuffle (ngau nhien, moi lan chay khac nhau): " + danhSachSo);

        List<Integer> khongSuaDuoc = Collections.unmodifiableList(danhSachSo);
        System.out.println("Danh sach bat bien: " + khongSuaDuoc);
        // khongSuaDuoc.add(100); // <-- se nem UnsupportedOperationException neu bo comment
    }
}
