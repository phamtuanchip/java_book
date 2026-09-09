public class OperatorsDemo {
    public static void main(String[] args) {
        // Toan tu so hoc
        int a = 17;
        int b = 5;
        System.out.println("a + b = " + (a + b));
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b = " + (a * b));
        System.out.println("a / b = " + (a / b));   // chia so nguyen - cat phan du
        System.out.println("a % b = " + (a % b));   // phep chia lay du

        // Chia so nguyen vs chia so thuc
        System.out.println("17 / 5 (int) = " + (17 / 5));
        System.out.println("17.0 / 5 (double) = " + (17.0 / 5));

        // Toan tu tang/giam
        int dem = 10;
        dem++;
        System.out.println("sau dem++, dem = " + dem);
        dem--;
        dem--;
        System.out.println("sau dem-- 2 lan, dem = " + dem);

        // Khac biet pre-increment va post-increment
        int x = 5;
        int y = x++; // gan gia tri CU cua x cho y, roi moi tang x
        System.out.println("x++: x = " + x + ", y = " + y);

        int m = 5;
        int n = ++m; // tang m TRUOC, roi gan gia tri MOI cho n
        System.out.println("++m: m = " + m + ", n = " + n);

        // Toan tu so sanh
        System.out.println("a > b: " + (a > b));
        System.out.println("a == b: " + (a == b));
        System.out.println("a != b: " + (a != b));

        // Toan tu logic
        boolean coVe = true;
        boolean duTuoi = false;
        System.out.println("coVe && duTuoi: " + (coVe && duTuoi));
        System.out.println("coVe || duTuoi: " + (coVe || duTuoi));
        System.out.println("!coVe: " + (!coVe));

        // Short-circuit evaluation
        System.out.println("Vi du short-circuit:");
        int[] mang = {};
        if (mang.length > 0 && mang[0] == 1) {
            System.out.println("khong bao gio in ra dong nay");
        } else {
            System.out.println("&& da 'tat' truoc khi kiem tra mang[0], tranh loi truy cap ngoai pham vi");
        }

        // Do uu tien toan tu
        int ketQua = 2 + 3 * 4;        // * uu tien hon +, ket qua 14 khong phai 20
        int ketQuaCoNgoac = (2 + 3) * 4; // ngoac don thay doi thu tu, ket qua 20
        System.out.println("2 + 3 * 4 = " + ketQua);
        System.out.println("(2 + 3) * 4 = " + ketQuaCoNgoac);
    }
}
