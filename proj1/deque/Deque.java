package deque;

public interface Deque<T> {

    //Adds an item of type T to the front of the deque.
    void addFirst(T item);

    // Adds an item of type T to the back of the deque.
    void addLast(T item);

    //Returns the number of items in the deque.
    int size();

    //Prints the items in the deque from first to last, separated by a space.
    void printDeque();

    //Removes and returns the item at the front of the deque.
    T removeFirst();

    // Removes and returns the item at the back of the deque.
    T removeLast();

    //Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    T get(int index);

    //Returns true if deque is empty, false otherwise.
    default boolean isEmpty() {
        return this.size() == 0;
    }

}
