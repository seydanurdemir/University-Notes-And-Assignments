package com.DataMining.Assignment3;

import java.util.ArrayList;

/**
 *
 * @author seydanur
 * CSE 454 - Data Mining
 * Assignment 3
 * Seyda Nur DEMIR
 * 12 10 44 042
 * FP-Growth Algorithm Implementation
 * FP_Tree Class
 */
public class FP_Tree
{
    //These are variables to represent FP_Tree
    private ArrayList<FP_Tree> childNodes = new ArrayList<>();
    private FP_Tree parentNode;
    private FP_Tree nextNode;
    private String item;
    private int counts;

    //Gets item
    public String getItem() { return item; }
    
    //Sets item to itemValue
    public void setItem(String itemValue) { item = itemValue; }
    
    //Gets parentNode
    public FP_Tree getParentNode() { return parentNode; }
    
    //Sets parentNode to parentNodeValue
    public void setParentNode(FP_Tree parentNodeValue) { parentNode = parentNodeValue; }
    
    //Gets childNodes
    public ArrayList<FP_Tree> getChildNodes() { return childNodes; }
    
    //Gets count
    public int getCount() { return counts; }
    
    //Sets count to countValue
    public void setCount(int countsValue) { counts = countsValue; }
    
    //Increase count by 1
    public void increaseCount() { int tempCount = counts; tempCount++; counts = tempCount; }
    
    //Gets nextNode
    public FP_Tree getNextNode() { return nextNode; }
    
    //Sets nextNode to nextNodeValue
    public void setNextNode(FP_Tree nextNodeValue) { nextNode = nextNodeValue; }
}