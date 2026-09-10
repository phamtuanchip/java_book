// abstract class: khong the 'new HinhHoc()' truc tiep, chi dung lam lop cha de ke thua
public abstract class HinhHoc {
    protected String tenHinh;

    public HinhHoc(String tenHinh) {
        this.tenHinh = tenHinh;
    }

    // Phuong thuc abstract - KHONG co than ham, MOI lop con BAT BUOC phai override
    public abstract double tinhDienTich();
    public abstract double tinhChuVi();

    // Phuong thuc THUONG (co than ham day du) - lop con ke thua nguyen, dung chung
    public void inThongTin() {
        System.out.println(tenHinh + ": dien tich = " + tinhDienTich() + ", chu vi = " + tinhChuVi());
    }
}
