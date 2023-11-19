package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
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

    /**
     *Resize the array if it is full ,or it's usage factor is under 25%
     */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int wizPos = (nextFront + 1) % items.length;

        for (int i = 0; i < size; i += 1) {
            a[i] = items[wizPos];
            wizPos = (wizPos + 1) % items.length;
        }

        items = a;
        nextFront = capacity - 1;
        nextBack = size;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(2 * size);
        }

        items[nextFront] = x;
        size += 1;

        nextFront = (nextFront - 1 + items.length) % items.length;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(2 * size);
        }

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
        items[first] = null;
        nextFront = first;
        size -= 1;

        if ((items.length >= 16) && (items.length > 4 * size)) {
            resize(size * 2);
        }
        return x;
    }

    @Override
    public T removeLast() {
        if (size < 1) {
            return null;
        }
        int last = (nextBack - 1 + items.length) % items.length;
        T x = items[last];
        items[last] = null;
        nextBack = last;
        size -= 1;

        if ((items.length >= 16) && (items.length > 4 * size)) {
            resize(size * 2);
        }
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
    public void printDeque() {
        int first = (nextFront + 1) % items.length;
        for (int i = 0; i < size - 1; i += 1) {
            System.out.print(items[(first + i) % items.length]);
            System.out.print(" ");
        }
        System.out.println(items[(first + size - 1) % items.length]);
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;
        private int count;
        public ArrayDequeIterator() {
            wizPos = (nextFront + 1) % items.length;
            count = 0;
        }

        public boolean hasNext() {
            return count < size;
        }
        public T next() {
            T returnItem = items[wizPos];
            wizPos = (wizPos + 1) % items.length;
            count += 1;
            return returnItem;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> other = (Deque<T>) o;
        if (this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i += 1) {
            if (this.get(i) != other.get(i)) {
                return false;
            }
        }
        return true;
    }



}
