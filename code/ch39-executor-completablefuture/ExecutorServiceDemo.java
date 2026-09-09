import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("-- Van de voi tu tao Thread thu cong --");
        System.out.println("Neu co 1000 tac vu nho, tao 1000 object Thread rieng rat TON KEM");
        System.out.println("(moi Thread chiem bo nho rieng, tao/huy Thread cung co chi phi)");

        System.out.println("-- ExecutorService: mot 'ho boi' (pool) thread duoc TAI SU DUNG --");
        // fixedThreadPool(4): CHI tao dung 4 thread, tai su dung cho MOI tac vu duoc
        // np vao, thay vi tao thread moi cho tung tac vu
        ExecutorService pool = Executors.newFixedThreadPool(4);

        for (int i = 1; i <= 10; i++) {
            int soThuTu = i; // bien cuc bo effectively final de dung trong lambda (Chuong 30)
            pool.submit(() -> {
                System.out.println("Tac vu " + soThuTu + " chay boi " + Thread.currentThread().getName());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // shutdown(): KHONG nhan them tac vu moi, nhung VAN cho cac tac vu DA NOP
        // chay xong het
        pool.shutdown();
        boolean daXongHet = pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Tat ca tac vu da chay xong? " + daXongHet);
        System.out.println("(chi co 4 thread duoc TAO, nhung xu ly het 10 tac vu - tai su dung)");
    }
}
