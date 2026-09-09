import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        SanPham a = new SanPham("But bi", 5_000);
        SanPham b = new SanPham("But bi", 5_000); // NOI DUNG giong het 'a', nhung la object KHAC

        System.out.println("-- toString() --");
        System.out.println(a); // println tu goi a.toString()

        System.out.println("-- equals() vs == --");
        System.out.println("a == b: " + (a == b));           // false - khac object trong bo nho
        System.out.println("a.equals(b): " + a.equals(b));   // true - NOI DUNG giong nhau, da override equals

        System.out.println("-- hashCode() --");
        System.out.println("a.hashCode() == b.hashCode(): " + (a.hashCode() == b.hashCode())); // true, dung quy tac

        System.out.println("-- Comparable: sap xep theo thu tu TU NHIEN (gia) --");
        SanPham[] danhSach = {
            new SanPham("Vo", 8_000),
            new SanPham("But bi", 5_000),
            new SanPham("Thuoc ke", 3_000)
        };
        Arrays.sort(danhSach); // dung compareTo() da dinh nghia trong SanPham
        for (SanPham sp : danhSach) {
            System.out.println(sp);
        }

        System.out.println("-- Comparator: sap xep theo TIEU CHI KHAC, khong sua duoc class SanPham --");
        // Comparator cho phep sap xep theo TIEU CHI TUY CHON, khong phu thuoc vao
        // compareTo() da dinh san trong class - o day sap theo TEN thay vi gia
        Comparator<SanPham> theoTen = new Comparator<SanPham>() {
            @Override
            public int compare(SanPham sp1, SanPham sp2) {
                return sp1.getTen().compareTo(sp2.getTen());
            }
        };
        Arrays.sort(danhSach, theoTen);
        for (SanPham sp : danhSach) {
            System.out.println(sp);
        }
    }
}
