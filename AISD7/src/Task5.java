import java.util.Arrays;
import java.util.Scanner;

public class Task5 {
    public static void main(String[] args) {
        Scanner mscr = new Scanner(System.in);
        int n = mscr.nextInt();
        int[] st = new int[n];
        for (int i = 0; i < n; i++) {
            st[i] = mscr.nextInt();
        }
        Arrays.sort(st);
        if (n == 0 || n == 1) {
            System.out.println(0);
            return;
        }
        if (n == 2) {
            System.out.println(st[1] - st[0]);
            return;
        }
        int[] d = new int[n + 1];
        d[0] = 0;
        d[1] = 0;
        d[2] = st[1] - st[0];
        for (int i = 3; i <= n; i++) {
            int res1 = d[i - 2] + (st[i - 1] - st[i - 2]);
            int res2 = d[i - 3] + (st[i - 1] - st[i - 3]);
            d[i] = Math.min(res1, res2);
        }

        System.out.println(d[n]);
    }
}