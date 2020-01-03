package cocurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 */
public class MyThreadPoolExcutor {
	private int coreThreadSize;
	private int maxThreadSize;
	private AtomicInteger currentThreadSize;
	private BlockingQueue<Runnable> queue;
	
	/**
	 * �����̳߳ط���
	 * @param coreThread
	 * @param maxThread
	 * @param queue
	 */
	public MyThreadPoolExcutor(int coreThread,int maxThread,BlockingQueue<Runnable> queue) {
		this.coreThreadSize=coreThread;
		this.maxThreadSize=maxThread;
		this.queue=queue;
	}
	/**
	 * �̳߳صĺ��ķ���
	 * @param task
	 * @throws Exception 
	 */
	public void excute(Runnable task) throws Exception {
		if(task==null) throw new NullPointerException();
		
		//����߳���С�ں����߳������򴴽��߳�x
		if(currentThreadSize.get()<coreThreadSize) {
			new Worker().start();//����һ����һ����̣߳�����߳�ѭ��ִ������
			currentThreadSize.incrementAndGet();
		}
		if(queue.offer(task)) {//�ж϶����Ƿ��������������ӳɹ����򷵻�
			return ;
		}else if(currentThreadSize.getAndIncrement()<maxThreadSize) {
			new TempThread(task).start();//�����������������߳�����������
		}else {//���ڻ��������߳������ܾ�
			throw new Exception("�߳����������ܾ�ִ��");
		}
			
	}
	/**
	 * һ��������̣߳���Զ����رգ����ɺ����̳߳صĹؼ�
	 * @author Administrator
	 *
	 */
	public class Worker extends Thread{
		@Override
		public void run() {
			Runnable task=null;
			try {
				while(task!=null||(task=queue.take())!=null) {
					task.run();
					task=null;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class TempThread extends Thread{
		public TempThread(Runnable task) {
			super(task);
		}
		
		@Override
		public void run() {
			super.run();
			currentThreadSize.decrementAndGet();
		}
	}
}
