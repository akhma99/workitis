import java.util.Scanner;
public class Task1 {
    public static void main(String[] args) {
        Scanner mscr = new Scanner(System.in);
        long n = mscr.nextLong();
        long a = 0;
        long b = 1;
        while (a < n) {
            long sl = a + b;
            a = b;
            b = sl;
        }
        if (a == n)
            System.out.println(n + " число фибоначчи");
        else
            System.out.println(n + " не число фибоначчи");
    }
}