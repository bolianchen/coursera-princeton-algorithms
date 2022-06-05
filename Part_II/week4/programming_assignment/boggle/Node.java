// R-way trie node
public class Node {
    // only for upper-case Letters
    private int R = 26;
    private Node[] next = new Node[R];
    private boolean isString;

    public Node getNext(char c) {
        return next[c - 65];
    }

    public void setNext(char c, Node n) {
        next[c - 65] = n;
    }

    public boolean isString() {
        return isString;
    }

    public void setIsString(boolean v) {
        isString = v;
    }
}
