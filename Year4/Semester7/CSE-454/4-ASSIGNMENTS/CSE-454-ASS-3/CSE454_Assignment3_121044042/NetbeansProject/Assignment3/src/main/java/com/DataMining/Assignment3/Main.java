package com.DataMining.Assignment3;

/**
 *
 * @author seydanur
 * CSE 454 - Data Mining
 * Assignment 3
 * Seyda Nur DEMIR
 * 12 10 44 042
 * FP-Growth Algorithm Implementation
 * Main Class
 */
public class Main {
    public static void main(String [] args)
    {
        Test testObject = new Test();
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("TEST 1.1 : Dataset From Book (Table 6.1) with Minimum Support 2");
        testObject.test(2,"C:\\Users\\sefer\\Documents\\NetBeansProjects\\Assignment3\\src\\main\\java\\com\\DataMining\\Assignment3\\DatasetFromBook.txt");
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("TEST 1.2 : Dataset From Book (Table 6.1) with Minimum Support 3");
        testObject.test(3,"C:\\Users\\sefer\\Documents\\NetBeansProjects\\Assignment3\\src\\main\\java\\com\\DataMining\\Assignment3\\DatasetFromBook.txt");
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("TEST 2.1 : Dataset For Test with Minimum Support 2");
        testObject.test(2,"C:\\Users\\sefer\\Documents\\NetBeansProjects\\Assignment3\\src\\main\\java\\com\\DataMining\\Assignment3\\DatasetForTest.txt");
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("TEST 2.2 : Dataset For Test with Minimum Support 3");
        testObject.test(3,"C:\\Users\\sefer\\Documents\\NetBeansProjects\\Assignment3\\src\\main\\java\\com\\DataMining\\Assignment3\\DatasetForTest.txt");
        
        System.out.println("-------------------------------------------------------------------- END");
    }
}