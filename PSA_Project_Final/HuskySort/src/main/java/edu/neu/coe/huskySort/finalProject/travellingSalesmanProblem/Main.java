package edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem;

import edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.geneticGreedyAlgorithm.GeneticGreedySalesman;
import edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.geneticAlgorithm.GeneticSalesman;
import edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.greedyAlgorithm.Greedy;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dimpleben Kanjibhai Patel
 */
public class Main {

    static FileOutputStream fis;

    public static void main(String args[]) {

        try {
            fis = new FileOutputStream("./src/TravellingSalesManBenchmarking.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);

            String content = "Array Size, Greedy Minimum Cost, Genetic Minimum Cost, GeneticGreedy Minimum Cost \n";
            bw.write(content);
            bw.flush();

            processing(bw);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processing(BufferedWriter bw) throws IOException {
        for (int n = 4; n <= 2048; n = n * 2) {
            Random r = new Random();
            int[][] tsp = new int[n][n];
            int initalNode = r.nextInt(tsp.length);

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    tsp[i][j] = r.nextInt(100);
                    tsp[j][i] = tsp[i][j];
                }
                tsp[i][i] = -1;
            }
            System.out.println("Arraysize : " + n);

            // Greedy Algorithm
            List<Integer> visitedRouteList = new ArrayList<>();
            int greedyResult = processGreedy(visitedRouteList, tsp, initalNode);

            // Genetic Algorithm
            int geneticResult = processGenetic(tsp, initalNode, n);

            // GreedyGenetic Algorithm
            int greedyGeneticResult = processGeneticGreedy(tsp, initalNode, n, visitedRouteList);

            //Writing Results in Excel file
            writeInFile(bw, n, greedyResult, geneticResult, greedyGeneticResult);
        }
    }

    //Method to calculate cost using greedy algorithm
    private static int processGeneticGreedy(int[][] tsp, int initalNode, int n, List<Integer> visitedRouteList) {

        System.out.println("Genetic-Greedy start");
        GeneticGreedySalesman geneticGreedyAlgorithm = new GeneticGreedySalesman(n, SelectionType.TOURNAMENT, tsp, initalNode, 0);
        int greedyGeneticResult = geneticGreedyAlgorithm.optimize(visitedRouteList);
        System.out.println("Genetic-Greedy end");

        return greedyGeneticResult;
    }

    //Method to calculate cost using genetic algorithm
    private static int processGenetic(int[][] tsp, int initalNode, int n) {

        System.out.println("Genetic start");
        GeneticSalesman geneticAlgorithm = new GeneticSalesman(n, SelectionType.TOURNAMENT, tsp, initalNode, 0);
        int geneticResult = geneticAlgorithm.optimize();
        System.out.println("Genetic end");

        return geneticResult;
    }

    //Method to calculate cost using Genetic-Greedy algorithm
    private static int processGreedy(List<Integer> visitedRouteList, int[][] tsp, int initalNode) {

        System.out.println("Greedy start");
        long startTime = System.nanoTime();

        Greedy greedy = new Greedy();
        int greedyResult = greedy.findMinRoute(tsp, initalNode, visitedRouteList);

        long endTime = System.nanoTime();
        double time = endTime - startTime;
        time = (double) (endTime - startTime) / 1000000000;
        System.out.println("Greedy end");

        return greedyResult;
    }

    //Method to write results in excel file
    private static void writeInFile(BufferedWriter bw, int n, int greedyResult, int geneticResult, int greedyGeneticResult) throws IOException {

        String content = n + "," + greedyResult + "," + geneticResult + "," + greedyGeneticResult + "\n";
        bw.write(content);
        bw.flush();
    }
}
