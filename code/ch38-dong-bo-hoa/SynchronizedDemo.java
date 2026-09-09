public class SynchronizedDemo {
    static int demAnToan = 0;

    // synchronized tren PHUONG THUC static: chi MOT thread duoc chay ben trong
    // phuong thuc nay TAI MOT THOI DIEM, cac thread khac PHAI CHO toi luot
    static synchronized void tangDem() {
        demAnToan++;
    }

    public static void main(String[] args) throws InterruptedException {
        int soThread = 10;
        int soLanTangMoiThread = 100_000;

        Thread[] danhSachThread = new Thread[soThread];
        for (int i = 0; i < soThread; i++) {
            danhSachThread[i] = new Thread(() -> {
                for (int j = 0; j < soLanTangMoiThread; j++) {
                    tangDem(); // goi qua phuong thuc synchronized
                }
            });
        }

        for (Thread t : danhSachThread) {
            t.start();
        }
        for (Thread t : danhSachThread) {
            t.join();
        }

        int ketQuaMongDoi = soThread * soLanTangMoiThread;
        System.out.println("Ket qua mong doi : " + ketQuaMongDoi);
        System.out.println("Ket qua THUC TE   : " + demAnToan);
        System.out.println("(Voi synchronized, ket qua LUON DUNG, moi lan chay)");

        System.out.println("-- synchronized tren MOT KHOI LENH, thay vi CA phuong thuc --");
        Object khoa = new Object(); // object bat ky, dung LAM "ma khoa" chung
        int[] demKhoiLenh = {0}; // dung mang 1 phan tu de "bat" (capture) duoc trong lambda (Chuong 30)

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50_000; i++) {
                synchronized (khoa) { // chi phan code BEN TRONG khoi nay bi khoa, khong phai ca phuong thuc
                    demKhoiLenh[0]++;
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50_000; i++) {
                synchronized (khoa) {
                    demKhoiLenh[0]++;
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Ket qua khoi lenh synchronized: " + demKhoiLenh[0] + " (mong doi 100000)");
    }
}
