import java.util.Arrays;

public class ArrayBasicsDemo {
    public static void main(String[] args) {
        // Khai bao va khoi tao mang
        int[] diem = new int[5]; // mang 5 phan tu, mac dinh gia tri 0
        diem[0] = 8;
        diem[1] = 7;
        diem[2] = 9;
        diem[3] = 6;
        diem[4] = 10;

        // Khai bao va khoi tao ngay voi gia tri co san
        String[] ten = {"An", "Binh", "Chi", "Dung", "Em"};

        // Duyet mang bang for co chi so
        System.out.println("-- Duyet bang for co chi so --");
        for (int i = 0; i < diem.length; i++) {
            System.out.println(ten[i] + ": " + diem[i] + " diem");
        }

        // Duyet mang bang for-each - don gian hon khi khong can chi so
        System.out.println("-- Duyet bang for-each --");
        int tong = 0;
        for (int d : diem) {
            tong += d;
        }
        double trungBinh = (double) tong / diem.length;
        System.out.println("Tong: " + tong + ", Trung binh: " + trungBinh);

        // Mang la kieu THAM CHIEU - gan mang cho bien khac la gan THAM CHIEU, khong sao chep
        int[] diemBanSao = diem;
        diemBanSao[0] = 100; // sua qua diemBanSao...
        System.out.println("diem[0] sau khi sua qua diemBanSao: " + diem[0]); // ...anh huong ca diem!

        // Muon sao chep THAT SU noi dung, dung Arrays.copyOf
        int[] diemSaoChepThat = Arrays.copyOf(diem, diem.length);
        diemSaoChepThat[0] = -1;
        System.out.println("diem[0] sau khi sua ban sao that: " + diem[0] + " (khong doi)");

        // In mang de debug - khong dung println truc tiep tren mang
        System.out.println("In truc tiep mang (khong huu ich): " + diem);
        System.out.println("In dung cach voi Arrays.toString: " + Arrays.toString(diem));

        // Sap xep mang
        int[] mangChuaSapXep = {5, 2, 8, 1, 9};
        Arrays.sort(mangChuaSapXep);
        System.out.println("Sau khi sap xep: " + Arrays.toString(mangChuaSapXep));

        // Truy cap ngoai pham vi se lam chuong trinh dung dot ngot voi loi
        // ArrayIndexOutOfBoundsException. KHONG bo comment dong duoi day khi chay binh
        // thuong - no se lam chuong trinh crash ngay tai day (cach xu ly dung se hoc o
        // Chuong 21). Tam thoi cu ghi nho: chi so hop le cua mang 'diem' la 0..4.
        // System.out.println(diem[10]);
        System.out.println("Chuong trinh chay xong binh thuong (khong dung thu chi so ngoai pham vi).");
    }
}
