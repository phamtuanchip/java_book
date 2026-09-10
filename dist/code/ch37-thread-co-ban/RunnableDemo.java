// Cach 2 (KHUYEN DUNG hon): implements Runnable, roi truyen vao Thread. Uu diem:
// class cua ban VAN CON "cho trong" de extends mot class khac neu can (Chuong 16 -
// Java khong ho tro da ke thua CLASS, nhung Runnable la INTERFACE nen khong bi han
// che nay)
class TacVuInDem implements Runnable {
    private String ten;

    public TacVuInDem(String ten) {
        this.ten = ten;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(ten + " dem: " + i);
        }
    }
}

public class RunnableDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new TacVuInDem("Thread-X"));
        Thread t2 = new Thread(new TacVuInDem("Thread-Y"));

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("-- Cach ngan gon hon: Runnable bang lambda (Chuong 30) --");
        // Runnable la mot functional interface (chi co 1 phuong thuc abstract: run())
        Thread t3 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Thread-Lambda dem: " + i);
            }
        });
        t3.start();
        t3.join();

        System.out.println("Chuong trinh ket thuc.");
    }
}
