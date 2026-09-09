import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) {
        Map<String, String> diaChiEmail = new HashMap<>();
        diaChiEmail.put("an", "an@example.com");
        diaChiEmail.put("binh", "binh@example.com");

        System.out.println("-- Cach CU: co the quen kiem tra null --");
        String emailKhongAnToan = diaChiEmail.get("chi"); // key khong ton tai -> null
        System.out.println("Gia tri (co the la null): " + emailKhongAnToan);
        // System.out.println(emailKhongAnToan.length()); // <-- NullPointerException neu bo comment

        System.out.println("-- Tao Optional --");
        Optional<String> optCoGiaTri = Optional.of("gia tri co san");
        Optional<String> optRong = Optional.empty();
        Optional<String> optTuGiaTriCoTheNull = Optional.ofNullable(diaChiEmail.get("chi")); // an toan du gia tri co the null

        System.out.println("-- Kiem tra va lay gia tri --");
        System.out.println("optCoGiaTri co gia tri khong? " + optCoGiaTri.isPresent());
        System.out.println("optRong co gia tri khong? " + optRong.isPresent());
        System.out.println("optRong RONG? " + optRong.isEmpty()); // isEmpty() la nguoc lai cua isPresent()

        System.out.println("-- Lay gia tri AN TOAN, co gia tri thay the neu rong --");
        String emailChi = optTuGiaTriCoTheNull.orElse("khong-co-email@default.com");
        System.out.println("Email cua 'chi': " + emailChi);

        System.out.println("-- orElseGet(): gia tri thay the duoc TINH TOAN, chi khi CAN thiet --");
        String emailDung = optTuGiaTriCoTheNull.orElseGet(() -> {
            System.out.println("(dang tao gia tri mac dinh...)");
            return "tao-moi@default.com";
        });
        System.out.println("Ket qua: " + emailDung);

        System.out.println("-- ifPresent(): chi thuc hien hanh dong NEU co gia tri --");
        optCoGiaTri.ifPresent(gt -> System.out.println("Gia tri ton tai: " + gt));
        optRong.ifPresent(gt -> System.out.println("Dong nay KHONG BAO GIO chay"));

        System.out.println("-- ifPresentOrElse(): co CA HAI nhanh (Java 9+) --");
        optRong.ifPresentOrElse(
            gt -> System.out.println("Co gia tri: " + gt),
            () -> System.out.println("Khong co gia tri nao ca")
        );

        System.out.println("-- Ket hop Optional voi map(): bien doi gia tri BEN TRONG Optional --");
        Optional<String> optEmailHoa = optTuGiaTriCoTheNull.map(String::toUpperCase);
        System.out.println("Email viet hoa (van la Optional rong neu goc rong): " + optEmailHoa);

        System.out.println("-- Mo phong phuong thuc tra ve Optional thay vi null truc tiep --");
        Optional<String> ketQua = timEmail(diaChiEmail, "an");
        System.out.println(ketQua.orElse("Khong tim thay"));
        System.out.println(timEmail(diaChiEmail, "khongtontai").orElse("Khong tim thay"));
    }

    // Quy uoc HIEN DAI: phuong thuc CO THE khong tim thay gia tri nen TRA VE Optional<T>
    // thay vi tra ve T (co nguy co la null) - nguoi GOI phuong thuc BUOC PHAI tu quyet
    // dinh xu ly truong hop rong, khong the "vo tinh quen" nhu voi null truc tiep
    static Optional<String> timEmail(Map<String, String> danhBa, String ten) {
        return Optional.ofNullable(danhBa.get(ten));
    }
}
