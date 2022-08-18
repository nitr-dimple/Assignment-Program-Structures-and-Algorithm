package edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.GreedyAlgorithm;

import edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.greedyAlgorithm.Greedy;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 * @author Dimpleben Kanjibhai Patel
 */
public class GreedyTest {

    @Test
    public void testGreedy1() throws Exception {

        int[][] tsp = {{-1, 10, 15, 20},
                {10, -1, 35, 25},
                {15, 35, -1, 30},
                {20, 25, 30, -1}};

        Greedy greedy = new Greedy();
        List<Integer> visitedRouteList = new ArrayList<>();
        int greedyResult =  greedy.findMinRoute(tsp, 0,  visitedRouteList);

        assertEquals(80, greedyResult);
    }

    @Test
    public void testGreedy2() throws Exception {

        int[][] tsp = {{-1, 10, 5, 2, 6, 8},
                {10, -1, 3,7, 1, 6},
                {5, 3, -1, 4,9,10},
                {2, 7, 4, -1, 5, 9},
                {6, 1, 9, 5 ,-1, 2},
                {8, 6, 10, 9,2 ,-1}};

        Greedy greedy = new Greedy();
        List<Integer> visitedRouteList = new ArrayList<>();
        int greedyResult =  greedy.findMinRoute(tsp, 0,  visitedRouteList);

        assertEquals(20, greedyResult);
    }

    @Test
    public void testGreedy3() throws Exception {

        int[][] tsp = {{-1, 10, 15, 20},
                {10, -1, 35, 25},
                {15, 35, -1, 30},
                {20, 25, 30, -1}};

        Greedy greedy = new Greedy();
        List<Integer> visitedRouteList = new ArrayList<>();
        int greedyResult =  greedy.findMinRoute(tsp, 3,  visitedRouteList);

        assertEquals(95, greedyResult);
    }

    @Test
    public void testGreedy4() throws Exception {

        int[][] tsp = {{-1, 10, 5, 2, 6, 8},
                {10, -1, 3,7, 1, 6},
                {5, 3, -1, 4,9,10},
                {2, 7, 4, -1, 5, 9},
                {6, 1, 9, 5 ,-1, 2},
                {8, 6, 10, 9,2 ,-1}};

        Greedy greedy = new Greedy();
        List<Integer> visitedRouteList = new ArrayList<>();
        int greedyResult =  greedy.findMinRoute(tsp, 3,  visitedRouteList);

        assertEquals(22, greedyResult);
    }
}
