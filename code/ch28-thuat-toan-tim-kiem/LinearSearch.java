public class LinearSearch {
    // Tim kiem tuyen tinh: duyet TUNG PHAN TU tu dau den cuoi, so sanh voi target.
    // KHONG can mang da sap xep - hoat dong dung voi mang BAT KY thu tu nao.
    // Do phuc tap: O(n) - truong hop xau nhat phai duyet het ca mang.
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // tim thay, tra ve chi so
            }
        }
        return -1; // khong tim thay
    }

    public static void main(String[] args) {
        int[] numbers = {10, 4, 2, 8, 5}; // KHONG can sap xep truoc
        int target = 8;
        int index = linearSearch(numbers, target);
        if (index != -1) {
            System.out.println("Tim thay tai chi so: " + index);
        } else {
            System.out.println("Khong tim thay");
        }
    }
}
