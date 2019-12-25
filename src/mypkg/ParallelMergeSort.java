package mypkg;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class ParallelMergeSort {

    /*private static final int maxParallelDepth = (int) (Math.log(Runtime.getRuntime().availableProcessors()) / Math.log(2));
    private static void mergeSortParallel(int[] arry, int l, int r, int depthLimit, int depth)
    {
        if(l >= r) return;
        CountDownLatch countDownLatch = new CountDownLatch(2);
        int mid = (l+r)/2;
        new SortThread(depth, depthLimit, arry, countDownLatch, l, mid).start();
        new SortThread(depth, depthLimit, arry, countDownLatch, mid+1, r).start();
        try {
            countDownLatch.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        SerialMergeSort.merge(arry, l, r, mid);
    }
    private static class SortThread extends Thread
    {
        private int depth;
        private int depthLimit;
        private int[] arry;
        private CountDownLatch mergeSignal;
        private int l;
        private int r;
        public SortThread(int depth, int depthLimit, int[] arry, CountDownLatch mergeSignal, int l, int r)
        {
            super();
            this.depth = depth;
            this.depthLimit = depthLimit;
            this.arry = arry;
            this.mergeSignal = mergeSignal;
            this.l = l;
            this.r = r;
        }
        @Override
        public void run()
        {
            if(depth < depthLimit)
            {
                mergeSortParallel(arry, l, r, depthLimit, depth+1);
            }
            else
            {
                SerialMergeSort.serialMergeSort(arry, l, r);
            }
            mergeSignal.countDown();
        }
    }*/

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
            //局部排序+正则采样
            SerialMergeSort.serialMergeSort(array, L, R);
            for(int i = 0; i < processCount; i++)
            {
                int partialPivotId = i*(R-L+1)/processCount;
                sample[id*processCount+i] = array[L+partialPivotId];
            }
            try{ cyclicBarrier.await(); }
            catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }

            //采样排序+选择主元
            if(id == 0)
            {
                SerialMergeSort.serialMergeSort(sample, 0, processCount*processCount-1);
                for(int i = 0; i < processCount-1; i++) pivot[i] = sample[(i+1)*processCount];
            }
            try{ cyclicBarrier.await(); }
            catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }

            //主元划分
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

            //全局交换
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

    public static void parallelMergeSort(int[] arry, int l, int r)
    {
        /*//System.out.println("Parallel merge sort available processors:"+Runtime.getRuntime().availableProcessors());
        if(maxParallelDepth >= 1)
            mergeSortParallel(arry, l, r, maxParallelDepth, 1);
        else
            mergeSortParallel(arry, l, r, 1, 1);*/
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
