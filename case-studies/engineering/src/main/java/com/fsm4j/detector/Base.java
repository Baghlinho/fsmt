package com.fsm4j.detector;

public class Base extends SequenceDetectorState {

	public Base(SequenceDetectorContext context) {
	    super(context, "0", "Base");
	}


	public Base(SequenceDetectorContext context, String _id, String _name) {
		super(context, _id, _name);
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		

	}

	public void readBit(int b) {

		SequenceDetector ctx = context.getOwner();

		super.readBit(b);

	}

}

