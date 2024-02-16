package com.fsm4j.detector;

public class S0 extends Base {

	public S0(SequenceDetectorContext context) {
	    super(context, "1", "S0");
	}


	public void __exit__() {

		

	}

	public void __enter__() {

		

	}

	public void readBit(int b) {

		SequenceDetector ctx = context.getOwner();

		if(b == 0) {

			context.getState().__exit__();

			try {

				ctx.mismatch();

			} finally {
			    context.setState(new S0(context));
			    context.getState().__enter__();
			}

			return;

		}

		else if(b == 1) {

			context.getState().__exit__();

			try {

				ctx.mismatch();

			} finally {
			    context.setState(new S1(context));
			    context.getState().__enter__();
			}

			return;

		}

		super.readBit(b);

	}

}

