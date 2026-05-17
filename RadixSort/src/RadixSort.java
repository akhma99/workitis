import java.util.Arrays;

public class RadixSort {

    public static class Metrics {
        public long durationNanos;
        public long iterations;

        public Metrics(long durationNanos, long iterations) {
            this.durationNanos = durationNanos;
            this.iterations = iterations;
        }
    }

    private static int getMax(int[] arr, Metrics metrics) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            metrics.iterations++;
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    private static void countingSortForRadix(int[] arr, int exp, Metrics metrics) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        for (int i = 0; i < n; i++) {
            metrics.iterations++;
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }

        for (int i = 1; i < 10; i++) {
            metrics.iterations++;
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            metrics.iterations++;
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        for (int i = 0; i < n; i++) {
            metrics.iterations++;
            arr[i] = output[i];
        }
    }

    public static Metrics sort(int[] arr) {
        Metrics metrics = new Metrics(0, 0);

        if (arr == null || arr.length <= 1) {
            return metrics;
        }

        long startTime = System.nanoTime();

        int max = getMax(arr, metrics);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortForRadix(arr, exp, metrics);
        }

        long stopTime = System.nanoTime();
        metrics.durationNanos = (stopTime - startTime);

        return metrics;
    }
}