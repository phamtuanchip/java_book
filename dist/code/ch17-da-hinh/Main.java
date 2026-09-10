public class Main {
    public static void main(String[] args) {
        // UPCASTING: gan object lop con cho bien KIEU LOP CHA - luon hop le, TU DONG,
        // khong can ep kieu ro rang
        NhanVien nv1 = new NhanVien("Nguyen Van A", 10_000_000);
        NhanVien nv2 = new NhanVienBanHang("Tran Thi B", 8_000_000, 100_000_000);
        NhanVien nv3 = new NhanVienQuanLy("Le Van C", 15_000_000, 3_000_000);

        // Mang kieu NhanVien co the chua CA BA loai object khac nhau
        NhanVien[] danhSachNhanVien = { nv1, nv2, nv3 };

        // DA HINH: cung goi tinhLuong() tren tung phan tu, nhung MOI OBJECT TU CHAY
        // dung phien ban tinhLuong() cua CHINH LOP THAT SU cua no (khong phai lop khai
        // bao la NhanVien) - day goi la "dynamic binding" / lien ket dong, quyet dinh
        // LUC CHAY, khong phai luc bien dich
        System.out.println("-- Tinh luong da hinh cho tung nhan vien --");
        double tongQuyLuong = 0;
        for (NhanVien nv : danhSachNhanVien) {
            System.out.println(nv.getTen() + ": " + nv.tinhLuong());
            tongQuyLuong += nv.tinhLuong();
        }
        System.out.println("Tong quy luong: " + tongQuyLuong);

        // nv2 duoc KHAI BAO kieu NhanVien, nen KHONG goi truc tiep duoc phuong thuc
        // rieng cua NhanVienBanHang (vi du capNhatDoanhSo) - du ban chat object THAT SU
        // la NhanVienBanHang:
        //
        // nv2.capNhatDoanhSo(999); // <-- LOI BIEN DICH neu bo comment, vi kieu KHAI BAO la NhanVien

        // DOWNCASTING: ep tro lai kieu cu the hon, de goi duoc phuong thuc rieng cua no.
        // Luon kiem tra 'instanceof' TRUOC khi ep, tranh ClassCastException luc chay
        if (nv2 instanceof NhanVienBanHang) {
            NhanVienBanHang nvbh = (NhanVienBanHang) nv2; // ep kieu ro rang
            nvbh.capNhatDoanhSo(150_000_000);
            System.out.println("Sau khi cap nhat doanh so: " + nvbh.tinhLuong());
        }

        // Pattern matching cho instanceof (Java 16+) - gop kiem tra va ep kieu thanh MOT
        // dong, se hoc chi tiet o Chuong 33 (Java hien dai). Xem truoc o day:
        if (nv3 instanceof NhanVienQuanLy nvql) {
            System.out.println(nvql.getTen() + " la quan ly, luong: " + nvql.tinhLuong());
        }

        // Downcasting SAI kieu se nem ClassCastException luc CHAY, khong phai luc bien dich
        // NhanVienBanHang epSai = (NhanVienBanHang) nv1; // nv1 la NhanVien "thuan", KHONG phai NhanVienBanHang
    }
}
