import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class TestGeneration {
    public static void main(String[] args) {
        int minSize = 100;
        int maxSize = 10000;
        int steps = 100;
        int stepSize = (maxSize - minSize) / (steps - 1);

        String fileName = "radix_sort_metrics.csv";
        Random random = new Random(42);

        try (FileWriter fw = new FileWriter(fileName);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("Size,DurationNanos,Iterations");

            for (int i = 0; i < steps; i++) {
                int size = minSize + (i * stepSize);

                int[] originalArray = new int[size];
                for (int j = 0; j < size; j++) {
                    originalArray[j] = random.nextInt(100000);
                }

                RadixSort.Metrics metrics = RadixSort.sort(originalArray);

                pw.printf("%d,%d,%d%n", size, metrics.durationNanos, metrics.iterations);
            }

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
            e.printStackTrace();
        }
    }
}