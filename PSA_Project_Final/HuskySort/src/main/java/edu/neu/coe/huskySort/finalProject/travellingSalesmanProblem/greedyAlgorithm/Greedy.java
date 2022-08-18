package edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.greedyAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dimpleben Kanjibhai Patel
 */
public class Greedy {
    // Function to find the minimum
    // cost path for all the paths
    public int findMinRoute(int[][] tsp, int initialNode, List<Integer> visitedRouteList)
    {
        int sum = 0;
        int counter = 0;
        int j = 0;
        int min = Integer.MAX_VALUE;

        // Starting from the random indexed
        // city i.e., the first city
        Random r = new Random();

        int i = initialNode;
        visitedRouteList.add(initialNode);
        int[] route = new int[tsp.length];

        // Traverse the adjacency
        // matrix tsp[][]
        int count = 0;
        while (i < tsp.length
                && j < tsp[i].length) {

            // Corner of the Matrix

            if (counter >= tsp[i].length - 1) {
                break;
            }

            // If this path is unvisited then
            // and if the cost is less then
            // update the cost
            if (j != i
                    && !(visitedRouteList.contains(j))) {
                if (tsp[i][j] < min) {
                    min = tsp[i][j];
                    route[counter] = j + 1;
                }
            }
            j++;

            // Check all paths from the
            // ith indexed city
            if (j == tsp[i].length) {
                sum += min;
                min = Integer.MAX_VALUE;
                visitedRouteList.add(route[counter] - 1);
                j = 0;
                i = route[counter] - 1;
                counter++;
            }
        }


        // Update the ending city in array
        // from city which was last visited
        i = route[counter - 1] - 1;
        sum += tsp[i][initialNode];

        // Started from the node where
        // we finished as well.
//        System.out.println("Greedy Result: ");
//        System.out.print("Path: ");
//        for(int x: visitedRouteList)
//            System.out.print(x + " ");
//        System.out.print(visitedRouteList.get(0));
//        System.out.println();
//        System.out.print("Minimum Cost : ");
//        System.out.println(sum);
        return sum;
    }
}
