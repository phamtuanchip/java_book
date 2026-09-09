import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// CSV (Comma-Separated Values) co CAU TRUC DON GIAN - chi can String.split() (Chuong
// 11) va StringBuilder (Chuong 11) la doc/ghi duoc, KHONG can thu vien ngoai
public class CsvDemo {
    record SinhVien(String ten, int tuoi, double diemTrungBinh) {
    }

    public static void main(String[] args) throws IOException {
        List<SinhVien> danhSach = List.of(
            new SinhVien("Nguyen Van A", 20, 8.5),
            new SinhVien("Tran Thi B", 21, 9.0),
            new SinhVien("Le Van C", 19, 7.2)
        );

        Path duongDan = Path.of("sinh_vien.csv");

        System.out.println("-- Ghi CSV --");
        StringBuilder noiDung = new StringBuilder();
        noiDung.append("ten,tuoi,diemTrungBinh\n"); // dong dau tien: header
        for (SinhVien sv : danhSach) {
            noiDung.append(sv.ten()).append(",")
                   .append(sv.tuoi()).append(",")
                   .append(sv.diemTrungBinh()).append("\n");
        }
        Files.writeString(duongDan, noiDung.toString());
        System.out.println("Da ghi: " + duongDan);
        System.out.println(Files.readString(duongDan));

        System.out.println("-- Doc CSV --");
        List<String> cacDong = Files.readAllLines(duongDan);
        List<SinhVien> daDoc = new ArrayList<>();
        for (int i = 1; i < cacDong.size(); i++) { // bat dau tu 1 - BO QUA dong header
            String[] cot = cacDong.get(i).split(",");
            String ten = cot[0];
            int tuoi = Integer.parseInt(cot[1]);
            double diem = Double.parseDouble(cot[2]);
            daDoc.add(new SinhVien(ten, tuoi, diem));
        }
        System.out.println("Da doc duoc " + daDoc.size() + " sinh vien:");
        daDoc.forEach(System.out::println);

        Files.deleteIfExists(duongDan);
    }
}
