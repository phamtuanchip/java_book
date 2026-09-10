public class BuggyProgram {
    // Chuong trinh nay CO LOI CO Y - dung de thuc hanh debug trong IDE (xem README).
    // Muc tieu: tinh trung binh cong cua mot mang, nhung ket qua RA SAI.
    public static void main(String[] args) {
        int[] diem = {8, 7, 9, 6, 10};
        double trungBinh = tinhTrungBinh(diem);
        System.out.println("Diem trung binh: " + trungBinh);
        System.out.println("(Ket qua DUNG phai la 8.0 - neu ban thay so khac, do la LOI can debug)");
    }

    static double tinhTrungBinh(int[] mang) {
        int tong = 0;
        // LOI CO Y: dung <= thay vi < , vong lap chay THUA mot lan, doc ra ngoai
        // pham vi mang (hoac cong sai neu mang co "dem" o cuoi - o day se gay
        // ArrayIndexOutOfBoundsException, xem Chuong 9)
        for (int i = 0; i <= mang.length; i++) {
            tong += mang[i];
        }
        return (double) tong / mang.length;
    }
}
