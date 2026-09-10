import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Unit test (Chuong 41) cho phan logic QUAN TRONG NHAT cua du an - dung dung tinh
// than "thiet ke de test": KhoHang KHONG phu thuoc Scanner/System.out nen test
// duoc de dang, khong can gia lap ban phim/man hinh
class KhoHangTest {
    private KhoHang kho;

    @BeforeEach
    void setUp() {
        kho = new KhoHang();
        kho.themMoi(new SanPham("SP001", "But bi", 50, 5_000));
    }

    @Test
    void testThemMoiVaTimTheoMa() throws SanPhamKhongTonTaiException {
        SanPham sp = kho.timTheoMa("SP001");
        assertEquals("But bi", sp.getTen());
        assertEquals(50, sp.getSoLuong());
    }

    @Test
    void testTimSanPhamKhongTonTai() {
        assertThrows(SanPhamKhongTonTaiException.class, () -> kho.timTheoMa("SP999"));
    }

    @Test
    void testNhapKho() throws SanPhamKhongTonTaiException {
        kho.nhapKho("SP001", 20);
        assertEquals(70, kho.timTheoMa("SP001").getSoLuong());
    }

    @Test
    void testXuatKhoThanhCong() throws Exception {
        kho.xuatKho("SP001", 30);
        assertEquals(20, kho.timTheoMa("SP001").getSoLuong());
    }

    @Test
    void testXuatKhoVuotSoLuongTonKho() {
        assertThrows(SoLuongKhongDuException.class, () -> kho.xuatKho("SP001", 999));
    }

    @Test
    void testTimSanPhamSapHet() {
        kho.themMoi(new SanPham("SP002", "Vo", 3, 8_000));
        List<SanPham> sapHet = kho.timSanPhamSapHet(10);
        assertEquals(1, sapHet.size());
        assertEquals("SP002", sapHet.get(0).getMaSanPham());
    }

    @Test
    void testTinhTongGiaTriKho() {
        // SP001: 50 * 5000 = 250000
        assertEquals(250_000.0, kho.tinhTongGiaTriKho());
    }
}
