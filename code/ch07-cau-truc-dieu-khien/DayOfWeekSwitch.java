public class DayOfWeekSwitch {
    public static void main(String[] args) {
        int thu = 3;

        // switch statement kieu cu - can 'break' o moi nhanh de tranh fall-through
        switch (thu) {
            case 1:
                System.out.println("(statement) Thu Hai");
                break;
            case 2:
                System.out.println("(statement) Thu Ba");
                break;
            case 3:
                System.out.println("(statement) Thu Tu");
                break;
            case 7:
                System.out.println("(statement) Chu Nhat");
                break;
            default:
                System.out.println("(statement) Ngay khong hop le");
        }

        // switch expression kieu moi (Java 14+) - tra ve gia tri truc tiep, khong can break
        String tenThu = switch (thu) {
            case 1 -> "Thu Hai";
            case 2 -> "Thu Ba";
            case 3 -> "Thu Tu";
            case 4 -> "Thu Nam";
            case 5 -> "Thu Sau";
            case 6 -> "Thu Bay";
            case 7 -> "Chu Nhat";
            default -> "Ngay khong hop le";
        };
        System.out.println("(expression) " + tenThu);

        // Vi du fall-through CO CHU DICH: gop nhieu case dung chung 1 xu ly
        int thang = 4;
        int soNgay;
        switch (thang) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                soNgay = 31;
                break;
            case 4: case 6: case 9: case 11:
                soNgay = 30;
                break;
            case 2:
                soNgay = 28; // bo qua nam nhuan de vi du don gian
                break;
            default:
                soNgay = 0;
        }
        System.out.println("Thang " + thang + " co " + soNgay + " ngay");
    }
}
