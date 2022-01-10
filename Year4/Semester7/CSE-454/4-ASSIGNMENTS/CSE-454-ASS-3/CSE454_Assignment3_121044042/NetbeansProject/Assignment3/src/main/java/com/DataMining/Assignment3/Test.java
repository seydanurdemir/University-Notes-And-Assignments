package com.DataMining.Assignment3;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author seydanur
 * CSE 454 - Data Mining
 * Assignment 3
 * Seyda Nur DEMIR
 * 12 10 44 042
 * FP-Growth Algorithm Implementation
 * Test Class
 */
public class Test {
    public void test (int min_support, String filename) {
        FP_Growth fpgrowth = new FP_Growth(min_support, filename);
        
        ArrayList<ArrayList<String>> Dataset;
        
        Dataset = fpgrowth.createDataset(filename);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Dataset [D] Items [I]");
        System.out.println("------------------------------------------------------------------------");
        fpgrowth.printDataset(Dataset);
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Items [F] and Frequencies [Count]");
        System.out.println("------------------------------------------------------------------------");
        HashMap<String,Integer> ItemsAndCounts = fpgrowth.findItemsAndCounts(Dataset);
        fpgrowth.printHashMap(ItemsAndCounts);

        System.out.println("------------------------------------------------------------------------");
        System.out.println("Sorted List [descending order as L]");
        System.out.println("------------------------------------------------------------------------");
        HashMap<String, Integer> sortedItem = fpgrowth.sortByValue(ItemsAndCounts);
        fpgrowth.printHashMap(sortedItem);

        System.out.println("------------------------------------------------------------------------");
        System.out.println("Removed Items [Count >= Minimum Support Count]");
        System.out.println("------------------------------------------------------------------------");
        HashMap<String, Integer> a = fpgrowth.removeItemsLessThanMinSup(sortedItem);
        fpgrowth.printHashMap(a);
        
        FP_Growth fpg = new FP_Growth(min_support, filename);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Mined Frequent Patterns");
        System.out.println("------------------------------------------------------------------------");
        fpg.FPGrowthMethod(Dataset,null);
    }
}