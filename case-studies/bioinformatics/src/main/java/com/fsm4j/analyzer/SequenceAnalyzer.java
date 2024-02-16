package com.fsm4j.analyzer;

public class SequenceAnalyzer implements SequenceAnalyzerActions {

	private SequenceAnalyzerContext context;


	public SequenceAnalyzer() {
	    this.context = new SequenceAnalyzerContext(this);
	}

	public void found() {
		System.out.println("found sequence!");
	}

	public void match(char nucleotide) {
		context.match(nucleotide);
	}
}

