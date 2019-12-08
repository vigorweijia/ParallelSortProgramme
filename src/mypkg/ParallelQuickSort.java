package mypkg;

public class ParallelQuickSort {

    private static int aliveProcess;

    private static final int maxParallelDepth = (int) (Math.log(Runtime.getRuntime().availableProcessors())/Math.log(2));

    private static void quickSort(int[] arry, int l,int r, int depthLimit, int depth)
    {
        if(l >= r) return;
        int x = SerialQuickSort.partion(arry, l, r);
        if(depth < depthLimit)
        {
            aliveProcess += 2;
            new SortThread(arry, l, x, depthLimit, depth+1).start();
            new SortThread(arry, x+1, r, depthLimit, depth+1).start();
        }
        else
        {
            SerialQuickSort.serialQuickSort(arry, l, x);
            SerialQuickSort.serialQuickSort(arry, x+1, r);
        }
    }

    private static class SortThread extends Thread
    {
        private int[] arry;
        private int l;
        private int r;
        private int depthLimit;
        private int depth;


        public SortThread(int[] arry, int l, int r ,int depthLimit, int depth)
        {
            this.arry = arry;
            this.l = l;
            this.r = r;
            this.depthLimit = depthLimit;
            this.depth = depth;
        }

        @Override
        public void run()
        {
            quickSort(arry, l, r, depth, depthLimit);
            aliveProcess--;
        }
    }

    public static void parallelQuickSort(int[] arry, int l, int r)
    {
        if(maxParallelDepth >= 1)
            quickSort(arry, l, r, maxParallelDepth, 1);
        else
            quickSort(arry, l, r, 1, 1);
        while(aliveProcess > 0);
    }
}
