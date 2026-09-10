import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReduceCollectDemo {
    public static void main(String[] args) {
        List<SanPham> danhSach = List.of(
            new SanPham("But bi", "Van phong pham", 5_000),
            new SanPham("Vo", "Van phong pham", 8_000),
            new SanPham("Ao thun", "Thoi trang", 150_000),
            new SanPham("Quan jean", "Thoi trang", 350_000)
        );

        System.out.println("-- reduce(): gop TAT CA phan tu thanh MOT gia tri duy nhat --");
        double tongGiaTri = danhSach.stream()
            .map(SanPham::getGia)
            .reduce(0.0, Double::sum); // gia tri KHOI DAU la 0.0, ham gop la Double::sum
        System.out.println("Tong gia tri: " + tongGiaTri);

        // reduce() thuc chat TUONG DUONG voi vong lap:
        // double tong = 0.0;
        // for (SanPham sp : danhSach) { tong = tong + sp.getGia(); }

        System.out.println("-- Collectors.toMap(): chuyen List thanh Map --");
        Map<String, Double> tenToiGia = danhSach.stream()
            .collect(Collectors.toMap(SanPham::getTen, SanPham::getGia));
        System.out.println(tenToiGia);

        System.out.println("-- Collectors.groupingBy(): nhom theo mot tieu chi --");
        Map<String, List<SanPham>> theoLoai = danhSach.stream()
            .collect(Collectors.groupingBy(SanPham::getLoai));
        theoLoai.forEach((loai, ds) -> {
            System.out.println(loai + ": " + ds.size() + " san pham");
        });

        System.out.println("-- Ket hop groupingBy + tinh tong tung nhom --");
        Map<String, Double> tongTheoLoai = danhSach.stream()
            .collect(Collectors.groupingBy(SanPham::getLoai, Collectors.summingDouble(SanPham::getGia)));
        System.out.println(tongTheoLoai);

        System.out.println("-- Collectors.joining(): noi cac chuoi lai --");
        String danhSachTen = danhSach.stream()
            .map(SanPham::getTen)
            .collect(Collectors.joining(", "));
        System.out.println("Danh sach: " + danhSachTen);
    }
}
