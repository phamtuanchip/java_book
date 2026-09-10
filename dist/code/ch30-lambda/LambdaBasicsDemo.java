public class LambdaBasicsDemo {
    // Functional interface: interface CHI CO DUNG MOT phuong thuc abstract.
    // @FunctionalInterface la tuy chon, nhung NEN dung - trinh bien dich se BAO LOI
    // neu ai do lo them phuong thuc abstract thu hai vao interface nay
    @FunctionalInterface
    interface PhepToan {
        int apDung(int a, int b);
    }

    public static void main(String[] args) {
        System.out.println("-- Cach 1: anonymous class (da hoc o Chuong 19) --");
        PhepToan congAnonymous = new PhepToan() {
            @Override
            public int apDung(int a, int b) {
                return a + b;
            }
        };
        System.out.println("3 + 5 = " + congAnonymous.apDung(3, 5));

        System.out.println("-- Cach 2: lambda expression - CHINH XAC cung y nghia, ngan gon hon nhieu --");
        PhepToan cong = (a, b) -> a + b;
        System.out.println("3 + 5 = " + cong.apDung(3, 5));

        // Lambda voi THAN HAM nhieu dong - can { } va return ro rang
        PhepToan congCoLog = (a, b) -> {
            System.out.println("Dang cong " + a + " va " + b);
            return a + b;
        };
        System.out.println("Ket qua: " + congCoLog.apDung(10, 20));

        // Cac phep toan khac, dung LAI CUNG MOT interface PhepToan
        PhepToan tru = (a, b) -> a - b;
        PhepToan nhan = (a, b) -> a * b;
        System.out.println("10 - 3 = " + tru.apDung(10, 3));
        System.out.println("10 * 3 = " + nhan.apDung(10, 3));

        // Lambda "bat" (capture) bien tu ngoai - bien do PHAI la effectively final
        // (khong bi gan lai gia tri sau khi khai bao, du khong ghi tu khoa 'final')
        int heSo = 100;
        PhepToan nhanHeSo = (a, b) -> (a + b) * heSo;
        System.out.println("(3 + 5) * heSo = " + nhanHeSo.apDung(3, 5));
        // heSo = 200; // <-- neu bo comment dong nay, lambda ben tren se LOI BIEN DICH
    }
}
