package net.nima.demo.labs.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;

public class DisruptorHolder {

	
	/**
	 * 执行器
	 */
	private static ExecutorService executor = Executors.newFixedThreadPool(2);
	
	/**
	 * RingBuffer,其体积最好是2的N次方
	 */
	private static int RINGBUFER_SIZE = 1024;
	
	/*-----------------------------------------------------------*/
	
	/**
	 * 事件工厂
	 */
	private static EventFactory<RequestEvent> eventFactory = new RequestEventFactory();
	/**
	 * 
	 */
	private static RingBuffer<RequestEvent> ringBuffer = new RingBuffer<RequestEvent>(eventFactory, RINGBUFER_SIZE);
	
	private static EventHandler<RequestEvent> handler = new RequestEventHandler();
	
	private static SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
	
	private static BatchEventProcessor<RequestEvent> processor = new BatchEventProcessor<RequestEvent>(ringBuffer, sequenceBarrier, handler);
	
	private static void produce(){
		ringBuffer.setGatingSequences(new Sequence(RINGBUFER_SIZE));
		long sequenceId = ringBuffer.next();
		RequestEvent event = ringBuffer.get(sequenceId);
		event.setAppId("1000");
		ringBuffer.publish(sequenceId);
	}
	
	public static void main(String[] args) {
		executor.submit(processor);
		for(int i =1; i < RINGBUFER_SIZE; i++){
			produce();
		}
	}
	
}
