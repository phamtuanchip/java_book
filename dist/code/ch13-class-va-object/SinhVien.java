public class SinhVien {
    // Field (thuoc tinh) - du lieu ma moi object SinhVien tu mang theo rieng
    String ten;
    int[] diem;

    // Constructor khong tham so - Java se tu cung cap neu ban KHONG viet constructor nao
    // ca, nhung o day ta viet ro rang de minh hoa
    public SinhVien() {
        this.ten = "Chua co ten";
        this.diem = new int[0];
    }

    // Constructor co tham so - cach thuong dung nhat de khoi tao object voi du lieu ngay
    public SinhVien(String ten, int[] diem) {
        this.ten = ten;   // 'this.ten' la FIELD, 'ten' (ben phai) la THAM SO - can 'this' de phan biet
        this.diem = diem;
    }

    // Phuong thuc INSTANCE (khong co 'static') - luon thao tac tren du lieu CUA CHINH
    // object goi no (truy cap qua 'this', dung hoac an)
    public double tinhDiemTrungBinh() {
        if (diem.length == 0) {
            return 0.0;
        }
        int tong = 0;
        for (int d : diem) {
            tong += d;
        }
        return (double) tong / diem.length;
    }

    public void inThongTin() {
        System.out.println("Sinh vien: " + this.ten + ", diem TB: " + tinhDiemTrungBinh());
    }
}
