package com.fsm4j.tcp;

public interface SimplifiedTcpActions {

	void receiveSYN();

	void receiveACK();

	void receiveFIN();

	void sendSYN();

	void sendACK();

	void sendFIN();

}

