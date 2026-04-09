import java.util.HashSet;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 5, 7, 8, 9, 10};
        String str = "I love ITIS";
        //
        // task1(array,6);
        task3(str);
    }
    public static void task1(int[] array,int target){
        for (int i = 0; i < array.length; i++){
            if (array[i]>target){
                int temp = i-1;
                System.out.println(temp);
                return;
            }
        }
    }
    public static boolean task2(int first,int second){
        return first == second*second;
    }
    public static void task3(String str){
        int count=0;
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i)==' '){
                count++;
            }
        }

        String[] ans = new String[count+1];
        for (int i = 0; i < ans.length; i++){
            ans[i]="";
        }
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i)!=' '){
                ans[index] += str.charAt(i);
            } else {
                index++;
            }
        }
        for (int i = ans.length-1; i >= 0; i--) {
            System.out.print(ans[i]+" ");
        }
    }

    public static int task4(int[] nums, int target){
        if (nums.length < 3) return 0;
        bubbleSort(nums);
        int closestSum = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }
                if (currentSum < target) {
                    left++;
                } else if (currentSum > target) {
                    right--;
                } else {
                    return currentSum;
                }
            }
        }
        return closestSum;
    }

    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }



    public static Integer task6(int[] arr1, int[] arr2, int[] arr3) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int num : arr1) {
            set1.add(num);
        }
        for (int num : arr2) {
            set2.add(num);
        }
        for (int num : arr3) {
            if (set1.contains(num) && set2.contains(num)) {
                return num;
            }
        }
        return null;
    }
}