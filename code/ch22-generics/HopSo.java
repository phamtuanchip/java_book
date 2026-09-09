// Bounded type parameter: <T extends Number> gioi han T CHI duoc la Number hoac
// lop con cua Number (Integer, Double, Long...) - nho vay ben trong class DUOC PHEP
// goi cac phuong thuc cua Number nhu doubleValue(), du KHONG biet T cu the la gi
public class HopSo<T extends Number> {
    private T giaTri;

    public HopSo(T giaTri) {
        this.giaTri = giaTri;
    }

    public double layGiaTriThuc() {
        return giaTri.doubleValue(); // hop le vi MOI Number deu co doubleValue()
    }
}
