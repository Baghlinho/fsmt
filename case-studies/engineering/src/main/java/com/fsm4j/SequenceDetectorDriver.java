package com.fsm4j;

import com.fsm4j.detector.SequenceDetector;

public class SequenceDetectorDriver {
    public static void main(String[] args) {
        SequenceDetector detector = new SequenceDetector();
        detector.startWorking("11010101011");
    }
}
