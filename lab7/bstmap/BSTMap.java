package bstmap;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.StdOut;
import org.apache.commons.math3.random.BitsStreamGenerator;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{

    private int size;
    private BSTree bst;
    private List<K> keyList;//按增序记录bst中的key

    public BSTMap() {
        size = 0;
        bst = null;
        keyList = new ArrayList<>();
    }

    private class BSTree {
        K key;
        V val;
        BSTree leftChild;
        BSTree rightChild;

        BSTree(K k, V v, BSTree lchild, BSTree rChild) {
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

        BSTree BSTGet(K getKey) {
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
        BSTree BSTInsert(K insertKey, V insertValue) {
            int cmp = insertKey.compareTo(this.key);
            if (cmp == 0) {
                this.val = insertValue; //replace the previous value
            }
            else if (cmp < 0) {
                if (leftChild == null) {
                    leftChild = new BSTree(insertKey, insertValue, null, null);
                }
                else {
                    leftChild = leftChild.BSTInsert(insertKey, insertValue);
                }

            }
            else {
                if (rightChild == null) {
                    rightChild = new BSTree(insertKey, insertValue, null, null);
                }
                else {
                    rightChild = rightChild.BSTInsert(insertKey, insertValue);
                }
            }
            return this;
        }

        BSTree BSTDelete(K deleteKey) {
            int cmp = deleteKey.compareTo(this.key);
            if (cmp < 0) {
                if (leftChild != null) {
                    leftChild = leftChild.BSTDelete(deleteKey);
                }
                //如果deleteKey不存在，这棵树不需要变化
            }
            else if (cmp > 0) {
                if (rightChild != null) {
                    rightChild = rightChild.BSTDelete(deleteKey);
                }
                //如果deleteKey不存在，这棵树不需要变化
            }
            else {//cmp == 0; this 就是需要被删除的节点
                if ((rightChild == null) && (leftChild == null)) { //删除节点为叶子节点，
                    return null;
                }
                else if (rightChild == null)  { //删除节点的右孩子为空，只有左孩子
                    this.key = leftChild.key;
                    this.val = leftChild.val;
                    this.rightChild = leftChild.rightChild;
                    this.leftChild = leftChild.leftChild; //只有左孩子的情况下this的左孩子需要在最后一步修改，才不会影响其他值的改变

                }
                else if (leftChild == null) { //删除节点的左孩子为空，只有右孩子
                    this.key = rightChild.key;
                    this.val = rightChild.val;
                    this.leftChild = rightChild.leftChild;
                    this.rightChild = rightChild.rightChild; //只有右孩子的情况下this的右孩子需要在最后一步修改，才不会影响其他值的改变
                }
                else { //删除节点左右孩子均不为空，需要找到被删除节点的前驱或者后继节点
                    BSTree q = this; //q用来指向被删除节点的前驱节点的双亲节点
                    BSTree s = this.leftChild; //s用来指向被删除节点的前驱节点
                    while (s.rightChild != null) {
                        q = s;
                        s = s.rightChild;
                    }
                    //将s的key,value传递给被删除节点this;
                    this.key = s.key;
                    this.val = s.val;

                    if (q != this) {
                        q.rightChild = s.leftChild;
                    }else {
                        q.leftChild = s.leftChild;
                    }
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

        BSTree lookup = bst.BSTGet(key);
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
            bst = new BSTree(key, value, null, null);
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
        V reVal = get(key);
        if (reVal != null) {
            bst = bst.BSTDelete(key);
            size -= 1;
            keyList.remove(key);
        }
        return reVal;
    }

    @Override
    public V remove(K key, V value) {
        V reVal = get(key);
        if (reVal == value) {
            bst = bst.BSTDelete(key);
            size -= 1;
            keyList.remove(key);
            return reVal;
        }else {
            return null;
        }
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
        @Override
        public boolean hasNext() {
            return cur < size;
        }

        @Override
        public K next() {
            K ret = keyList.get(cur);
            cur += 1;
            return ret;
        }
    }
}
