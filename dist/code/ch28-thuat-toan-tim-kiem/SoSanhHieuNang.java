import java.util.Arrays;

public class SoSanhHieuNang {
    public static void main(String[] args) {
        int kichThuoc = 2_000_000;
        int[] mang = new int[kichThuoc];
        for (int i = 0; i < kichThuoc; i++) {
            mang[i] = i * 2; // mang da sap xep, gia tri chan: 0, 2, 4, 6, ...
        }
        int target = kichThuoc - 2; // phan tu GAN CUOI - truong hop xau cho linear search

        System.out.println("So sanh tim kiem trong mang " + kichThuoc + " phan tu:");

        long t1 = System.nanoTime();
        int r1 = LinearSearch.linearSearch(mang, target);
        long t2 = System.nanoTime();
        System.out.println("Linear Search       : " + (t2 - t1) / 1000 + " micro giay, ket qua = " + r1);

        long t3 = System.nanoTime();
        int r2 = JumpSearch.jumpSearch(mang, target);
        long t4 = System.nanoTime();
        System.out.println("Jump Search         : " + (t4 - t3) / 1000 + " micro giay, ket qua = " + r2);

        long t5 = System.nanoTime();
        int r3 = BinarySearch.binarySearch(mang, target);
        long t6 = System.nanoTime();
        System.out.println("Binary Search       : " + (t6 - t5) / 1000 + " micro giay, ket qua = " + r3);

        long t7 = System.nanoTime();
        int r4 = InterpolationSearch.interpolationSearch(mang, target);
        long t8 = System.nanoTime();
        System.out.println("Interpolation Search: " + (t8 - t7) / 1000 + " micro giay, ket qua = " + r4);

        System.out.println("\nCa 4 thuat toan deu phai tra ve CUNG MOT chi so: " + Arrays.asList(r1, r2, r3, r4));
    }
}
