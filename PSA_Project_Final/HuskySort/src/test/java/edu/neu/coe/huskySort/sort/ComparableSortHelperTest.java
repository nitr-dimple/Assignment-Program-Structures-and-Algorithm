package edu.neu.coe.huskySort.sort;

import org.junit.Test;

import static org.junit.Assert.*;

public class ComparableSortHelperTest {

    static final class BaseHelperWithSortedTest<X extends Comparable<X>> extends ComparableSortHelper<X> {

        public BaseHelperWithSortedTest() {
            super("test");
        }

        public BaseHelperWithSortedTest(final int i, final long l) {
            super("test", i, l);
        }

        /**
         * Method to post-process the array xs after sorting.
         * By default, this method does nothing.
         *
         * @param xs the array to be tested.
         * @return the result of calling sorted(xs).
         */
        @Override
        public boolean postProcess(final X[] xs) {
            return sorted(xs);
        }
    }

    @Test
    public void instrumented() {
        assertFalse(new BaseHelperWithSortedTest<String>().instrumented());
    }

    @Test
    public void inverted() {
        final BaseHelperWithSortedTest<String> stringBaseHelperWithSortedTest = new BaseHelperWithSortedTest<>();
        assertFalse(stringBaseHelperWithSortedTest.inverted("a", "b"));
        assertTrue(stringBaseHelperWithSortedTest.inverted("b", "a"));
    }

    @Test
    public void compare() {
        final String[] xs = new String[]{"a", "b"};
        final ComparisonSortHelper<String> helper = new BaseHelperWithSortedTest<>();
        assertEquals(-1, helper.compare(xs, 0, 1));
        assertEquals(0, helper.compare(xs, 0, 0));
        assertEquals(1, helper.compare(xs, 1, 0));
    }

    @Test
    public void swap() {
        final String[] xs = new String[]{"a", "b"};
        final ComparisonSortHelper<String> helper = new BaseHelperWithSortedTest<>();
        helper.swap(xs, 0, 1);
        assertArrayEquals(new String[]{"b", "a"}, xs);
        helper.swap(xs, 0, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
    }

    @Test
    public void sorted() {
        final String[] xs = new String[]{"a", "b"};
        final ComparisonSortHelper<String> helper = new BaseHelperWithSortedTest<>();
        assertTrue(helper.sorted(xs));
        helper.swap(xs, 0, 1);
        assertFalse(helper.sorted(xs));
    }

    @Test
    public void inversions() {
        final String[] xs = new String[]{"a", "b"};
        final ComparisonSortHelper<String> helper = new BaseHelperWithSortedTest<>();
        assertEquals(0, helper.inversions(xs));
        helper.swap(xs, 0, 1);
        assertEquals(1, helper.inversions(xs));
    }

    @Test
    public void postProcess1() {
        final String[] xs = new String[]{"a", "b"};
        final ComparisonSortHelper<String> helper = new BaseHelperWithSortedTest<>();
        helper.postProcess(xs);
    }

    @Test
    public void postProcess2() {
        final String[] xs = new String[]{"b", "a"};
        final ComparisonSortHelper<String> helper = new BaseHelperWithSortedTest<>();
        assertFalse(helper.postProcess(xs));
    }

    @Test
    public void random() {
        final String[] words = new String[]{"Hello", "World"};
        final ComparisonSortHelper<String> helper = new BaseHelperWithSortedTest<>(3, 0L);
        final String[] strings = helper.random(String.class, r -> words[r.nextInt(2)]);
        assertArrayEquals(new String[]{"World", "World", "Hello"}, strings);
    }

    @Test
    public void testToString() {
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test", 3);
        assertEquals("ComparisonSortHelper for test with 3 elements", helper.toString());
    }

    @Test
    public void getDescription() {
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test", 3);
        assertEquals("test", helper.getDescription());
    }

    @Test(expected = RuntimeException.class)
    public void getSetN() {
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test", 3);
        assertEquals(3, helper.getN());
        helper.init(4);
        assertEquals(4, helper.getN());
    }

    @Test
    public void getSetNBis() {
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test");
        assertEquals(0, helper.getN());
        helper.init(4);
        assertEquals(4, helper.getN());
    }

    @Test
    public void close() {
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test");
        helper.close();
    }

    @Test
    public void swapStable() {
        final String[] xs = new String[]{"a", "b"};
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test");
        helper.swapStable(xs, 1);
        assertArrayEquals(new String[]{"b", "a"}, xs);
        helper.swapStable(xs, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
    }

    @Test
    public void fixInversion1() {
        final String[] xs = new String[]{"a", "b"};
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test");
        helper.fixInversion(xs, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
        helper.swapStable(xs, 1);
        assertArrayEquals(new String[]{"b", "a"}, xs);
        helper.fixInversion(xs, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
    }

    @Test
    public void testFixInversion2() {
        final String[] xs = new String[]{"a", "b"};
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test");
        helper.fixInversion(xs, 0, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
        helper.swap(xs, 0, 1);
        assertArrayEquals(new String[]{"b", "a"}, xs);
        helper.fixInversion(xs, 0, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
    }

    @Test
    public void testSwapInto() {
        final String[] xs = new String[]{"a", "b", "c"};
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test");
        helper.swapInto(xs, 0, 2);
        assertArrayEquals(new String[]{"c", "a", "b"}, xs);
        helper.swapInto(xs, 0, 1);
        assertArrayEquals(new String[]{"a", "c", "b"}, xs);
        helper.swapInto(xs, 0, 0);
        assertArrayEquals(new String[]{"a", "c", "b"}, xs);
    }


    @Test
    public void testSwapIntoSorted0() {
        final String[] xs = new String[]{"a", "b", "c"};
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test");
        helper.swapIntoSorted(xs, 2);
        assertArrayEquals(new String[]{"a", "b", "c"}, xs);
    }

    @Test
    public void testSwapIntoSorted1() {
        final String[] xs = new String[]{"a", "c", "b"};
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test");
        helper.swapIntoSorted(xs, 2);
        assertArrayEquals(new String[]{"a", "b", "c"}, xs);
    }

    @Test
    public void testSwapIntoSorted2() {
        final String[] xs = new String[]{"a", "c", "b"};
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test");
        helper.swapIntoSorted(xs, 1);
        assertArrayEquals(new String[]{"a", "c", "b"}, xs);
    }

    @Test
    public void testSwapIntoSorted3() {
        final String[] xs = new String[]{"a", "c", "b"};
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test");
        helper.swapIntoSorted(xs, 0);
        assertArrayEquals(new String[]{"a", "c", "b"}, xs);
    }
}