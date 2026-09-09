// Checked exception TU DINH NGHIA: extends Exception (khong phai RuntimeException),
// nen trinh bien dich BAT BUOC noi goi phai xu ly (try/catch hoac khai bao throws)
public class SoDuKhongDuException extends Exception {
    private double soThieu;

    public SoDuKhongDuException(String thongDiep, double soThieu) {
        super(thongDiep); // truyen thong diep loi cho lop cha Exception
        this.soThieu = soThieu;
    }

    public double getSoThieu() {
        return soThieu;
    }
}
