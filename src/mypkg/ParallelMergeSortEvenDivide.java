package mypkg;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class ParallelMergeSortEvenDivide {
    private static class SortThread extends Thread
    {
        public int id;
        public int L;
        public int R;

        public SortThread(int id, int L, int R)
        {
            this.id = id;
            this.L = L;
            this.R = R;
        }

        @Override
        public void run()
        {
            //Step2: partial sort
            SerialMergeSort.serialMergeSort(array, L, R);
            //Step3: select samples
            for(int i = 0; i < processCount; i++)
            {
                int partialPivotId = i*(R-L+1)/processCount;
                sample[id*processCount+i] = array[L+partialPivotId];
            }
            try{ cyclicBarrier.await(); }
            catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }

            if(id == 0)
            {
                //Step4: samples sort
                SerialMergeSort.serialMergeSort(sample, 0, processCount*processCount-1);
                //Step5: select pivots
                for(int i = 0; i < processCount-1; i++) pivot[i] = sample[(i+1)*processCount];
            }
            try{ cyclicBarrier.await(); }
            catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }

            //Step6: divide according to pivot
            pivotPosition[id][0] = L;
            int pivotIndex = 0;
            for(int i = L; i <= R; i++)
            {
                if(array[i] > pivot[pivotIndex])
                {
                    pivotPosition[id][pivotIndex+1] = i;
                    pivotIndex++;
                    i--;
                    if(pivotIndex >= processCount-1) break;
                }
            }
            pivotPosition[id][pivotIndex+1] = R+1;
            try{ cyclicBarrier.await(); }
            catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }

            //Step7: global exchange
            int len = 0;
            int st = 0;
            for(int i = 0; i < processCount; i++)
            {
                len += pivotPosition[i][id+1]-pivotPosition[i][id];
                st += pivotPosition[i][id]-pivotPosition[i][0];
            }
            L = st;
            R = st+len-1;
            int k = 0;
            for(int i = 0; i < processCount; i++)
            {
                for(int j = pivotPosition[i][id]; j < pivotPosition[i][id+1]; j++)
                {
                    res[L+k] = array[j];
                    k++;
                }
            }
            //Step8: merge sort
            SerialMergeSort.serialMergeSort(res, L, R);
            try{ cyclicBarrier.await(); }
            catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
            countDownLatch.countDown();
        }
    }

    public static int[] array;
    public static int[] res;
    public static int[] sample;
    public static int[] pivot;
    public static final int processCount = 8;
    public static int pivotPosition[][];
    public static CyclicBarrier cyclicBarrier;
    public static int globalLenth;
    public static CountDownLatch countDownLatch;

    public static void parallelMergeSortEvenDivide(int[] arry, int l, int r)
    {
        array = arry;
        cyclicBarrier = new CyclicBarrier(processCount);
        int lenth = r-l+1;
        sample = new int[lenth];
        pivot = new int[lenth];
        res = new int[lenth];
        int blockSize = (lenth+processCount-1)/processCount;
        pivotPosition = new int[processCount][processCount+1];
        globalLenth = lenth;
        countDownLatch = new CountDownLatch(processCount);
        for(int i = 0; i < processCount; i++)
        {
            //Step1: evenly divide
            SortThread t = new SortThread(i, i*blockSize, Math.min((i+1)*blockSize, lenth)-1);
            t.start();
        }
        try{
            countDownLatch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        for(int i = 0; i < globalLenth; i++) array[i] = res[i];
    }
}
