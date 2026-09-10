import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PreparedStatementDemo {
    static final String URL = "jdbc:h2:mem:testdb2";

    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, "sa", "")) {
            try (Statement setup = conn.createStatement()) {
                setup.execute("CREATE TABLE nguoi_dung (id INT PRIMARY KEY, ten VARCHAR(100), mat_khau VARCHAR(100))");
                setup.execute("INSERT INTO nguoi_dung VALUES (1, 'admin', 'bimat123')");
            }

            System.out.println("-- CACH SAI: noi chuoi truc tiep vao SQL - RUI RO SQL INJECTION --");
            String tenNguoiDungDauVao = "admin' OR '1'='1"; // du lieu tu NGUOI DUNG nhap (gia lap ke tan cong)
            String sqlNguyHiem = "SELECT * FROM nguoi_dung WHERE ten = '" + tenNguoiDungDauVao + "'";
            System.out.println("Cau SQL thuc te se chay: " + sqlNguyHiem);
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlNguyHiem)) {
                int demKetQua = 0;
                while (rs.next()) {
                    demKetQua++;
                }
                System.out.println("So ban ghi TRA VE: " + demKetQua
                    + " (LE RA phai la 0 vi khong co ai ten dung nhu vay - day chinh la SQL injection)");
            }

            System.out.println("-- CACH DUNG: PreparedStatement voi tham so ('?') --");
            String sqlAnToan = "SELECT * FROM nguoi_dung WHERE ten = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlAnToan)) {
                pstmt.setString(1, tenNguoiDungDauVao); // gia tri duoc TRUYEN RIENG, KHONG noi vao chuoi SQL
                try (ResultSet rs = pstmt.executeQuery()) {
                    int demKetQua = 0;
                    while (rs.next()) {
                        demKetQua++;
                    }
                    System.out.println("So ban ghi tra ve: " + demKetQua + " (DUNG - PreparedStatement chan duoc injection)");
                }
            }

            System.out.println("-- PreparedStatement cho INSERT, tranh loi ky tu dac biet trong chuoi --");
            String sqlThem = "INSERT INTO nguoi_dung (id, ten, mat_khau) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlThem)) {
                pstmt.setInt(1, 2);
                pstmt.setString(2, "O'Brien"); // ten co dau nhay don - se GAY LOI neu noi chuoi truc tiep
                pstmt.setString(3, "matkhau456");
                pstmt.executeUpdate();
                System.out.println("Da them nguoi dung co ten chua dau nhay don thanh cong.");
            }
        }
    }
}
