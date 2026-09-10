public class SealedDemo {
    // sealed: khai bao ro RANG DANH SACH DAY DU cac lop con duoc phep - KHONG mot
    // lop nao khac ngoai danh sach 'permits' co the extends class nay. Khac voi
    // abstract class thuong (Chuong 18), noi BAT KY class nao cung extends duoc
    sealed interface HinhDang permits HinhTron, HinhVuong, HinhTamGiac {
    }

    record HinhTron(double banKinh) implements HinhDang {
    }

    record HinhVuong(double canh) implements HinhDang {
    }

    record HinhTamGiac(double day, double chieuCao) implements HinhDang {
    }

    // Pattern matching cho switch (Java 21+): switch truc tiep tren KIEU cua
    // object, TU DONG ep kieu vao bien trong tung nhanh - khong can instanceof +
    // ep kieu thu cong nhu Chuong 17
    static double tinhDienTich(HinhDang hinh) {
        return switch (hinh) {
            case HinhTron h -> Math.PI * h.banKinh() * h.banKinh();
            case HinhVuong h -> h.canh() * h.canh();
            case HinhTamGiac h -> h.day() * h.chieuCao() / 2;
            // KHONG can 'default' - trinh bien dich BIET CHAC danh sach permits la
            // DAY DU, tu xac nhan da xu ly HET moi truong hop co the co
        };
    }

    public static void main(String[] args) {
        HinhDang[] danhSach = {
            new HinhTron(5),
            new HinhVuong(4),
            new HinhTamGiac(6, 3)
        };

        for (HinhDang hinh : danhSach) {
            System.out.println(hinh + " -> dien tich = " + tinhDienTich(hinh));
        }

        // KHONG the lam dieu nay - HinhDang la sealed, chi permits 3 class da liet ke:
        // class HinhChuNhat implements HinhDang { ... } // <-- loi bien dich neu dat o file khac ma khong sua 'permits'
    }
}
