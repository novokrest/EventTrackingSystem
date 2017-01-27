package core;

public class AvlTree<Key extends Comparable<Key>, Value extends NumberEx> {
    private AvlNode<Key, Value> root;
    private int size;

    public int size() {
        return size;
    }

    public boolean empty() {
        return root == null;
    }

    public Value value() {
        if (root != null) {
            return root.value;
        }
        return null;
    }

    public void increment(Key k) {
        AvlNode<Key, Value> node = found(k);
        increment(node);
    }

    private void increment(AvlNode<Key, Value> node) {
        while (node != null) {
            node.value.increment();
            node = node.parent;
        }
    }

    public void insert(Key k, Value value) {
        AvlNode<Key, Value> n = new AvlNode<>(k, value);
        insertAVL(root, n);
        ++size;
    }

    public void insertAVL(AvlNode<Key, Value> p, AvlNode<Key, Value> q) {
        if(p == null) {
            root = q;
            return;
        }

        if(q.key.compareTo(p.key) < 0) {
            if (p.left == null) {
                p.left = q;
                q.parent = p;
                recursiveBalance(p);
            } else {
                insertAVL(p.left, q);
            }
            return;
        }

        if(q.key.compareTo(p.key) > 0) {
            if(p.right == null) {
                p.right = q;
                q.parent = p;
                recursiveBalance(p);
            } else {
                insertAVL(p.right,q);
            }
        }
    }

    public void removeMin() {
        AvlNode<Key, Value> node = root;
        while (node.left != null) {
            node = node.left;
        }
        removeFoundNode(node);
    }

    public void remove(Key k) {
        AvlNode<Key, Value> p = found(k);
        if (p != null) {
            removeFoundNode(p);
        }
    }

    public void removeLessOrEqual(Key k) {
        AvlNode<Key, Value> p = found(k);
        if (p != null) {
            removeFoundNodeAndLeftSubTree(p);
        }
    }

    public void removeFoundNode(AvlNode<Key, Value> q) {
        AvlNode<Key, Value> r;
        if(q.left == null || q.right == null) {
            if(q.parent == null) {
                root = null;
                return;
            }
            r = q;
        } else {
            r = successor(q);
            q.key = r.key;
        }

        AvlNode<Key, Value> p;
        if(r.left != null) {
            p = r.left;
        } else {
            p = r.right;
        }

        if(p != null) {
            p.parent = r.parent;
        }

        if(r.parent == null) {
            root = p;
        } else {
            if(r == r.parent.left) {
                r.parent.left = p;
            } else {
                r.parent.right = p;
            }
            recursiveBalance(r.parent);
        }
    }

    public void removeFoundNodeAndLeftSubTree(AvlNode<Key, Value> p) {
        if (p.parent == null) {
            root = null;
            return;
        }

        AvlNode<Key, Value> parent = p.parent;
        if (parent.left == p) {
            parent.left = p.right;
        }
        else {
            parent.right = p.right;
        }

        recursiveBalance(parent);
    }

    public Value foundValue(Key k) {
        AvlNode<Key, Value> founded = found(k);
        if (founded != null) {
            return founded.value;
        }
        return null;
    }

    private AvlNode<Key, Value> found(Key k) {
        return found(root, k);
    }

    private AvlNode<Key, Value> found(AvlNode<Key, Value> p, Key k) {
        if (p == null || p.key.compareTo(k) == 0) {
            return p;
        }

        if(p.key.compareTo(k) > 0)  {
            return found(p.left,k);
        }

        return found(p.right,k);
    }

    private void recursiveBalance(AvlNode<Key, Value> cur) {
        update(cur);
        int balance = cur.balance;

        if(balance == -2) {
            if(safeHeight(cur.left.left) >= safeHeight(cur.left.right)) {
                cur = rotateRight(cur);
            } else {
                cur = doubleRotateLeftRight(cur);
            }
        } else if(balance == 2) {
            if(safeHeight(cur.right.right) >= safeHeight(cur.right.left)) {
                cur = rotateLeft(cur);
            } else {
                cur = doubleRotateRightLeft(cur);
            }
        }

        if(cur.parent != null) {
            recursiveBalance(cur.parent);
        } else {
            root = cur;
        }
    }

    private AvlNode<Key, Value> doubleRotateLeftRight(AvlNode<Key, Value> u) {
        u.left = rotateLeft(u.left);
        return rotateRight(u);
    }

    private AvlNode<Key, Value> doubleRotateRightLeft(AvlNode<Key, Value> u) {
        u.right = rotateRight(u.right);
        return rotateLeft(u);
    }

    private AvlNode<Key, Value> rotateLeft(AvlNode<Key, Value> n) {
        AvlNode<Key, Value> v = n.right;
        v.parent = n.parent;

        n.right = v.left;

        if(n.right != null) {
            n.right.parent = n;
        }

        v.left = n;
        n.parent = v;

        if(v.parent != null) {
            if(v.parent.right == n) {
                v.parent.right = v;
            } else if(v.parent.left == n) {
                v.parent.left = v;
            }
        }

        update(n);
        update(v);

        return v;
    }

    private AvlNode<Key, Value> rotateRight(AvlNode<Key, Value> n) {

        AvlNode<Key, Value> v = n.left;
        v.parent = n.parent;

        n.left = v.right;

        if(n.left != null) {
            n.left.parent = n;
        }

        v.right = n;
        n.parent = v;


        if(v.parent != null) {
            if(v.parent.right == n) {
                v.parent.right = v;
            } else if(v.parent.left == n) {
                v.parent.left = v;
            }
        }

        update(n);
        update(v);

        return v;
    }

    private AvlNode<Key, Value> successor(AvlNode<Key, Value> q) {
        if(q.right != null) {
            AvlNode<Key, Value> r = q.right;
            while(r.left != null) {
                r = r.left;
            }
            return r;
        } else {
            AvlNode<Key, Value> p = q.parent;
            while(p != null && q == p.right) {
                q = p;
                p = q.parent;
            }
            return p;
        }
    }

    private void update(AvlNode<Key, Value> cur) {
        int leftHeight = safeHeight(cur.left), rightHeight = safeHeight(cur.right);
        cur.height = Math.max(leftHeight, rightHeight) + 1;
        cur.balance = rightHeight - leftHeight;
        cur.value.set(safeValue(cur.left) + safeValue(cur.right));
    }

    private int safeHeight(AvlNode<Key, Value> cur) {
        if(cur == null) {
            return -1;
        }
        return cur.height;
    }

    private int safeValue(AvlNode<Key, Value> cur) {
        if (cur == null) {
            return 0;
        }
        return cur.value.getInt();
    }

    private static class AvlNode<T, Value> {
        private AvlNode<T, Value> left;
        private AvlNode<T, Value> right;
        private AvlNode<T, Value> parent;

        private T key;
        private Value value;
        private int balance;
        private int height;

        private AvlNode(T key, Value value) {
            Verifiers.verifyArgNotNull(key, "key");
            Verifiers.verifyArgNotNull(value, "value");
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "" + key;
        }
    }
}
