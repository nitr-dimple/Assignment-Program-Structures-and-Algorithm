package edu.neu.coe.huskySort.finalProject.quickSort;

import edu.neu.coe.huskySort.sort.ComparisonSortHelper;
import edu.neu.coe.huskySort.sort.HelperFactory;
import edu.neu.coe.huskySort.sort.Sort;
import edu.neu.coe.huskySort.util.Config;
import edu.neu.coe.huskySort.util.Instrumenter;
import edu.neu.coe.huskySort.util.PrivateMethodInvoker;
import edu.neu.coe.huskySort.util.StatPack;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static edu.neu.coe.huskySort.util.Utilities.round;

public class Main {

    public static void main(String args[]) throws IOException {

        int k = 2;
        int sizeK = 21;

        double[] meanSwap = new double[sizeK - k];
        double[] meanCompare = new double[sizeK - k];
        double[] meanRatio = new double[sizeK - k];
        int j = 0;

        for (k = 3; k < sizeK; k++) {
            int N = (int) Math.pow(2, k);
            int levels = k - 2;
            final Config config = Config.setupConfig("true", "", "", "", "");
            final ComparisonSortHelper<Integer> helper = (ComparisonSortHelper<Integer>) HelperFactory.create("quick sort basic", N, config);
            System.out.println(helper);
            Sort<Integer> s = new QuickSort_Basic<>(helper);
            s.init(N);

            final int THOUSAND = 1000;
            double swap = 0.0;
            double compare = 0.0;
            double ratio = 0.0;

            for (int i = 0; i < THOUSAND; i++) {

                final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(10000));
                helper.preProcess(xs);
                Integer[] ys = s.sort(xs);
                helper.postProcess(ys);
                final PrivateMethodInvoker privateMethodTester = new PrivateMethodInvoker(helper);
                final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
                final int compares = (int) statPack.getStatistics(Instrumenter.COMPARES).mean();
                final int inversions = (int) statPack.getStatistics(Instrumenter.INVERSIONS).mean();
                final int fixes = (int) statPack.getStatistics(Instrumenter.FIXES).mean();
                final int swaps = (int) statPack.getStatistics(Instrumenter.SWAPS).mean();
                final int copies = (int) statPack.getStatistics(Instrumenter.COPIES).mean();
                final int worstCompares = round(2.0 * N * Math.log(N));
                final int bestCompares = round(N * k);
                swap = swaps;
                compare = compares;
            }
            meanSwap[j] = swap;
            meanCompare[j] = compare;
            meanRatio[j] = compare * 1.0 / swap;
            System.out.println("Mean Swap for k(" + k + ")=" + meanSwap[j]);
            System.out.println("Mean compare for k(" + k + ")=" + meanCompare[j]);
            System.out.println("Mean ratio for k(" + k + ")=" + meanRatio[j]);
            j++;
        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/QuickSortResult.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            String content = "Array Size , No of Swaps , No of Comparisons, Compares/Swaps \n";
            bw.write(content);
            bw.flush();
            for (int i = meanSwap.length - 1; i >= 0; i--) {
                content = (int) Math.pow(2, --k) + "," + meanSwap[i] + "," + meanCompare[i] + "," + meanRatio[i] + "\n";
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
