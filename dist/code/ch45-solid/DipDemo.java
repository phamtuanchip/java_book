public class DipDemo {
    // TRUOC (VI PHAM DIP): DichVuThongBaoViPham (module CAP CAO, chua logic nghiep
    // vu quan trong) phu thuoc TRUC TIEP vao EmailSenderCuThe (module CAP THAP, chi
    // tiet cai dat) - muon doi sang gui SMS thay vi Email, BAT BUOC phai SUA
    // DichVuThongBaoViPham
    static class EmailSenderCuThe {
        void guiEmail(String noiDung) {
            System.out.println("[Email] " + noiDung);
        }
    }

    static class DichVuThongBaoViPham {
        private EmailSenderCuThe sender = new EmailSenderCuThe(); // phu thuoc TRUC TIEP vao class CU THE

        void thongBaoNguoiDung(String thongDiep) {
            sender.guiEmail(thongDiep);
        }
    }

    // SAU (TUAN THU DIP): CA HAI module CAP CAO va CAP THAP deu phu thuoc vao MOT
    // TRUU TUONG (interface) chung - giong het y tuong Strategy o Chuong 44
    interface KenhGui {
        void gui(String noiDung);
    }

    static class EmailSender implements KenhGui {
        @Override
        public void gui(String noiDung) {
            System.out.println("[Email] " + noiDung);
        }
    }

    static class SmsSender implements KenhGui {
        @Override
        public void gui(String noiDung) {
            System.out.println("[SMS] " + noiDung);
        }
    }

    static class DichVuThongBao {
        private KenhGui kenh; // phu thuoc vao TRUU TUONG (interface), KHONG phai class cu the

        // "Tiem" (inject) phu thuoc qua constructor - DichVuThongBao KHONG tu 'new'
        // ra kenh gui cu the nao, ma NHAN no tu ben ngoai
        DichVuThongBao(KenhGui kenh) {
            this.kenh = kenh;
        }

        void thongBaoNguoiDung(String thongDiep) {
            kenh.gui(thongDiep);
        }
    }

    public static void main(String[] args) {
        System.out.println("-- Vi pham DIP --");
        DichVuThongBaoViPham dv1 = new DichVuThongBaoViPham();
        dv1.thongBaoNguoiDung("Xin chao (chi gui duoc Email, code cung nhac)");

        System.out.println("-- Tuan thu DIP: doi kenh gui KHONG can sua DichVuThongBao --");
        DichVuThongBao dvEmail = new DichVuThongBao(new EmailSender());
        dvEmail.thongBaoNguoiDung("Xin chao qua Email");

        DichVuThongBao dvSms = new DichVuThongBao(new SmsSender());
        dvSms.thongBaoNguoiDung("Xin chao qua SMS");

        System.out.println("DichVuThongBao KHONG HE thay doi giua hai truong hop tren -");
        System.out.println("chi khac o KENH GUI duoc TRUYEN VAO tu ben ngoai.");
    }
}
