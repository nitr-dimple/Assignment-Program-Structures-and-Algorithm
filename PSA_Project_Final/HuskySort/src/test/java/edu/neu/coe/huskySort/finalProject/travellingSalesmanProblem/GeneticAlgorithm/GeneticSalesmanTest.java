package edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.GeneticAlgorithm;

import edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.geneticAlgorithm.GeneticSalesmanGenome;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Dimpleben Kanjibhai Patel
 */
public class GeneticSalesmanTest {

    @Test
    public void testGenetic1() throws Exception {

        int[][] tsp = {{-1, 10, 15, 20},
                {10, -1, 35, 25},
                {15, 35, -1, 30},
                {20, 25, 30, -1}};

        List<GeneticSalesmanGenome> population = new ArrayList<>();
        List<Integer> genome = new ArrayList<Integer>(Arrays.asList(2,3,1 ));
        GeneticSalesmanGenome  geneticSalesmanGenome = new GeneticSalesmanGenome(4,genome, tsp, 0);
        assertEquals(80,geneticSalesmanGenome.getFitness());
        population.add(geneticSalesmanGenome);

        genome = new ArrayList<Integer>(Arrays.asList(2,1,3));
        geneticSalesmanGenome =  new GeneticSalesmanGenome(4,genome, tsp, 0);
        assertEquals(95,geneticSalesmanGenome.getFitness());
        population.add(geneticSalesmanGenome);

        genome = new ArrayList<Integer>(Arrays.asList(1,2,3 ));
        geneticSalesmanGenome =  new GeneticSalesmanGenome(4,genome, tsp, 0);
        assertEquals(95,geneticSalesmanGenome.getFitness());
        population.add(geneticSalesmanGenome);

        assertEquals(80, Collections.min(population).getFitness());
    }

    @Test
    public void testGenetic2() throws Exception {

        int[][] tsp = {{-1, 10, 15, 20},
                {10, -1, 35, 25},
                {15, 35, -1, 30},
                {20, 25, 30, -1}};

        List<Integer> genome = new ArrayList<Integer>(Arrays.asList(0,3,1 ));
        GeneticSalesmanGenome  geneticSalesmanGenome = new GeneticSalesmanGenome(4,genome, tsp, 2);
        assertEquals(95,geneticSalesmanGenome.getFitness());

        genome = new ArrayList<Integer>(Arrays.asList(3,0,1));
        geneticSalesmanGenome =  new GeneticSalesmanGenome(4,genome, tsp, 2);
        assertEquals(95,geneticSalesmanGenome.getFitness());

        genome = new ArrayList<Integer>(Arrays.asList(1,3,0));
        geneticSalesmanGenome =  new GeneticSalesmanGenome(4,genome, tsp, 2);
        assertEquals(95,geneticSalesmanGenome.getFitness());

        genome = new ArrayList<Integer>(Arrays.asList(3,1,0));
        geneticSalesmanGenome =  new GeneticSalesmanGenome(4,genome, tsp, 2);
        assertEquals(80,geneticSalesmanGenome.getFitness());
    }

    @Test
    public void testGenetic3() throws Exception {

        int[][] tsp = {{-1, 10, 5, 2, 6, 8},
                        {10, -1, 3,7, 1, 6},
                        {5, 3, -1, 4,9,10},
                        {2, 7, 4, -1, 5, 9},
                        {6, 1, 9, 5 ,-1, 2},
                        {8, 6, 10, 9,2 ,-1}};

        List<Integer> genome = new ArrayList<Integer>(Arrays.asList(2,3,5,4,1));
        GeneticSalesmanGenome  geneticSalesmanGenome = new GeneticSalesmanGenome(4,genome, tsp, 0);
        assertEquals(29,geneticSalesmanGenome.getFitness());

        genome = new ArrayList<Integer>(Arrays.asList(2,5,1,4,3));
        geneticSalesmanGenome =  new GeneticSalesmanGenome(4,genome, tsp, 0);
        assertEquals(37,geneticSalesmanGenome.getFitness());

        genome = new ArrayList<Integer>(Arrays.asList(5,1,2, 4,3 ));
        geneticSalesmanGenome =  new GeneticSalesmanGenome(4,genome, tsp, 0);
        assertEquals(36,geneticSalesmanGenome.getFitness());

    }

    @Test
    public void testGenetic4() throws Exception {

        int[][] tsp = {{-1, 10, 5, 2, 6, 8},
                {10, -1, 3,7, 1, 6},
                {5, 3, -1, 4,9,10},
                {2, 7, 4, -1, 5, 9},
                {6, 1, 9, 5 ,-1, 2},
                {8, 6, 10, 9,2 ,-1}};

        List<Integer> genome = new ArrayList<Integer>(Arrays.asList(2,3,5,4,1));
        GeneticSalesmanGenome  geneticSalesmanGenome = new GeneticSalesmanGenome(4,genome, tsp, 1);
        assertEquals(25,geneticSalesmanGenome.getFitness());

        genome = new ArrayList<Integer>(Arrays.asList(2,5,1,4,3));
        geneticSalesmanGenome =  new GeneticSalesmanGenome(4,genome, tsp, 5);
        assertEquals(38,geneticSalesmanGenome.getFitness());

        genome = new ArrayList<Integer>(Arrays.asList(5,1,2, 4,3 ));
        geneticSalesmanGenome =  new GeneticSalesmanGenome(4,genome, tsp, 4);
        assertEquals(34,geneticSalesmanGenome.getFitness());

    }
}
