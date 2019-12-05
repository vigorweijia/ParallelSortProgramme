package mypkg;

public class SerialEnumerationSort {
    private static void enumerationSort(int[] arry, int l, int r)
    {
        int[] A = new int[r-l+1];
        for(int i = 0; i < r-l+1; i++) A[i] = arry[i];
        for(int i = 0; i < r-l+1; i++)
        {
            int k = 0;
            for(int j = 0; j < r-l+1; j++)
            {
                if(i == j) continue;
                if(i < j&&A[j] < A[i]) k++;
                if(i > j&&A[j] <= A[i]) k++;
            }
            arry[l+k] = A[i];
        }
    }
    public static void serialEnumerationSort(int[] arry, int l, int r)
    {
        enumerationSort(arry, l, r);
    }
}
