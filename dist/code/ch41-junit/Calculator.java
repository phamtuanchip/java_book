// Class "duoi kiem tra" (system under test) cho chuong nay - CHI chua logic tinh
// toan thuan tuy, KHONG lien quan gi toi giao dien/nhap xuat - day la thiet ke TOT
// cho kiem thu: logic tach biet khoi I/O de test duoc DE DANG, khong can gia lap
// (mock) ban phim/man hinh
public class Calculator {
    public double cong(double a, double b) {
        return a + b;
    }

    public double tru(double a, double b) {
        return a - b;
    }

    public double nhan(double a, double b) {
        return a * b;
    }

    public double chia(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Khong the chia cho 0");
        }
        return a / b;
    }
}
