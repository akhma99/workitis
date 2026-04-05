import java.io.*;
import java.util.*;

public class SegmentTreeFileTest {

    // ============ ДЕРЕВО ОТРЕЗКОВ ============
    static class SegmentTree {
        private long[] tree;
        private long[] array;
        private int size;

        public SegmentTree(int n) {
            this.size = n;
            tree = new long[4 * n];
            array = new long[n];
        }

        public void build(long[] arr, int i, int left, int right) {
            if (right == left) {
                tree[i] = arr[right];
                array[right] = arr[right];
            } else {
                int mid = (right + left) / 2;
                build(arr, i * 2, left, mid);
                build(arr, i * 2 + 1, mid + 1, right);
                tree[i] = tree[i * 2] + tree[i * 2 + 1];
            }
        }

        public void update(int i, int left, int right, int updateIndex, long value) {
            if (right == left) {
                tree[i] = value;
                array[updateIndex] = value;
            } else {
                int mid = (right + left) / 2;
                if (updateIndex <= mid) {
                    update(i * 2, left, mid, updateIndex, value);
                } else {
                    update(i * 2 + 1, mid + 1, right, updateIndex, value);
                }
                tree[i] = tree[i * 2] + tree[i * 2 + 1];
            }
        }

        public long sum(int i, int left, int right, int inputLeft, int inputRight) {
            if (inputLeft > inputRight) return 0;
            if (left == inputLeft && right == inputRight) {
                return tree[i];
            }
            int mid = (left + right) / 2;
            long leftSum = sum(i * 2, left, mid, inputLeft, Math.min(mid, inputRight));
            long rightSum = sum(i * 2 + 1, mid + 1, right, Math.max(mid + 1, inputLeft), inputRight);
            return leftSum + rightSum;
        }

        public int findIndexByValue(int i, int left, int right, long target) {
            if (left == right) {
                return (array[left] == target) ? left : -1;
            }
            int mid = (left + right) / 2;
            int leftResult = findIndexByValue(i * 2, left, mid, target);
            if (leftResult != -1) return leftResult;
            return findIndexByValue(i * 2 + 1, mid + 1, right, target);
        }

        public void append(long value) {
            long[] newArray = new long[size + 1];
            System.arraycopy(array, 0, newArray, 0, size);
            newArray[size] = value;
            array = newArray;
            size++;
            rebuildTree();
        }

        public void insert(int index, long value) {
            if (index < 0 || index > size) return;
            long[] newArray = new long[size + 1];
            System.arraycopy(array, 0, newArray, 0, index);
            newArray[index] = value;
            System.arraycopy(array, index, newArray, index + 1, size - index);
            array = newArray;
            size++;
            rebuildTree();
        }

        public void deleteByIndex(int index) {
            if (index < 0 || index >= size) return;
            long[] newArray = new long[size - 1];
            System.arraycopy(array, 0, newArray, 0, index);
            System.arraycopy(array, index + 1, newArray, index, size - index - 1);
            array = newArray;
            size--;
            rebuildTree();
        }

        private void rebuildTree() {
            tree = new long[4 * size];
            build(array, 1, 0, size - 1);
        }

        public int getSize() { return size; }
        public long get(int index) { return array[index]; }
    }

    // ============ ГЕНЕРАТОР ТЕСТОВЫХ ФАЙЛОВ ============
    static class TestFileGenerator {
        private static final Random random = new Random();

        public static void generateAllTestFiles() throws IOException {
            String dir = "test_data";
            new File(dir).mkdirs();

            System.out.println("Генерация тестовых файлов...");

            // Генерируем 50 размеров от 100 до 10000
            List<Integer> sizes = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                int size = 100 + (i * (10000 - 100) / 49);
                sizes.add(size);
            }

            int fileId = 1;
            for (int size : sizes) {
                int queries = size + random.nextInt(size);
                String filename = dir + "/test_" + String.format("%03d", fileId++) + "_" + size + "_" + queries + ".txt";
                generateSingleTestFile(filename, size, queries);
            }

            // Специальные тесты
            generateSpecialTestFiles(dir);

            System.out.println("✅ Сгенерировано 52 тестовых файла (50 обычных + 2 специальных)");
        }

        private static void generateSingleTestFile(String filename, int n, int q) throws IOException {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                writer.println(n + " " + q);
                for (int i = 0; i < n; i++) {
                    if (i > 0) writer.print(" ");
                    writer.print(random.nextLong() % 1_000_000_000 + 1);
                }
                writer.println();
                for (int i = 0; i < q; i++) {
                    if (random.nextBoolean()) {
                        int k = random.nextInt(n) + 1;
                        long u = random.nextLong() % 1_000_000_000 + 1;
                        writer.println("1 " + k + " " + u);
                    } else {
                        int a = random.nextInt(n) + 1;
                        int b = random.nextInt(n - a + 1) + a;
                        writer.println("2 " + a + " " + b);
                    }
                }
            }
        }

        private static void generateSpecialTestFiles(String dir) throws IOException {
            // Сортированный массив
            try (PrintWriter writer = new PrintWriter(new FileWriter(dir + "/test_sorted_10000.txt"))) {
                writer.println("10000 20000");
                for (int i = 1; i <= 10000; i++) {
                    if (i > 1) writer.print(" ");
                    writer.print(i);
                }
                writer.println();
                for (int i = 0; i < 20000; i++) {
                    writer.println("2 1 10000");
                }
            }

            // Константный массив
            try (PrintWriter writer = new PrintWriter(new FileWriter(dir + "/test_constant_10000.txt"))) {
                writer.println("10000 20000");
                for (int i = 0; i < 10000; i++) {
                    if (i > 0) writer.print(" ");
                    writer.print(42);
                }
                writer.println();
                for (int i = 0; i < 10000; i++) {
                    int a = new Random().nextInt(10000) + 1;
                    int b = new Random().nextInt(10000 - a + 1) + a;
                    writer.println("2 " + a + " " + b);
                }
                for (int i = 0; i < 10000; i++) {
                    writer.println("1 " + (new Random().nextInt(10000) + 1) + " " + (new Random().nextInt(1000) + 1));
                }
            }
        }
    }

    // ============ ЗАГРУЗЧИК ТЕСТОВ ============
    static class TestLoader {
        public static TestCase loadTest(String filename) throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String[] firstLine = reader.readLine().split(" ");
                int n = Integer.parseInt(firstLine[0]);
                int q = Integer.parseInt(firstLine[1]);

                long[] array = new long[n];
                String[] arrayLine = reader.readLine().split(" ");
                for (int i = 0; i < n; i++) {
                    array[i] = Long.parseLong(arrayLine[i]);
                }

                List<Query> queries = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    int type = Integer.parseInt(parts[0]);
                    if (type == 1) {
                        queries.add(new UpdateQuery(Integer.parseInt(parts[1]) - 1, Long.parseLong(parts[2])));
                    } else {
                        queries.add(new SumQuery(Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[2]) - 1));
                    }
                }

                return new TestCase(n, q, array, queries);
            }
        }

        public static List<String> getAllTestFiles(String dir) {
            List<String> files = new ArrayList<>();
            File folder = new File(dir);
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        files.add(file.getPath());
                    }
                }
            }
            Collections.sort(files);
            return files;
        }
    }

    // ============ КЛАССЫ ДЛЯ ТЕСТОВ ============
    static abstract class Query {
        int type;
        Query(int type) { this.type = type; }
    }

    static class UpdateQuery extends Query {
        int index;
        long value;
        UpdateQuery(int index, long value) {
            super(1);
            this.index = index;
            this.value = value;
        }
    }

    static class SumQuery extends Query {
        int left, right;
        SumQuery(int left, int right) {
            super(2);
            this.left = left;
            this.right = right;
        }
    }

    static class TestCase {
        int n, q;
        long[] array;
        List<Query> queries;
        TestCase(int n, int q, long[] array, List<Query> queries) {
            this.n = n;
            this.q = q;
            this.array = array;
            this.queries = queries;
        }
    }

    // ============ МЕТРИКИ ПРОИЗВОДИТЕЛЬНОСТИ ============
    static class PerformanceMetrics {
        double buildTime;
        double processTime;
        double insertTime;
        double deleteTime;
        double searchBestCase;
        double searchAverageCase;
        double searchWorstCase;
        double searchNotFound;
    }

    // ============ ТЕСТИРОВАНИЕ ВСЕХ СЛУЧАЕВ ============
    public static PerformanceMetrics testAllCases(TestCase test) {
        PerformanceMetrics metrics = new PerformanceMetrics();
        SegmentTree st = new SegmentTree(test.n);

        // Построение дерева
        long start = System.nanoTime();
        st.build(test.array, 1, 0, test.n - 1);
        metrics.buildTime = (System.nanoTime() - start) / 1_000_000.0;

        // Обработка запросов
        start = System.nanoTime();
        for (Query query : test.queries) {
            if (query.type == 1) {
                UpdateQuery uq = (UpdateQuery) query;
                st.update(1, 0, st.getSize() - 1, uq.index, uq.value);
            } else {
                SumQuery sq = (SumQuery) query;
                st.sum(1, 0, st.getSize() - 1, sq.left, sq.right);
            }
        }
        metrics.processTime = (System.nanoTime() - start) / 1_000_000.0;

        // Измерение вставки (100 раз)
        start = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            st.insert(st.getSize() / 2, 999);
        }
        metrics.insertTime = (System.nanoTime() - start) / 1_000_000.0 / 100;

        // Измерение удаления (100 раз)
        start = System.nanoTime();
        for (int i = 0; i < 100 && st.getSize() > 0; i++) {
            st.deleteByIndex(st.getSize() / 2);
        }
        metrics.deleteTime = (System.nanoTime() - start) / 1_000_000.0 / 100;

        // Поиск - лучший случай (первый элемент)
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            st.findIndexByValue(1, 0, st.getSize() - 1, test.array[0]);
        }
        metrics.searchBestCase = (System.nanoTime() - start) / 1_000_000.0 / 1000;

        // Поиск - средний случай (элемент из середины)
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            st.findIndexByValue(1, 0, st.getSize() - 1, test.array[test.n / 2]);
        }
        metrics.searchAverageCase = (System.nanoTime() - start) / 1_000_000.0 / 1000;

        // Поиск - худший случай (последний элемент)
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            st.findIndexByValue(1, 0, st.getSize() - 1, test.array[test.n - 1]);
        }
        metrics.searchWorstCase = (System.nanoTime() - start) / 1_000_000.0 / 1000;

        // Поиск - элемент не найден
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            st.findIndexByValue(1, 0, st.getSize() - 1, -999999L);
        }
        metrics.searchNotFound = (System.nanoTime() - start) / 1_000_000.0 / 1000;

        return metrics;
    }

    // ============ ЭКСПОРТ В CSV ============
    private static void exportAllData(List<String> testFiles, List<PerformanceMetrics> allMetrics) throws IOException {

        // 1. Общий файл со всеми данными
        try (PrintWriter writer = new PrintWriter(new FileWriter("all_results.csv"))) {
            writer.println("Файл;Размер;Построение_мс;Запросы_мс;Вставка_мс;Удаление_мс;Поиск_лучший_мс;Поиск_средний_мс;Поиск_худший_мс;Поиск_не_найден_мс");

            for (int i = 0; i < testFiles.size() && i < allMetrics.size(); i++) {
                String fileName = new File(testFiles.get(i)).getName();
                PerformanceMetrics m = allMetrics.get(i);
                int size = extractSizeFromFileName(fileName);

                writer.printf(Locale.US, "%s;%d;%.6f;%.6f;%.6f;%.6f;%.6f;%.6f;%.6f;%.6f%n",
                        fileName, size, m.buildTime, m.processTime, m.insertTime, m.deleteTime,
                        m.searchBestCase, m.searchAverageCase, m.searchWorstCase, m.searchNotFound);
            }
        }

        // 2. Только данные для графиков поиска
        try (PrintWriter writer = new PrintWriter(new FileWriter("search_cases.csv"))) {
            writer.println("Размер;Лучший_случай_мс;Средний_случай_мс;Худший_случай_мс;Не_найден_мс");
            for (int i = 0; i < testFiles.size() && i < allMetrics.size(); i++) {
                int size = extractSizeFromFileName(new File(testFiles.get(i)).getName());
                PerformanceMetrics m = allMetrics.get(i);
                writer.printf(Locale.US, "%d;%.6f;%.6f;%.6f;%.6f%n",
                        size, m.searchBestCase, m.searchAverageCase, m.searchWorstCase, m.searchNotFound);
            }
        }

        // 3. Данные для сравнения вставки и удаления
        try (PrintWriter writer = new PrintWriter(new FileWriter("insert_delete.csv"))) {
            writer.println("Размер;Вставка_мс;Удаление_мс");
            for (int i = 0; i < testFiles.size() && i < allMetrics.size(); i++) {
                int size = extractSizeFromFileName(new File(testFiles.get(i)).getName());
                PerformanceMetrics m = allMetrics.get(i);
                writer.printf(Locale.US, "%d;%.6f;%.6f%n", size, m.insertTime, m.deleteTime);
            }
        }

        System.out.println("\n✅ Созданы CSV файлы:");
        System.out.println("  📄 all_results.csv - все результаты");
        System.out.println("  📄 search_cases.csv - для графика поиска (4 кривые)");
        System.out.println("  📄 insert_delete.csv - для графика вставки/удаления");
    }

    private static int extractSizeFromFileName(String fileName) {
        String[] parts = fileName.split("_");
        for (String part : parts) {
            try {
                int num = Integer.parseInt(part);
                if (num > 0 && num < 100000) {
                    return num;
                }
            } catch (NumberFormatException e) {}
        }
        return 0;
    }

    private static double average(double[] values) {
        double sum = 0;
        for (double v : values) sum += v;
        return sum / values.length;
    }

    // ============ ОСНОВНОЙ МЕТОД ============
    public static void runAllTests() throws IOException {
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("     ТЕСТИРОВАНИЕ ДЕРЕВА ОТРЕЗКОВ (КРАТКАЯ СТАТИСТИКА)");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // Генерируем тестовые файлы
        TestFileGenerator.generateAllTestFiles();

        // Загружаем все тесты
        List<String> testFiles = TestLoader.getAllTestFiles("test_data");
        System.out.println("\n📁 Найдено тестовых файлов: " + testFiles.size() + "\n");

        List<PerformanceMetrics> allMetrics = new ArrayList<>();

        // Прогресс-бар
        int total = testFiles.size();
        int current = 0;

        System.out.print("Тестирование: [");

        for (String file : testFiles) {
            try {
                TestCase test = TestLoader.loadTest(file);

                // Запускаем 3 прогона и усредняем
                double[] buildTimes = new double[3];
                double[] processTimes = new double[3];
                double[] insertTimes = new double[3];
                double[] deleteTimes = new double[3];
                double[] bestCases = new double[3];
                double[] avgCases = new double[3];
                double[] worstCases = new double[3];
                double[] notFoundCases = new double[3];

                for (int run = 0; run < 3; run++) {
                    PerformanceMetrics metrics = testAllCases(test);
                    buildTimes[run] = metrics.buildTime;
                    processTimes[run] = metrics.processTime;
                    insertTimes[run] = metrics.insertTime;
                    deleteTimes[run] = metrics.deleteTime;
                    bestCases[run] = metrics.searchBestCase;
                    avgCases[run] = metrics.searchAverageCase;
                    worstCases[run] = metrics.searchWorstCase;
                    notFoundCases[run] = metrics.searchNotFound;
                }

                PerformanceMetrics avgMetrics = new PerformanceMetrics();
                avgMetrics.buildTime = average(buildTimes);
                avgMetrics.processTime = average(processTimes);
                avgMetrics.insertTime = average(insertTimes);
                avgMetrics.deleteTime = average(deleteTimes);
                avgMetrics.searchBestCase = average(bestCases);
                avgMetrics.searchAverageCase = average(avgCases);
                avgMetrics.searchWorstCase = average(worstCases);
                avgMetrics.searchNotFound = average(notFoundCases);

                allMetrics.add(avgMetrics);
                current++;

                // Рисуем прогресс
                if (current % (total / 20) == 0 || current == total) {
                    System.out.print("=");
                }

            } catch (Exception e) {
                System.err.println("\n❌ Ошибка: " + e.getMessage());
            }
        }

        System.out.println("] 100%\n");

        // ============ КРАТКАЯ СТАТИСТИКА ============

        // Вычисляем средние значения
        double avgBuild = 0, avgProcess = 0, avgInsert = 0, avgDelete = 0;
        double avgBest = 0, avgAverage = 0, avgWorst = 0, avgNotFound = 0;

        for (PerformanceMetrics m : allMetrics) {
            avgBuild += m.buildTime;
            avgProcess += m.processTime;
            avgInsert += m.insertTime;
            avgDelete += m.deleteTime;
            avgBest += m.searchBestCase;
            avgAverage += m.searchAverageCase;
            avgWorst += m.searchWorstCase;
            avgNotFound += m.searchNotFound;
        }

        avgBuild /= allMetrics.size();
        avgProcess /= allMetrics.size();
        avgInsert /= allMetrics.size();
        avgDelete /= allMetrics.size();
        avgBest /= allMetrics.size();
        avgAverage /= allMetrics.size();
        avgWorst /= allMetrics.size();
        avgNotFound /= allMetrics.size();

        // Находим min/max
        double minBest = Double.MAX_VALUE, maxWorst = 0;
        double minInsert = Double.MAX_VALUE, maxInsert = 0;
        double minDelete = Double.MAX_VALUE, maxDelete = 0;

        for (PerformanceMetrics m : allMetrics) {
            minBest = Math.min(minBest, m.searchBestCase);
            maxWorst = Math.max(maxWorst, m.searchWorstCase);
            minInsert = Math.min(minInsert, m.insertTime);
            maxInsert = Math.max(maxInsert, m.insertTime);
            minDelete = Math.min(minDelete, m.deleteTime);
            maxDelete = Math.max(maxDelete, m.deleteTime);
        }

        // Выводим статистику
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("                    КРАТКАЯ СТАТИСТИКА");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.println("\n📊 ПОИСК ЭЛЕМЕНТА:");
        System.out.printf("  ✅ Лучший случай (первый элемент):  %.6f мс (мин: %.6f мс)%n", avgBest, minBest);
        System.out.printf("  📊 Средний случай (середина):       %.6f мс%n", avgAverage);
        System.out.printf("  ⚠️  Худший случай (последний):       %.6f мс (макс: %.6f мс)%n", avgWorst, maxWorst);
        System.out.printf("  ❌ Элемент не найден:               %.6f мс%n", avgNotFound);
        System.out.printf("  📈 Соотношение худший/лучший:       %.2fx%n", avgWorst / avgBest);

        System.out.println("\n✏️  ВСТАВКА И УДАЛЕНИЕ:");
        System.out.printf("  ➕ Вставка:  %.6f мс (мин: %.6f мс, макс: %.6f мс)%n", avgInsert, minInsert, maxInsert);
        System.out.printf("  ➖ Удаление: %.6f мс (мин: %.6f мс, макс: %.6f мс)%n", avgDelete, minDelete, maxDelete);
        System.out.printf("  📊 Соотношение вставка/удаление:    %.2fx%n", avgInsert / avgDelete);

        System.out.println("\n🏗️  ОСТАЛЬНЫЕ ОПЕРАЦИИ:");
        System.out.printf("  Построение дерева:    %.3f мс%n", avgBuild);
        System.out.printf("  Обработка запросов:   %.3f мс%n", avgProcess);

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("              ДЕТАЛЬНЫЕ ДАННЫЕ СОХРАНЕНЫ В ФАЙЛАХ");
        System.out.println("═══════════════════════════════════════════════════════════════");

        // Сохраняем данные в CSV
        exportAllData(testFiles, allMetrics);

        // Сохраняем статистику в файл
        try (PrintWriter writer = new PrintWriter(new FileWriter("statistics.txt"))) {
            writer.println("СТАТИСТИКА ПО ВСЕМ ТЕСТАМ");
            writer.println("========================");
            writer.printf(Locale.US, "Количество тестов: %d%n", allMetrics.size());
            writer.printf(Locale.US, "\nПОИСК ЭЛЕМЕНТА:%n");
            writer.printf(Locale.US, "  Лучший случай (первый элемент):  %.6f мс (мин: %.6f мс)%n", avgBest, minBest);
            writer.printf(Locale.US, "  Средний случай (середина):       %.6f мс%n", avgAverage);
            writer.printf(Locale.US, "  Худший случай (последний):       %.6f мс (макс: %.6f мс)%n", avgWorst, maxWorst);
            writer.printf(Locale.US, "  Элемент не найден:               %.6f мс%n", avgNotFound);
            writer.printf(Locale.US, "  Соотношение худший/лучший:       %.2fx%n", avgWorst / avgBest);
            writer.printf(Locale.US, "\nВСТАВКА И УДАЛЕНИЕ:%n");
            writer.printf(Locale.US, "  Вставка:  %.6f мс (мин: %.6f мс, макс: %.6f мс)%n", avgInsert, minInsert, maxInsert);
            writer.printf(Locale.US, "  Удаление: %.6f мс (мин: %.6f мс, макс: %.6f мс)%n", avgDelete, minDelete, maxDelete);
            writer.printf(Locale.US, "  Соотношение вставка/удаление:    %.2fx%n", avgInsert / avgDelete);
            writer.printf(Locale.US, "\nОСТАЛЬНЫЕ ОПЕРАЦИИ:%n");
            writer.printf(Locale.US, "  Построение дерева:    %.3f мс%n", avgBuild);
            writer.printf(Locale.US, "  Обработка запросов:   %.3f мс%n", avgProcess);
        }

        System.out.println("  📄 statistics.txt - полная статистика");
        System.out.println("\n✅ Тестирование завершено!");
    }

    public static void main(String[] args) {
        try {
            runAllTests();
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}