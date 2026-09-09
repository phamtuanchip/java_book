public class Main {
    public static void main(String[] args) {
        TaiKhoanNganHang tk = new TaiKhoanNganHang("Nguyen Van A", 100_000);

        System.out.println("Chu tai khoan: " + tk.getChuTaiKhoan());
        System.out.println("So du ban dau: " + tk.getSoDu());

        tk.napTien(50_000);
        tk.rutTien(30_000);

        // Thu rut qua so du hien co - bi tu choi, khong lam soDu am
        tk.rutTien(1_000_000);

        // Thu nap so am - bi tu choi
        tk.napTien(-500);

        System.out.println("So du cuoi cung: " + tk.getSoDu());

        // KHONG the lam dieu nay - field 'soDu' la private, dong bien dich se bao loi:
        // tk.soDu = -999_999_999; // <-- bo comment dong nay se thay loi bien dich ngay

        // Vi tao khoan khoi tao voi so du am - tu dong duoc dieu chinh ve 0
        TaiKhoanNganHang tkLoi = new TaiKhoanNganHang("Tran Thi B", -50_000);
        System.out.println("So du tai khoan tao voi gia tri am: " + tkLoi.getSoDu());

        System.out.println("-- Vi du setter co kiem tra hop le (NguoiDung) --");
        NguoiDung nd = new NguoiDung("a@example.com", 25);
        System.out.println(nd.getEmail() + ", " + nd.getTuoi() + " tuoi");

        nd.setTuoi(-5);              // bi tu choi, giu nguyen 25
        nd.setEmail("khong-hop-le"); // bi tu choi (thieu @), giu nguyen email cu
        System.out.println(nd.getEmail() + ", " + nd.getTuoi() + " tuoi (khong doi)");

        nd.setTuoi(30); // hop le, duoc chap nhan
        System.out.println(nd.getEmail() + ", " + nd.getTuoi() + " tuoi");
    }
}
