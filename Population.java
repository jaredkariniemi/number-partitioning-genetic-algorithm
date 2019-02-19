package hello;
import java.util.*;
import java.lang.*;
import java.io.*;



public class Population {
    
    private int popSize;
    private List integerSet = new LinkedList();
    private List candidates = new LinkedList();
    
    public Population(List integerSet){
        this.integerSet = integerSet;
        popSize = candidates.size();
    }
    
    public Population(){
        popSize = candidates.size();
    }
    
    public Iterator getIntegerSet() {
        return (integerSet.listIterator());
    }
    
    public Iterator getCandidates() {
        return (candidates.listIterator());
    }
    
    public Candidate getCandidate(int index){
        return ((Candidate) candidates.get(index));
    }
    
    public int getPopSize(){
        return popSize;
    }
    
    public void addCandidate(Candidate candidate){
        //add to list in fitness position
        int index = 0;
        int candidateFitness = candidate.getFitness();
        if(candidates.size() > 0){
            Candidate compareCandidate = (Candidate) candidates.get(index);
            int compareFitness = compareCandidate.getFitness();
            while(index < candidates.size()){
                compareCandidate = (Candidate) candidates.get(index);
                compareFitness = compareCandidate.getFitness();
                if (candidateFitness < compareFitness) break;
                index++;
            }
        }
        candidates.add(index, candidate);
        popSize = candidates.size();
    }
    
    public void setIntegerSet(List integerSet){
        this.integerSet = integerSet;
    }
}