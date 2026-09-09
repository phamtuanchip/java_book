import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Class trung tam cua du an - GOM du lieu (Map, Chuong 26) va HANH VI hop le
// (Chuong 14 - encapsulation) vao mot cho, dung dung tinh than Chuong 12
public class KhoHang {
    // LinkedHashMap (Chuong 26): giu dung thu tu them san pham vao, tien theo doi
    private final Map<String, SanPham> danhSach = new LinkedHashMap<>();

    public void themMoi(SanPham sp) {
        danhSach.put(sp.getMaSanPham(), sp); // put() se GHI DE neu ma da ton tai
    }

    public SanPham timTheoMa(String maSanPham) throws SanPhamKhongTonTaiException {
        SanPham sp = danhSach.get(maSanPham);
        if (sp == null) {
            throw new SanPhamKhongTonTaiException(maSanPham);
        }
        return sp;
    }

    public void nhapKho(String maSanPham, int soLuongThem) throws SanPhamKhongTonTaiException {
        SanPham sp = timTheoMa(maSanPham); // tai su dung, tu nem loi neu khong ton tai
        sp.datSoLuong(sp.getSoLuong() + soLuongThem);
    }

    public void xuatKho(String maSanPham, int soLuongXuat)
            throws SanPhamKhongTonTaiException, SoLuongKhongDuException {
        SanPham sp = timTheoMa(maSanPham);
        if (soLuongXuat > sp.getSoLuong()) {
            throw new SoLuongKhongDuException(maSanPham, sp.getSoLuong(), soLuongXuat);
        }
        sp.datSoLuong(sp.getSoLuong() - soLuongXuat);
    }

    public void xoa(String maSanPham) throws SanPhamKhongTonTaiException {
        if (!danhSach.containsKey(maSanPham)) {
            throw new SanPhamKhongTonTaiException(maSanPham);
        }
        danhSach.remove(maSanPham);
    }

    public List<SanPham> layTatCa() {
        return new ArrayList<>(danhSach.values());
    }

    // Ung dung Stream API (Chuong 31): loc san pham co so luong duoi mot nguong
    public List<SanPham> timSanPhamSapHet(int nguong) {
        return danhSach.values().stream()
            .filter(sp -> sp.getSoLuong() < nguong)
            .sorted(Comparator.comparing(SanPham::getSoLuong))
            .collect(Collectors.toList());
    }

    public double tinhTongGiaTriKho() {
        return danhSach.values().stream()
            .mapToDouble(SanPham::tinhTongGiaTri)
            .sum();
    }

    // File I/O (Chuong 34) - luu toan bo kho ra file CSV (Chuong 35)
    public void luuRaFile(Path duongDan) throws IOException {
        List<String> dong = danhSach.values().stream()
            .map(SanPham::toCsvLine)
            .collect(Collectors.toList());
        Files.write(duongDan, dong);
    }

    public void docTuFile(Path duongDan) throws IOException {
        if (!Files.exists(duongDan)) {
            return; // chua co file nao tu truoc - kho bat dau rong, khong phai loi
        }
        danhSach.clear();
        List<String> dong = Files.readAllLines(duongDan);
        for (String d : dong) {
            if (d.isBlank()) {
                continue;
            }
            SanPham sp = SanPham.tuCsvLine(d);
            danhSach.put(sp.getMaSanPham(), sp);
        }
    }
}
