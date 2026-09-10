package com.example.school;

// public: co the duoc import va dung tu package KHAC
public class SinhVien {
    private String ten;

    public SinhVien(String ten) {
        this.ten = ten;
    }

    public String getTen() {
        return ten;
    }

    public String taoMaSinhVien() {
        // Goi mot class package-private CUNG package - hop le vi cung nam trong
        // com.example.school
        return MaSinhVienGenerator.sinhMa(ten);
    }
}
