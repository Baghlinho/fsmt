package com.fsm4j.tcp;

public class SynSent extends Base {

	public SynSent(SimplifiedTcpContext context) {
	    super(context, "3", "SynSent");
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		System.out.println("> SynSent [enter]: Sent connection request");

	}

	public void readMsg(String msg) {

		SimplifiedTcp ctx = context.getOwner();

		if("SYN".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.receiveSYN();
				            ctx.sendACK();

			} finally {
			    context.setState(new SynReceived(context));
			    context.getState().__enter__();
			}

			return;

		}

		else if("SYN,ACK".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.receiveSYN();
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
			    context.setState(new SynSent(context));
			
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

