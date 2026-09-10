import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// Cach HIEN DAI (java.nio.file, tu Java 7, hoan thien them o Java 11) - ngan gon
// hon nhieu so voi java.io cho cac tac vu don gian
public class ModernNioDemo {
    public static void main(String[] args) throws IOException {
        Path duongDan = Path.of("du_lieu_hien_dai.txt"); // Path thay the cho File cu

        System.out.println("-- Ghi TOAN BO noi dung bang MOT dong --");
        List<String> cacDong = List.of("Dong thu nhat", "Dong thu hai", "Dong thu ba");
        Files.write(duongDan, cacDong, StandardCharsets.UTF_8);
        System.out.println("Da ghi xong: " + duongDan);

        System.out.println("-- Doc TOAN BO file thanh List<String> bang MOT dong --");
        List<String> daDoc = Files.readAllLines(duongDan, StandardCharsets.UTF_8);
        for (int i = 0; i < daDoc.size(); i++) {
            System.out.println((i + 1) + ": " + daDoc.get(i));
        }

        System.out.println("-- Doc toan bo file thanh MOT String duy nhat (Java 11+) --");
        String noiDung = Files.readString(duongDan);
        System.out.println("Do dai noi dung: " + noiDung.length() + " ky tu");

        System.out.println("-- Them noi dung vao CUOI file (append) --");
        Files.writeString(duongDan, "\nDong duoc them vao sau", StandardCharsets.UTF_8,
            java.nio.file.StandardOpenOption.APPEND);
        System.out.println(Files.readString(duongDan));

        System.out.println("-- Kiem tra file ton tai, xoa file --");
        System.out.println("File ton tai? " + Files.exists(duongDan));
        Files.delete(duongDan);
        System.out.println("File ton tai sau khi xoa? " + Files.exists(duongDan));
    }
}
