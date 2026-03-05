import queue.MyQueue;

public class Main {
    public static void main(String[] args) {
        MyQueue<Integer> queue1 = new MyQueue<>(3);
        MyQueue<Integer> queue2 = new MyQueue<>(3);

        queue1.enqueue(1);
        queue1.enqueue(3);
        queue1.enqueue(5);
        queue2.enqueue(2);
        queue2.enqueue(4);
        queue2.enqueue(6);
        MyQueue<Integer> queue3 = sortQueue(queue1,queue2);
        queue3.print();
    }
    public static MyQueue<Integer> sortQueue(MyQueue<Integer> queue1, MyQueue<Integer> queue2){
        //тут короче похоже на сортировку слиянием хадиева, суть такая же, типо идем по 2 очередям и и сравнием элементы и закидываем меньший
        MyQueue<Integer> result = new MyQueue<>(queue1.getSize()+queue2.getSize());
        MyQueue<Integer> temp1 = copyQueue(queue1);
        MyQueue<Integer> temp2 = copyQueue(queue2);
        while (temp1.getSize()!=0 && temp2.getSize()!=0) {
            int val1 = temp1.peek();
            int val2 = temp2.peek();

            if (val1 < val2) {
                result.enqueue(temp1.dequeue());
            } else {
                result.enqueue(temp2.dequeue());
            }
        }

        while (temp1.getSize()!=0) {
            result.enqueue(temp1.dequeue());
        }

        while (temp2.getSize()!=0) {
            result.enqueue(temp2.dequeue());
        }

        return result;
    }
    private static <T> MyQueue<T> copyQueue(MyQueue<T> original) {
        MyQueue<T> copy = new MyQueue<>(original.getSize());
        MyQueue<T> temp = new MyQueue<>(original.getSize());

        while (original.getSize()!=0) {
            T value = original.dequeue();
            copy.enqueue(value);
            temp.enqueue(value);
        }

        while (temp.getSize()!=0) {
            original.enqueue(temp.dequeue());
        }

        return copy;
    }
}