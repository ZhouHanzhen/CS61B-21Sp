package deque;

public class ArrayDeque<T> implements Deque<T>{
    private int size;
    private T[] items;
    private int nextFront;
    private int nextBack;



    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFront = 0;
        nextBack = 1;

    }
    public ArrayDeque(T x) {
        items = (T[]) new Object[8];
        items[0] = x;
        size = 1;
        nextFront = 7;
        nextBack = 1;

    }

    @Override
    public void addFirst(T x) {
        items[nextFront] = x;
        size += 1;

        nextFront = (nextFront - 1 + items.length) % items.length;
    }

    @Override
    public void addLast(T x) {
        items[nextBack] = x;
        size += 1;

        nextBack = (nextBack + 1) % items.length;
    }

    @Override
    public T removeFirst() {
        if (size < 1) {
            return null;
        }
        int first = (nextFront + 1) % items.length;
        T x = items[first];
        nextFront = first;

        size -= 1;
        return x;
    }

    @Override
    public T removeLast() {
        if (size < 1) {
            return null;
        }
        int last = (nextBack - 1 + items.length) % items.length;
        T x = items[last];
        nextBack = last;

        size -= 1;
        return x;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        if ((index < 0) || (index > size - 1)) {
            return null;
        }
        int first = (nextFront + 1) % items.length;

        return items[(first + index) % items.length];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void printDeque() {
        int first = (nextFront + 1) % items.length;
        for (int i = 0; i < size -1; i += 1) {
            System.out.print(items[(first + i) % items.length]);
            System.out.print(" ");
        }
        System.out.println(items[(first + size - 1) % items.length]);
    }
}
