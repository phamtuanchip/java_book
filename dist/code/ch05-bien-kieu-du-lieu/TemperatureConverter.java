import java.util.Scanner;

public class TemperatureConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhap nhiet do theo do C: ");
        double celsius = scanner.nextDouble();

        double fahrenheit = celsius * 9 / 5 + 32;

        System.out.println(celsius + " do C = " + fahrenheit + " do F");

        scanner.close();
    }
}
