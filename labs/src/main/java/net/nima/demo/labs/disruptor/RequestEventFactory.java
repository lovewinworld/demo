package net.nima.demo.labs.disruptor;

import com.lmax.disruptor.EventFactory;

public class RequestEventFactory implements EventFactory<RequestEvent>{

	@Override
	public RequestEvent newInstance() {
		return new RequestEvent();
	}

}
