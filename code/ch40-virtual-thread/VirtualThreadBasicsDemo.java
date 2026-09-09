public class VirtualThreadBasicsDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("-- Platform thread (cach cu, tu Chuong 37) --");
        Thread platformThread = new Thread(() -> {
            System.out.println("Chay tren: " + Thread.currentThread());
        });
        platformThread.start();
        platformThread.join();

        System.out.println("-- Virtual thread (Java 21+) --");
        // Thread.ofVirtual(): tao MOT virtual thread, cu phap tuong tu Thread thuong
        Thread virtualThread = Thread.ofVirtual().start(() -> {
            System.out.println("Chay tren: " + Thread.currentThread());
        });
        virtualThread.join();

        System.out.println("-- Kiem tra la virtual thread hay khong --");
        System.out.println("platformThread.isVirtual() = " + platformThread.isVirtual());
        System.out.println("virtualThread.isVirtual()  = " + virtualThread.isVirtual());
    }
}
