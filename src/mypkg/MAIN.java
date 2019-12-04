package mypkg;

public class MAIN {
    public static void main(String[] args)
    {
        int[] arry = {1,8,2,7,6,3,5,4,1,5,11,-2,9};
        //SerialQuickSort.serialQuickSort(arry,0,12);
        SerialMergeSort.serialMergeSort(arry, 0, 12);
        for(int each : arry)
        {
            System.out.print(each);
            System.out.print(" ");
        }
    }
}
