package edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.geneticAlgorithm;

import edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.SelectionType;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class GeneticSalesman {
    private int generationSize;
    private int genomeSize;
    private int numberOfCities;
    private int reproductionSize;
    private int maxIterations;
    private float mutationRate;
    private int tournamentSize;
    private SelectionType selectionType;
    private int[][] travelPrices;
    private int startingCity;
    private int targetFitness;

    public GeneticSalesman(int numberOfCities, SelectionType selectionType, int[][] travelPrices, int startingCity, int targetFitness) {
        this.numberOfCities = numberOfCities;
        this.genomeSize = numberOfCities - 1;
        this.selectionType = selectionType;
        this.travelPrices = travelPrices;
        this.startingCity = startingCity;
        this.targetFitness = targetFitness;

        generationSize = 5000;
        reproductionSize = 200;
        maxIterations = 5000;
        mutationRate = 0.1f;
        tournamentSize = 40;
    }

    public List<GeneticSalesmanGenome> initialPopulation() {
        List<GeneticSalesmanGenome> population = new ArrayList<>();
        for (int i = 0; i < generationSize; i++) {
            population.add(new GeneticSalesmanGenome(numberOfCities, travelPrices, startingCity));
        }
        return population;
    }

    public List<GeneticSalesmanGenome> selection(List<GeneticSalesmanGenome> population) {
        List<GeneticSalesmanGenome> selected = new ArrayList<>();
        GeneticSalesmanGenome winner;
        for (int i = 0; i < reproductionSize; i++) {
            if (selectionType == SelectionType.ROULETTE) {
                selected.add(rouletteSelection(population));
            } else if (selectionType == SelectionType.TOURNAMENT) {
                selected.add(tournamentSelection(population));
            }
        }

        return selected;
    }

    public GeneticSalesmanGenome rouletteSelection(List<GeneticSalesmanGenome> population) {
        int totalFitness = population.stream().map(GeneticSalesmanGenome::getFitness).mapToInt(Integer::intValue).sum();
        Random random = new Random();
        int selectedValue = random.nextInt(totalFitness);
        float recValue = (float) 1 / selectedValue;
        float currentSum = 0;
        for (GeneticSalesmanGenome genome : population) {
            currentSum += (float) 1 / genome.getFitness();
            if (currentSum >= recValue) {
                return genome;
            }
        }
        int selectRandom = random.nextInt(generationSize);
        return population.get(selectRandom);
    }

    public static <E> List<E> pickNRandomElements(List<E> list, int n) {
        Random r = new Random();
        int length = list.size();
        if (length < n) return null;
        for (int i = length - 1; i >= length - n; --i) {
            Collections.swap(list, i, r.nextInt(i + 1));
        }
        return list.subList(length - n, length);
    }

    public GeneticSalesmanGenome tournamentSelection(List<GeneticSalesmanGenome> population) {
        List<GeneticSalesmanGenome> selected = pickNRandomElements(population, tournamentSize);
        return Collections.min(selected);
    }

    public GeneticSalesmanGenome mutate(GeneticSalesmanGenome salesman) {
        Random random = new Random();
        float mutate = random.nextFloat();
        if (mutate < mutationRate) {
            List<Integer> genome = salesman.getGenome();
            Collections.swap(genome, random.nextInt(genomeSize), random.nextInt(genomeSize));
            return new GeneticSalesmanGenome(genome, numberOfCities, travelPrices, startingCity);
        }
        return salesman;
    }

    public List<GeneticSalesmanGenome> createGeneration(List<GeneticSalesmanGenome> population) {
        List<GeneticSalesmanGenome> generation = new ArrayList<>();
        int currentGenerationSize = 0;
        while (currentGenerationSize < generationSize) {
            List<GeneticSalesmanGenome> parents = pickNRandomElements(population, 2);
            List<GeneticSalesmanGenome> children = crossover(parents);
            children.set(0, mutate(children.get(0)));
            children.set(1, mutate(children.get(1)));
            generation.addAll(children);
            currentGenerationSize += 2;
        }
        return generation;
    }

    public List<GeneticSalesmanGenome> crossover(List<GeneticSalesmanGenome> parents) {
        Random random = new Random();
        int breakpoint = random.nextInt(genomeSize);
        List<GeneticSalesmanGenome> children = new ArrayList<>();

        // copy parental genomes - we copy so we wouldn't modify in case they were
        // chosen to participate in crossover multiple times
        List<Integer> parent1Genome = new ArrayList<>(parents.get(0).getGenome());
        List<Integer> parent2Genome = new ArrayList<>(parents.get(1).getGenome());

        // creating child 1
        for (int i = 0; i < breakpoint; i++) {
            int newVal;
            newVal = parent2Genome.get(i);
            Collections.swap(parent1Genome, parent1Genome.indexOf(newVal), i);
        }
        children.add(new GeneticSalesmanGenome(parent1Genome, numberOfCities, travelPrices, startingCity));
        parent1Genome = parents.get(0).getGenome(); // reseting the edited parent

        // creating child 2
        for (int i = breakpoint; i < genomeSize; i++) {
            int newVal = parent1Genome.get(i);
            Collections.swap(parent2Genome, parent2Genome.indexOf(newVal), i);
        }
        children.add(new GeneticSalesmanGenome(parent2Genome, numberOfCities, travelPrices, startingCity));

        return children;
    }

    public int optimize() {
        List<GeneticSalesmanGenome> population = initialPopulation();
        GeneticSalesmanGenome globalBestGenome = population.get(0);
        List<Integer> fitness = new ArrayList<>();
        for (int i = 0; i < maxIterations; i++) {
            List<GeneticSalesmanGenome> selected = selection(population);
            population = createGeneration(selected);
            globalBestGenome = Collections.min(population);
            if (globalBestGenome.getFitness() < targetFitness)
                break;
            fitness.add(globalBestGenome.fitness);
        }

        try {
            FileOutputStream fis = new FileOutputStream("./src/GeneticResult_" + numberOfCities + ".csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = 1;
            for (int cost : fitness) {
                String content = j + "," + cost + "\n";
                j++;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return globalBestGenome.fitness;
    }

    public void printGeneration(List<GeneticSalesmanGenome> generation) {
        for (GeneticSalesmanGenome genome : generation) {
            System.out.println(genome);
        }
    }
}

