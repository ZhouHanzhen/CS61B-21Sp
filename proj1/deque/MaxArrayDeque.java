package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> cptr;
    public MaxArrayDeque(Comparator<T> c) {
        super();
        cptr = c;
    }

    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            T tempItem = this.get(i);
            if(cptr.compare(tempItem, maxItem) > 0) {
                maxItem = tempItem;
            }
        }
        return maxItem;
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            T tempItem = this.get(i);
            if(c.compare(tempItem, maxItem) > 0) {
                maxItem = tempItem;
            }
        }
        return maxItem;
    }


}
