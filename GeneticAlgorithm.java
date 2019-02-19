
package hello;

import java.util.*;
import java.lang.*;
import java.io.*;

public class GeneticAlgorithm {
    
    private static final int POP_SIZE = 50;
    private static final int LAST_GENERATION = 50;
    private static final int TARGET_FITNESS_PERCENTAGE = 0;
    private static final int NUMBER_OF_SURVIVORS = (int) Math.ceil(POP_SIZE / 10);
    private Population population = new Population();
    
    private List<Integer> integerSet = new LinkedList();
    public static void main(String[] args){
        try
        {
            for(int k = 0; k < 1; k++){
                GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
                geneticAlgorithm.run(args);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        System.exit(0);
    }
    public void run (String[] args) throws Exception
    {
        //read in integerSet
        String fileName = "GeneticAlgorithm\\src\\main\\java\\hello\\integerSet200From1To1000.txt";
        getIntegerSet(fileName);
        population.setIntegerSet(integerSet);
        int integerSetSum = 0;
        integerSetSum = getIntegerSetSum();
        
        generateCandidates();
        
        Population children = new Population(integerSet);
        List<Integer> bestFitnessFromEachGeneration = new LinkedList<Integer>();
        int currentGeneration = 0;
        double bestFitnessPercentage = 100;
        int bestFitness = 9999;
        while(bestFitnessPercentage > TARGET_FITNESS_PERCENTAGE && currentGeneration
            != LAST_GENERATION){
            for(int i = 0; i < NUMBER_OF_SURVIVORS; i++){
                children.addCandidate(population.getCandidate(i));
            }
            while(children.getPopSize() < POP_SIZE){
                children.addCandidate(generateChild());
            }
            population = children;
            children = new Population(integerSet);
            bestFitness = population.getCandidate(0).getFitness();
            bestFitnessFromEachGeneration.add(currentGeneration,bestFitness);
            bestFitnessPercentage = ((double)bestFitness/integerSetSum)*100;
            currentGeneration++;
        }
        System.out.println("best fitness: " + bestFitness);
        System.out.println("best fitness percentage: " + bestFitnessPercentage);
        System.out.println("Current Generation: " + currentGeneration);
        System.out.println("best fitness from each generation: " + bestFitnessFromEachGeneration);
        Candidate bestCandidate = population.getCandidate(0);
        Iterator bestBinaryIterator = bestCandidate.getBinaryIndicator();
        
        // List<Integer> list1 = new ArrayList<Integer>();
        // List<Integer> list2 = new ArrayList<Integer>();
        
        // for(int index = 0; bestBinaryIterator.hasNext(); index++){
        //     int nextBit = (int) bestBinaryIterator.next();
        //     if(nextBit == 0){
        //         list1.add(integerSet.get(index));
        //     }
        //     else{
        //         list2.add(integerSet.get(index));
        //     }
        // }
        //System.out.println("list 1:" + list1 + "\nlist 2: " + list2)
    }
    
    private void generateCandidates(){
        
        while(population.getPopSize() < POP_SIZE){
            List binaryIndicator = new LinkedList();
            for(int i = 0; i < integerSet.size(); i++){
                double random = Math.random();
                int nextBit = (int) Math.round(random);
                binaryIndicator.add(nextBit);
            }
            Candidate candidate = new Candidate(binaryIndicator, integerSet);
            population.addCandidate(candidate);
        }
    }
    
    private Candidate generateChild(){
        Candidate child = new Candidate(integerSet);
        //random select 2 parents from best half of population
        Random rand1 = new Random();
        int parent1Index = rand1.nextInt(population.getPopSize()/2);
        Random rand2 = new Random();
        int parent2Index = rand2.nextInt(population.getPopSize()/2);
        //random select a crossover point, bits from parent 1 are propogated to 
        //child from 0 to crossover point, parent 2 from xover to end
        Random randCrossover = new Random();
        int crossoverIndex = randCrossover.nextInt(integerSet.size());
        Candidate parent1 = population.getCandidate(parent1Index);
        Candidate parent2 = population.getCandidate(parent2Index);
        Iterator parent1BinaryIterator = parent1.getBinaryIndicator();
        Iterator parent2BinaryIterator = parent2.getBinaryIndicator();
        int index = 0;
        while(index < crossoverIndex && parent1BinaryIterator.hasNext()){
            //get values from parent1 binary indicator list and copy to child
            child.modifyBinaryIndicator(index, (int) parent1BinaryIterator.next());
            index++;
        }
        while(index < integerSet.size() && parent2BinaryIterator.hasNext()){
            //get values from parent2 binary indicator list and copy to child
            child.modifyBinaryIndicator(index, (int) parent2BinaryIterator.next());
            index++;            
        }
        mutate(child);
        return child;
    }
    
    private void mutate(Candidate child){
        //the percentage off the solution is from perfect is the 
        //percent of total bits to be flipped
        int fitness = child.getFitness();
        int sum = getIntegerSetSum();
        int setSize = integerSet.size();
        int numberOfMutations = (int) Math.ceil((fitness/sum)
                                           *(setSize));

        double random = Math.random();
        //find number of bits to flip based on fitness, random generate the
        //neccessary number of positions to flip and do so
        if(random > 0.8){
            for(int index = 0; index < numberOfMutations; index++){
                Random rand = new Random();
                int bit = rand.nextInt(integerSet.size());
                child.modifyBinaryIndicator(index, bit);
            }
        }
    }
    
    private void getIntegerSet(String fileName) throws FileNotFoundException {
        try{
            Scanner reader = new Scanner(new File(fileName));
            while(reader.hasNext()){
                integerSet.add(reader.nextInt());
            }        
        }
        catch (FileNotFoundException ex){
            System.out.println("File Not Found");
        }
    }
    
    private int getIntegerSetSum(){
        int sum = 0;
        for(int i = 0; i < integerSet.size(); i++){
            sum += (int) integerSet.get(i);
        }
        return sum;
    }
    
}