import java.util.Objects;

public class RecordDemo {
    // record: khai bao MOT DONG, tu dong sinh constructor, getter (ten khong co
    // tien to "get"), equals(), hashCode(), toString() - dung cho DU LIEU BAT BIEN
    // (immutable), khong can code lap lai nhu class thuong (so sanh voi
    // SanPham.java o Chuong 20, phai TU VIET het cac phan nay)
    record DiemToaDo(int x, int y) {
    }

    // record CO THE co them phuong thuc TU VIET, va CO THE kiem tra hop le trong
    // "compact constructor" (constructor rut gon, khong can lap lai danh sach tham so)
    record KhoangCach(double giaTri) {
        KhoangCach {
            if (giaTri < 0) {
                throw new IllegalArgumentException("Khoang cach khong the am");
            }
        }

        double sangMet() {
            return giaTri;
        }

        double sangKilomet() {
            return giaTri / 1000;
        }
    }

    public static void main(String[] args) {
        DiemToaDo d1 = new DiemToaDo(3, 4);
        DiemToaDo d2 = new DiemToaDo(3, 4); // NOI DUNG giong het d1

        System.out.println("-- Getter TU SINH, khong co tien to 'get' --");
        System.out.println("x = " + d1.x() + ", y = " + d1.y());

        System.out.println("-- toString() TU SINH --");
        System.out.println(d1); // in ra: DiemToaDo[x=3, y=4]

        System.out.println("-- equals()/hashCode() TU SINH, so sanh theo NOI DUNG --");
        System.out.println("d1.equals(d2): " + d1.equals(d2)); // true - khac object nhung noi dung giong

        System.out.println("-- record la BAT BIEN - khong co setter --");
        // d1.x = 10; // <-- KHONG BIEN DICH DUOC, record khong co field co the gan lai

        System.out.println("-- Compact constructor kiem tra hop le --");
        KhoangCach k = new KhoangCach(1500);
        System.out.println(k.giaTri() + "m = " + k.sangKilomet() + "km");

        try {
            KhoangCach kSai = new KhoangCach(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Loi nhu mong doi: " + e.getMessage());
        }
    }
}
