package com.fsm4j.tcp;

public class SynReceived extends Base {

	public SynReceived(SimplifiedTcpContext context) {
	    super(context, "5", "SynReceived");
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		System.out.println("> SynReceived [enter]: Received connection request");

	}

	public void readMsg(String msg) {

		SimplifiedTcp ctx = context.getOwner();

		if("ACK".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.receiveACK();

			} finally {
			    context.setState(new Established(context));
			    context.getState().__enter__();
			}

			return;

		}

		else {

			try {

				

			} finally {
			    context.setState(new SynReceived(context));
			
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

