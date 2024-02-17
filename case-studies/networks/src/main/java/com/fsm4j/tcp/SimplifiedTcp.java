package com.fsm4j.tcp;

public class SimplifiedTcp implements SimplifiedTcpActions {

	private SimplifiedTcpContext context;


	public SimplifiedTcp() {
	    this.context = new SimplifiedTcpContext(this);
	}


	public void receiveSYN() {

		System.out.println("- received SYN message");

	}

	public void receiveACK() {

		System.out.println("- received ACK message");

	}

	public void receiveFIN() {

		System.out.println("- received FIN message");

	}

	public void sendSYN() {

		System.out.println("- sent SYN message");

	}

	public void sendACK() {

		System.out.println("- sent ACK message");

	}

	public void sendFIN() {

		System.out.println("- sent FIN message");

	}

	public void startProtocol() {
		context.start();
	}

	public void sendMessage(String msg) {
		context.readMsg(msg);
	}

}

