import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// java.util.function chua san CAC functional interface DUNG CHUNG rat pho bien,
// khong can tu dinh nghia interface rieng nhu PhepToan o LambdaBasicsDemo.java
public class FunctionalInterfacesDemo {
    public static void main(String[] args) {
        System.out.println("-- Predicate<T>: nhan T, tra ve boolean --");
        Predicate<Integer> laSoChan = n -> n % 2 == 0;
        System.out.println("10 la so chan? " + laSoChan.test(10));
        System.out.println("7 la so chan? " + laSoChan.test(7));

        System.out.println("-- Function<T, R>: nhan T, tra ve R (kieu KHAC) --");
        Function<String, Integer> doDaiChuoi = s -> s.length();
        System.out.println("Do dai 'Java': " + doDaiChuoi.apply("Java"));

        System.out.println("-- Consumer<T>: nhan T, KHONG tra ve gi (chi thuc hien hanh dong) --");
        Consumer<String> inHoaToanBo = s -> System.out.println(s.toUpperCase());
        inHoaToanBo.accept("xin chao");

        System.out.println("-- Supplier<T>: KHONG nhan gi, tra ve T --");
        Supplier<String> taoChaoMung = () -> "Chao mung ban den voi Java!";
        System.out.println(taoChaoMung.get());

        System.out.println("-- BiFunction<T, U, R>: nhan HAI tham so, tra ve R --");
        BiFunction<Integer, Integer, Integer> congHaiSo = (a, b) -> a + b;
        System.out.println("5 + 7 = " + congHaiSo.apply(5, 7));

        System.out.println("-- Ket hop cac functional interface: loc va bien doi danh sach --");
        java.util.List<Integer> danhSach = java.util.List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (int so : danhSach) {
            if (laSoChan.test(so)) { // dung LAI Predicate da dinh nghia o tren
                System.out.println(so + " la so chan");
            }
        }
    }
}
