public class LoopsDemo {
    public static void main(String[] args) {
        // for - dung khi biet truoc so lan lap
        System.out.println("-- for: dem tu 1 den 5 --");
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }

        // while - dung khi chua biet truoc so lan lap, phu thuoc dieu kien
        System.out.println("-- while: chia doi 100 den khi nho hon 1 --");
        double giaTri = 100;
        while (giaTri >= 1) {
            System.out.println("giaTri = " + giaTri);
            giaTri = giaTri / 2;
        }

        // do-while - luon chay it nhat 1 lan, kiem tra dieu kien SAU
        System.out.println("-- do-while: chay it nhat 1 lan --");
        int soLanThu = 0;
        do {
            soLanThu++;
            System.out.println("Lan thu " + soLanThu);
        } while (soLanThu < 3);

        // break - thoat vong lap ngay lap tuc
        System.out.println("-- break: dung khi tim thay so chia het cho 7 --");
        for (int i = 1; i <= 100; i++) {
            if (i % 7 == 0) {
                System.out.println("Tim thay: " + i);
                break;
            }
        }

        // continue - bo qua lan lap hien tai, tiep tuc lan sau
        System.out.println("-- continue: chi in so le tu 1 den 10 --");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue;
            }
            System.out.println("So le: " + i);
        }

        // Vong lap long nhau - bang cuu chuong 3x3
        System.out.println("-- vong lap long nhau: bang nhan 1..3 --");
        for (int a = 1; a <= 3; a++) {
            for (int b = 1; b <= 3; b++) {
                System.out.println(a + " x " + b + " = " + (a * b));
            }
        }

        // Nhan label de break/continue vong lap ngoai tu vong lap trong
        System.out.println("-- label break: dung ca 2 vong lap khi tim thay tich = 6 --");
        outerLoop:
        for (int a = 1; a <= 3; a++) {
            for (int b = 1; b <= 3; b++) {
                if (a * b == 6) {
                    System.out.println("Dung tai a=" + a + ", b=" + b);
                    break outerLoop;
                }
            }
        }

        // Vong lap vo han co dieu kien thoat ben trong (dung than trong)
        System.out.println("-- vong lap vo han voi dieu kien thoat --");
        int n = 1;
        while (true) {
            if (n > 3) {
                break;
            }
            System.out.println("n = " + n);
            n++;
        }
    }
}
