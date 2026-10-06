import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Part B (TreeQueries).
 * These build trees by hand with the IntNode constructor, so some of them
 * are deliberately NOT valid BSTs.
 */
public class TreeQueriesTest {

    /** Shorthand for building trees by hand. */
    private static IntNode n(int v, IntNode left, IntNode right) {
        return new IntNode(v, left, right);
    }

    private static IntNode leaf(int v) {
        return new IntNode(v);
    }

    /** The lecture tree: 8, 3, 12, 1, 6, 10, 15, 4, 11, 14, 18. */
    private static IntNode lectureTree() {
        return n(8,
                n(3, leaf(1), n(6, leaf(4), null)),
                n(12, n(10, null, leaf(11)), n(15, leaf(14), leaf(18))));
    }

    // ---------- height ----------

    @Test
    public void testHeight() {
        assertEquals(-1, TreeQueries.height(null), "the empty tree has height -1");
        assertEquals(0, TreeQueries.height(leaf(7)), "a single node has height 0");
        assertEquals(1, TreeQueries.height(n(7, leaf(3), null)));
        assertEquals(3, TreeQueries.height(lectureTree()));
    }

    @Test
    public void testHeightOfLopsidedTree() {
        // 1 -> 2 -> 3 -> 4 -> 5, all right children (what sorted inserts build)
        IntNode chain = n(1, null, n(2, null, n(3, null, n(4, null, leaf(5)))));
        assertEquals(4, TreeQueries.height(chain));   // 4 edges, 5 nodes
    }

    // ---------- countLeaves ----------

    @Test
    public void testCountLeaves() {
        assertEquals(0, TreeQueries.countLeaves(null));
        assertEquals(1, TreeQueries.countLeaves(leaf(7)));
        assertEquals(1, TreeQueries.countLeaves(n(7, leaf(3), null)));
        assertEquals(5, TreeQueries.countLeaves(lectureTree()));
    }

    // ---------- isValidBST ----------

    @Test
    public void testValidTrees() {
        assertTrue(TreeQueries.isValidBST(null));
        assertTrue(TreeQueries.isValidBST(leaf(7)));
        assertTrue(TreeQueries.isValidBST(lectureTree()));
    }

    @Test
    public void testChildOnWrongSide() {
        assertFalse(TreeQueries.isValidBST(n(5, leaf(9), null)));
        assertFalse(TreeQueries.isValidBST(n(5, null, leaf(2))));
    }

    @Test
    public void testDuplicateIsInvalid() {
        assertFalse(TreeQueries.isValidBST(n(5, leaf(5), null)));
        assertFalse(TreeQueries.isValidBST(n(5, null, leaf(5))));
    }

    @Test
    public void testGrandchildOnWrongSideOfRoot() {
        // Every parent/child pair looks fine, but 9 is in 8's LEFT subtree:
        //        8
        //       /
        //      3
        //       \
        //        9
        assertFalse(TreeQueries.isValidBST(n(8, n(3, null, leaf(9)), null)));

        // Same trap on the right side: 7 is in 8's RIGHT subtree.
        //        8
        //         \
        //          12
        //         /
        //        7
        assertFalse(TreeQueries.isValidBST(n(8, null, n(12, leaf(7), null))));
    }

    @Test
    public void testDeepViolation() {
        // The lecture tree with 11 changed to 13: 13 is under 10 (fine) and
        // under 12's left subtree (not fine).
        IntNode t = n(8,
                n(3, leaf(1), n(6, leaf(4), null)),
                n(12, n(10, null, leaf(13)), n(15, leaf(14), leaf(18))));
        assertFalse(TreeQueries.isValidBST(t));
    }

    @Test
    public void testExtremeValues() {
        assertTrue(TreeQueries.isValidBST(n(0, leaf(Integer.MIN_VALUE), leaf(Integer.MAX_VALUE))));
    }

}
