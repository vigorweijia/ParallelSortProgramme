package mypkg;

import java.io.*;
import java.util.Scanner;

public class MAIN {
    private static int[] nums;
    private static int[] backup;
    private static final int lenth = 30000;
    private static void readFromFile(String fileName) throws FileNotFoundException
    {
        nums = new int[lenth];
        backup = new int[lenth];
        File file = new File(fileName);
        if(!file.exists())
        {
            System.out.println("File not exist");
            System.exit(0);
        }
        Scanner scanner = new Scanner(file);
        int i = 0;
        while (scanner.hasNext())
        {
            backup[i++] = scanner.nextInt();
        }
        scanner.close();
        return;
    }

    private static void returnBack()
    {
        for(int i = 0; i < lenth; i++) nums[i] = backup[i];
    }

    private static void writeToFile(String fileName)
    {
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(int i = 0; i < lenth; i++) bufferedWriter.write(nums[i] + " ");
            bufferedWriter.write("\n");
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        //Generate random data here.
        //GenerateRandom.generateRandom(lenth);

        try{
            readFromFile("src/mypkg/random.txt");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        double startTime, endTime;

        returnBack();
        startTime = System.currentTimeMillis();
        SerialQuickSort.serialQuickSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Serial Quick Sort: " + (endTime-startTime));
        writeToFile("order1.txt");

        returnBack();
        startTime = System.currentTimeMillis();
        SerialMergeSort.serialMergeSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Serial Merge Sort: " + (endTime-startTime));
        writeToFile("order2.txt");

        returnBack();
        startTime = System.currentTimeMillis();
        SerialEnumerationSort.serialEnumerationSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Serial Enumeration Sort: " + (endTime-startTime));
        writeToFile("order3.txt");

        returnBack();
        startTime = System.currentTimeMillis();
        ParallelQuickSort.parallelQuickSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Parallel Quick Sort: " + (endTime-startTime));
        writeToFile("order4.txt");

        returnBack();
        startTime = System.currentTimeMillis();
        ParallelMergeSort.parallelMergeSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Parallel Merge Sort: " + (endTime-startTime));
        writeToFile("order5.txt");

        returnBack();
        startTime = System.currentTimeMillis();
        ParallelEnumerationSort.parallelEnumerationSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Parallel Enumeration Sort: " + (endTime-startTime));
        writeToFile("order6.txt");

    }
}
