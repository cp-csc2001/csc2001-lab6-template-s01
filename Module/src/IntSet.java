import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A set of ints stored in a binary search tree of mutable IntNodes.
 *
 * The tree is private: no method ever returns an IntNode, so code outside
 * this class cannot reach in and change (or break) the tree. Every change
 * goes through add() and remove().
 *
 * Duplicates are not stored: adding a value that is already present
 * leaves the set unchanged.
 *
 * Do not rename the public methods below - the tests call them by name.
 * You may add private helper methods.
 */
public class IntSet {

    private IntNode root;   // null when the set is empty
    private int size;       // number of values in the set

    /** Creates an empty set. */
    public IntSet() {
        root = null;
        size = 0;
    }

    /**
     * Adds v to the set.
     * return true if v was added, false if it was already present
     */
    public boolean add(int v) {
        // TODO: use the insert() helper below - and remember to keep size up to date
    }

    /**
     * Inserts v below t and returns the root of that subtree afterward.
     * Mutable style: reassign t.left / t.right instead of building new nodes.
     */
    private static IntNode insert(IntNode t, int v) {
        // TODO
    }

    /**
     * Reports whether v is in the set.
     * return true if v is present
     */
    public boolean contains(int v) {
        // TODO
    }

    /**
     * Removes v from the set.
     * return true if v was removed, false if it was not present
     */
    public boolean remove(int v) {
        // TODO: use the remove(IntNode, int) helper below
    }

    /**
     * Removes v below t and returns the root of that subtree afterward.
     * Handle all three cases from lecture: leaf, one child, two children
     * (copy the in-order successor up, then remove it from the right subtree).
     */
    private static IntNode remove(IntNode t, int v) {
        // TODO
    }

    /** @return the number of values in the set */
    public int size() {
        // TODO
    }

    /** @return true if the set has no values */
    public boolean isEmpty() {
        // TODO
    }

    /**
     * @return the smallest value in the set
     * @throws NoSuchElementException if the set is empty
     */
    public int min() {
        // TODO
    }

    /**
     * @return the largest value in the set
     * @throws NoSuchElementException if the set is empty
     */
    public int max() {
        // TODO
    }

    /**
     * return a new list of every value in the set, in increasing order
     */
    public List<Integer> toList() {
        List<Integer> out = new ArrayList<>();
        // TODO: fill out with an in-order traversal (a private recursive helper works well)
        return out;
    }

    /**
     * return the height of the underlying tree (see TreeQueries.height).
     * This one is done for you, but it depends on your Part B code.
     */
    public int height() {
        return TreeQueries.height(root);
    }
}
