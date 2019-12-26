package mypkg;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class ParallelQuickSortSimpleDivide {
    private static int[] arry;
    private static CyclicBarrier cyclicBarrier;
    private static CountDownLatch countDownLatch;
    private static final int maxParallelDepth = (int) (Math.log(Runtime.getRuntime().availableProcessors())/Math.log(2));
    private static final int p = Runtime.getRuntime().availableProcessors();
    private static SortAction[] sortActions;

    private static void quickSort(int l,int r, int depth)
    {
        cyclicBarrier = new CyclicBarrier(p);
        countDownLatch = new CountDownLatch(p);
        sortActions = new SortAction[p];
        for(int i = 0; i < p; i++)
        {
            sortActions[i] = new SortAction();
            sortActions[i].id = i; //#################
            sortActions[i].l = l;
            sortActions[i].r = r;
            sortActions[i].flag = false;
        }
        sortActions[0].flag = true;
        for(int i = 0; i < p; i++)
        {
            sortActions[i].start();
        }
        try {
            countDownLatch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private static class SortAction extends Thread
    {
        public int l;
        public int r;
        public int id;
        public boolean flag;

        @Override
        public void run()
        {
            for(int i = 0; i < maxParallelDepth; i++)
            {
                boolean nowFlag = flag;
                try { cyclicBarrier.await(); }
                catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
                if(nowFlag == true)
                {
                    //System.out.println("i:"+i+"id:"+id+"  l:"+l+",r:"+r);
                    if(l < r)
                    {
                        int x = SerialQuickSort.partion(arry, l, r);
                        int sonId = id + (int)Math.pow(2,maxParallelDepth-i-1);
                        sortActions[sonId].flag = true;
                        sortActions[sonId].r = r;
                        sortActions[sonId].l = x+1;
                        r = x-1;
                    }
                }
                try { cyclicBarrier.await(); }
                catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
                //System.out.println("i:"+i+" ThreadId:"+id);
            }
            if(l < r) {
                SerialQuickSort.serialQuickSort(arry, l, r);
            }
            countDownLatch.countDown();
        }
    }

    public static void parallelQuickSortSimpleDivide(int[] array, int l, int r)
    {
        arry = array;
        quickSort(l, r,1);
    }
}
