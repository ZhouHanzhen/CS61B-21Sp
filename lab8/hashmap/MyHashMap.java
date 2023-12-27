package hashmap;

import afu.org.checkerframework.checker.oigj.qual.O;
import org.checkerframework.checker.units.qual.C;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class  MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size;
    private double loadFactor;
    private int bucketNum;
    private HashSet<K> keys;

    /** Constructors */
    public MyHashMap() {
        size = 0;
        loadFactor = 0.75;
        bucketNum = 16;
        buckets = createTable(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            buckets[i] = createBucket();
        }
        keys = new HashSet<>();
    }

    public MyHashMap(int initialSize) { 
        size = 0;
        loadFactor = 0.75;
        bucketNum = initialSize;
        buckets = createTable(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            buckets[i] = createBucket();
        }
        keys = new HashSet<>();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        size = 0;
        loadFactor = maxLoad;
        bucketNum = initialSize;
        buckets = createTable(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            buckets[i] = createBucket();
        }
        keys = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    protected Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    //Helpful function

    //Get the hashTable index of a Node
    private int getBucketIndex(K key) {
        int hc = key.hashCode();
        int index = Math.floorMod(hc, bucketNum);
        return index;
    }

    //Add a Node to the HashTable
    //if N/M is bigger than maxLoad,to resize
    private void addNode(K key, V value) {
        double load = size * 1.0 / bucketNum;

        if (load > loadFactor){
            //Resize
            int newBucketNum = bucketNum * 2;
            Collection<Node>[] newBuckets = createTable(newBucketNum);
            for (int i = 0; i < newBucketNum; i++) {
                newBuckets[i] = createBucket();
            }

            //copy node from old table to new table
            for (int j = 0; j < bucketNum; j++) {
                for (Node n : buckets[j]) {
                    int l = getBucketIndex(n.key);
                    newBuckets[l].add(n);
                }
            }

            //replace
            bucketNum = newBucketNum;
            buckets = newBuckets;
        }

        //add node
        Node x = createNode(key, value);
        int index = getBucketIndex(key);
        buckets[index].add(x);
        keys.add(key);
        size += 1;

    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < bucketNum; i++) {
            buckets[i] = createBucket();
        }
        keys = new HashSet<>();
    }

    @Override
    public boolean containsKey(K key) {
        int index = getBucketIndex(key);
        for (Node n : buckets[index]) {
            if(n.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = getBucketIndex(key);
        for (Node n : buckets[index]) {
            if(n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    //When the same key is inserted, update the value
    private void changeValue(K key, V value) {
        int index = getBucketIndex(key);
        for (Node n : buckets[index]) {
            if(n.key.equals(key)) {
                n.value = value;
                break;
            }
        }
    }

    //remove a node if it matches the key
    private V delete(K key) {
        int index = getBucketIndex(key);
        for (Node n : buckets[index]) {
            if(n.key.equals(key)) {
                V v = n.value;
                buckets[index].remove(n);
                keys.remove(key);
                size -= 1;
                return v;
            }
        }
        return null;
    }

    //remove a node if it matches the key and the value
    private V delete(K key, V value) {
        int index = getBucketIndex(key);
        for (Node n : buckets[index]) {
            if((n.key.equals(key)) && (n.value.equals(value))) {
                V v = n.value;
                buckets[index].remove(n);
                keys.remove(key);
                size -= 1;
                return v;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            addNode(key, value);
        }
        else {
            //已经存在key
            //因为如果改变Node value的值，hashcode()也会改变，所以先执行删除key，在添加key
            delete(key);
            addNode(key, value);
        }
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        return delete(key);
    }

    @Override
    public V remove(K key, V value) {
        return delete(key, value);
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {
        private int cur;
        private List<K> keysIter;
        public MyHashMapIterator() {
            cur = 0;
            keysIter = new ArrayList<>();
            keysIter.addAll(keys);
        }
        @Override
        public boolean hasNext() {
            return cur < size;
        }

        @Override
        public K next() {
            K k = keysIter.get(cur);
            cur += 1;
            return k;
        }
    }

}
