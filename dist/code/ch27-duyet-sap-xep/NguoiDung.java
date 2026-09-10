public class NguoiDung {
    private String ten;
    private int tuoi;

    public NguoiDung(String ten, int tuoi) {
        this.ten = ten;
        this.tuoi = tuoi;
    }

    public String getTen() {
        return ten;
    }

    public int getTuoi() {
        return tuoi;
    }

    @Override
    public String toString() {
        return ten + "(" + tuoi + ")";
    }
}
