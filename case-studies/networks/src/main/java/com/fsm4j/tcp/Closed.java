package com.fsm4j.tcp;

public class Closed extends Base {

	public Closed(SimplifiedTcpContext context) {
	    super(context, "2", "Closed");
	}


	public void __exit__() {

		System.out.println("--------------CONTINUE TCP--------------");
		           System.out.println("*** Choose message type(s) to send comma separated [SYN, ACK, FIN]");

	}

	public void __enter__() {

		System.out.println("> Closed [enter]: Connection closed");
		           System.out.println("--------------PAUSE TCP--------------");
		           System.out.println("*** Choose device's connection sequence type [initiator, responder]");
		           System.out.println("(Note: You will be the device with the other sequence type)");

	}

	public void readMsg(String msg) {

		SimplifiedTcp ctx = context.getOwner();

		if("initiator".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.sendSYN();

			} finally {
			    context.setState(new SynSent(context));
			    context.getState().__enter__();
			}

			return;

		}

		else if("responder".equals(msg)) {

			context.getState().__exit__();

			try {

				

			} finally {
			    context.setState(new Listen(context));
			    context.getState().__enter__();
			}

			return;

		}

		else {

			try {

				

			} finally {
			    context.setState(new Closed(context));
			
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

