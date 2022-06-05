import java.util.Iterator;

public class CapitalLettersTrieSET {

    private Node root;      // root of trie
    private int n;          // number of keys in trie

    /**
     * Initializes an empty set of strings.
     */
    public CapitalLettersTrieSET() {
    }

    /**
     * Does the set contain the given key?
     * @param key the key
     * @return {@code true} if the set contains {@code key} and
     *     {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString();
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.getNext(c), key, d+1);
    }

    /**
     * Adds the key to the set if it is not already present.
     * @param key the key to add
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.isString()) n++;
            x.setIsString(true);
        }
        else {
            char c = key.charAt(d);
            x.setNext(c, add(x.getNext(c), key, d+1));
        }
        return x;
    }

    /**
     * Returns all of the keys in the set that start with {@code prefix}.
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     *     as an iterable
     */
    public Node keysWithPrefix(String prefix) {
        Node x = get(root, prefix, 0);
        return x;
    }

}
