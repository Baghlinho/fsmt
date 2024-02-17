package com.fsm4j.tcp;

import com.fsm4j.AbstractState;

public abstract class SimplifiedTcpState extends AbstractState {

	protected SimplifiedTcpContext context;


	protected SimplifiedTcpState(SimplifiedTcpContext context, String _id, String _name) {
	    super(_id, _name);
	    this.context = context;
	}


	protected void readMsg(String msg) {

		Base();

	}

	protected void start() {

		Base();

	}

	protected void timerExpired() {

		Base();

	}

	protected void Base() {
	    throw new RuntimeException("Undefined transtion");
	}

}

