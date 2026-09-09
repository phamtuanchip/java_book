package com.example.school;

// KHONG co 'public' - day la class package-private: chi cac class KHAC trong
// CUNG package (com.example.school) moi dung duoc no. Class Main o package
// com.example.app se KHONG the goi truc tiep class nay.
class MaSinhVienGenerator {
    static String sinhMa(String ten) {
        int tongMaKyTu = 0;
        for (int i = 0; i < ten.length(); i++) {
            tongMaKyTu += ten.charAt(i);
        }
        return "SV" + ten.length() + "-" + (tongMaKyTu % 1000);
    }
}
