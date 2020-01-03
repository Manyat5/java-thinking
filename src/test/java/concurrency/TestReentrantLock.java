package concurrency;

import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wwy
 */
@Data
public class TestReentrantLock {
    ReentrantLock lock=new ReentrantLock();
    public void reentrantLockHello(){
        lock.lock();
        System.out.println("reentrantLockHello");
    }

    public synchronized void synchronizedHello(){
        System.out.println("synchronizedHello");
    }

    public static void main(String[] args) throws InterruptedException {
        TestReentrantLock test = new TestReentrantLock();
        test.reentrantLockHello();
        new Thread(()->test.reentrantLockHello()).start();
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
