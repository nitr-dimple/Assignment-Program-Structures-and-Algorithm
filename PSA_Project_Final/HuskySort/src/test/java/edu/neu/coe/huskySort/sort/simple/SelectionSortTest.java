/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.huskySort.sort.simple;

import edu.neu.coe.huskySort.sort.ComparableSortHelper;
import edu.neu.coe.huskySort.sort.ComparisonSortHelper;
import edu.neu.coe.huskySort.sort.HelperFactory;
import edu.neu.coe.huskySort.sort.SortWithHelper;
import edu.neu.coe.huskySort.util.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ALL")
public class SelectionSortTest {

    @Test
    public void sort1() throws Exception {
        final List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(2);
        list.add(1);
        Integer[] xs = list.toArray(new Integer[0]);
        ComparableSortHelper<Integer> helper = new ComparableSortHelper<>("SelectionSort", xs.length);
        SelectionSort<Integer> sorter = new SelectionSort<Integer>(helper);
        Integer[] ys = sorter.sort(xs);
        assertTrue(helper.sorted(ys));
    }

    @Test
    public void sort2() throws Exception {
        final Config config = ConfigTest.setupConfig("true", "0", "1", "", "");
        int n = 100;
        ComparisonSortHelper<Integer> helper = HelperFactory.create("SelectionSort", n, config);
        helper.init(n);
        Instrumenter instrumenter = helper.getInstrumenter();
        final PrivateMethodInvoker privateMethodInvoker = new PrivateMethodInvoker(instrumenter);
        final StatPack statPack = (StatPack) privateMethodInvoker.invokePrivate("getStatPack");
        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000));
        SortWithHelper<Integer> sorter = new SelectionSort<Integer>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        assertTrue(helper.sorted(ys));
        final int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
        assertEquals(n * (n - 1) / 2, compares);
        final int inversions = (int) statPack.getStatistics(Instrumenter.INVERSIONS).mean();
        final int fixes = (int) statPack.getStatistics(Instrumenter.FIXES).mean();
        System.out.println(statPack);
        assertEquals(inversions, fixes);
    }

}