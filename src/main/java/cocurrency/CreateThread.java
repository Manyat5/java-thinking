package cocurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author wwy
 */
public class CreateThread {
    public void m1() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(() -> "hello world");
        Thread thread = new Thread(futureTask);
        thread.start();
        thread.join();
        if(futureTask.isDone()){
            System.out.println(futureTask.get());
        }else {
            System.out.println("任务未执行完");
        }
    }

    public static void main(String[] args) throws Exception{
        new CreateThread().m1();
    }
}
