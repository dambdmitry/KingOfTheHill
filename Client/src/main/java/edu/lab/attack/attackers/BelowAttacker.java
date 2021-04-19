package edu.lab.attack.attackers;

import edu.lab.attack.Attacker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BelowAttacker extends Attacker {

    public BelowAttacker(String ip, Integer port) {
        super(ip, port);

    }

    @Override
    public void run() {
        for (int i = MIN_POINT; i <= MAX_POINT / 2 + 1; i++) {
            try(Socket socket = new Socket(ip, port)){
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                sendPoint(i, out);
                if (checkAnswer(receiveAnswer(in))){
                    sendDead(ip);
                    break;
                }
            } catch(IOException ignored){
                System.out.println("ПРОБЛЕМА У ПСА");
            }
        }
    }
}
