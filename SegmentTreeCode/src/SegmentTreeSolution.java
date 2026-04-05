//ccылка на задачу, которая решается нашей структурой данных:
//https://cses.fi/problemset/task/1648/

import java.io.*;
import java.util.*;

public class SegmentTreeSolution {

    // ============ ДЕРЕВО ОТРЕЗКОВ ============
    static class SegmentTree {
        private long[] tree;
        private long[] arr;
        private int n;

        public SegmentTree(int size) {
            this.n = size;
            tree = new long[4 * size];
            arr = new long[size];
        }

        // Построение дерева
        public void build(long[] input, int v, int tl, int tr) {
            if (tl == tr) {
                tree[v] = input[tl];
                arr[tl] = input[tl];
            } else {
                int tm = (tl + tr) / 2;
                build(input, v * 2, tl, tm);
                build(input, v * 2 + 1, tm + 1, tr);
                tree[v] = tree[v * 2] + tree[v * 2 + 1];
            }
        }

        // Обновление значения по индексу
        public void update(int v, int tl, int tr, int idx, long val) {
            if (tl == tr) {
                tree[v] = val;
                arr[idx] = val;
            } else {
                int tm = (tl + tr) / 2;
                if (idx <= tm) {
                    update(v * 2, tl, tm, idx, val);
                } else {
                    update(v * 2 + 1, tm + 1, tr, idx, val);
                }
                tree[v] = tree[v * 2] + tree[v * 2 + 1];
            }
        }

        // Сумма на отрезке [l, r]
        public long sum(int v, int tl, int tr, int l, int r) {
            if (l > r) return 0;
            if (l == tl && r == tr) {
                return tree[v];
            }
            int tm = (tl + tr) / 2;
            return sum(v * 2, tl, tm, l, Math.min(r, tm)) +
                    sum(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r);
        }

        // ============ МЕТОДЫ ПОИСКА ============

        // Поиск индекса по значению (первое вхождение)
        public int findByValue(int v, int tl, int tr, long target) {
            if (tl == tr) {
                return (arr[tl] == target) ? tl : -1;
            }
            int tm = (tl + tr) / 2;
            int leftResult = findByValue(v * 2, tl, tm, target);
            if (leftResult != -1) return leftResult;
            return findByValue(v * 2 + 1, tm + 1, tr, target);
        }

        // Поиск максимального значения на отрезке
        private long[] maxTree;

        public void buildMaxTree(int v, int tl, int tr) {
            if (maxTree == null) {
                maxTree = new long[4 * n];
            }
            if (tl == tr) {
                maxTree[v] = arr[tl];
            } else {
                int tm = (tl + tr) / 2;
                buildMaxTree(v * 2, tl, tm);
                buildMaxTree(v * 2 + 1, tm + 1, tr);
                maxTree[v] = Math.max(maxTree[v * 2], maxTree[v * 2 + 1]);
            }
        }

        public long findMax(int v, int tl, int tr, int l, int r) {
            if (l > r) return Long.MIN_VALUE;
            if (l == tl && r == tr) {
                return maxTree[v];
            }
            int tm = (tl + tr) / 2;
            return Math.max(
                    findMax(v * 2, tl, tm, l, Math.min(r, tm)),
                    findMax(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r)
            );
        }

        // ============ МЕТОДЫ ДОБАВЛЕНИЯ ============

        // Добавление элемента в конец
        public void append(long value) {
            long[] newArr = new long[n + 1];
            System.arraycopy(arr, 0, newArr, 0, n);
            newArr[n] = value;
            arr = newArr;
            n++;
            rebuildTree();
        }

        // Вставка элемента по индексу (со сдвигом вправо)
        public void insert(int index, long value) {
            if (index < 0 || index > n) {
                System.err.println("Ошибка: индекс вне диапазона");
                return;
            }

            long[] newArr = new long[n + 1];
            System.arraycopy(arr, 0, newArr, 0, index);
            newArr[index] = value;
            System.arraycopy(arr, index, newArr, index + 1, n - index);
            arr = newArr;
            n++;
            rebuildTree();
        }

        // ============ МЕТОДЫ УДАЛЕНИЯ ============

        // Удаление элемента по индексу
        public void deleteByIndex(int index) {
            if (index < 0 || index >= n) {
                System.err.println("Ошибка: индекс вне диапазона");
                return;
            }

            long[] newArr = new long[n - 1];
            System.arraycopy(arr, 0, newArr, 0, index);
            System.arraycopy(arr, index + 1, newArr, index, n - index - 1);
            arr = newArr;
            n--;
            rebuildTree();
        }

        // Удаление по значению (удаляет первое вхождение)
        public boolean deleteByValue(long value) {
            int index = findByValue(1, 0, n - 1, value);
            if (index == -1) {
                return false;
            }
            deleteByIndex(index);
            return true;
        }

        // Перестроение дерева после изменения размера
        private void rebuildTree() {
            tree = new long[4 * n];
            build(arr, 1, 0, n - 1);

            if (maxTree != null) {
                maxTree = new long[4 * n];
                buildMaxTree(1, 0, n - 1);
            }
        }

        public int size() {
            return n;
        }
    }

    // ============ ОСНОВНОЙ КЛАСС ДЛЯ РЕШЕНИЯ ЗАДАЧИ ============
    static class Solution {

        public static void solve() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pw = new PrintWriter(System.out);

            // Чтение n и q
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            // Чтение массива
            long[] arr = new long[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                arr[i] = Long.parseLong(st.nextToken());
            }

            // Построение дерева отрезков
            SegmentTree segTree = new SegmentTree(n);
            segTree.build(arr, 1, 0, n - 1);

            // Обработка запросов
            while (q-- > 0) {
                st = new StringTokenizer(br.readLine());
                int type = Integer.parseInt(st.nextToken());

                if (type == 1) {
                    // Запрос на обновление: 1 k u
                    int k = Integer.parseInt(st.nextToken());
                    long u = Long.parseLong(st.nextToken());
                    segTree.update(1, 0, n - 1, k - 1, u);
                } else if (type == 2) {
                    // Запрос на сумму: 2 a b
                    int a = Integer.parseInt(st.nextToken());
                    int b = Integer.parseInt(st.nextToken());
                    long result = segTree.sum(1, 0, n - 1, a - 1, b - 1);
                    pw.println(result);
                }
            }

            pw.flush();
            pw.close();
            br.close();
        }
    }

    // ============ ДЕМОНСТРАЦИЯ ДОПОЛНИТЕЛЬНЫХ МЕТОДОВ ============
    public static void main(String[] args) throws IOException {
        Solution.solve(); // Раскомментировать для решения задачи
    }
}