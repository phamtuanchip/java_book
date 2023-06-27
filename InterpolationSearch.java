public class InterpolationSearch {
    public static int interpolationSearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right && target >= arr[left] && target <= arr[right]) {
            if (left == right) {
                if (arr[left] == target) {
                    return left; // Return the index if found
                }
                return -1; // Return -1 if not found
            }
            
            int pos = left + ((target - arr[left]) * (right - left)) / (arr[right] - arr[left]);
            
            if (arr[pos] == target) {
                return pos; // Return the index if found
            }
            
            if (arr[pos] < target) {
                left = pos + 1;
            } else {
                right = pos - 1;
            }
        }
        
        return -1; // Return -1 if not found
    }
    
    public static void main(String[] args) {
        int[] numbers = {2, 4, 5, 8, 10, 12, 15};
        int target = 8;
        int index = interpolationSearch(numbers, target);
        if (index != -1) {
            System.out.println("Element found at index: " + index);
        } else {
            System.out.println("Element not found");
        }
    }
}
