package net.nima.demo.labs.task;

import java.util.HashMap;

/**
 * 任务上下文
 * 
 * @author Kevin
 */
public class TaskContext implements java.io.Serializable {

	private static final long serialVersionUID = 3871203755432263971L;
	
	private HashMap<Object, Object> data = new HashMap<Object, Object>();
	
	/**
     * Put the specified value into the context's data map with the given key.
     * Possibly useful for sharing data between listeners and jobs.
     *
     * <p>NOTE: this data is volatile - it is lost after the job execution
     * completes, and all TriggerListeners and JobListeners have been 
     * notified.</p> 
     *  
     * @param key
     * @param value
     */
    public void put(Object key, Object value) {
        data.put(key, value);
    }
    
    /**
     * Get the value with the given key from the context's data map.
     * 
     * @param key
     */
    public Object get(Object key) {
        return data.get(key);
    }

}
