public class Main {
    public static void main(String[] args) {
        System.out.println("-- try/catch co ban: unchecked exception --");
        try {
            int ketQua = 10 / 0; // ArithmeticException - RuntimeException, KHONG bat buoc khai bao throws
            System.out.println("Khong bao gio in ra dong nay");
        } catch (ArithmeticException e) {
            System.out.println("Bat duoc loi: " + e.getMessage());
        }

        System.out.println("-- finally: LUON chay, du co loi hay khong --");
        try {
            System.out.println("Dang thu...");
            int[] mang = new int[3];
            System.out.println(mang[5]); // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Bat duoc loi truy cap mang: " + e.getMessage());
        } finally {
            System.out.println("Khoi finally LUON chay, bat ke co loi hay khong");
        }

        System.out.println("-- Multi-catch: bat nhieu loai loi khac nhau --");
        String[] duLieu = {"123", "abc", null};
        for (String s : duLieu) {
            try {
                int soNguyen = Integer.parseInt(s); // NumberFormatException neu 's' khong phai so
                System.out.println("Chuyen doi thanh cong: " + soNguyen);
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Loi voi gia tri '" + s + "': " + e.getClass().getSimpleName());
            }
        }

        System.out.println("-- Checked exception tu dinh nghia --");
        TaiKhoan tk = new TaiKhoan(100_000);
        try {
            tk.rutTien(50_000);
            System.out.println("Rut thanh cong, so du con lai: " + tk.getSoDu());
            tk.rutTien(200_000); // se nem SoDuKhongDuException
        } catch (SoDuKhongDuException e) {
            System.out.println("Loi: " + e.getMessage());
            System.out.println("So tien con thieu: " + e.getSoThieu());
        }

        System.out.println("-- try-with-resources --");
        try (NguonTaiNguyen tn = new NguonTaiNguyen("file.txt")) {
            tn.doc();
            // KHONG can goi tn.close() thu cong - JVM TU DONG goi khi ra khoi khoi try
        }

        System.out.println("-- try-with-resources KE CA khi co loi --");
        try (NguonTaiNguyen tn = new NguonTaiNguyen("data.csv")) {
            tn.doc();
            throw new RuntimeException("Loi gia lap khi dang xu ly");
        } catch (RuntimeException e) {
            System.out.println("Bat duoc loi: " + e.getMessage());
            System.out.println("(Luu y: dong 'Da dong tai nguyen: data.csv' van da in RA TRUOC dong nay)");
        }

        System.out.println("Chuong trinh ket thuc binh thuong.");
    }
}
