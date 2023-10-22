package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T>{
    /**Doubly Linked List Node*/
    private class TNode{
        public TNode prev;
        public T item;
        public TNode next;
        public TNode(TNode p, T i, TNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }


    /** circular sentinel approaches*/
    /* The first item (if it exists) is at sentinel.next. */
    private TNode sentinel;
    private int size;

    /** Creates an empty LinkedListDeque. */
    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }


    public LinkedListDeque(T x) {
        sentinel = new TNode(null, null, null);
        sentinel.next = new TNode(sentinel,x,sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }


    /** Adds x to the front of the list. */
    @Override
    public void addFirst(T x) {
        sentinel.next = new TNode(sentinel, x, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size = size + 1;
    }

    @Override
    public void addLast(T x) {
        sentinel.prev.next = new TNode(sentinel.prev, x, sentinel);
        sentinel.prev = sentinel.prev.next;
        size = size + 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        TNode p = sentinel.next;//the first item sentinel.next
        while (p != sentinel.prev) {//the last item sentinel.prev
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
        System.out.println(p.item);
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        TNode t = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;

        size = size - 1;
        return t.item;
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        TNode t = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;

        size = size - 1;
        return t.item;
    }


    @Override
    public T get(int index) {
        if ((index < 0) || (index >= size)) {
            return null;
        }

        int i = 0;
        TNode p = sentinel.next;
        while (i != index) {
            p = p.next;
            i = i + 1;
        }
        return p.item;
    }

    public T getRecursiveBasic(TNode p, int index) {
        if(index == 0) {
            return p.item;
        }
        else {
            return getRecursiveBasic(p.next, index - 1);
        }
    }
    public T getRecursive(int index) {

        if ((index < 0) || (index >= size)) {
            return null;
        }
        else {
            return getRecursiveBasic(sentinel.next, index);
        }
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
    private class LinkedListDequeIterator implements Iterator<T> {
        private TNode p;
        public LinkedListDequeIterator() {
            p = sentinel.next;
        }

        public boolean hasNext() {
            return p != sentinel;
        }

        public T next() {
            T returnItem = p.item;
            p = p.next;
            return returnItem;
        }

    }

    /** Returns true if this Deque equals to Object o;
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(o == null) {
            return false;
        }
        if ( !(o instanceof Deque)) {
            return false;
        }

        Deque<T> other = (Deque<T>) o;
        if(this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i += 1) {
            if(this.get(i) != other.get(i)) {
                return false;
            }
        }
        return true;
    }

}
