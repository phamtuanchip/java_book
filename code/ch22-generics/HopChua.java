// Generic class: <T> la THAM SO KIEU (type parameter) - "T" chi la ten quy uoc
// (Type), se duoc THAY THE bang mot kieu cu the khi su dung, vi du HopChua<String>,
// HopChua<Integer>...
public class HopChua<T> {
    private T noiDung;

    public HopChua(T noiDung) {
        this.noiDung = noiDung;
    }

    public T layNoiDung() {
        return noiDung;
    }

    public void datNoiDung(T noiDungMoi) {
        this.noiDung = noiDungMoi;
    }

    @Override
    public String toString() {
        return "HopChua[" + noiDung + "]";
    }
}
