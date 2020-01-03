package concurrency;

import org.junit.Test;

/**
 * @author wwy
 */
public class TestThread {
    @Test
    public void testInterrupt(){
        Thread thread = Thread.currentThread();
        thread.interrupt();
        System.out.println("程序运行结束");
    }
    private void m(){

    }
}
