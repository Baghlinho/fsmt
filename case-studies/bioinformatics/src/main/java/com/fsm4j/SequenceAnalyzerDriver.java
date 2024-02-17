package com.fsm4j;

import com.fsm4j.analyzer.SequenceAnalyzer;

public class SequenceAnalyzerDriver {

    public static void main(String[] args) {

        analyzeSequence("CTACTAGCATAGCC");
        analyzeSequence("CCTACCGTAAATCA");

    }

    static void analyzeSequence(String sequence) {
        SequenceAnalyzer analyzer = new SequenceAnalyzer();
        System.out.println(sequence);
        for(char nucleotide: sequence.toCharArray()) {
            analyzer.match(nucleotide);
        }
    }
}