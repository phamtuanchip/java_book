import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

// Diem vao chuong trinh - CHI lo doc/hien menu, moi logic nghiep vu that su nam
// trong KhoHang.java (Chuong 45 - SRP: tach trach nhiem giao dien khoi logic)
public class Main {
    static final Path FILE_DU_LIEU = Path.of("kho_hang.csv");

    public static void main(String[] args) {
        KhoHang kho = new KhoHang();

        try {
            kho.docTuFile(FILE_DU_LIEU);
            System.out.println("Da doc du lieu tu " + FILE_DU_LIEU + " (neu co san).");
        } catch (IOException e) {
            System.out.println("Khong doc duoc file du lieu: " + e.getMessage());
        }

        if (kho.layTatCa().isEmpty()) {
            themDuLieuMauBanDau(kho);
        }

        Scanner scanner = new Scanner(System.in);
        boolean tiepTuc = true;

        while (tiepTuc) {
            hienThiMenu();
            String luaChon = scanner.nextLine().trim();

            try {
                switch (luaChon) {
                    case "1" -> danhSachSanPham(kho);
                    case "2" -> themSanPham(kho, scanner);
                    case "3" -> nhapKho(kho, scanner);
                    case "4" -> xuatKho(kho, scanner);
                    case "5" -> xoaSanPham(kho, scanner);
                    case "6" -> sanPhamSapHet(kho, scanner);
                    case "7" -> System.out.println("Tong gia tri kho: " + kho.tinhTongGiaTriKho());
                    case "0" -> tiepTuc = false;
                    default -> System.out.println("Lua chon khong hop le, thu lai.");
                }
            } catch (SanPhamKhongTonTaiException | SoLuongKhongDuException e) {
                // Bat CA HAI loai exception nghiep vu bang MOT khoi catch (Chuong 21 - multi-catch)
                System.out.println("Loi: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Loi: ban nhap sai dinh dang so, thu lai.");
            }
        }

        try {
            kho.luuRaFile(FILE_DU_LIEU);
            System.out.println("Da luu du lieu vao " + FILE_DU_LIEU);
        } catch (IOException e) {
            System.out.println("Khong luu duoc du lieu: " + e.getMessage());
        }

        System.out.println("Tam biet!");
    }

    static void hienThiMenu() {
        System.out.println();
        System.out.println("===== QUAN LY KHO HANG =====");
        System.out.println("1. Xem danh sach san pham");
        System.out.println("2. Them san pham moi");
        System.out.println("3. Nhap kho");
        System.out.println("4. Xuat kho");
        System.out.println("5. Xoa san pham");
        System.out.println("6. Xem san pham sap het hang");
        System.out.println("7. Tong gia tri kho");
        System.out.println("0. Luu va thoat");
        System.out.print("Chon: ");
    }

    static void danhSachSanPham(KhoHang kho) {
        List<SanPham> tatCa = kho.layTatCa();
        if (tatCa.isEmpty()) {
            System.out.println("Kho dang trong.");
            return;
        }
        for (SanPham sp : tatCa) {
            System.out.println(sp);
        }
    }

    static void themSanPham(KhoHang kho, Scanner scanner) {
        System.out.print("Ma san pham: ");
        String ma = scanner.nextLine().trim();
        System.out.print("Ten san pham: ");
        String ten = scanner.nextLine().trim();
        System.out.print("So luong: ");
        int soLuong = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Gia: ");
        double gia = Double.parseDouble(scanner.nextLine().trim());

        kho.themMoi(new SanPham(ma, ten, soLuong, gia));
        System.out.println("Da them san pham " + ma);
    }

    static void nhapKho(KhoHang kho, Scanner scanner) throws SanPhamKhongTonTaiException {
        System.out.print("Ma san pham can nhap kho: ");
        String ma = scanner.nextLine().trim();
        System.out.print("So luong nhap them: ");
        int soLuong = Integer.parseInt(scanner.nextLine().trim());

        kho.nhapKho(ma, soLuong);
        System.out.println("Da nhap kho thanh cong.");
    }

    static void xuatKho(KhoHang kho, Scanner scanner)
            throws SanPhamKhongTonTaiException, SoLuongKhongDuException {
        System.out.print("Ma san pham can xuat kho: ");
        String ma = scanner.nextLine().trim();
        System.out.print("So luong xuat: ");
        int soLuong = Integer.parseInt(scanner.nextLine().trim());

        kho.xuatKho(ma, soLuong);
        System.out.println("Da xuat kho thanh cong.");
    }

    static void xoaSanPham(KhoHang kho, Scanner scanner) throws SanPhamKhongTonTaiException {
        System.out.print("Ma san pham can xoa: ");
        String ma = scanner.nextLine().trim();
        kho.xoa(ma);
        System.out.println("Da xoa san pham " + ma);
    }

    static void sanPhamSapHet(KhoHang kho, Scanner scanner) {
        System.out.print("Nguong so luong (vi du 10): ");
        int nguong = Integer.parseInt(scanner.nextLine().trim());
        List<SanPham> sapHet = kho.timSanPhamSapHet(nguong);
        if (sapHet.isEmpty()) {
            System.out.println("Khong co san pham nao sap het hang.");
        } else {
            sapHet.forEach(System.out::println);
        }
    }

    static void themDuLieuMauBanDau(KhoHang kho) {
        kho.themMoi(new SanPham("SP001", "But bi", 50, 5_000));
        kho.themMoi(new SanPham("SP002", "Vo hoc sinh", 8, 8_000));
        kho.themMoi(new SanPham("SP003", "Thuoc ke", 30, 3_000));
        System.out.println("Chua co du lieu cu, da tao du lieu mau ban dau.");
    }
}
