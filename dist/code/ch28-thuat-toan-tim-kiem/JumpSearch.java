public class JumpSearch {
    // Tim kiem nhay buoc: BAT BUOC mang da sap xep. Y tuong: "nhay" theo tung KHOI
    // co kich thuoc can(n) thay vi tung phan tu mot (nhanh hon linear search), roi
    // tim kiem tuyen tinh TRONG khoi da xac dinh chua target.
    // Do phuc tap: O(sqrt(n)) - nam GIUA linear search O(n) va binary search O(log n).
    public static int jumpSearch(int[] arr, int target) {
        int n = arr.length;
        int step = (int) Math.floor(Math.sqrt(n)); // kich thuoc moi buoc nhay
        int prev = 0;

        // Buoc 1: NHAY theo tung khoi cho toi khi tim thay khoi CO THE chua target
        while (arr[Math.min(step, n) - 1] < target) {
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) {
                return -1;
            }
        }

        // Buoc 2: tim kiem TUYEN TINH trong pham vi khoi da xac dinh
        while (arr[prev] < target) {
            prev++;
            if (prev == Math.min(step, n)) {
                return -1;
            }
        }

        if (arr[prev] == target) {
            return prev;
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] numbers = {2, 4, 5, 8, 10, 12, 15}; // PHAI da sap xep tang dan
        int target = 8;
        int index = jumpSearch(numbers, target);
        if (index != -1) {
            System.out.println("Tim thay tai chi so: " + index);
        } else {
            System.out.println("Khong tim thay");
        }
    }
}
