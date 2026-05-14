import java.util.Scanner;
public class Task3 {
    public static void main(String[] args) {
        Scanner mscr = new Scanner(System.in);
        String s = mscr.nextLine();
        int len = s.length();
        String res = "";
        int a = 1;
        int b = 2;
        if (1 <= len) {
            res = res + s.charAt(0);
        }
        while (b <= len) {
            res = res + s.charAt(b - 1);
            int sl = a + b;
            a = b;
            b = sl;
        }
        System.out.println("Результат: " + res);
    }
}