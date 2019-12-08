package mypkg;

public class MAIN {
    public static void main(String[] args)
    {
        int[] arry = {1,8,2,7,6,3,5,4,1,5,11,-2,9,5,1};
        /*SerialQuickSort.serialQuickSort(arry,0,14);
        for(int each : arry)
        {
            System.out.print(each);
            System.out.print(" ");
        }
        System.out.println();
        SerialMergeSort.serialMergeSort(arry, 0, 14);
        for(int each : arry)
        {
            System.out.print(each);
            System.out.print(" ");
        }
        System.out.println();
        SerialEnumerationSort.serialEnumerationSort(arry, 0, 14);
        for(int each : arry)
        {
            System.out.print(each);
            System.out.print(" ");
        }
        System.out.println();*/
        /*ParallelMergeSort.parallelMergeSort(arry, 0, 14);
        for(int each : arry)
        {
            System.out.print(each);
            System.out.print(" ");
        }
        System.out.println();*/
        /*ParallelQuickSort.parallelQuickSort(arry, 0, 14);
        for(int each : arry)
        {
            System.out.print(each);
            System.out.print(" ");
        }
        System.out.println();*/
        ParallelEnumerationSort.parallelEnumerationSort(arry, 0, 14);
        for(int each : arry)
        {
            System.out.print(each);
            System.out.print(" ");
        }
        System.out.println();
    }
}
