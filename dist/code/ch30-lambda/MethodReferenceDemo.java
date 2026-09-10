import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferenceDemo {
    public static void main(String[] args) {
        System.out.println("-- Loai 1: tham chieu toi phuong thuc STATIC --");
        // Lambda: (a, b) -> Integer.sum(a, b)  <=>  Method reference: Integer::sum
        BiFunction<Integer, Integer, Integer> cong = Integer::sum;
        System.out.println("3 + 5 = " + cong.apply(3, 5));

        System.out.println("-- Loai 2: tham chieu toi phuong thuc INSTANCE cua MOT object cu the --");
        String ten = "Xin chao Java";
        // Lambda: () -> ten.toUpperCase()  <=>  Method reference: ten::toUpperCase
        Supplier<String> layChuHoa = ten::toUpperCase;
        System.out.println(layChuHoa.get());

        System.out.println("-- Loai 3: tham chieu toi phuong thuc INSTANCE, tren THAM SO duoc truyen vao --");
        // Lambda: s -> s.length()  <=>  Method reference: String::length
        Function<String, Integer> layDoDai = String::length;
        System.out.println("Do dai 'Java': " + layDoDai.apply("Java"));

        System.out.println("-- Loai 4: tham chieu toi CONSTRUCTOR --");
        // Lambda: () -> new java.util.ArrayList<Integer>()  <=>  Method reference: ArrayList::new
        Supplier<List<Integer>> taoDanhSachMoi = java.util.ArrayList::new;
        List<Integer> danhSach = taoDanhSachMoi.get();
        danhSach.add(1);
        danhSach.add(2);
        System.out.println("Danh sach vua tao: " + danhSach);

        System.out.println("-- Ap dung thuc te: sap xep bang method reference (nhu Chuong 27) --");
        List<String> ds = new java.util.ArrayList<>(List.of("Chuoi", "Ab", "Toan"));
        ds.sort(String::compareTo); // giong het Chuong 20/27, nhung viet gon bang method reference
        System.out.println(ds);
    }
}
