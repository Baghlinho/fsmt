package com.fsm4j.tcp;

public class TimeWait extends Base {

	public TimeWait(SimplifiedTcpContext context) {
	    super(context, "12", "TimeWait");
	}


	public void __exit__() {

		System.out.println("> TimeWait [exit]: Timer expired, terminating connection...");

	}

	public void __enter__() {

		System.out.println("> TimeWait [enter]: Connection termination in 5 seconds...");
		           try {
		               Thread.sleep(5000);
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

				

			} finally {
			    context.setState(new Closed(context));
			    context.getState().__enter__();
			}

			return;

		}

	}

}

