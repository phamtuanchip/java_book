import java.util.List;
import java.util.stream.Collectors;

public class StreamBasicsDemo {
    public static void main(String[] args) {
        List<String> ten = List.of("Nguyen Van A", "Tran Thi B", "Le Van C", "An", "Binh Minh");

        System.out.println("-- Cach CU (truoc Stream): vong lap thu cong --");
        List<String> tenDaiCu = new java.util.ArrayList<>();
        for (String t : ten) {
            if (t.length() > 5) {
                tenDaiCu.add(t.toUpperCase());
            }
        }
        System.out.println(tenDaiCu);

        System.out.println("-- Cach MOI: Stream, cung MOT ket qua, doc gan nhu tieng Anh --");
        List<String> tenDaiMoi = ten.stream()
            .filter(t -> t.length() > 5)   // THAO TAC TRUNG GIAN: giu lai phan tu thoa dieu kien
            .map(String::toUpperCase)       // THAO TAC TRUNG GIAN: bien doi tung phan tu
            .collect(Collectors.toList());  // THAO TAC KET THUC: gom ket qua thanh List
        System.out.println(tenDaiMoi);

        System.out.println("-- Stream KHONG sua doi danh sach goc --");
        System.out.println("Danh sach 'ten' goc van nguyen: " + ten);

        System.out.println("-- Cac thao tac ket thuc pho bien khac --");
        long soLuong = ten.stream().filter(t -> t.length() > 5).count();
        System.out.println("So luong ten dai hon 5 ky tu: " + soLuong);

        boolean coTenNaoBatDauBangA = ten.stream().anyMatch(t -> t.startsWith("A"));
        System.out.println("Co ten nao bat dau bang 'A'? " + coTenNaoBatDauBangA);

        boolean tatCaDeuDaiHon2 = ten.stream().allMatch(t -> t.length() > 2);
        System.out.println("Tat ca ten deu dai hon 2 ky tu? " + tatCaDeuDaiHon2);

        java.util.Optional<String> tenDauTien = ten.stream()
            .filter(t -> t.startsWith("L"))
            .findFirst();
        System.out.println("Ten dau tien bat dau bang 'L': " + tenDauTien.orElse("khong tim thay"));

        System.out.println("-- sorted(): sap xep qua Stream, khong sua danh sach goc --");
        List<String> tenDaSapXep = ten.stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println(tenDaSapXep);
    }
}
