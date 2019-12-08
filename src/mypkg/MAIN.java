package mypkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MAIN {
    private static int[] nums;

    private static void readFromFile(String fileName) throws FileNotFoundException
    {
        nums = new int[30000];
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
            nums[i++] = scanner.nextInt();
        }
        scanner.close();
        return;
    }

    public static void main(String[] args)
    {
        try{
            readFromFile("random.txt");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        int lenth = nums.length;
        double startTime, endTime;

        startTime = System.currentTimeMillis();
        SerialQuickSort.serialQuickSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Serial Quick Sort: " + (startTime-endTime));

        startTime = System.currentTimeMillis();
        SerialMergeSort.serialMergeSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Serial Merge Sort: " + (startTime-endTime));

        startTime = System.currentTimeMillis();
        SerialEnumerationSort.serialEnumerationSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Serial Enumeration Sort: " + (startTime-endTime));

        startTime = System.currentTimeMillis();
        ParallelQuickSort.parallelQuickSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Parallel Quick Sort: " + (startTime-endTime));

        startTime = System.currentTimeMillis();
        ParallelMergeSort.parallelMergeSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Parallel Merge Sort: " + (startTime-endTime));

        startTime = System.currentTimeMillis();
        ParallelEnumerationSort.parallelEnumerationSort(nums, 0, lenth-1);
        endTime = System.currentTimeMillis();
        System.out.println("Parallel Enumeration Sort: " + (startTime-endTime));
    }
}
