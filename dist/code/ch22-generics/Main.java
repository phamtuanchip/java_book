public class Main {
    public static void main(String[] args) {
        System.out.println("-- Generic class HopChua<T> --");
        HopChua<String> hopTen = new HopChua<>("Nguyen Van A");
        HopChua<Integer> hopTuoi = new HopChua<>(25);

        System.out.println(hopTen);
        System.out.println(hopTuoi);

        String ten = hopTen.layNoiDung(); // KHONG can ep kieu - trinh bien dich tu biet la String
        System.out.println("Do dai ten: " + ten.length());

        // KHONG the lam dieu nay - sai kieu, trinh bien dich bao loi NGAY, khong doi
        // toi luc chay:
        // hopTuoi.datNoiDung("chuoi thay vi so"); // <-- loi bien dich neu bo comment

        System.out.println("-- Generic method --");
        String[] danhSachTen = {"An", "Binh", "Chi"};
        Utils.inMoiPhanTu(danhSachTen);

        Integer[] danhSachSo = {1, 2, 3};
        Utils.inMoiPhanTu(danhSachSo); // cung MOT phuong thuc, dung duoc voi NHIEU kieu khac nhau

        System.out.println("-- Bounded type: HopSo<T extends Number> --");
        HopSo<Integer> hopSoNguyen = new HopSo<>(42);
        HopSo<Double> hopSoThuc = new HopSo<>(3.14);
        System.out.println("Gia tri thuc cua hopSoNguyen: " + hopSoNguyen.layGiaTriThuc());
        System.out.println("Gia tri thuc cua hopSoThuc: " + hopSoThuc.layGiaTriThuc());

        // KHONG the lam dieu nay - String khong phai Number, vi pham 'extends Number':
        // HopSo<String> hopSai = new HopSo<>("khong phai so"); // <-- loi bien dich neu bo comment

        System.out.println("-- Wildcard: chap nhan moi kieu con cua Number --");
        Utils.inGiaTriSo(hopTuoi); // HopChua<Integer>
        Utils.inGiaTriSo(new HopChua<>(9.99)); // HopChua<Double>
    }
}
