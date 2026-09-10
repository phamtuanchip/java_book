public class NguoiDung {
    private String email;
    private int tuoi;

    public NguoiDung(String email, int tuoi) {
        setEmail(email); // goi qua setter de duoc kiem tra hop le ngay ca luc khoi tao
        setTuoi(tuoi);
    }

    public String getEmail() {
        return email;
    }

    // Setter co kiem tra hop le - day la diem khac biet quan trong so voi field public:
    // moi lan gan gia tri MOI deu phai qua kiem tra nay, khong co duong tat nao khac
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            System.out.println("Canh bao: email khong hop le, giu nguyen gia tri cu");
            return;
        }
        this.email = email;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        if (tuoi < 0 || tuoi > 150) {
            System.out.println("Canh bao: tuoi khong hop le (" + tuoi + "), giu nguyen gia tri cu");
            return;
        }
        this.tuoi = tuoi;
    }
}
