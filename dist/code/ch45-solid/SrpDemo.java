public class SrpDemo {
    // TRUOC (VI PHAM SRP): mot class lam BA VIEC khac nhau - luu du lieu nhan vien,
    // TINH TOAN bao cao, VA ghi file - moi ly do thay doi (doi cach tinh luong, doi
    // dinh dang bao cao, doi noi luu tru) deu buoc phai sua CUNG mot class
    static class NhanVienViPham {
        String ten;
        double luong;

        NhanVienViPham(String ten, double luong) {
            this.ten = ten;
            this.luong = luong;
        }

        String taoBaoCao() { // VIEC THU HAI: tinh toan/dinh dang bao cao
            return "Bao cao luong: " + ten + " - " + luong;
        }

        void luuVaoFile(String duongDan) { // VIEC THU BA: ghi file
            System.out.println("(gia lap) Da ghi bao cao vao " + duongDan);
        }
    }

    // SAU (TUAN THU SRP): moi class chi co MOT ly do de thay doi
    static class NhanVien { // CHI luu du lieu
        String ten;
        double luong;

        NhanVien(String ten, double luong) {
            this.ten = ten;
            this.luong = luong;
        }
    }

    static class BaoCaoNhanVien { // CHI lo tao noi dung bao cao
        String tao(NhanVien nv) {
            return "Bao cao luong: " + nv.ten + " - " + nv.luong;
        }
    }

    static class LuuTruBaoCao { // CHI lo luu tru
        void luuVaoFile(String noiDung, String duongDan) {
            System.out.println("(gia lap) Da ghi '" + noiDung + "' vao " + duongDan);
        }
    }

    public static void main(String[] args) {
        System.out.println("-- Sau khi tach theo SRP --");
        NhanVien nv = new NhanVien("Nguyen Van A", 15_000_000);
        BaoCaoNhanVien baoCao = new BaoCaoNhanVien();
        LuuTruBaoCao luuTru = new LuuTruBaoCao();

        String noiDung = baoCao.tao(nv);
        luuTru.luuVaoFile(noiDung, "baocao.txt");

        System.out.println("Doi cach TINH BAO CAO chi can sua BaoCaoNhanVien.");
        System.out.println("Doi NOI LUU TRU (vi du sang database) chi can sua LuuTruBaoCao.");
        System.out.println("NhanVien khong bi anh huong boi ca hai thay doi tren.");
    }
}
