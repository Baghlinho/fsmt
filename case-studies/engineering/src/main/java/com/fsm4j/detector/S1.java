package com.fsm4j.detector;

public class S1 extends Base {

	public S1(SequenceDetectorContext context) {
	    super(context, "2", "S1");
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
			    context.setState(new S2(context));
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

