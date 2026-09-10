// implements AutoCloseable: cho phep dung trong try-with-resources, JVM tu goi
// close() khi ra khoi khoi try, KE CA khi co exception xay ra ben trong
public class NguonTaiNguyen implements AutoCloseable {
    private String ten;

    public NguonTaiNguyen(String ten) {
        this.ten = ten;
        System.out.println("Da mo tai nguyen: " + ten);
    }

    public void doc() {
        System.out.println("Dang doc du lieu tu: " + ten);
    }

    @Override
    public void close() {
        System.out.println("Da dong tai nguyen: " + ten);
    }
}
