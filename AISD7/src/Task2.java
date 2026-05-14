import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner mscr = new Scanner(System.in);
        int n = mscr.nextInt();
        int a = 0;
        int b = 1;
        if (n == 0) {
            System.out.println(0);
            return;
        }
        if (n == 1) {
            System.out.println(1);
            return;
        }
        for (int i = 2; i <= n; i++) {
            int sl = (a + b) % 10;
            a = b;
            b = sl;
        }

        System.out.println(b);
    }
}