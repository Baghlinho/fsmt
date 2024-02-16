package com.fsm4j.detector;

public class SequenceDetector implements SequenceDetectorActions {

	private SequenceDetectorContext context;


	public SequenceDetector() {
	    this.context = new SequenceDetectorContext(this);
	}


	public void match() {

		System.out.print(1);

	}

	public void mismatch() {

		System.out.print(0);

	}

	public void startWorking(String sequence) {

		System.out.printf("\nInput:  %s\n", sequence);
		System.out.print("Output: ");
		for (int i = 0; i < sequence.length(); i++) {
			int b = (int) sequence.charAt(i) - '0';
			context.readBit(b);
		}
		System.out.println();

	}

}

