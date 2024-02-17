package com.fsm4j.tcp;

public class Closing extends Base {

	public Closing(SimplifiedTcpContext context) {
	    super(context, "11", "Closing");
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		System.out.println("> Closing [enter]: Waiting for matching termination request...");

	}

	public void readMsg(String msg) {

		SimplifiedTcp ctx = context.getOwner();

		if("ACK".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.receiveACK();

			} finally {
			    context.setState(new TimeWait(context));
			    context.getState().__enter__();
			}

			return;

		}

		else {

			try {

				

			} finally {
			    context.setState(new Closing(context));
			
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

