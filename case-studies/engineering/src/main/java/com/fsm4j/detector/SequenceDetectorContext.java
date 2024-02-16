package com.fsm4j.detector;

import com.fsm4j.AbstractFsmContext;

public class SequenceDetectorContext extends AbstractFsmContext {
	protected SequenceDetector owner;


	public SequenceDetectorContext(SequenceDetector owner) {
	    this.owner = owner;
	    setState(new S0(this));
	}

	public void readBit(int b) {

		((SequenceDetectorState) getState()).readBit(b);

	}


	public SequenceDetector getOwner() {
	    return this.owner;
	}

}

