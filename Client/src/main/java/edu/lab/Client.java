package edu.lab;

import edu.lab.controller.socket.ClientForIgor;
import edu.lab.controller.socket.ServerForIgor;
import edu.lab.controller.socket.client.ClientForIgorImpl;
import edu.lab.controller.socket.client.attackers.AboveAttacker;
import edu.lab.controller.socket.client.attackers.BelowAttacker;
import edu.lab.controller.socket.server.ServerForIgorImpl;

import java.net.SocketException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) throws SocketException {
        ClientForIgor client = new ClientForIgorImpl();
        ServerForIgor server = new ServerForIgorImpl();
        final Integer port = 2222;
        client.registry("valid cd;4444;Mitin Dmitry");
        server.receiveAgreement();
        server.receiveReady();
        server.waitStartGame();
        List<String> sacrifices = client.getActualListOfPlayers();//Далее замногопоточить дибилов
        int amount = sacrifices.size();
        ExecutorService exec = Executors.newFixedThreadPool(amount * 2);

        for(String ip : sacrifices){
            exec.execute(new AboveAttacker(ip, port));
            exec.execute(new BelowAttacker(ip, port));
        }
    }
}
