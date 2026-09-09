import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Callable KHAC Runnable (Chuong 37) o cho: Callable CO TRA VE GIA TRI (va
        // co the nem checked exception), Runnable thi khong
        Callable<Integer> tinhToanTonKem = () -> {
            System.out.println("Dang tinh toan...");
            Thread.sleep(1000); // gia lap tac vu ton thoi gian
            return 42;
        };

        // submit() tra ve NGAY LAP TUC mot Future - "giay hen" cho ket qua se co
        // TRONG TUONG LAI, khong CHO tac vu chay xong moi tra ve
        Future<Integer> ketQuaTuongLai = pool.submit(tinhToanTonKem);

        System.out.println("Da nop tac vu, main VAN TIEP TUC chay ngay, khong bi chan...");
        System.out.println("Dang lam vic khac trong luc cho...");

        // get() moi la noi THUC SU CHO ket qua - chan (block) neu tac vu CHUA xong
        int ketQua = ketQuaTuongLai.get();
        System.out.println("Ket qua nhan duoc: " + ketQua);

        pool.shutdown();
    }
}
