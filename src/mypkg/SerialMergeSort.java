package mypkg;

public class SerialMergeSort {
    private static void merge(int[] arry, int l, int r, int mid)
    {
        int[] A = new int[mid-l+1];
        int[] B = new int[r-mid];
        for(int i = 0; i < mid-l+1; i++) A[i] = arry[l+i];
        for(int i = 0; i < r-mid; i++) B[i] = arry[mid+1+i];
        int i = 0,j = 0,k = 0;
        while(i < mid-l+1||j < r-mid)
        {
            if(i >= mid-l+1)
            {
                arry[l+k] = B[j];
                k++;
                j++;
            }
            else if(j >= r-mid)
            {
                arry[l+k] = A[i];
                k++;
                i++;
            }
            else
            {
                if(A[i] < B[j]) {
                    arry[l + k] = A[i];
                    i++;
                    k++;
                }
                else {
                    arry[l + k] = B[j];
                    j++;
                    k++;
                }
            }
        }
    }
    private static void mergeSort(int[] arry, int l, int r)
    {
        if(l >= r) return;
        int mid = (l+r)/2;
        mergeSort(arry, l, mid);
        mergeSort(arry, mid+1, r);
        merge(arry, l, r, mid);
    }
    public static void serialMergeSort(int[] arry, int l, int r)
    {
        mergeSort(arry, l, r);
    }
}
