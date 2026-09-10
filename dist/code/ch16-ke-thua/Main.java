public class Main {
    public static void main(String[] args) {
        NhanVien nv = new NhanVien("Nguyen Van A", 10_000_000);
        NhanVienBanHang nvbh = new NhanVienBanHang("Tran Thi B", 8_000_000, 100_000_000);
        NhanVienQuanLy nvql = new NhanVienQuanLy("Le Van C", 15_000_000, 3_000_000);

        nv.inThongTin();
        nvbh.inThongTin();
        nvql.inThongTin();

        // NhanVienBanHang va NhanVienQuanLy KE THUA getTen() tu NhanVien - khong can
        // viet lai
        System.out.println("Ten nhan vien ban hang: " + nvbh.getTen());

        // NhanVienBanHang co them phuong thuc RIENG (khong co o lop cha)
        nvbh.capNhatDoanhSo(200_000_000);
        System.out.println("Luong sau khi cap nhat doanh so: " + nvbh.tinhLuong());
    }
}
