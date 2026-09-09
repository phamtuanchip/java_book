public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                System.out.println("Thread bat dau chay, se ngu 1 giay...");
                Thread.sleep(1000); // TAM DUNG thread nay 1000ms, KHONG chan main thread
                System.out.println("Thread thuc day sau khi ngu.");
            } catch (InterruptedException e) {
                System.out.println("Thread bi ngat trong luc dang ngu!");
            }
        });

        System.out.println("Trang thai: " + t.getState()); // NEW - da tao nhung CHUA start

        t.start();
        System.out.println("Trang thai ngay sau start(): " + t.getState()); // RUNNABLE

        Thread.sleep(200); // cho main "ngu" mot chut de quan sat trang thai giua chung
        System.out.println("Trang thai giua chung (dang t.sleep ben trong): " + t.getState()); // TIMED_WAITING

        t.join(); // CHO t chay xong HOAN TOAN roi main moi tiep tuc
        System.out.println("Trang thai sau khi ket thuc: " + t.getState()); // TERMINATED

        System.out.println("-- Minh hoa KHONG DAM BAO thu tu giua cac thread doc lap --");
        for (int lan = 0; lan < 2; lan++) {
            Thread a = new Thread(() -> System.out.println("A"));
            Thread b = new Thread(() -> System.out.println("B"));
            a.start();
            b.start();
            a.join();
            b.join();
            System.out.println("(chay lan " + lan + " - thu tu A/B co the khac nhau moi lan chay)");
        }
    }
}
