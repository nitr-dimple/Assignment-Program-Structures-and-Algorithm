/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.huskySort.sort.simple;

import edu.neu.coe.huskySort.sort.ComparableSortHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("ALL")
public class BucketSortTest {

    @Test
    public void sort4() throws Exception {
        final List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(2);
        list.add(1);
        Integer[] xs = list.toArray(new Integer[0]);
        ComparableSortHelper<Integer> helper = new ComparableSortHelper<>("BucketSort", xs.length);
        BucketSort<Integer> sorter = new BucketSort<>(2, helper);
        Integer[] ys = sorter.sort(xs);
        assertTrue(helper.sorted(ys));
        System.out.println(sorter.toString());
    }

    @Test
    public void sortN() throws Exception {
        int N = 10000;
        Integer[] xs = new Integer[N];
        Random random = new Random();
        for (int i = 0; i < N; i++) xs[i] = random.nextInt(10000);
        ComparableSortHelper<Integer> helper = new ComparableSortHelper<>("BucketSort", xs.length);
        BucketSort<Integer> sorter = new BucketSort<>(100, helper);
        Integer[] ys = sorter.sort(xs);
        assertTrue(helper.sorted(ys));
        System.out.println(sorter.toString());
    }


}