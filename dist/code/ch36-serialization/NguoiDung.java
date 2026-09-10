import java.io.Serializable;

// implements Serializable: "danh dau" (marker interface - khong co phuong thuc nao
// can override) rang object cua class nay CO THE duoc chuyen thanh chuoi byte de
// luu xuong file/gui qua mang, va khoi phuc lai sau
public class NguoiDung implements Serializable {
    // serialVersionUID: "phien ban" cua cau truc class, dung de kiem tra tuong
    // thich khi doc lai file da serialize truoc do. NEN khai bao tuong minh (neu
    // khong Java tu sinh MOT GIA TRI dua tren cau truc class hien tai - chi can sua
    // class MOT CHUT la gia tri tu sinh doi, gay loi khi doc file cu)
    private static final long serialVersionUID = 1L;

    private String ten;
    private int tuoi;

    // transient: field nay se KHONG duoc luu khi serialize - dung cho du lieu nhay
    // cam (mat khau) hoac du lieu khong nen/khong the luu (ket noi mang, file handle...)
    private transient String matKhauTamThoi;

    public NguoiDung(String ten, int tuoi, String matKhauTamThoi) {
        this.ten = ten;
        this.tuoi = tuoi;
        this.matKhauTamThoi = matKhauTamThoi;
    }

    @Override
    public String toString() {
        return "NguoiDung{ten='" + ten + "', tuoi=" + tuoi + ", matKhauTamThoi=" + matKhauTamThoi + "}";
    }
}
