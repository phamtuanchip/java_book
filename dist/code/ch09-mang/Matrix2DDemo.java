import java.util.Arrays;

public class Matrix2DDemo {
    public static void main(String[] args) {
        // Mang 2 chieu = mang cua cac mang. Khai bao ro kich thuoc: 3 hang, 4 cot
        int[][] banCo = new int[3][4];

        // Gan gia tri tung o
        for (int hang = 0; hang < banCo.length; hang++) {
            for (int cot = 0; cot < banCo[hang].length; cot++) {
                banCo[hang][cot] = hang * 10 + cot;
            }
        }

        // In ma tran
        System.out.println("-- Ma tran 3x4 --");
        for (int hang = 0; hang < banCo.length; hang++) {
            System.out.println(Arrays.toString(banCo[hang]));
        }

        // Khoi tao truc tiep voi gia tri co san
        int[][] soLieu = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // Tinh tong tat ca phan tu
        int tong = 0;
        for (int[] hang : soLieu) {
            for (int giaTri : hang) {
                tong += giaTri;
            }
        }
        System.out.println("Tong tat ca phan tu: " + tong);

        // Mang khong deu (jagged array) - moi hang co so cot khac nhau
        int[][] mangKhongDeu = new int[3][];
        mangKhongDeu[0] = new int[]{1};
        mangKhongDeu[1] = new int[]{1, 2};
        mangKhongDeu[2] = new int[]{1, 2, 3};
        System.out.println("-- Mang khong deu (jagged array) --");
        for (int[] hang : mangKhongDeu) {
            System.out.println(Arrays.toString(hang) + " (co " + hang.length + " phan tu)");
        }
    }
}
