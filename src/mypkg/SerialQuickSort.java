package mypkg;

public class SerialQuickSort {
    public static int partion(int[] arry, int l, int r)
    {
        int pivot = arry[r];
        int j = l-1;
        for(int i = l; i < r; i++)
        {
            if(arry[i] < pivot)
            {
                j++;
                int temp = arry[j];
                arry[j] = arry[i];
                arry[i] = temp;
            }
        }
        int temp = arry[j+1];
        arry[j+1] = arry[r];
        arry[r] = temp;
        return j+1;
    }
    private static void quickSort(int[] arry, int l, int r)
    {
        if(l >= r) return;
        int x = partion(arry, l, r);
        quickSort(arry, l, x-1);
        quickSort(arry, x+1, r);
    }
    public static void serialQuickSort(int[] arry, int l, int r)
    {
        quickSort(arry, l, r);
    }
}
