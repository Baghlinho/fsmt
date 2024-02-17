package com.fsm4j.tcp;

public class Established extends Base {

	public Established(SimplifiedTcpContext context) {
	    super(context, "6", "Established");
	}


	public void __exit__() {

		System.out.println("--------------CONTINUE TCP--------------");
		            System.out.println("*** Choose message type(s) to send comma separated [SYN, ACK, FIN]");

	}

	public void __enter__() {

		System.out.println("> Established [enter]: Connection established, data can be exchanged");
		            System.out.println("--------------PAUSE TCP--------------");
		            System.out.println("*** 1) Exchange data, the device will display the content, OR");
		            System.out.println("*** 2) Choose device's termination sequence type [initiator, responder]");
		            System.out.println("(Note: You will be the device with the other sequence type)");

	}

	public void readMsg(String msg) {

		SimplifiedTcp ctx = context.getOwner();

		if("initiator".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.sendFIN();

			} finally {
			    context.setState(new FINWait1(context));
			    context.getState().__enter__();
			}

			return;

		}

		else if("responder".equals(msg)) {

			context.getState().__exit__();

			try {

				ctx.receiveFIN();
				            ctx.sendACK();

			} finally {
			    context.setState(new CloseWait(context));
			    context.getState().__enter__();
			}

			return;

		}

		else {

			try {

				System.out.println("\"\"\"\n" + msg + "\n\"\"\"");

			} finally {
			    context.setState(new Established(context));
			
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

