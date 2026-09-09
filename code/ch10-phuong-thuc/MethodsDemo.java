public class MethodsDemo {

    public static void main(String[] args) {
        // Goi phuong thuc khong tham so, khong gia tri tra ve
        printWelcome();

        // Goi phuong thuc co tham so, co gia tri tra ve
        int tong = add(3, 5);
        System.out.println("3 + 5 = " + tong);

        // Overloading: cung ten "add" nhung khac so luong/kieu tham so
        System.out.println("3 + 5 + 7 = " + add(3, 5, 7));
        System.out.println("2.5 + 1.5 = " + add(2.5, 1.5));

        // Pham vi bien (scope): 'x' khai bao trong main khong lien quan gi
        // den 'x' khai bao ben trong phuong thuc scopeDemo
        int x = 100;
        scopeDemo();
        System.out.println("x trong main van la: " + x);

        // Java truyen tham so THEO GIA TRI (pass-by-value) - kieu nguyen thuy
        int soGoc = 10;
        System.out.println("Truoc khi goi tangGap10: soGoc = " + soGoc);
        tangGap10(soGoc);
        System.out.println("Sau khi goi tangGap10: soGoc = " + soGoc + " (KHONG doi!)");

        // Nhung voi mang (kieu tham chieu), sua NOI DUNG ben trong phuong thuc
        // se anh huong ra ben ngoai, vi ca hai deu tro toi cung mot mang
        int[] mang = {1, 2, 3};
        System.out.println("Truoc khi goi nhanDoiPhanTu: " + java.util.Arrays.toString(mang));
        nhanDoiPhanTu(mang);
        System.out.println("Sau khi goi nhanDoiPhanTu: " + java.util.Arrays.toString(mang) + " (CO doi!)");
    }

    // Phuong thuc khong tham so, khong gia tri tra ve (void)
    static void printWelcome() {
        System.out.println("Chao mung ban den voi Chuong 10!");
    }

    // Phuong thuc co 2 tham so int, tra ve int
    static int add(int a, int b) {
        return a + b;
    }

    // Overload 1: cung ten "add", 3 tham so int
    static int add(int a, int b, int c) {
        return a + b + c;
    }

    // Overload 2: cung ten "add", tham so kieu double
    static double add(double a, double b) {
        return a + b;
    }

    static void scopeDemo() {
        int x = 999; // day la BIEN KHAC, chi ton tai trong pham vi phuong thuc nay
        System.out.println("x trong scopeDemo = " + x);
    }

    static void tangGap10(int n) {
        n = n * 10; // chi thay doi BAN SAO cuc bo 'n', khong anh huong bien goc ben ngoai
        System.out.println("Ben trong tangGap10, n = " + n);
    }

    static void nhanDoiPhanTu(int[] mang) {
        for (int i = 0; i < mang.length; i++) {
            mang[i] = mang[i] * 2; // sua PHAN TU cua mang duoc tro toi tu ben ngoai
        }
    }
}
