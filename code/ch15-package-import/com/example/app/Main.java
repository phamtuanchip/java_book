package com.example.app;

// import: nap class SinhVien tu package com.example.school de dung trong file nay
import com.example.school.SinhVien;

public class Main {
    public static void main(String[] args) {
        SinhVien sv = new SinhVien("Nguyen Van A");
        System.out.println("Ten: " + sv.getTen());
        System.out.println("Ma sinh vien: " + sv.taoMaSinhVien());

        // KHONG the goi truc tiep MaSinhVienGenerator o day - thu bo comment dong duoi
        // day se thay loi bien dich, vi class do la package-private trong
        // com.example.school, khong "nhin thay" duoc tu com.example.app:
        //
        // com.example.school.MaSinhVienGenerator.sinhMa("test");
    }
}
