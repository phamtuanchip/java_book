import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VarDemo {
    public static void main(String[] args) {
        // var: trinh bien dich TU SUY LUAN kieu tu ve phai, KHONG phai "kieu dong"
        // (dynamic typing) nhu JavaScript/Python - kieu van CO DINH, chi la ban
        // KHONG PHAI GO no ra
        var ten = "Nguyen Van A";      // suy luan la String
        var tuoi = 25;                  // suy luan la int
        var danhSach = new ArrayList<String>(); // suy luan la ArrayList<String>

        System.out.println(ten + ", " + tuoi + " tuoi");
        danhSach.add("Java");
        System.out.println(danhSach);

        // KHONG the lam dieu nay - kieu VAN CO DINH sau khi suy luan, du dung 'var':
        // ten = 123; // <-- loi bien dich neu bo comment, vi 'ten' da duoc suy luan la String

        // 'var' dac biet huu ich khi kieu ben phai DA RO RANG va DAI DONG, tranh
        // lap lai thua thai:
        Map<String, List<Integer>> banDoCu = new java.util.HashMap<String, List<Integer>>();
        var banDoMoi = new java.util.HashMap<String, List<Integer>>(); // ngan gon hon nhieu

        // 'var' trong vong lap for
        for (var i = 0; i < 3; i++) {
            System.out.println("i = " + i);
        }

        // LUU Y: 'var' KHONG dung duoc cho field cua class, tham so phuong thuc,
        // hay khi khong co gia tri khoi tao ngay - chi dung duoc cho BIEN CUC BO co
        // gia tri khoi tao ro rang
    }
}
