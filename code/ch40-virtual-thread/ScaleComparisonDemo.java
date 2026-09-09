import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ScaleComparisonDemo {
    public static void main(String[] args) throws InterruptedException {
        int soTacVu = 10_000;
        AtomicInteger demHoanThanh = new AtomicInteger(0);

        System.out.println("-- Chay " + soTacVu + " tac vu (moi tac vu 'ngu' 100ms mo phong cho I/O)"
            + " bang virtual thread pool --");

        long batDau = System.currentTimeMillis();

        // newVirtualThreadPerTaskExecutor(): MOI tac vu duoc chay tren MOT virtual
        // thread RIENG (khong gioi han so luong co dinh nhu fixedThreadPool o
        // Chuong 39) - JVM tu quan ly hang chuc nghin virtual thread hieu qua
        try (ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < soTacVu; i++) {
                pool.submit(() -> {
                    try {
                        Thread.sleep(100); // mo phong CHO I/O (goi API, doc file, truy van DB...)
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    demHoanThanh.incrementAndGet();
                });
            }
        } // try-with-resources (Chuong 21): ExecutorService cung la AutoCloseable tu Java 19+,
          // tu dong cho tat ca tac vu xong roi moi dong pool khi ra khoi khoi try

        long ketThuc = System.currentTimeMillis();
        System.out.println("Da hoan thanh: " + demHoanThanh.get() + "/" + soTacVu + " tac vu");
        System.out.println("Thoi gian: " + (ketThuc - batDau) + " ms");
        System.out.println("(Neu dung platform thread thuong cho tung nay tac vu CUNG LUC,");
        System.out.println(" nhieu he thong se het bo nho hoac cham han rat nhieu -");
        System.out.println(" virtual thread xu ly duoc quy mo nay MOT CACH BINH THUONG)");
    }
}
