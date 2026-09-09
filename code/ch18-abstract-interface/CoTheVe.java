// interface: khai bao "hop dong" - lop nao implements phai cung cap ve()
public interface CoTheVe {
    void ve(); // phuong thuc abstract mac dinh (khong can ghi 'abstract', interface tu hieu)

    // default method (Java 8+): CO san than ham NGAY TRONG interface, lop implements
    // duoc KE THUA luon, khong bat buoc phai override
    default void veDamNet() {
        System.out.println("=== VE DAM NET ===");
        ve();
        System.out.println("=== HET ===");
    }

    // static method: goi truc tiep qua ten interface, khong lien quan toi object nao
    static void inHuongDan() {
        System.out.println("Goi ve() de ve hinh, hoac veDamNet() de ve co vien nhan manh.");
    }
}
