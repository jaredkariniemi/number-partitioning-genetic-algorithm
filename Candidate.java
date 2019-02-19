package hello;

import java.util.*;
import java.lang.*;
import java.io.*;


public class Candidate {
    
    private List binaryIndicator = new LinkedList();
    private List integerSet = new LinkedList();
    private int fitness;
    
    public Candidate(List binaryIndicator, List integerSet){
        this.binaryIndicator = binaryIndicator;
        this.integerSet = integerSet;
        calculateFitness();
    }
    
    public Candidate(List integerSet){
        this.integerSet = integerSet;
    }
    
    public void modifyBinaryIndicator(int index, int bit){
        //change value at index indexToChange to int bit
        if(binaryIndicator.size() == 0){
            binaryIndicator.add(bit);
        }
        else{
            binaryIndicator.add(index,bit);
        }
        calculateFitness();
    }
    
    private void calculateFitness() {
        int list1Sum = 0;
        int list2Sum = 0;
        int counter = 0;
        for(ListIterator iterator = binaryIndicator.listIterator(); iterator.hasNext();){
            int bit = (int) iterator.next();
            int currentInt = (int) integerSet.get(counter);
            if(bit == 0){
                list1Sum += currentInt; 
            }
            else if(bit == 1){
                list2Sum += currentInt;
            }
            counter++;
        }
        fitness = Math.abs(list1Sum - list2Sum);
    }
    public Iterator getBinaryIndicator(){
        return (binaryIndicator.listIterator());
    }
    
    public Iterator getIntegerSet() {
        return (integerSet.listIterator());
    }
    
    public int getFitness(){
        return fitness;
    }
    
}

//might have some issues with int vs Integer and Linked List (needs Integer object?)
//issues with adding int/Integer with normal +?