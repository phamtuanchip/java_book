public class FactoryDemo {
    interface ThongBao {
        void gui(String noiDung);
    }

    static class ThongBaoEmail implements ThongBao {
        @Override
        public void gui(String noiDung) {
            System.out.println("[Email] Gui: " + noiDung);
        }
    }

    static class ThongBaoSms implements ThongBao {
        @Override
        public void gui(String noiDung) {
            System.out.println("[SMS] Gui: " + noiDung);
        }
    }

    static class ThongBaoPush implements ThongBao {
        @Override
        public void gui(String noiDung) {
            System.out.println("[Push] Gui: " + noiDung);
        }
    }

    // Factory: gom logic "TAO object LOAI NAO" vao MOT CHO DUY NHAT, thay vi rai
    // 'new ThongBaoEmail()'/'new ThongBaoSms()' khap noi trong code goi
    static class ThongBaoFactory {
        static ThongBao tao(String loai) {
            return switch (loai) {
                case "email" -> new ThongBaoEmail();
                case "sms" -> new ThongBaoSms();
                case "push" -> new ThongBaoPush();
                default -> throw new IllegalArgumentException("Loai thong bao khong ho tro: " + loai);
            };
        }
    }

    public static void main(String[] args) {
        // Code GOI khong can biet CHI TIET class cu the nao dang duoc tao - chi can
        // biet no nhan duoc mot 'ThongBao' hop le
        ThongBao tb1 = ThongBaoFactory.tao("email");
        ThongBao tb2 = ThongBaoFactory.tao("sms");
        ThongBao tb3 = ThongBaoFactory.tao("push");

        tb1.gui("Chao mung ban!");
        tb2.gui("Ma OTP cua ban la 123456");
        tb3.gui("Ban co thong bao moi");

        System.out.println("-- Loi khi loai khong ton tai --");
        try {
            ThongBaoFactory.tao("fax");
        } catch (IllegalArgumentException e) {
            System.out.println("Loi nhu mong doi: " + e.getMessage());
        }
    }
}
