public class Main {
    public static void main(String[] args) {
        // 'new' tao ra MOT OBJECT MOI trong bo nho, goi constructor tuong ung
        SinhVien sv1 = new SinhVien("Nguyen Van A", new int[]{8, 7, 9});
        SinhVien sv2 = new SinhVien("Tran Thi B", new int[]{10, 9, 10});

        // sv1 va sv2 la HAI OBJECT DOC LAP, moi object tu mang du lieu rieng
        sv1.inThongTin();
        sv2.inThongTin();

        // Truy cap truc tiep field (co the lam vay vi field dang khai bao khong co
        // access modifier rieng - Chuong 14 se hoc cach GIOI HAN truy cap nay, ly do
        // vi sao no khong an toan trong thuc te)
        System.out.println(sv1.ten + " co " + sv1.diem.length + " diem");

        // Dung constructor khong tham so
        SinhVien sv3 = new SinhVien();
        sv3.inThongTin();

        // Sua du lieu cua sv3 SAU KHI da tao object
        sv3.ten = "Le Van C";
        sv3.diem = new int[]{6, 7};
        sv3.inThongTin();

        // Moi object la MOT VUNG NHO RIENG - sua sv3 KHONG anh huong sv1, sv2
        System.out.println(sv1.ten + " van khong doi: " + sv1.ten);
    }
}
