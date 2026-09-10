// Cach 1: extends Thread, override run() - DON GIAN de hoc truoc, nhung IT DUOC
// KHUYEN DUNG hon cach 2 (Runnable, xem RunnableDemo.java) trong thuc te, vi Java
// khong ho tro da ke thua class (Chuong 16) - extends Thread roi thi KHONG con
// "cho trong" de extends them class nao khac nua
class DemSo extends Thread {
    private String ten;

    public DemSo(String ten) {
        this.ten = ten;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(ten + " dem: " + i);
        }
    }
}

public class ExtendsThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        DemSo t1 = new DemSo("Thread-A");
        DemSo t2 = new DemSo("Thread-B");

        System.out.println("Trang thai truoc khi start: " + t1.getState()); // NEW

        t1.start(); // BAT DAU chay run() TREN MOT LUONG MOI - KHONG goi t1.run() truc tiep!
        t2.start();

        // Cho ca hai thread chay xong TRUOC KHI main tiep tuc - khong co dong nay,
        // main co the ket thuc TRUOC KHI t1/t2 in xong (thu tu KHONG DAM BAO)
        t1.join();
        t2.join();

        System.out.println("Trang thai sau khi ket thuc: " + t1.getState()); // TERMINATED
        System.out.println("Ca hai thread da chay xong, main tiep tuc.");
    }
}
