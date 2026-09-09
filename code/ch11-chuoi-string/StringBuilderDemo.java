public class StringBuilderDemo {
    public static void main(String[] args) {
        // StringBuilder - dung khi can noi/sua chuoi NHIEU LAN, vi du trong vong lap
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            sb.append(i);
            if (i < 5) {
                sb.append(", ");
            }
        }
        System.out.println("Ket qua StringBuilder: " + sb.toString());

        // StringBuilder co the sua noi dung TAI CHO, khong tao chuoi moi moi lan
        StringBuilder tb = new StringBuilder("Xin chao");
        tb.append(", Java!");
        tb.insert(0, ">> ");
        tb.replace(3, 8, "Hello");
        System.out.println("Sau khi sua: " + tb);
        System.out.println("Do dai hien tai: " + tb.length());

        tb.reverse();
        System.out.println("Dao nguoc: " + tb);

        // So sanh hieu nang: noi 50,000 lan bang String (+) vs StringBuilder
        int soLanNoi = 50_000;

        long batDau1 = System.nanoTime();
        String ketQuaString = "";
        for (int i = 0; i < soLanNoi; i++) {
            ketQuaString = ketQuaString + i;
        }
        long ketThuc1 = System.nanoTime();

        long batDau2 = System.nanoTime();
        StringBuilder ketQuaBuilder = new StringBuilder();
        for (int i = 0; i < soLanNoi; i++) {
            ketQuaBuilder.append(i);
        }
        long ketThuc2 = System.nanoTime();

        System.out.println("-- So sanh hieu nang (" + soLanNoi + " lan noi) --");
        System.out.println("String +          : " + (ketThuc1 - batDau1) / 1_000_000 + " ms");
        System.out.println("StringBuilder.append: " + (ketThuc2 - batDau2) / 1_000_000 + " ms");
    }
}
