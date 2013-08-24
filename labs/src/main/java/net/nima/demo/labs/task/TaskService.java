package net.nima.demo.labs.task;

/**
 * 任务服务接口
 * 
 * @author Kevin
 */
public interface TaskService {
	
	/**
	 * 执行任务
	 * 
	 * @param context
	 */
	void execute(TaskContext context);

}
