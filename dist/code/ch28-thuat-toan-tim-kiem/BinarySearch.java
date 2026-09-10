public class BinarySearch {
    // Tim kiem nhi phan: BAT BUOC mang da SAP XEP TANG DAN truoc.
    // Moi buoc loai bo MOT NUA khong gian tim kiem con lai, bang cach so sanh voi
    // phan tu O GIUA (mid).
    // Do phuc tap: O(log n) - nhanh hon HAN linear search voi mang lon.
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // tranh tran so so voi (left + right) / 2

            if (arr[mid] == target) {
                return mid; // tim thay, tra ve chi so
            }

            if (arr[mid] < target) {
                left = mid + 1; // target o NUA BEN PHAI
            } else {
                right = mid - 1; // target o NUA BEN TRAI
            }
        }

        return -1; // khong tim thay
    }

    public static void main(String[] args) {
        int[] numbers = {2, 4, 5, 8, 10}; // PHAI da sap xep tang dan
        int target = 8;
        int index = binarySearch(numbers, target);
        if (index != -1) {
            System.out.println("Tim thay tai chi so: " + index);
        } else {
            System.out.println("Khong tim thay");
        }
    }
}
