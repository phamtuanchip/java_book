public class Greeter {
    public static void main(String[] args) {
        String name = "hoc vien";
        if (args.length > 0) {
            name = args[0];
        }
        System.out.println("Xin chao, " + name + "!");
        System.out.println("Day la doi so dong lenh nhan duoc: " + args.length + " doi so.");
    }
}
