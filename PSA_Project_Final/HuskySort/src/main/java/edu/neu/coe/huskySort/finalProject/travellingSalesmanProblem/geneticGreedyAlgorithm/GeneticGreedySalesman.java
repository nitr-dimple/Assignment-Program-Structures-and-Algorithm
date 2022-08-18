package edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.geneticGreedyAlgorithm;

import edu.neu.coe.huskySort.finalProject.travellingSalesmanProblem.SelectionType;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class GeneticGreedySalesman {
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

    public GeneticGreedySalesman(int numberOfCities, SelectionType selectionType, int[][] travelPrices, int startingCity, int targetFitness) {
        this.numberOfCities = numberOfCities;
        this.genomeSize = numberOfCities - 1;
        this.selectionType = selectionType;
        this.travelPrices = travelPrices;
        this.startingCity = startingCity;
        this.targetFitness = targetFitness;

        generationSize = 5000;
        reproductionSize = 200;
        maxIterations = 3000;
        mutationRate = 0.1f;
        tournamentSize = 40;
    }

    public List<GeneticGreedySalesmanGenome> initialPopulation() {
        List<GeneticGreedySalesmanGenome> population = new ArrayList<>();
        for (int i = 1; i < generationSize; i++) {
            population.add(new GeneticGreedySalesmanGenome(numberOfCities, travelPrices, startingCity));
        }
        return population;
    }

    public List<GeneticGreedySalesmanGenome> selection(List<GeneticGreedySalesmanGenome> population) {
        List<GeneticGreedySalesmanGenome> selected = new ArrayList<>();
        GeneticGreedySalesmanGenome winner;
        for (int i = 0; i < reproductionSize; i++) {
            if (selectionType == SelectionType.ROULETTE) {
                selected.add(rouletteSelection(population));
            } else if (selectionType == SelectionType.TOURNAMENT) {
                selected.add(tournamentSelection(population));
            }
        }

        return selected;
    }

    public GeneticGreedySalesmanGenome rouletteSelection(List<GeneticGreedySalesmanGenome> population) {
        int totalFitness = population.stream().map(GeneticGreedySalesmanGenome::getFitness).mapToInt(Integer::intValue).sum();
        Random random = new Random();
        int selectedValue = random.nextInt(totalFitness);
        float recValue = (float) 1 / selectedValue;
        float currentSum = 0;
        for (GeneticGreedySalesmanGenome genome : population) {
            currentSum += (float) 1 / genome.getFitness();
            if (currentSum >= recValue) {
                return genome;
            }
        }
        int selectRandom = random.nextInt(generationSize);
        return population.get(selectRandom);
    }

    public static <E> List<E> pickNRandomElements(List<GeneticGreedySalesmanGenome> list, int n) {
        Random r = new Random();
        int length = list.size();
        if (length < n) return null;
        for (int i = length - 1; i >= length - n; --i) {
            Collections.swap(list, i, r.nextInt(i + 1));
        }
        List<GeneticGreedySalesmanGenome> selected = new ArrayList<GeneticGreedySalesmanGenome>();
        Collections.min(list);
        selected.add(Collections.min(list));
        selected.addAll(list.subList(length - n, length));
        return (List<E>) selected;
    }

    public GeneticGreedySalesmanGenome tournamentSelection(List<GeneticGreedySalesmanGenome> population) {
        List<GeneticGreedySalesmanGenome> selected = pickNRandomElements(population, tournamentSize);
        return Collections.min(selected);
    }

    public GeneticGreedySalesmanGenome mutate(GeneticGreedySalesmanGenome salesman) {
        Random random = new Random();
        float mutate = random.nextFloat();
        if (mutate < mutationRate) {
            List<Integer> genome = salesman.getGenome();
            Collections.swap(genome, random.nextInt(genomeSize), random.nextInt(genomeSize));
            return new GeneticGreedySalesmanGenome(genome, numberOfCities, travelPrices, startingCity);
        }
        return salesman;
    }

    public List<GeneticGreedySalesmanGenome> createGeneration(List<GeneticGreedySalesmanGenome> population) {
        List<GeneticGreedySalesmanGenome> generation = new ArrayList<>();
        int currentGenerationSize = 0;
        while (currentGenerationSize < generationSize) {
            List<GeneticGreedySalesmanGenome> parents = pickNRandomElements(population, 2);
            List<GeneticGreedySalesmanGenome> children = crossover(parents);
            children.set(0, mutate(children.get(0)));
            children.set(1, mutate(children.get(1)));
            generation.addAll(children);
            currentGenerationSize += 2;
        }
        return generation;
    }

    public List<GeneticGreedySalesmanGenome> crossover(List<GeneticGreedySalesmanGenome> parents) {
        Random random = new Random();
        int breakpoint = random.nextInt(genomeSize);
        List<GeneticGreedySalesmanGenome> children = new ArrayList<>();

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
        children.add(new GeneticGreedySalesmanGenome(parent1Genome, numberOfCities, travelPrices, startingCity));
        parent1Genome = parents.get(0).getGenome(); // reseting the edited parent

        // creating child 2
        for (int i = breakpoint; i < genomeSize; i++) {
            int newVal = parent1Genome.get(i);
            Collections.swap(parent2Genome, parent2Genome.indexOf(newVal), i);
        }
        children.add(new GeneticGreedySalesmanGenome(parent2Genome, numberOfCities, travelPrices, startingCity));

        return children;
    }

    public int optimize(List<Integer> greedyResult) {
        List<GeneticGreedySalesmanGenome> population = initialPopulation();
        greedyResult.remove(0);
        population.add(new GeneticGreedySalesmanGenome(numberOfCities, greedyResult, travelPrices, startingCity));

        GeneticGreedySalesmanGenome globalBestGenome = population.get(0);
        List<Integer> fitness = new ArrayList<>();
        for (int i = 0; i < maxIterations; i++) {
            long startTime = System.nanoTime();
            List<GeneticGreedySalesmanGenome> selected = selection(population);
            population = createGeneration(selected);
            globalBestGenome = Collections.min(population);
            if (globalBestGenome.getFitness() < targetFitness)
                break;
            fitness.add(globalBestGenome.fitness);
//            System.out.print(i + ": " + globalBestGenome+", ");

            long endTime = System.nanoTime();
            double time = (double) (endTime - startTime) / 1000000000;
//            System.out.println("Time taken = "+time);
        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/GeneticGreedyResult_" + numberOfCities + ".csv");
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

    public void printGeneration(List<GeneticGreedySalesmanGenome> generation) {
        for (GeneticGreedySalesmanGenome genome : generation) {
            System.out.println(genome);
        }
    }
}

