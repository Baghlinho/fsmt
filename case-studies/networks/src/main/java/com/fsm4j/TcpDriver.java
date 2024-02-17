package com.fsm4j;

import com.fsm4j.tcp.SimplifiedTcp;

import java.util.Scanner;

public class TcpDriver {
    public static void main(String[] args) {

        SimplifiedTcp simplifiedTcp = new SimplifiedTcp();
        simplifiedTcp.startProtocol();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("INPUT: ");
            String input = scanner.nextLine();
            simplifiedTcp.sendMessage(input);
        }
    }
}
