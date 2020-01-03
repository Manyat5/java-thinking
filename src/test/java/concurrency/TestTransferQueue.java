package concurrency;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @author wwy
 */
public class TestTransferQueue {
    static LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
    public static void main(String[] args) {
        //创建生产者线程
        for (int i = 0; i < 1; i++) {
            new Thread(()->{
                while(true){
                    try {
                        queue.transfer("馒头");
                        System.out.println(Thread.currentThread().getName()+"线程生产一个馒头");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        //创建消费者线程
        for (int i = 0; i < 1; i++) {
            new Thread(()->{
                while(true){
                    try {
                        String mantou = queue.take();
                        System.out.println(Thread.currentThread().getName()+"消费"+mantou);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}


