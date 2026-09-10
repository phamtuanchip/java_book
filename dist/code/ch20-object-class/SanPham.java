import java.util.Objects;

// implements Comparable<SanPham>: khai bao "SanPham co THU TU TU NHIEN", cho phep
// sap xep truc tiep bang Arrays.sort() ma khong can cung cap them gi khac
public class SanPham implements Comparable<SanPham> {
    private String ten;
    private double gia;

    public SanPham(String ten, double gia) {
        this.ten = ten;
        this.gia = gia;
    }

    public String getTen() {
        return ten;
    }

    public double getGia() {
        return gia;
    }

    // Moi class deu KE THUA toString() tu Object, mac dinh in ra thu vo nghia nhu
    // SanPham@1b6d3586. Override lai de in NOI DUNG co y nghia - println/noi chuoi
    // se TU DONG goi toString() nay
    @Override
    public String toString() {
        return "SanPham{ten='" + ten + "', gia=" + gia + "}";
    }

    // equals(): dinh nghia lai THE NAO LA "BANG NHAU" ve NOI DUNG, thay vi mac dinh
    // cua Object (chi bang nhau khi la CUNG MOT object trong bo nho, tuong duong ==)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;               // cung mot object - chac chan bang nhau
        if (o == null || getClass() != o.getClass()) return false; // khac kieu - khong the bang nhau
        SanPham sp = (SanPham) o;
        return Double.compare(sp.gia, gia) == 0 && Objects.equals(ten, sp.ten);
    }

    // QUY TAC BAT BUOC: hai object equals() tra ve true PHAI co hashCode() giong
    // nhau. Luon override CA HAI cung nhau, khong bao gio chi override mot trong hai
    @Override
    public int hashCode() {
        return Objects.hash(ten, gia);
    }

    // Comparable.compareTo(): dinh nghia THU TU TU NHIEN - o day sap xep theo GIA tang dan
    @Override
    public int compareTo(SanPham kia) {
        return Double.compare(this.gia, kia.gia);
    }
}
