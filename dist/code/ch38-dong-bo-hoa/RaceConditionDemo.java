public class RaceConditionDemo {
    static int demKhongAnToan = 0;

    public static void main(String[] args) throws InterruptedException {
        int soThread = 10;
        int soLanTangMoiThread = 100_000;

        Thread[] danhSachThread = new Thread[soThread];
        for (int i = 0; i < soThread; i++) {
            danhSachThread[i] = new Thread(() -> {
                for (int j = 0; j < soLanTangMoiThread; j++) {
                    demKhongAnToan++; // KHONG AN TOAN khi nhieu thread cung sua bien nay
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
        System.out.println("Ket qua THUC TE   : " + demKhongAnToan);
        System.out.println("(Chay lai nhieu lan - gia tri THUC TE thuong KHAC ket qua mong doi,");
        System.out.println(" va co the KHAC NHAU giua cac lan chay - day la RACE CONDITION)");
    }
}
