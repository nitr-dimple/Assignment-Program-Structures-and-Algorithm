package edu.neu.coe.huskySort.util;

import java.util.Random;
import java.util.function.Function;

/**
 * This defines the behavior of a basic Helper for processing elements of type X.
 * NOTE: that X is not required to be Comparable.
 *
 * @param <X> the underlying type.
 */
public interface Helper<X> {
    /**
     * @return true if this is an instrumented ComparisonSortHelper.
     */
    default boolean instrumented() {
        return getInstrumenter() != null;
    }

    /**
     * Copy the element at source[j] into target[i]
     *
     * @param source the source array.
     * @param i      the target index.
     * @param target the target array.
     * @param j      the source index.
     */
    void copy(X[] source, int i, X[] target, int j);

    /**
     * Return true if xs is sorted, i.e. has no inversions.
     *
     * @param xs an array of Xs.
     * @return true if there are no inversions, else false.
     */
    boolean sorted(X[] xs);

    /**
     * Method to determine if x1 and x2 are inverted.
     * <p>
     * NOTE: This MUST be a non-instrumenting comparison.
     * <p>
     * CONSIDER defining this in terms of a non-overridable compare function.
     *
     * @param x1 the first (left) value of X.
     * @param x2 the second (right) value of X.
     * @return x1 > x2.
     */
    boolean invertedPure(X x1, X x2);

    /**
     * Count the number of inversions of this array.
     *
     * @param xs an array of Xs.
     * @return the number of inversions.
     */
    int inversions(X[] xs);

    /**
     * Method to post-process the array xs after sorting.
     *
     * @param xs the array that has been sorted.
     * @return whether the postProcessing was successful.
     */
    boolean postProcess(X[] xs);

    /**
     * Method to generate an array of randomly chosen X elements.
     *
     * @param clazz the class of X.
     * @param f     a function which takes a Random and generates a random value of X.
     * @return an array of X of length determined by the current value according to setN.
     */
    X[] random(Class<X> clazz, Function<Random, X> f);

    /**
     * @return the description of this ComparisonSortHelper.
     */
    String getDescription();

    /**
     * @return the cutoff value.
     */
    int getCutoff();

    /**
     * Initialize this ComparisonSortHelper with the size of the array to be managed.
     *
     * @param n the size to be managed.
     */
    void init(int n);

    /**
     * Get the current value of N.
     *
     * @return the value of N.
     */
    int getN();

    /**
     * Close this ComparisonSortHelper, freeing up any resources used.
     */
    void close();

    void incrementCopies(int n);

    X[] preProcess(X[] xs);

    void registerDepth(int depth);

    int maxDepth();

    Instrumenter getInstrumenter();

    void setCheckSorted(boolean checkSorted);

    void swap(X[] xs, int i, int j);
}
