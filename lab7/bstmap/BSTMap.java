package bstmap;

import edu.princeton.cs.algs4.StdOut;
import org.apache.commons.math3.random.BitsStreamGenerator;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K> {

    private int size;
    private BSTNode bst;
    private List<K> keyList;//按增序记录bst中的key

    public BSTMap() {
        size = 0;
        bst = null;
        keyList = new ArrayList<>();
    }

    private class BSTNode {
        K key;
        V val;
        BSTNode leftChild;
        BSTNode rightChild;

        BSTNode(K k, V v, BSTNode lchild, BSTNode rChild) {
            key = k;
            val = v;
            leftChild = lchild;
            rightChild = rChild;
        }

        //Search a key in a BST
        //return true if key in this tree;else return false
        boolean BSTSearch(K searchKey) {
            int cmp = searchKey.compareTo(this.key);
            if (cmp == 0) {
                return true;
            }
            else if (cmp < 0) {
                if (leftChild == null) {
                    return false;
                }
                return leftChild.BSTSearch(searchKey);
            }
            else {
                if (rightChild == null) {
                    return false;
                }
                return rightChild.BSTSearch(searchKey);
            }
        }

        BSTNode BSTGet(K getKey) {
            int cmp = getKey.compareTo(this.key);
            if (cmp == 0) {
                return this;
            }
            else if (cmp < 0) {
                if (leftChild == null) {
                    return null;
                }
                return leftChild.BSTGet(getKey);
            }
            else {
                if (rightChild == null) {
                    return null;
                }
                return rightChild.BSTGet(getKey);
            }
        }

        //Insert a node to a BST
        BSTNode BSTInsert(K insertKey, V insertValue) {
            int cmp = insertKey.compareTo(this.key);
            if (cmp == 0) {
                this.val = insertValue; //replace the previous value
            }
            else if (cmp < 0) {
                if (leftChild == null) {
                    leftChild = new BSTNode(insertKey, insertValue, null, null);
                }
                else {
                    leftChild = leftChild.BSTInsert(insertKey, insertValue);
                }

            }
            else {
                if (rightChild == null) {
                    rightChild = new BSTNode(insertKey, insertValue, null, null);
                }
                else {
                    rightChild = rightChild.BSTInsert(insertKey, insertValue);
                }
            }
            return this;
        }

        void printInOrderBST() {
            if (this.leftChild != null) {
                leftChild.printInOrderBST();
            }
            System.out.print(key + ":");
            System.out.println(val);
            if (this.rightChild != null) {
                rightChild.printInOrderBST();
            }
        }

    }

    @Override
    public void clear() {
        size = 0;
        bst = null;
    }

    @Override
    //need BST Operation: Search
    public boolean containsKey(K key) {
        if (bst == null) {
            return false;
        }
        return bst.BSTSearch(key);
    }

    @Override
    //need BST Operation: Search
    public V get(K key) {
        if (bst == null) {
            return null;
        }

        BSTNode lookup = bst.BSTGet(key);
        if (lookup == null) {
            return null;
        }
        return lookup.val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    //need BST Operation: Insert
    public void put(K key, V value) {
        if (bst == null) {
            size += 1;
            bst = new BSTNode(key, value, null, null);
        }
        else {
            if (!containsKey(key)) {
                size += 1;
            }
            bst = bst.BSTInsert(key, value);
        }
        keyList.add(key);
    }

    public void printInOrder() {
        if (bst != null) {
            bst.printInOrderBST();
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> s = new HashSet<>();
        for (K k : keyList) {
            s.add(k);
        }
        return s;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private int cur;
        public BSTMapIterator() {
            cur = 0;
        }
        public boolean hasNext() {
            return cur < size;
        }
        public K next() {
            K ret = keyList.get(cur);
            cur += 1;
            return ret;
        }
    }
}
