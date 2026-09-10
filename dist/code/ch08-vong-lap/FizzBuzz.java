public class FizzBuzz {
    public static void main(String[] args) {
        // Bai toan kinh dien de luyen vong lap + if/else: in 1..100,
        // boi cua 3 in "Fizz", boi cua 5 in "Buzz", boi ca 2 in "FizzBuzz"
        for (int i = 1; i <= 100; i++) {
            if (i % 15 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }
}
