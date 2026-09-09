public class Main {
    public static void main(String[] args) {
        int a = MathUtils.square(4);
        int b = MathUtils.square(7);
        System.out.println("4 binh phuong la " + a);
        System.out.println("7 binh phuong la " + b);
        System.out.println("a co phai so chan? " + MathUtils.isEven(a));
        System.out.println("b co phai so chan? " + MathUtils.isEven(b));
    }
}
