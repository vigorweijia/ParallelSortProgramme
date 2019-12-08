package mypkg;

import java.util.concurrent.CountDownLatch;

public class ParallelMergeSort {

    private static final int maxParallelDepth = (int) (Math.log(Runtime.getRuntime().availableProcessors()) / Math.log(2));

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
    }

    public static void parallelMergeSort(int[] arry, int l, int r)
    {
        if(maxParallelDepth >= 1)
            mergeSortParallel(arry, l, r, maxParallelDepth, 1);
        else
            mergeSortParallel(arry, l, r, 1, 1);
    }
}
