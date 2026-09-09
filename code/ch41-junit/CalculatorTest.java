import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Quy uoc dat ten: class test ten <TenLopCanTest>Test - JUnit va cac IDE/build tool
// deu tu dong nhan dien duoc quy uoc nay
class CalculatorTest {
    private Calculator calculator;

    // @BeforeEach: phuong thuc nay chay TRUOC MOI phuong thuc @Test - dam bao MOI
    // test bat dau voi mot object 'calculator' MOI, KHONG bi anh huong boi test khac
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Cong hai so duong")
    void testCongHaiSoDuong() {
        double ketQua = calculator.cong(2, 3);
        assertEquals(5, ketQua); // assertEquals(gia_tri_MONG_DOI, gia_tri_THUC_TE)
    }

    @Test
    @DisplayName("Cong so am")
    void testCongSoAm() {
        assertEquals(-1, calculator.cong(-3, 2));
    }

    @Test
    void testTru() {
        assertEquals(7, calculator.tru(10, 3));
    }

    @Test
    void testNhan() {
        assertEquals(20, calculator.nhan(4, 5));
    }

    @Test
    void testChiaBinhThuong() {
        assertEquals(2.5, calculator.chia(5, 2));
    }

    @Test
    @DisplayName("Chia cho 0 phai nem ArithmeticException")
    void testChiaChoKhong() {
        // assertThrows: xac nhan doan code (lambda) THUC SU nem dung loai exception
        // mong doi - day la cach test PHAN LOGIC XU LY LOI, khong chi phan "chay dung"
        ArithmeticException loi = assertThrows(
            ArithmeticException.class,
            () -> calculator.chia(5, 0)
        );
        assertEquals("Khong the chia cho 0", loi.getMessage());
    }
}
