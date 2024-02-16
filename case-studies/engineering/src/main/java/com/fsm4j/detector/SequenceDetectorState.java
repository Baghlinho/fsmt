package com.fsm4j.detector;

import com.fsm4j.AbstractState;

public abstract class SequenceDetectorState extends AbstractState {

	protected SequenceDetectorContext context;


	protected SequenceDetectorState(SequenceDetectorContext context, String _id, String _name) {
	    super(_id, _name);
	    this.context = context;
	}


	protected void readBit(int b) {

		Base();

	}

	protected void Base() {
	    throw new RuntimeException("Undefined transtion");
	}

}

