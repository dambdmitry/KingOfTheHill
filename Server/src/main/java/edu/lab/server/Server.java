package edu.lab.server;

import edu.lab.server.fort.Fort;

public class Server {
    public static void main(String[] args) {
        Fort fort = new Fort(2222);
        fort.startFight();
    }
}
