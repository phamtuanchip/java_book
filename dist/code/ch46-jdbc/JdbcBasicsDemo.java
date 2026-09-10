import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Dung H2 (co so du lieu nhung trong bo nho, khong can cai dat server rieng) de
// minh hoa JDBC ma khong can dung/cau hinh MySQL/PostgreSQL that. CAN tai
// h2-*.jar (xem README) de bien dich va chay file nay.
public class JdbcBasicsDemo {
    // URL ket noi: "jdbc:h2:mem:testdb" nghia la CSDL H2, luu TRONG BO NHO (mem),
    // ten "testdb" - du lieu MAT HET khi chuong trinh ket thuc (chi de hoc/thu nghiem)
    static final String URL = "jdbc:h2:mem:testdb";

    public static void main(String[] args) throws SQLException {
        // try-with-resources (Chuong 21): Connection la AutoCloseable, tu dong dong
        // ket noi khi ra khoi khoi try
        try (Connection conn = DriverManager.getConnection(URL, "sa", "")) {
            System.out.println("Da ket noi CSDL thanh cong.");

            try (Statement stmt = conn.createStatement()) {
                // Tao bang
                stmt.execute("CREATE TABLE sinh_vien (id INT PRIMARY KEY, ten VARCHAR(100), tuoi INT)");
                System.out.println("Da tao bang 'sinh_vien'.");

                // Them du lieu
                stmt.execute("INSERT INTO sinh_vien VALUES (1, 'Nguyen Van A', 20)");
                stmt.execute("INSERT INTO sinh_vien VALUES (2, 'Tran Thi B', 21)");
                stmt.execute("INSERT INTO sinh_vien VALUES (3, 'Le Van C', 19)");
                System.out.println("Da them 3 ban ghi.");

                // Truy van va doc ket qua
                System.out.println("-- Danh sach sinh vien --");
                try (ResultSet rs = stmt.executeQuery("SELECT id, ten, tuoi FROM sinh_vien ORDER BY id")) {
                    while (rs.next()) { // next() di chuyen toi dong TIEP THEO, tra ve false khi HET du lieu
                        int id = rs.getInt("id");
                        String ten = rs.getString("ten");
                        int tuoi = rs.getInt("tuoi");
                        System.out.println(id + " - " + ten + " - " + tuoi + " tuoi");
                    }
                }
            }
        }
        // Connection/Statement/ResultSet DEU da duoc dong tu dong o day, ke ca khi
        // co loi xay ra giua chung
    }
}
