package com.fsm4j.tcp;

public class LastAck extends Base {

	public LastAck(SimplifiedTcpContext context) {
	    super(context, "8", "LastAck");
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		System.out.println("> LastAck [enter]: Waiting for last acknowledgement...");

	}

	public void readMsg(String msg) {

		SimplifiedTcp ctx = context.getOwner();

		if("ACK".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.receiveACK();

			} finally {
			    context.setState(new Closed(context));
			    context.getState().__enter__();
			}

			return;

		}

		else {

			try {

				

			} finally {
			    context.setState(new LastAck(context));
			
			}

			return;

		}

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

