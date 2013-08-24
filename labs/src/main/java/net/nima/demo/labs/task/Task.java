package net.nima.demo.labs.task;

/**
 * ������
 * 
 * @author Kevin
 */
public class Task implements Runnable {

	private TaskContext context;
	private TaskService service;
	
	public Task(TaskContext context, TaskService service){
		this.context = context;
		this.service = service;
	}
	
	@Override
	public void run() {
		service.execute(context);
	}
	
}
