public class StringBasicsDemo {
    public static void main(String[] args) {
        String ten = "Nguyen Van A";

        // Cac thao tac co ban
        System.out.println("Do dai: " + ten.length());
        System.out.println("Chu hoa: " + ten.toUpperCase());
        System.out.println("Chu thuong: " + ten.toLowerCase());
        System.out.println("Ky tu tai vi tri 0: " + ten.charAt(0));
        System.out.println("Chuoi con (0-6): " + ten.substring(0, 6));
        System.out.println("Chuoi con (tu 7): " + ten.substring(7));
        System.out.println("Vi tri cua 'Van': " + ten.indexOf("Van"));
        System.out.println("Co chua 'Nguyen' khong: " + ten.contains("Nguyen"));
        System.out.println("Thay 'A' bang 'B': " + ten.replace("A", "B"));
        System.out.println("Xoa khoang trang thua: [" + "  xin chao  ".trim() + "]");
        System.out.println("Tach chuoi theo dau cach: " + java.util.Arrays.toString(ten.split(" ")));

        // So sanh chuoi - LOI KINH DIEN: dung == thay vi equals
        String a = new String("hello");
        String b = new String("hello");
        System.out.println("-- So sanh chuoi --");
        System.out.println("a == b: " + (a == b));         // false! khac object trong bo nho
        System.out.println("a.equals(b): " + a.equals(b)); // true - so sanh NOI DUNG

        // String pool - literal giong nhau se dung CHUNG mot object trong bo nho
        String c = "hello";
        String d = "hello";
        System.out.println("c == d (deu la literal): " + (c == d)); // true - vi String pool

        // String la BAT BIEN (immutable) - moi thao tac "sua" tra ve CHUOI MOI
        String goc = "Java";
        String daSua = goc.toUpperCase();
        System.out.println("goc = " + goc + " (khong doi)");
        System.out.println("daSua = " + daSua + " (chuoi MOI)");

        // Noi chuoi bang + trong vong lap - kem hieu qua, xem StringBuilderDemo.java
        String ketQua = "";
        for (int i = 1; i <= 5; i++) {
            ketQua = ketQua + i; // moi lan lap tao ra MOT chuoi moi hoan toan
        }
        System.out.println("Noi bang +: " + ketQua);
    }
}
