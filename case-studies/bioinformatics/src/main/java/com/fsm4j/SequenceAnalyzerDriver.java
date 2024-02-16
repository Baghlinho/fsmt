package com.fsm4j;

import com.fsm4j.analyzer.SequenceAnalyzer;

public class SequenceAnalyzerDriver {

    public static void main(String[] args) {
        SequenceAnalyzer analyzer = new SequenceAnalyzer();

        String sequence = "CCTACTAGCATAGC";
        System.out.println(sequence);
        for(char nucleotide: sequence.toCharArray()) {
            analyzer.match(nucleotide);
        }
        sequence = "CCTACCGTAAATCA";
        System.out.println(sequence);
        for(char nucleotide: sequence.toCharArray()) {
            analyzer.match(nucleotide);
        }
    }
}