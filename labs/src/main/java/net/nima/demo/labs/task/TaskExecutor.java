package net.nima.demo.labs.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ����ִ����
 * 
 * @author Kevin
 */
public class TaskExecutor {
	
	/**
	 * �������
	 */
	private static BlockingQueue<Runnable> workers = new LinkedBlockingQueue<Runnable>(3000);
	
	/**
	 * ��������
	 */
	private static final RejectedExecutionHandler defaultHandler = new DiscardOldestPolicy();
	
	/**
	 * �߳�ִ����
	 */
	private static ExecutorService service = new ThreadPoolExecutor(3, 3, 0, TimeUnit.MILLISECONDS, workers, defaultHandler);
	
	/**
	 * ���һ����������ִ������
	 * 
	 * @param task
	 * @return
	 */
	public static boolean addTask(Runnable task){
		return workers.offer(task);
	}
	
	/**
	 * ��ʼ������ִ����
	 */
	public void init() {
		Thread daemon = new Thread(new ServiceDaemon());
		daemon.setName("pix-task-watcher");
		daemon.start();
	}
	
	/**
	 * ������������ķ���
	 */
	private void service(){
		while(true) {
			try {
				service.execute(workers.take());
			} catch (InterruptedException e) {
				// ignore ��μ�أ�
			}
		}
	}

	/**
	 * ����һ���̼߳��
	 */
	class ServiceDaemon implements Runnable {
		@Override
		public void run() {
			service();
		}
	}
	
	/**
     * �̹߳���
     */
    static class PoolThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        PoolThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() :
                                 Thread.currentThread().getThreadGroup();
            namePrefix = "pix-task-pool-" +
                          poolNumber.getAndIncrement() +
                         "-t-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()){
            	t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY){
            	t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
	
}
