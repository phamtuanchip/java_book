public class Main {
    public static void main(String[] args) {
        // KHONG the lam dieu nay - HinhHoc la abstract class:
        // HinhHoc h = new HinhHoc("test"); // <-- loi bien dich neu bo comment

        HinhTron tron = new HinhTron(5);
        HinhChuNhat chuNhat = new HinhChuNhat(4, 6);

        // inThongTin() la phuong thuc THUONG cua HinhHoc, ke thua nguyen, dung chung
        // cho ca hai lop con
        tron.inThongTin();
        chuNhat.inThongTin();

        // Da hinh qua abstract class - giong het co che da hinh o Chuong 17
        HinhHoc[] danhSachHinh = { tron, chuNhat };
        System.out.println("-- Da hinh qua abstract class --");
        for (HinhHoc h : danhSachHinh) {
            h.inThongTin();
        }

        // Goi phuong thuc tu interface
        System.out.println("-- Interface CoTheVe --");
        tron.ve();
        chuNhat.veDamNet(); // default method, ke thua tu interface, khong can tu viet

        CoTheVe.inHuongDan(); // static method cua interface, goi qua TEN INTERFACE

        // Interface thu hai
        System.out.println("-- Interface CoTheSoSanhDienTich --");
        System.out.println("Hinh tron co dien tich lon hon hinh chu nhat? " + tron.dienTichLonHon(chuNhat));
    }
}
