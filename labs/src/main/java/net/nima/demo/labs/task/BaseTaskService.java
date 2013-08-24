package net.nima.demo.labs.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ����������
 * 
 * @author Kevin
 */
public abstract class BaseTaskService implements TaskService {

	protected Logger getLogger() {
		return LoggerFactory.getLogger(this.getClass());
	}
	
	public abstract void execute(TaskContext context);

}
