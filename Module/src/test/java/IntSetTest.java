import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Part A (IntSet).
 *
 * Many tests use the 11-value tree from lecture, built by adding
 *     8, 3, 12, 1, 6, 10, 15, 4, 11, 14, 18
 * in that order, which gives this shape:
 *
 *                 8
 *            /         \
 *           3           12
 *          / \        /    \
 *         1   6      10     15
 *            /         \   /  \
 *           4          11 14  18
 *
 *   leaves:          1, 4, 11, 14, 18
 *   one child:       6 (left: 4), 10 (right: 11)
 *   two children:    3, 8, 12, 15
 *
 * Feel free to add your own tests, but these (with these exact names)
 * should all pass by the time you're done.
 */
public class IntSetTest {

    private static final int[] LECTURE_ORDER = {8, 3, 12, 1, 6, 10, 15, 4, 11, 14, 18};

    /** Builds the lecture tree shown above. */
    private static IntSet lectureSet() {
        IntSet s = new IntSet();
        for (int v : LECTURE_ORDER) {
            s.add(v);
        }
        return s;
    }

    /** Checks size, sorted contents, and that contains() finds every value. */
    private static void assertSetIs(IntSet s, Integer... expected) {
        assertEquals(List.of(expected), s.toList(), "toList() contents");
        assertEquals(expected.length, s.size(), "size()");
        for (int v : expected) {
            assertTrue(s.contains(v), "contains(" + v + ") should be true");
        }
    }

    // ---------- empty set ----------

    @Test
    public void testEmptySet() {
        IntSet s = new IntSet();
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
        assertEquals(List.of(), s.toList());
        assertFalse(s.contains(5));
        assertEquals(-1, s.height(), "the empty tree has height -1");
    }

    @Test
    public void testMinMaxOfEmptySetThrow() {
        IntSet s = new IntSet();
        assertThrows(NoSuchElementException.class, s::min);
        assertThrows(NoSuchElementException.class, s::max);
    }

    // ---------- add / contains / size ----------

    @Test
    public void testAddAndContains() {
        IntSet s = new IntSet();
        assertTrue(s.add(5));
        assertTrue(s.add(2));
        assertTrue(s.add(9));
        assertTrue(s.contains(5));
        assertTrue(s.contains(2));
        assertTrue(s.contains(9));
        assertFalse(s.contains(7));
        assertFalse(s.isEmpty());
        assertEquals(3, s.size());
    }

    @Test
    public void testAddDuplicateIsIgnored() {
        IntSet s = new IntSet();
        assertTrue(s.add(4));
        assertFalse(s.add(4), "adding a value that is already present should return false");
        assertEquals(1, s.size(), "a duplicate should not change size()");
        assertEquals(List.of(4), s.toList());
    }

    @Test
    public void testNegativeAndExtremeValues() {
        IntSet s = new IntSet();
        s.add(0);
        s.add(-7);
        s.add(Integer.MAX_VALUE);
        s.add(Integer.MIN_VALUE);
        assertSetIs(s, Integer.MIN_VALUE, -7, 0, Integer.MAX_VALUE);
        assertEquals(Integer.MIN_VALUE, s.min());
        assertEquals(Integer.MAX_VALUE, s.max());
    }

    // ---------- toList / min / max / height ----------

    @Test
    public void testToListIsSorted() {
        assertSetIs(lectureSet(), 1, 3, 4, 6, 8, 10, 11, 12, 14, 15, 18);
    }

    @Test
    public void testToListReturnsACopy() {
        IntSet s = lectureSet();
        List<Integer> list = s.toList();
        try {
            list.clear();   // changing the returned list...
        } catch (UnsupportedOperationException e) {
            // ...or an unmodifiable list is fine too
        }
        assertEquals(11, s.size(), "changing the list from toList() must not change the set");
        assertTrue(s.contains(8));
    }

    @Test
    public void testMinAndMax() {
        IntSet s = lectureSet();
        assertEquals(1, s.min());
        assertEquals(18, s.max());
    }

    @Test
    public void testHeightOfLectureTree() {
        assertEquals(3, lectureSet().height());
    }

    // ---------- remove: the three cases ----------

    @Test
    public void testRemoveLeaf() {
        IntSet s = lectureSet();
        assertTrue(s.remove(4));
        assertFalse(s.contains(4));
        assertSetIs(s, 1, 3, 6, 8, 10, 11, 12, 14, 15, 18);
    }

    @Test
    public void testRemoveNodeWithOnlyRightChild() {
        IntSet s = lectureSet();
        assertTrue(s.remove(10));
        assertFalse(s.contains(10));
        assertSetIs(s, 1, 3, 4, 6, 8, 11, 12, 14, 15, 18);
    }

    @Test
    public void testRemoveNodeWithOnlyLeftChild() {
        IntSet s = lectureSet();
        assertTrue(s.remove(6));
        assertFalse(s.contains(6));
        assertSetIs(s, 1, 3, 4, 8, 10, 11, 12, 14, 15, 18);
    }

    @Test
    public void testRemoveNodeWithTwoChildren() {
        IntSet s = lectureSet();
        assertTrue(s.remove(12));   // successor 14 is a leaf
        assertFalse(s.contains(12));
        assertSetIs(s, 1, 3, 4, 6, 8, 10, 11, 14, 15, 18);
    }

    @Test
    public void testRemoveRoot() {
        IntSet s = lectureSet();
        assertTrue(s.remove(8));    // two children; successor 10 has a right child
        assertFalse(s.contains(8));
        assertSetIs(s, 1, 3, 4, 6, 10, 11, 12, 14, 15, 18);
    }

    @Test
    public void testRemoveRootWithOneChild() {
        IntSet s = new IntSet();
        s.add(5);
        s.add(9);
        s.add(7);
        assertTrue(s.remove(5));    // the root itself is replaced
        assertSetIs(s, 7, 9);
        assertEquals(7, s.min());
    }

    @Test
    public void testRemoveOnlyValue() {
        IntSet s = new IntSet();
        s.add(42);
        assertTrue(s.remove(42));
        assertTrue(s.isEmpty());
        assertEquals(List.of(), s.toList());
        assertEquals(-1, s.height());
        assertTrue(s.add(42), "the set should be usable again after becoming empty");
        assertEquals(List.of(42), s.toList());
    }

    @Test
    public void testRemoveMissingValue() {
        IntSet s = lectureSet();
        assertFalse(s.remove(99));
        assertFalse(s.remove(7));
        assertSetIs(s, 1, 3, 4, 6, 8, 10, 11, 12, 14, 15, 18);
        assertFalse(new IntSet().remove(1), "removing from an empty set returns false");
    }

    @Test
    public void testRemoveEverything() {
        IntSet s = lectureSet();
        // remove in an order that hits every case, including the root several times
        int[] order = {8, 3, 15, 4, 10, 18, 1, 12, 6, 14, 11};
        int expectedSize = 11;
        for (int v : order) {
            assertTrue(s.remove(v), "remove(" + v + ")");
            expectedSize--;
            assertEquals(expectedSize, s.size());
            assertFalse(s.contains(v));
            List<Integer> left = s.toList();
            for (int i = 1; i < left.size(); i++) {
                assertTrue(left.get(i - 1) < left.get(i), "toList() not sorted after remove(" + v + ")");
            }
            for (int w : left) {
                assertTrue(s.contains(w), "contains(" + w + ") lost after remove(" + v + ")");
            }
        }
        assertTrue(s.isEmpty());
    }

    @Test
    public void testAddAfterRemove() {
        IntSet s = lectureSet();
        s.remove(8);
        s.remove(12);
        assertTrue(s.add(9));
        assertTrue(s.add(12));
        assertSetIs(s, 1, 3, 4, 6, 9, 10, 11, 12, 14, 15, 18);
    }
}
