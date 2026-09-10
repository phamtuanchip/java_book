import java.util.ArrayList;
import java.util.List;

public class ObserverDemo {
    // Observer: MOT object ("Subject" - chu the) tu dong THONG BAO cho mot danh
    // sach cac object khac ("Observer" - nguoi quan sat) moi khi trang thai cua no
    // thay doi, ma Subject KHONG can biet CHI TIET tung Observer lam gi voi thong
    // bao do
    interface NguoiQuanSat {
        void nhanThongBao(double giaMoi);
    }

    static class ManHinhGiaCoPhieu implements NguoiQuanSat {
        private String ten;

        ManHinhGiaCoPhieu(String ten) {
            this.ten = ten;
        }

        @Override
        public void nhanThongBao(double giaMoi) {
            System.out.println("[" + ten + "] Cap nhat gia moi: " + giaMoi);
        }
    }

    static class CanhBaoGiaThap implements NguoiQuanSat {
        private double nguongCanhBao;

        CanhBaoGiaThap(double nguongCanhBao) {
            this.nguongCanhBao = nguongCanhBao;
        }

        @Override
        public void nhanThongBao(double giaMoi) {
            if (giaMoi < nguongCanhBao) {
                System.out.println("[CANH BAO] Gia (" + giaMoi + ") xuong duoi nguong " + nguongCanhBao + "!");
            }
        }
    }

    // Subject: tu quan ly danh sach nguoi quan sat, tu thong bao khi co thay doi
    static class CoPhieu {
        private String maCoPhieu;
        private double gia;
        private List<NguoiQuanSat> danhSachQuanSat = new ArrayList<>();

        CoPhieu(String maCoPhieu, double giaBanDau) {
            this.maCoPhieu = maCoPhieu;
            this.gia = giaBanDau;
        }

        void dangKy(NguoiQuanSat nguoiQuanSat) {
            danhSachQuanSat.add(nguoiQuanSat);
        }

        void huyDangKy(NguoiQuanSat nguoiQuanSat) {
            danhSachQuanSat.remove(nguoiQuanSat);
        }

        void capNhatGia(double giaMoi) {
            this.gia = giaMoi;
            System.out.println("== " + maCoPhieu + " thay doi gia thanh " + giaMoi + " ==");
            for (NguoiQuanSat nqs : danhSachQuanSat) { // THONG BAO cho TAT CA nguoi dang ky
                nqs.nhanThongBao(giaMoi);
            }
        }
    }

    public static void main(String[] args) {
        CoPhieu coPhieu = new CoPhieu("JAVA", 100.0);

        ManHinhGiaCoPhieu manHinh1 = new ManHinhGiaCoPhieu("Man hinh A");
        ManHinhGiaCoPhieu manHinh2 = new ManHinhGiaCoPhieu("Man hinh B");
        CanhBaoGiaThap canhBao = new CanhBaoGiaThap(90.0);

        coPhieu.dangKy(manHinh1);
        coPhieu.dangKy(manHinh2);
        coPhieu.dangKy(canhBao);

        coPhieu.capNhatGia(105.0);
        coPhieu.capNhatGia(85.0); // se kich hoat canh bao

        System.out.println("-- Huy dang ky mot nguoi quan sat --");
        coPhieu.huyDangKy(manHinh2);
        coPhieu.capNhatGia(70.0); // manHinh2 se KHONG con nhan duoc thong bao nay
    }
}
