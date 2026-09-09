import java.util.logging.Level;
import java.util.logging.Logger;

// java.util.logging (viet tat: JUL) - co san TRONG JDK, khong can thu vien ngoai,
// nen chay duoc ngay bang javac/java thuan. Du an thuc te thuong dung SLF4J +
// Logback/Log4j2 (xem giai thich trong chuong) - can Maven/Gradle (Chuong 42) de
// quan ly, nen chuong nay minh hoa bang JUL cho DE CHAY TRUOC, y tuong la NHU NHAU
public class LoggingDemo {
    private static final Logger logger = Logger.getLogger(LoggingDemo.class.getName());

    public static void main(String[] args) {
        System.out.println("-- Van de cua System.out.println lam 'logging' --");
        System.out.println("Khong co MUC DO (level), khong co THOI GIAN, khong TAT duoc rieng le");

        System.out.println();
        System.out.println("-- Cac muc do (level) cua Logger, tu THAP den CAO --");
        logger.fine("FINE: chi tiet gO_LOI, thuong AN trong moi truong that (production)");
        logger.info("INFO: thong tin binh thuong, vi du 'da khoi dong xong'");
        logger.warning("WARNING: co the co van de, nhung chuong trinh VAN chay tiep duoc");
        logger.severe("SEVERE: loi nghiem trong, can chu y ngay");

        System.out.println();
        System.out.println("-- Ghi log kem exception --");
        try {
            int ketQua = 10 / 0;
        } catch (ArithmeticException e) {
            // Ghi CA thong diep LAN chi tiet exception (stack trace) - huu ich hon
            // NHIEU so voi chi System.out.println(e.getMessage())
            logger.log(Level.SEVERE, "Loi khi tinh toan", e);
        }

        System.out.println();
        System.out.println("-- Doi muc do toi thieu duoc ghi (loc bot log khong can thiet) --");
        logger.setLevel(Level.WARNING); // chi ghi WARNING tro len, INFO/FINE se bi AN
        logger.info("Dong nay se KHONG hien ra vi INFO thap hon WARNING");
        logger.warning("Dong nay VAN hien ra vi WARNING dat muc toi thieu");
    }
}
