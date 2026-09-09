package com.sach.vidu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SinhVienTest {
    @Test
    void testGetTen() {
        SinhVien sv = new SinhVien("Tran Thi B", 21);
        assertEquals("Tran Thi B", sv.getTen());
    }

    @Test
    void testGetTuoi() {
        SinhVien sv = new SinhVien("Tran Thi B", 21);
        assertEquals(21, sv.getTuoi());
    }
}
