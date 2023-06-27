public class JumpSearch {
    public static int jumpSearch(int[] arr, int target) {
        int n = arr.length;
        int step = (int) Math.floor(Math.sqrt(n));
        int prev = 0;
        
        while (arr[Math.min(step, n) - 1] < target) {
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) {
                return -1; // Return -1 if not found
            }
        }
        
        while (arr[prev] < target) {
            prev++;
            if (prev == Math.min(step, n)) {
                return -1; // Return -1 if not found
            }
        }
        
        if (arr[prev] == target) {
            return prev; // Return the index if found
        }
        
        return -1; // Return -1 if not found
    }
    
    public static void main(String[] args) {
        int[] numbers = {2, 4, 5, 8, 10, 12, 15};
        int target = 8;
        int index = jumpSearch(numbers, target);
        if (index != -1) {
            System.out.println("Element found at index: " + index);
        } else {
            System.out.println("Element not found");
        }
    }
}
