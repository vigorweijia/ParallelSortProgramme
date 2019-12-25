package mypkg;

import java.util.concurrent.CountDownLatch;

public class ParallelEnumerationSort {
    private static int[] rankAns;
    private static int globalStart;
    private static int globalEnd;

    private static void enumerationSort(int[] arry ,int l, int r)
    {
        int maxParallelProcess = Runtime.getRuntime().availableProcessors();
        //int maxParallelProcess = 16;
        //System.out.println("Parallel enumeration sort available processor:"+maxParallelProcess);
        if(r-l+1 < maxParallelProcess)
        {
            SerialEnumerationSort.serialEnumerationSort(arry, l, r);
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(maxParallelProcess);
        int partion = (r-l+1+maxParallelProcess-1)/maxParallelProcess;
        for(int i = 0; i < maxParallelProcess; i++)
        {
            int newL = i * partion;
            int newR = (i+1) * partion - 1;
            if(newR > r) newR = r;
            //System.out.println(newL + " " + newR);
            new SortThread(arry, newL, newR, countDownLatch).start();
        }
        try{
            countDownLatch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        int[] A = new int[r-l+1];
        for(int i = 0; i < r-l+1; i++) A[i] = arry[globalStart+i];
        for(int i = 0; i < r-l+1; i++)
        {
            arry[globalStart+rankAns[i]] = A[i];
        }
    }

    private static class SortThread extends Thread
    {
        int[] arry;
        int l;
        int r;
        CountDownLatch countDownLatch;

        public SortThread(int[] arry, int l, int r, CountDownLatch countDownLatch)
        {
            this.arry = arry;
            this.l = l;
            this.r = r;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run()
        {
            for(int i = l; i<= r; i++)
            {
                int rank = 0;
                for(int j = globalStart; j <= globalEnd; j++)
                {
                    if(i == j) continue;
                    if(i < j && arry[j] < arry[i]) rank++;
                    if(i > j && arry[j] <= arry[i]) rank++;
                }
                rankAns[i-globalStart] = rank;
                //System.out.println("arry["+i+"]="+arry[i]+", rank:"+rank);
            }
            countDownLatch.countDown();
        }
    }

    public static void parallelEnumerationSort(int[] arry, int l, int r)
    {
        rankAns = new int[r-l+1];
        globalStart = l;
        globalEnd = r;
        enumerationSort(arry, l, r);
    }
}
