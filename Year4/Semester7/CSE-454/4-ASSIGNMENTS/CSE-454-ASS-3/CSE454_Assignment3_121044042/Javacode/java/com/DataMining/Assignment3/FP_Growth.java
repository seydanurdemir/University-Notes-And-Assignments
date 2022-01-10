package com.DataMining.Assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * @author seydanur
 * CSE 454 - Data Mining
 * Assignment 3
 * Seyda Nur DEMIR
 * 12 10 44 042
 * FP-Growth Algorithm Implementation
 * FP_Growth Class
 */
public class FP_Growth
{
    private int min_support;
    private String filename;
    
    FP_Growth (int min_support, String filename) {
        this.min_support = min_support;
        this.filename = filename;
    }
    
    public ArrayList<ArrayList<String>> createDataset(String filename)
    {
        ArrayList<ArrayList<String>> myArrayList = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filename)))
        {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                ArrayList<String> items = new ArrayList<>();
                String[] arrayOfString = line.split("[, ?.@]+");
                for(int i = 0; i < arrayOfString.length; ++i)
                {
                    items.add(arrayOfString[i]);
                }
                myArrayList.add(items);
            }
        } catch (IOException e)
        {
            System.err.format("IOException: %s%n", e);
        }
        return myArrayList;
    }

    public void printDataset(ArrayList<ArrayList<String>> itemSet)
    {
        for(int i = 0; i < itemSet.size(); ++i)
        {
            ArrayList<String> lineOfDS = itemSet.get(i);
            for(int j = 0; j < lineOfDS.size(); ++j)
            {
                System.out.print(lineOfDS.get(j) + " ");
            }
            System.out.println();
        }
    }

    public HashMap<String,Integer> findItemsAndCounts(ArrayList<ArrayList<String>> itemSet)
    {
        int initialCount = 1;
        HashMap<String,Integer> countOfItems = new HashMap<>();
        for(int i = 0; i < itemSet.size(); ++i)
        {
            ArrayList<String> lineOfDS = itemSet.get(i);
            for(int j = 0; j < lineOfDS.size(); ++j)
            {
                if(countOfItems.containsKey(lineOfDS.get(j)))
                {
                    int tempCount = countOfItems.get(lineOfDS.get(j));
                    tempCount++;
                    countOfItems.replace(lineOfDS.get(j),tempCount);
                }
                else
                {
                    countOfItems.put(lineOfDS.get(j),initialCount);
                }
            }
        }
        return countOfItems;
    }

    public void printHashMap(HashMap<String, Integer> hashMap)
    {
        for (String key : hashMap.keySet())
        {
            System.out.print(key + " : ");
            System.out.println(hashMap.get(key));
        }
    }

    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hashMap)
    {
        ArrayList<Integer> Values = new ArrayList<>();
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        for (String key : hashMap.keySet())
        {
            Values.add(hashMap.get(key));
        }

        Collections.sort(Values, Collections.reverseOrder());

        for (int value : Values)
        {
            for (Map.Entry<String, Integer> entry : hashMap.entrySet())
            {
                if (entry.getValue().equals(value))
                {
                    sortedMap.put(entry.getKey(), value);
                }
            }
        }
        return sortedMap;
    }

    public HashMap<String,Integer> removeItemsLessThanMinSup(HashMap<String, Integer> hashMap)
    {
        HashMap<String,Integer> returnHashMap = new HashMap<>();
        for (String key : hashMap.keySet())
        {
            int value = hashMap.get(key);

            if(value >= min_support)
            {
                returnHashMap.put(key,value);
            }
        }
        return sortByValue(returnHashMap);
    }

    /*
        This method takes two 2D ArrayList.
        First represents data set, second represent frequent item set.
        This method creates heads for itemSet and creates tree.
        This method also calls a print method to print frequents pattern.
    
        This method found on Internet, it works for our algorithm
     */
    public void FPGrowthMethod(ArrayList<ArrayList<String>> itemSet, ArrayList<String> frequentItemSets)
    {
        ArrayList<FP_Tree> heads = buildHeadTable(itemSet); //Build heads of items

        FP_Tree root = buildFPTree(itemSet, heads); //Build FP_Tree

        //Best-case of recursive method
        if(root.getChildNodes().size() == 0)
        {
            return;
        }

        //Prints pattern
        printPattern(frequentItemSets, heads);

        //This is for finding pattern
        for(int i = 0; i < heads.size(); ++i)
        {
            ArrayList<String> pattern = new ArrayList<>();

            String itemOfHead = heads.get(i).getItem();
            pattern.add(itemOfHead);

            if(frequentItemSets != null)
            {
                pattern.addAll(frequentItemSets);
            }

            ArrayList<ArrayList<String>> newItemSet = new ArrayList<>();
            FP_Tree currentNode = heads.get(i).getNextNode();

            while(currentNode != null)
            {
                int counter = currentNode.getCount();
                ArrayList<String> parentNodes = new ArrayList<>();

                //parent is our currentNode
                FP_Tree parent = currentNode;

                //Traverse all parent nodes of currentNode and put them into parentNodes
                while((parent = parent.getParentNode()).getItem() != null)
                {
                    String item = parent.getItem();
                    parentNodes.add(item);
                }

                while(counter > 0)
                {
                    newItemSet.add(parentNodes);
                    counter--;
                }

                currentNode = currentNode.getNextNode();
            }

            //Recursive call
            FPGrowthMethod(newItemSet, pattern);
        }
    }

    public void printPattern(ArrayList<String> frequentItemSets, ArrayList<FP_Tree> heads)
    {
        if(frequentItemSets != null)
        {
            for(int i = 0; i < heads.size(); ++i)
            {
                System.out.print("{ ");
                for(int j = 0; j < frequentItemSets.size(); ++j)
                {
                    System.out.print(frequentItemSets.get(j) + " ");
                }
                System.out.println(heads.get(i).getItem() + " : " + heads.get(i).getCount() + " }");
            }
        }
    }

    public ArrayList<FP_Tree> buildHeadTable(ArrayList<ArrayList<String>> itemSet)
    {
        ArrayList<FP_Tree> head = new ArrayList<>();
        HashMap<String, Integer> itemMap;

        itemMap = findItemsAndCounts(itemSet);
        itemMap = removeItemsLessThanMinSup(itemMap); //It was sorted inside of this method.

        for (String key : itemMap.keySet())
        {
            FP_Tree FP_Tree_Node = new FP_Tree();
            FP_Tree_Node.increaseCount();
            FP_Tree_Node.setItem(key);
            FP_Tree_Node.setCount(itemMap.get(key));
            head.add(FP_Tree_Node);
        }
        return head;
    }

    public FP_Tree buildFPTree(ArrayList<ArrayList<String>> itemSet, ArrayList<FP_Tree> heads)
    {
        FP_Tree root = new FP_Tree();
        FP_Tree currentNode = root;

        for(ArrayList<String> items : itemSet)
        {
            for(String item : items)
            {
                FP_Tree tempTree = findChildNodes(currentNode,item);

                if(null == tempTree)
                {
                    tempTree = new FP_Tree();
                    tempTree.setItem(item);
                    tempTree.setParentNode(currentNode);
                    currentNode.getChildNodes().add(tempTree);
                    addNode(heads,tempTree);
                }
                currentNode = tempTree;
                tempTree.increaseCount();
            }
            currentNode = root;
        }
        return root;
    }

    public FP_Tree findChildNodes(FP_Tree currentNode, String item)
    {
        ArrayList<FP_Tree> children = currentNode.getChildNodes();
        if(children != null)
        {
            for(int i = 0; i < children.size(); ++i)
            {
                String currentItem = children.get(i).getItem();
                if(currentItem.equals(item))
                {
                    return children.get(i);
                }
            }
        }
        return null;
    }

    public void addNode(ArrayList<FP_Tree> heads, FP_Tree FP_Tree_Node)
    {
        FP_Tree currentNode;

        for(int i = 0; i < heads.size(); ++i)
        {
            String headItem = heads.get(i).getItem();
            String nodeItem = FP_Tree_Node.getItem();

            if(headItem.equals(nodeItem))
            {
                currentNode = heads.get(i);

                while(currentNode.getNextNode() != null)
                {
                    currentNode = currentNode.getNextNode();
                }
                currentNode.setNextNode(FP_Tree_Node);
            }
        }
    }
}