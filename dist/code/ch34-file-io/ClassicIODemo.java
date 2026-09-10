import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Cach CO DIEN (java.io) - da co tu Java 1.0, van rat pho bien trong code cu
public class ClassicIODemo {
    public static void main(String[] args) {
        String tenFile = "du_lieu_co_dien.txt";

        System.out.println("-- Ghi file bang BufferedWriter --");
        // try-with-resources (Chuong 21): tu dong close() ca hai resource, KE CA
        // khi co loi xay ra giua chung
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tenFile))) {
            writer.write("Dong thu nhat");
            writer.newLine(); // xuong dong - KHONG tu dong nhu println
            writer.write("Dong thu hai");
            writer.newLine();
            writer.write("Dong thu ba");
        } catch (IOException e) {
            // IOException la CHECKED exception (Chuong 21) - viet/doc file co the that
            // bai vi nhieu ly do (het dung luong dia, khong co quyen ghi...)
            System.out.println("Loi khi ghi file: " + e.getMessage());
        }
        System.out.println("Da ghi xong file: " + tenFile);

        System.out.println("-- Doc file bang BufferedReader --");
        try (BufferedReader reader = new BufferedReader(new FileReader(tenFile))) {
            String dong;
            int soThuTu = 1;
            while ((dong = reader.readLine()) != null) { // readLine() tra ve null khi HET file
                System.out.println(soThuTu + ": " + dong);
                soThuTu++;
            }
        } catch (IOException e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
        }
    }
}
