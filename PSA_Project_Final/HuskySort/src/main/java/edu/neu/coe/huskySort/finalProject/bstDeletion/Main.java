package edu.neu.coe.huskySort.finalProject.bstDeletion;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Code by Shubham Rajan Patil
 *
 * @param <Key>
 * @param <Value>
 */
public class Main {

    static FileOutputStream fis;

    public static void main(String[] args) {

        try {
            fis = new FileOutputStream("./src/BSTDeletion.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);

            String content = "No. of Nodes before deletion(N), No. of Nodes after deletion(Z), " +
                    "Simple Deletion Depth(P), Random Deletion Depth(Q), Optimized Deletion Depth(R), " +
                    "Theoretical Depth(H), P/H*100, Q/H*100, R/H*100, log N(L)\n";
            bw.write(content);
            bw.flush();
            processing(bw);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void processing(BufferedWriter bw) throws IOException {

        int size = 0;

        for (int n = 8; n <= 2048 * 1024; n *= 2) {

            double simpleAvg = 0.0;
            double randomAvg = 0.0;
            double optimizedAvg = 0.0;

            System.out.println();
            System.out.println("---------------------------------------N=" + n + "---------------------------------------");

            for (int iteration = 0; iteration < 1000; iteration++) {

                BSTSimple<Integer, Integer> bstSimple = new BSTSimple<Integer, Integer>();
                BSTRandomDeletion<Integer, Integer> bstRandom = new BSTRandomDeletion<Integer, Integer>();
                BSTOptimisedDeletion<Integer, Integer> bstOptimisedDeletion = new BSTOptimisedDeletion<Integer, Integer>();
                Random r = new Random();
                List<Integer> list = new ArrayList<>();
                int t = 0;

                put(t, bstSimple, bstRandom, bstOptimisedDeletion, list, n, r);
                delete(t, bstSimple, bstRandom, bstOptimisedDeletion, list, n, r);

                size = bstSimple.size();

                simpleAvg += bstSimple.depth();
                randomAvg += bstRandom.depth();
                optimizedAvg += bstOptimisedDeletion.depth();

            }

            simpleAvg /= 1000;
            randomAvg /= 1000;
            optimizedAvg /= 1000;

            writeInFile(bw, n, size, simpleAvg, randomAvg, optimizedAvg);

            System.out.println("Size = " + size);
            System.out.println("Theoretical Height=" + Math.sqrt(size));
            System.out.println("Simple Height = " + simpleAvg);
            System.out.println("Random Height = " + randomAvg);
            System.out.println("Optimized Height = " + optimizedAvg);
        }

    }

    // Printing Results in Excel
    private static void writeInFile(BufferedWriter bw, int n, int size, double simpleAvg, double randomAvg, double optimizedAvg) throws IOException {

        double sqrtOfSize = Math.sqrt(size);
        String content = n + "," + size + "," + simpleAvg + "," + randomAvg + "," + optimizedAvg + "," + sqrtOfSize + "," +
                simpleAvg / sqrtOfSize * 100 + "," + randomAvg / sqrtOfSize * 100 + "," + optimizedAvg / sqrtOfSize * 100 + "," +
                (Math.log(size) / Math.log(2)) + "\n";
        bw.write(content);
        bw.flush();
    }

    //Method to delete nodes from Binary Search Tree
    private static void delete(int t, BSTSimple<Integer, Integer> bstSimple, BSTRandomDeletion<Integer, Integer> bstRandom,
                               BSTOptimisedDeletion<Integer, Integer> bstOptimisedDeletion, List<Integer> list, int n, Random r) {

        for (int i = 0; i < n / 2; i++) {
            int index = r.nextInt(list.size());
            bstSimple.delete(list.get(index));
            bstRandom.delete(list.get(index));
            bstOptimisedDeletion.delete(list.get(index));
            list.remove(index);
        }
    }

    //Method to insert nodes from Binary Search Tree
    private static void put(int t, BSTSimple<Integer, Integer> bstSimple, BSTRandomDeletion<Integer, Integer> bstRandom,
                            BSTOptimisedDeletion<Integer, Integer> bstOptimisedDeletion, List<Integer> list, int n, Random r) {

        for (int i = 0; i < n; i++) {
            t = r.nextInt(1000000000);
            bstSimple.put(t, t);
            bstRandom.put(t, t);
            bstOptimisedDeletion.put(t, t);
            list.add(t);
        }
    }

}
