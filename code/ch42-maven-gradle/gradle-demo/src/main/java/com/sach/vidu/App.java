package com.sach.vidu;

import com.google.gson.Gson;

public class App {
    public static void main(String[] args) {
        SinhVien sv = new SinhVien("Nguyen Van A", 20);

        // Dung Gson (thu vien ngoai) - KHONG can tu tai .jar hay ghep -cp thu cong
        // nhu Chuong 35, Maven da lo het khi build
        Gson gson = new Gson();
        String json = gson.toJson(sv);
        System.out.println("JSON: " + json);
    }
}
