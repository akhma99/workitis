    import java.util.*;

    import static java.util.Collections.swap;

    //TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
    // click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
    public class Main {
        public static void main(String[] args) {
            int[] array1 = new int[]{1, 2, 1, 5, 3, 2};
            String[] str = new String[]{"апельсин", "мандарин", "банан"};
            //task3(array1,16);
            //task2(str);
            //task1(array1, 4);
            //task4(array1);

        }

        public static void task1(int[] array, int target) {
            Set<Integer> set = new HashSet<>();
            for (int i : array) {
                int result = target - i;
                if (set.contains(result)) {
                    System.out.println(i);
                    System.out.println(result);
                    return;
                }
                set.add(i);
            }
            ;
            System.out.println("такие числа не нашлись");
        }

        public static void task3(int[] array, int target) {
            int left = 0;
            int right = array.length - 1;
            while (left < right) {
                int sum = array[left] + array[right];
                if (sum == target) {
                    System.out.println("ответ:" + array[left] + " " + array[right]);
                    return;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
            System.out.println("такого нет");
        }

        public static List<String> task2(String[] array) {
            List<String> list = new ArrayList<>();
            for (String str : array) {
                list.add(str);
            }
            list.sort(Main::compareStrings);
            return list;
        }

        public static int compareStrings(String s1, String s2) {
            if (s1.equals(s2)) {
                return 0;
            }
            int length = Math.min(s1.length(), s2.length());
            for (int i = 0; i < length; i++) {
                if (s1.charAt(i) < s2.charAt(i)) {
                    return -1;
                } else if (s1.charAt(i) > s2.charAt(i)) {
                    return 1;
                }
            }
            if (s1.length() < s2.length()) {
                return -1;
            } else {
                return 1;
            }
        }

        public static void task4(int[] array) {
            List<String> strNums = new ArrayList<>();
            for (int num : array) {
                strNums.add(String.valueOf(num));
            }
            Collections.sort(strNums, (a, b) -> (b + a).compareTo(a + b));

            if (strNums.get(0).equals("0")) {
                System.out.println("0");
                return;
            }
            for (String str : strNums) {
                System.out.print(str);
            }
        }
    }

