public class InterpolationSearch {
    // Tim kiem noi suy: BAT BUOC mang da sap xep, hoat dong TOT NHAT khi du lieu
    // phan bo DEU (uniformly distributed). Thay vi luon chia doi nhu binary search,
    // no UOC LUONG vi tri co the chua target dua tren gia tri (giong cach nguoi that
    // tim mot ten trong danh ba dien thoai - nhay thang toi gan vi tri uoc luong,
    // khong chia doi tung buoc).
    // Do phuc tap trung binh: O(log log n) voi du lieu phan bo deu, nhung O(n) trong
    // truong hop xau nhat (du lieu phan bo rat khong deu).
    public static int interpolationSearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right && target >= arr[left] && target <= arr[right]) {
            if (left == right) {
                if (arr[left] == target) {
                    return left;
                }
                return -1;
            }

            // Cong thuc noi suy: uoc luong VI TRI co kha nang chua target, dua theo
            // TY LE giua target va khoang gia tri [arr[left], arr[right]]
            int pos = left + ((target - arr[left]) * (right - left)) / (arr[right] - arr[left]);

            if (arr[pos] == target) {
                return pos;
            }

            if (arr[pos] < target) {
                left = pos + 1;
            } else {
                right = pos - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] numbers = {2, 4, 5, 8, 10, 12, 15}; // PHAI da sap xep tang dan
        int target = 8;
        int index = interpolationSearch(numbers, target);
        if (index != -1) {
            System.out.println("Tim thay tai chi so: " + index);
        } else {
            System.out.println("Khong tim thay");
        }
    }
}
