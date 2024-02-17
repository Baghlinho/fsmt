package com.fsm4j.tcp;

public class Listen extends Base {

	public Listen(SimplifiedTcpContext context) {
	    super(context, "4", "Listen");
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		System.out.println("> Listen [enter]: Listening for connection requests...");

	}

	public void readMsg(String msg) {

		SimplifiedTcp ctx = context.getOwner();

		if("SYN".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.sendSYN();
				            ctx.sendACK();

			} finally {
			    context.setState(new SynReceived(context));
			    context.getState().__enter__();
			}

			return;

		}

		else {

			try {

				

			} finally {
			    context.setState(new Listen(context));
			
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

