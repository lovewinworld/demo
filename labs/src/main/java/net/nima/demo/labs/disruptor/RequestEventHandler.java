package net.nima.demo.labs.disruptor;

import com.lmax.disruptor.EventHandler;

public class RequestEventHandler implements EventHandler<RequestEvent>{

	@Override
	public void onEvent(RequestEvent event, long sequence, boolean endOfBatch)
			throws Exception {
		System.out.println(sequence);
	}

}
