package edu.neu.coe.huskySort.sort.radix;

import edu.neu.coe.huskySort.sort.ComparableSortHelper;
import edu.neu.coe.huskySort.sort.ComparisonSortHelper;
import edu.neu.coe.huskySort.sort.huskySort.HuskySortBenchmark;
import edu.neu.coe.huskySort.sort.huskySort.HuskySortBenchmarkHelper;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MSDStringSortTest {

    //    @Test
    public void sort() {
        final String[] input = "she sells seashells by the seashore the shells she sells are surely seashells".split(" ");
        final String[] expected = "are by seashells seashells seashore sells sells she she shells surely the the".split(" ");

        final MSDStringSort msdStringSort = new MSDStringSort(Alphabet.ASCII);
        msdStringSort.sort(input);
        assertArrayEquals(expected, input);
    }

    //    @Test
    public void sort1() {
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test", 1000, 1L);
        final String[] words = HuskySortBenchmarkHelper.getWords("3000-common-words.txt", HuskySortBenchmark::lineAsList);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(1000, xs.length);
        final MSDStringSort msdStringSort = new MSDStringSort(Alphabet.ASCII);
        msdStringSort.sort(xs);
        assertEquals("African-American", xs[0]);
        assertEquals("Palestinian", xs[16]);
    }

    //    @Test
    public void sort2() {
        final ComparisonSortHelper<String> helper = new ComparableSortHelper<>("test", 1000, 1L);
        final String[] words = HuskySortBenchmarkHelper.getWords("3000-common-words.txt", HuskySortBenchmark::lineAsList);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(1000, xs.length);
        final MSDStringSort msdStringSort = new MSDStringSort(new Alphabet(Alphabet.RADIX_UNICODE));
        msdStringSort.sort(xs);
        assertEquals("African-American", xs[0]);
        assertEquals("Palestinian", xs[16]);
    }

    //    @Test
    public void sortWithExtendedAscii() {
        final String[] input = ("Le renard brun rapide saute par-dessus le chien paresseux chac?? chacra ch??chara c??ntara cantar ca??a cana canal canap?? ca????n d??a desayuno ").split(" ");
        final MSDStringSort msdStringSort = new MSDStringSort(new Alphabet(Alphabet.RADIX_UNICODE));
        msdStringSort.sort(input);
        final Alphabet alphabet = msdStringSort.getAlphabet();
        assertTrue(new ComparableSortHelper<String>("sortWithUnicode").sorted(input));
    }

    //    @Test
    public void sortWithUnicode() {
        // CONSIDER compiling regex
        final String[] input = "python.txt\t???.txt\t\t???.txt\t\t???.txt\t\t??????.txt\t??????.txt\t??????.txt\t??????.txt\t?????????.txt\n???.txt\t\t???.txt\t\t???.txt\t\t???.txt\t\t??????.txt\t??????.txt\t??????.txt\t??????.txt\n???.txt\t\t???.txt\t\t???.txt\t\t???.txt\t\t??????.txt\t??????.txt\t??????.txt\t??????.txt".split("\\s+");
        System.out.println(Arrays.toString(input));
        MSDStringSort.setCutoff(1);
        final MSDStringSort msdStringSort = new MSDStringSort(new Alphabet(Alphabet.RADIX_UNICODE));
        msdStringSort.sort(input);
        final Alphabet alphabet = msdStringSort.getAlphabet();
        System.out.println(alphabet);
        System.out.println(Arrays.toString(input));
        final boolean sorted = new ComparableSortHelper<String>("sortWithUnicode").sorted(input);
        assertTrue(sorted);
    }
}