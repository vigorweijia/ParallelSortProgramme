package mypkg;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.*;

public class ParallelQuickSort {

    //System.out.println("Parallel quick sort available processors:" + Runtime.getRuntime().availableProcessors());
    private static int[] arry;
    private static ExecutorService executorService;
    private static final int maxParallelDepth = (int) (Math.log(Runtime.getRuntime().availableProcessors())/Math.log(2));
    private static int depthLimit;
    private static CountDownLatch countDownLatch;

    private static void quickSort(int l,int r, int depth)
    {
        if(l >= r) return;
        if(depth < depthLimit)
        {
            int x = SerialQuickSort.partion(arry, l, r);
            new SortAction(l, x-1, depth+1).start();
            new SortAction(x+1, r, depth+1).start();
        }
        else
        {
            SerialQuickSort.serialQuickSort(arry, l, r);
        }
    }

    private static class SortAction extends Thread
    {
        private int l;
        private int r;
        private int depth;

        public SortAction(int l, int r, int depth)
        {
            super();
            this.l = l;
            this.r = r;
            this.depth = depth;
        }

        @Override
        public void run()
        {
            quickSort(l, r, depth);
            countDownLatch.countDown();
        }
    }

    public static void parallelQuickSort(int[] array, int l, int r)
    {
        if(maxParallelDepth >= 1)
            depthLimit = maxParallelDepth;
        else
            depthLimit = 1;
        arry = array;
        //System.out.println((new Double(Math.pow(2,maxParallelDepth)-2)).intValue());
        countDownLatch = new CountDownLatch((new Double(Math.pow(2,maxParallelDepth)-2)).intValue());
        quickSort(l, r,1);
        try {
            countDownLatch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
