package com.fsm4j.tcp;

import com.fsm4j.AbstractFsmContext;

public class SimplifiedTcpContext extends AbstractFsmContext {
	protected SimplifiedTcp owner;


	public SimplifiedTcpContext(SimplifiedTcp owner) {
	    this.owner = owner;
	    setState(new Initial(this));
	}

	public void readMsg(String msg) {

		((SimplifiedTcpState) getState()).readMsg(msg);

	}

	public void start() {

		((SimplifiedTcpState) getState()).start();

	}

	public void timerExpired() {

		((SimplifiedTcpState) getState()).timerExpired();

	}


	public SimplifiedTcp getOwner() {
	    return this.owner;
	}

}

