package com.fsm4j.tcp;

public class Base extends SimplifiedTcpState {

	public Base(SimplifiedTcpContext context) {
	    super(context, "0", "Base");
	}


	public Base(SimplifiedTcpContext context, String _id, String _name) {
		super(context, _id, _name);
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		

	}

	public void readMsg(String msg) {

		SimplifiedTcp ctx = context.getOwner();

		super.readMsg(msg);

	}

	public void start() {

		SimplifiedTcp ctx = context.getOwner();

		super.start();

	}

	public void timerExpired() {

		SimplifiedTcp ctx = context.getOwner();

		super.timerExpired();

	}

}

