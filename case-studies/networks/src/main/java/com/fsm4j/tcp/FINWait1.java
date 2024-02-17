package com.fsm4j.tcp;

public class FINWait1 extends Base {

	public FINWait1(SimplifiedTcpContext context) {
	    super(context, "9", "FINWait1");
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		System.out.println("> FINWait1 [enter]: Sent termination request");

	}

	public void readMsg(String msg) {

		SimplifiedTcp ctx = context.getOwner();

		if("FIN".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.receiveFIN();
				            ctx.sendACK();

			} finally {
			    context.setState(new Closing(context));
			    context.getState().__enter__();
			}

			return;

		}

		else if("ACK".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.receiveACK();

			} finally {
			    context.setState(new FINWait2(context));
			    context.getState().__enter__();
			}

			return;

		}

		else {

			try {

				

			} finally {
			    context.setState(new FINWait1(context));
			
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

