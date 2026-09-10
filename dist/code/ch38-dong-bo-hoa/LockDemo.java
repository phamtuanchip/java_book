import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    static int dem = 0;
    static Lock khoa = new ReentrantLock();

    static void tangDem() {
        khoa.lock(); // GIU khoa - thread khac goi lock() se phai CHO
        try {
            dem++;
        } finally {
            khoa.unlock(); // BAT BUOC mo khoa trong finally, KE CA khi co loi xay ra ben trong try
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int soThread = 10;
        int soLan = 100_000;

        Thread[] cacThread = new Thread[soThread];
        for (int i = 0; i < soThread; i++) {
            cacThread[i] = new Thread(() -> {
                for (int j = 0; j < soLan; j++) {
                    tangDem();
                }
            });
        }
        for (Thread t : cacThread) t.start();
        for (Thread t : cacThread) t.join();

        System.out.println("Ket qua: " + dem + " (mong doi " + (soThread * soLan) + ")");

        System.out.println("-- Vi sao dung Lock thay vi synchronized? tryLock() --");
        Lock khoaThuNghiem = new ReentrantLock();
        khoaThuNghiem.lock();
        try {
            // tryLock() CO THE THU khoa ma KHONG bi CHAN VO THOI HAN nhu synchronized -
            // huu ich khi muon "thu roi bo cuoc" thay vi cho mai mai
            boolean daKhoaDuoc = khoaThuNghiem.tryLock();
            System.out.println("tryLock() tu chinh thread dang giu khoa: " + daKhoaDuoc + " (ReentrantLock cho phep 'tai khoa')");
        } finally {
            khoaThuNghiem.unlock();
            khoaThuNghiem.unlock(); // can unlock 2 LAN vi da lock 2 LAN (lock ban dau + tryLock thanh cong)
        }
    }
}
