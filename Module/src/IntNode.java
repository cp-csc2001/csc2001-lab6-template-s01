/**
 * A mutable binary tree node holding an int.
 * This class is complete - you do not need to change it.
 */
public class IntNode {
    int value;
    IntNode left;
    IntNode right;

    /** A leaf node (no children). */
    public IntNode(int value) {
        this(value, null, null);
    }

    /** A node with the given children. Handy for building trees by hand in tests. */
    public IntNode(int value, IntNode left, IntNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}
