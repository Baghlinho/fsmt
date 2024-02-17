package com.fsm4j.tcp;

public class FINWait2 extends Base {

	public FINWait2(SimplifiedTcpContext context) {
	    super(context, "10", "FINWait2");
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		System.out.println("> FINWait2 [enter]: Waiting for matching termination request...");

	}

	public void readMsg(String msg) {

		SimplifiedTcp ctx = context.getOwner();

		if("FIN".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.receiveFIN();
				            ctx.sendACK();

			} finally {
			    context.setState(new TimeWait(context));
			    context.getState().__enter__();
			}

			return;

		}

		else {

			try {

				

			} finally {
			    context.setState(new FINWait2(context));
			
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

