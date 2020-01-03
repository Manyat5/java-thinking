package stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author wwy
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        RecursiveTask<Long> task=new SumRecursiveTask(1,20000);
        Long invoke = forkJoinPool.invoke(task);
        System.out.println(invoke);
    }
}
class SumRecursiveTask extends RecursiveTask<Long>{
    private static final long TREEHOLD=2000L;
    private long begin;
    private long end;

    public SumRecursiveTask(long begin, long end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if(end-begin>TREEHOLD){
            long mid=(end+begin)/2;
            SumRecursiveTask taskLeft = new SumRecursiveTask(begin, mid);
            ForkJoinTask<Long> left = taskLeft.fork();
            SumRecursiveTask taskRight = new SumRecursiveTask(mid+1, end);
            ForkJoinTask<Long> right = taskRight.fork();
            return left.join()+right.join();
        }
        long result=0L;
        for (long i = begin; i <end ; i++) {
            result+=i;
        }
        return result;
    }
}