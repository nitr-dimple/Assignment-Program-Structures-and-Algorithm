package edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.geneticAlgorithm;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Dimpleben Kanjibhai Patel
 */

public class GeneticSalesmanGenome implements Comparable {
    List<Integer> genome;
    int[][] travelPrices;
    int startingCity;
    int numberOfCities = 0;
    int fitness;

    public GeneticSalesmanGenome(int numberOfCities, int[][] travelPrices, int startingCity) {
        this.travelPrices = travelPrices;
        this.startingCity = startingCity;
        this.numberOfCities = numberOfCities;
        genome = randomSalesman();
        fitness = this.calculateFitness();
    }

    public GeneticSalesmanGenome(int numberOfCities, List<Integer> genome, int[][] travelPrices, int startingCity) {
        this.travelPrices = travelPrices;
        this.startingCity = startingCity;
        this.numberOfCities = numberOfCities;
        this.genome = genome;
        fitness = this.calculateFitness();
    }

    public GeneticSalesmanGenome(List<Integer> permutationOfCities, int numberOfCities, int[][] travelPrices, int startingCity) {
        genome = permutationOfCities;
        this.travelPrices = travelPrices;
        this.startingCity = startingCity;
        this.numberOfCities = numberOfCities;
        fitness = this.calculateFitness();
    }

    public int calculateFitness() {
        int fitness = 0;
        int currentCity = startingCity;
        for (int gene : genome) {
            fitness += travelPrices[currentCity][gene];
            currentCity = gene;
        }
        fitness += travelPrices[genome.get(numberOfCities - 2)][startingCity];
        return fitness;
    }

    private List<Integer> randomSalesman() {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < numberOfCities; i++) {
            if (i != startingCity)
                result.add(i);
        }
        Collections.shuffle(result);
        return result;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public int getStartingCity() {
        return startingCity;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Path: ");
        sb.append(startingCity);
        for (int gene : genome) {
            sb.append(" ");
            sb.append(gene);
        }
        sb.append(" ");
        sb.append(startingCity);
        sb.append("\nGenetic:Minimum Cost is: ");
        sb.append(this.fitness);
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        GeneticSalesmanGenome genome = (GeneticSalesmanGenome) o;
        if (this.fitness > genome.getFitness())
            return 1;
        else if (this.fitness < genome.getFitness())
            return -1;
        else
            return 0;
    }
}
