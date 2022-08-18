package edu.neu.coe.huskySort.sort;

import edu.neu.coe.huskySort.util.Helper;

import static java.util.Arrays.binarySearch;

/**
 * ComparisonSortHelper interface.
 * <p>
 * A ComparisonSortHelper provides all of the utilities that are needed by sort methods, for example, compare and swap.
 * <p>
 * CONSIDER having the concept of a current sub-array, then we could dispense with the lo, hi parameters.
 *
 * @param <X> The underlying type to be sorted using this Helper.
 *                      NOTE that X is not required to be Comparable of X because the actual comparison is in the override methods.
 */
public interface ComparisonSortHelper<X> extends Helper<X> {

    /**
     * Compare value v with value w.
     *
     * @param v the first value.
     * @param w the second value.
     * @return -1 if v is less than w; 1 if v is greater than w; otherwise 0.
     */
    int compare(X v, X w);

    /**
     * Compare elements i and j of xs within the subarray lo...hi
     *
     * @param xs the array.
     * @param i  one of the indices.
     * @param j  the other index.
     * @return the result of comparing xs[i] to xs[j]
     */
    int compare(X[] xs, int i, int j);

    /**
     * Method to determine if v and w are inverted.
     * By default, we invoke invertedPure, which does no instrumentation.
     *
     * @param v the first value.
     * @param w the second value.
     * @return true if v is greater than w.
     */
    default boolean inverted(final X v, final X w) {
        return invertedPure(v, w);
    }

    /**
     * Method to perform a general swap, i.e. between xs[i] and xs[j]
     *
     * @param xs the array of X elements.
     * @param i  the index of the lower of the elements to be swapped.
     * @param j  the index of the higher of the elements to be swapped.
     */
    void swap(X[] xs, int i, int j);

    /**
     * Method to perform a stable swap, i.e. between xs[i] and xs[i-1]
     *
     * @param xs the array of X elements.
     * @param i  the index of the higher of the adjacent elements to be swapped.
     */
    default void swapStable(final X[] xs, final int i) {
        swap(xs, i - 1, i);
    }

    /**
     * Method to perform a stable swap, but only if xs[i] is less than xs[i-1], i.e. out of order.
     *
     * @param xs the array of elements under consideration
     * @param i  the index of the lower element.
     * @param j  the index of the upper element.
     * @return true if there was an inversion (i.e. the order was wrong and had to be fixed).
     */
    default boolean swapConditional(final X[] xs, final int i, final int j) {
        final X v = xs[i];
        final X w = xs[j];
        final boolean result = compare(v, w) > 0;
        if (result) {
            // CONSIDER invoking swap
            xs[i] = w;
            xs[j] = v;
        }
        return result;
    }

    /**
     * Method to perform a stable swap, but only if xs[i] is less than xs[i-1], i.e. out of order.
     *
     * @param xs the array of elements under consideration
     * @param i  the index of the upper element.
     * @return true if there was an inversion (i.e. the order was wrong and had to be fixed).
     */
    default boolean swapStableConditional(final X[] xs, final int i) {
        final X v = xs[i];
        final X w = xs[i - 1];
        final boolean result = compare(v, w) < 0;
        if (result) {
            xs[i] = w;
            xs[i - 1] = v;
        }
        return result;
    }

    /**
     * Method to perform a stable swap using half-exchanges,
     * i.e. between xs[i] and xs[j] such that xs[j] is moved to index i,
     * and xs[i] thru xs[j-1] are all moved up one.
     * This type of swap is used by insertion sort.
     * <p>
     * TODO this method does not seem to work.
     *
     * @param xs the array of Xs.
     * @param i  the index of the destination of xs[j].
     * @param j  the index of the right-most element to be involved in the swap.
     */
    void swapInto(X[] xs, int i, int j);

    /**
     * Method to perform a stable swap using half-exchanges, and binary search.
     * i.e. x[i] is moved leftwards to its proper place and all elements from
     * the destination of x[i] thru x[i-1] are moved up one place.
     * This type of swap is used by insertion sort.
     *
     * @param xs the array of X elements, whose elements 0 thru i-1 MUST be sorted.
     * @param i  the index of the element to be swapped into the ordered array xs[0..i-1].
     */
    default void swapIntoSorted(final X[] xs, final int i) {
        int j = binarySearch(xs, 0, i, xs[i]);
        if (j < 0) j = -j - 1;
        if (j < i) swapInto(xs, j, i);
    }

    /**
     * TODO eliminate this method as it has been superseded by swapConditional. However, maybe the latter is a better name.
     * Method to fix a potentially unstable inversion.
     *
     * @param xs the array of X elements.
     * @param i  the index of the lower of the elements to be swapped.
     * @param j  the index of the higher of the elements to be swapped.
     */
    default void fixInversion(final X[] xs, final int i, final int j) {
        swapConditional(xs, i, j);
    }

    /**
     * TODO eliminate this method as it has been superseded by swapStableConditional. However, maybe the latter is a better name.
     * Method to fix a stable inversion.
     *
     * @param xs the array of X elements.
     * @param i  the index of the higher of the adjacent elements to be swapped.
     */
    default void fixInversion(final X[] xs, final int i) {
        swapStableConditional(xs, i);
    }

    /**
     * If instrumenting, increment the number of fixes by n.
     *
     * @param n the number of copies made.
     */
    default void incrementFixes(final int n) {
        // do nothing.
    }

    default int getCutoff() {
        return 7;
    }

    /**
     * If instrumenting, increment the number of copies by n.
     *
     * @param n the number of copies made.
     */
    default void incrementCopies(final int n) {
        // do nothing.
    }

    /**
     * Method to post-process the array xs after sorting.
     * By default, this method does nothing.
     *
     * @param xs the array to be tested.
     * @return true.
     */
    default boolean postProcess(final X[] xs) {
        return true;
    }

    /**
     * Method to do any required preProcessing.
     *
     * @param xs the array to be sorted.
     * @return the array after any pre-processing.
     */
    default X[] preProcess(final X[] xs) {
        // CONSIDER invoking init from here.
        return xs;
    }

    default void registerDepth(final int depth) {
    }

    default int maxDepth() {
        return 0;
    }

}
