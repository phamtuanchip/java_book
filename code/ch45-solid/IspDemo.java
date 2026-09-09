public class IspDemo {
    // TRUOC (VI PHAM ISP): mot interface "beo" (fat interface) gom qua nhieu kha
    // nang khac nhau - MAY IN DON GIAN buoc phai implements CA scan() lan fax(),
    // du KHONG HO TRO, chi de bien dich duoc
    interface MayVanPhongDaNangViPham {
        void in(String noiDung);
        void scan(String noiDung);
        void fax(String noiDung);
        // Neu MayInDonGian (chi in duoc) phai implements interface nay, no BUOC
        // PHAI viet than ham cho ca scan()/fax() du KHONG HO TRO - thuong se nem
        // UnsupportedOperationException hoac de trong vo nghia, ca hai deu XAU
    }

    // SAU (TUAN THU ISP): tach thanh CAC interface NHO, moi class chi implements
    // dung nhung kha nang no THAT SU co
    interface CoTheIn {
        void in(String noiDung);
    }

    interface CoTheScan {
        void scan(String noiDung);
    }

    interface CoTheFax {
        void fax(String noiDung);
    }

    // May in don gian - CHI in duoc, khong bi ep phai co scan()/fax() vo nghia
    static class MayInDonGian implements CoTheIn {
        @Override
        public void in(String noiDung) {
            System.out.println("Dang in: " + noiDung);
        }
    }

    // May da nang - implements CA BA interface, vi NO THAT SU ho tro ca ba
    static class MayDaNang implements CoTheIn, CoTheScan, CoTheFax {
        @Override
        public void in(String noiDung) {
            System.out.println("[Da nang] Dang in: " + noiDung);
        }

        @Override
        public void scan(String noiDung) {
            System.out.println("[Da nang] Dang scan: " + noiDung);
        }

        @Override
        public void fax(String noiDung) {
            System.out.println("[Da nang] Dang fax: " + noiDung);
        }
    }

    public static void main(String[] args) {
        MayInDonGian mayDonGian = new MayInDonGian();
        mayDonGian.in("Tai lieu A");
        // mayDonGian.scan(...); // <-- KHONG TON TAI, vi MayInDonGian chi implements CoTheIn - dung nhu mong doi

        MayDaNang mayDaNang = new MayDaNang();
        mayDaNang.in("Tai lieu B");
        mayDaNang.scan("Tai lieu C");
        mayDaNang.fax("Tai lieu D");

        System.out.println("-- Ham chi can 'CoTheIn', khong quan tam may co scan/fax hay khong --");
        inTaiLieu(mayDonGian);
        inTaiLieu(mayDaNang); // MayDaNang CUNG la CoTheIn, dung duoc binh thuong
    }

    static void inTaiLieu(CoTheIn may) {
        may.in("Bao cao thang");
    }
}
