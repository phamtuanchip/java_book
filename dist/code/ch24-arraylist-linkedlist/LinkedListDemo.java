import java.util.LinkedList;
import java.util.List;

public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<String> danhSach = new LinkedList<>();
        danhSach.add("B");
        danhSach.add("C");

        // LinkedList co them cac phuong thuc THAO TAC O DAU/CUOI rat nhanh - day la
        // diem manh rieng cua no so voi ArrayList
        danhSach.addFirst("A");
        danhSach.addLast("D");
        System.out.println("Sau khi them dau/cuoi: " + danhSach);

        System.out.println("Phan tu dau: " + danhSach.getFirst());
        System.out.println("Phan tu cuoi: " + danhSach.getLast());

        danhSach.removeFirst();
        System.out.println("Sau khi xoa phan tu dau: " + danhSach);

        // So sanh thoi gian: them 100,000 phan tu vao DAU danh sach
        int soLuong = 100_000;

        List<Integer> arrayList = new java.util.ArrayList<>();
        long batDau1 = System.nanoTime();
        for (int i = 0; i < soLuong; i++) {
            arrayList.add(0, i); // them vao DAU - ArrayList phai DICH CHUYEN toan bo phan tu con lai
        }
        long ketThuc1 = System.nanoTime();

        List<Integer> linkedList = new LinkedList<>();
        long batDau2 = System.nanoTime();
        for (int i = 0; i < soLuong; i++) {
            linkedList.add(0, i); // them vao DAU - LinkedList chi can noi lai 1 lien ket
        }
        long ketThuc2 = System.nanoTime();

        System.out.println("-- So sanh them " + soLuong + " phan tu vao DAU danh sach --");
        System.out.println("ArrayList : " + (ketThuc1 - batDau1) / 1_000_000 + " ms");
        System.out.println("LinkedList: " + (ketThuc2 - batDau2) / 1_000_000 + " ms");
    }
}
