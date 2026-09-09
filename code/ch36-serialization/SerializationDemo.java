import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String tenFile = "nguoidung.ser";
        NguoiDung nd = new NguoiDung("Nguyen Van A", 25, "mat-khau-bi-mat-123");

        System.out.println("Truoc khi ghi: " + nd);

        System.out.println("-- Ghi object xuong file (serialize) --");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tenFile))) {
            out.writeObject(nd);
        }
        System.out.println("Da ghi xong: " + tenFile);

        System.out.println("-- Doc object tu file (deserialize) --");
        NguoiDung ndDaDoc;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(tenFile))) {
            ndDaDoc = (NguoiDung) in.readObject(); // BAT BUOC ep kieu, readObject() tra ve Object
        }
        System.out.println("Sau khi doc lai: " + ndDaDoc);
        System.out.println("(Luu y: matKhauTamThoi la null - vi field do khai bao 'transient', KHONG duoc luu)");

        java.nio.file.Files.deleteIfExists(java.nio.file.Path.of(tenFile));
    }
}
