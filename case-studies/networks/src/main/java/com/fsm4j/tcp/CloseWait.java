package com.fsm4j.tcp;

public class CloseWait extends Base {

	public CloseWait(SimplifiedTcpContext context) {
	    super(context, "7", "CloseWait");
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		System.out.println("> CloseWait [enter]: Received termination request, closing application...");
		           try {
		               Thread.sleep(3000);
		           } catch (InterruptedException e) {
		               throw new RuntimeException(e);
		           }
		           timerExpired();

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

		{

			context.getState().__exit__();

			try {

				ctx.sendFIN();

			} finally {
			    context.setState(new LastAck(context));
			    context.getState().__enter__();
			}

			return;

		}

	}

}

