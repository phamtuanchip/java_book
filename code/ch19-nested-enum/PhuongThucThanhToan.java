// Enum voi phuong thuc abstract, MOI hang so tu cung cap THAN HAM RIENG cua no
// (goi la "constant-specific class body") - huu ich khi moi hang so co hanh vi khac
// han nhau, thay vi viet mot khoi if/switch dai trong MOT phuong thuc chung
public enum PhuongThucThanhToan {
    TIEN_MAT {
        @Override
        public void xuLy(double soTien) {
            System.out.println("Nhan " + soTien + " tien mat, khong tinh phi.");
        }
    },
    THE_TIN_DUNG {
        @Override
        public void xuLy(double soTien) {
            double phi = soTien * 0.02;
            System.out.println("Tru the " + soTien + " + phi " + phi + " = " + (soTien + phi));
        }
    },
    CHUYEN_KHOAN {
        @Override
        public void xuLy(double soTien) {
            System.out.println("Chuyen khoan " + soTien + ", cho xac nhan tu ngan hang.");
        }
    };

    public abstract void xuLy(double soTien);
}
