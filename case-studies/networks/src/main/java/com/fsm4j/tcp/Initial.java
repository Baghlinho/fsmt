package com.fsm4j.tcp;

public class Initial extends Base {

	public Initial(SimplifiedTcpContext context) {
	    super(context, "1", "Initial");
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

		{

			context.getState().__exit__();

			try {

				System.out.println("(Note: Use the FSM diagram to track which state you and the device are in, and to see which messages you should exchange)");

			} finally {
			    context.setState(new Closed(context));
			    context.getState().__enter__();
			}

			return;

		}

	}

	public void timerExpired() {

		SimplifiedTcp ctx = context.getOwner();

		super.timerExpired();

	}

}

