package queue;

import java.util.NoSuchElementException;

public class MyQueue<T>{

    private Object[] values;
    private int head;
    private int tail; //с английского хвост, это короче чтобы отслеживать конец очереди
    private int size;

    public MyQueue(int capacity){
        this.values= new Object[capacity];
    }

    public void enqueue(T value){
        if (values.length==size){
            throw new IllegalStateException("Queue is full");
        }
        values[tail]=value;
        tail = (tail+1)%values.length; //это короче чтобы отследить следующее положение индекса
        // (умный ход чтобы не выйти за границу и возвращаться куда надо)
        size++;
    }

    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        T value = (T) values[head];
        head = (head + 1) % values.length; //та же самая схема, как в первом методе
        size--;
        return value;
    }

    public T peek(){
        if (size==0){
            throw new NoSuchElementException("Queue is empty");
        }
        return (T) values[head];
    }

    public int getSize(){return size;}

    public void print(){
        for (int i = 0; i < size; i++) {
            int index = (head + i) % values.length; // учитываем head
            System.out.print(values[index]);
        }
    }

}
