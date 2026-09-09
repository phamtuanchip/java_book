import java.util.Scanner;

public class GradeClassifier {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap diem (0-10): ");
        double diem = scanner.nextDouble();

        // if / else if / else co ban
        String xepLoai;
        if (diem >= 9.0) {
            xepLoai = "Xuat sac";
        } else if (diem >= 8.0) {
            xepLoai = "Gioi";
        } else if (diem >= 6.5) {
            xepLoai = "Kha";
        } else if (diem >= 5.0) {
            xepLoai = "Trung binh";
        } else {
            xepLoai = "Yeu";
        }
        System.out.println("Xep loai: " + xepLoai);

        // Toan tu ba ngoi (ternary) - thay the if/else ngan gon khi chi gan 1 gia tri
        String ketQua = (diem >= 5.0) ? "Dat" : "Khong dat";
        System.out.println("Ket qua: " + ketQua);

        scanner.close();
    }
}
