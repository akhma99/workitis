import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] matrix = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        int[][] minMatr = new int[n][m];
        minMatr[0][0] = matrix[0][0];

        for (int j = 1; j < m; j++) {
            minMatr[0][j] = minMatr[0][j - 1] + matrix[0][j];
        }

        for (int i = 1; i < n; i++) {
            minMatr[i][0] = minMatr[i - 1][0] + matrix[i][0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                minMatr[i][j] = Math.min(minMatr[i - 1][j], minMatr[i][j - 1]) + matrix[i][j];
            }
        }
        System.out.println(minMatr[n - 1][m - 1]);
    }
}
