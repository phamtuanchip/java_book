public class DeadlockDemo {
    static final Object khoaA = new Object();
    static final Object khoaB = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            synchronized (khoaA) {
                System.out.println("Thread-1: da giu khoaA, dang cho khoaB...");
                lamCham();
                synchronized (khoaB) { // Thread-1 CHO khoaB, trong khi Thread-2 dang GIU no
                    System.out.println("Thread-1: da giu ca hai khoa");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (khoaB) {
                System.out.println("Thread-2: da giu khoaB, dang cho khoaA...");
                lamCham();
                synchronized (khoaA) { // Thread-2 CHO khoaA, trong khi Thread-1 dang GIU no
                    System.out.println("Thread-2: da giu ca hai khoa");
                }
            }
        });

        System.out.println("Bat dau 2 thread - CHUONG TRINH SE TREO (deadlock), doi khoang 3 giay roi tu ket thuc de minh hoa");
        thread1.start();
        thread2.start();

        // Doi toi da 3 giay - neu deadlock xay ra (nhu du kien), hai thread se
        // KHONG BAO GIO ket thuc, nen join(3000) se HET HAN thay vi thanh cong
        thread1.join(3000);
        thread2.join(3000);

        System.out.println("Thread-1 con song (bi ket deadlock)? " + thread1.isAlive());
        System.out.println("Thread-2 con song (bi ket deadlock)? " + thread2.isAlive());
        System.out.println("Chuong trinh chinh ket thuc - nhung 2 thread tren co the van con 'treo' vinh vien.");
        System.exit(0); // BAT BUOC thoat cuong buc, vi 2 thread deadlock se KHONG BAO GIO tu ket thuc
    }

    static void lamCham() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
