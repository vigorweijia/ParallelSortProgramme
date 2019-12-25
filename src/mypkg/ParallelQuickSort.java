package mypkg;

import java.util.concurrent.CountDownLatch;

public class ParallelQuickSort {

    private static int aliveProcess;

    private static final int maxParallelDepth = (int) (Math.log(Runtime.getRuntime().availableProcessors())/Math.log(2));

    private static void quickSort(int[] arry, int l,int r, int depthLimit, int depth)
    {
        if(l >= r) return;
        if(depth < depthLimit)
        {
            CountDownLatch countDownLatch = new CountDownLatch(2);
            int mid = (l+r)/2;
            new SortThread(arry, l, mid, depthLimit, depth+1, countDownLatch).start();
            new SortThread(arry, mid+1, r, depthLimit, depth+1, countDownLatch).start();
            try {
                countDownLatch.await();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            SerialMergeSort.merge(arry, l, r, mid);
        }
        else
        {
            SerialQuickSort.serialQuickSort(arry, l, r);
        }
    }

    private static class SortThread extends Thread
    {
        private int[] arry;
        private int l;
        private int r;
        private int depthLimit;
        private int depth;
        private CountDownLatch countDownLatch;

        public SortThread(int[] arry, int l, int r ,int depthLimit, int depth, CountDownLatch countDownLatch)
        {
            this.arry = arry;
            this.l = l;
            this.r = r;
            this.depthLimit = depthLimit;
            this.depth = depth;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run()
        {
            quickSort(arry, l, r, depth, depthLimit);
            countDownLatch.countDown();
        }
    }

    public static void parallelQuickSort(int[] arry, int l, int r)
    {
        System.out.println("Parallel quick sort available processors:" + Runtime.getRuntime().availableProcessors());
        if(maxParallelDepth >= 1)
            quickSort(arry, l, r, maxParallelDepth, 1);
        else
            quickSort(arry, l, r, 1, 1);
    }
}
