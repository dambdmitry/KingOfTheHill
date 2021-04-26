package edu.lab.attack.attackers;

import edu.lab.attack.Attacker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class AboveAttacker extends Attacker {

    public AboveAttacker(String ip, Integer port) {
        super(ip, port);
    }

    @Override
    public void run() {
        int high = MAX_POINT;
        int low = MIN_POINT;
        boolean isEnemyAlive = true;
        while(isEnemyAlive){
            long shot = (low + high) / 2;
            try (Socket socket = new Socket(ip, port)) {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                sendPoint((int) shot, out);
                String result = receiveAnswer(in);
                switch (result){
                    case "L" : high = (int) (shot - 1); break;
                    case "R" : low = (int) (shot + 1); break;
                    case "D" : {
                        sendDead(ip);
                        isEnemyAlive = false;
                        return; //Лучше ретурнуть
                    }
                    case "N" : continue;
                    default:
                        System.out.println("Что-то не то мне подсунули: " + shot);
                }
            } catch (IOException e) {
                System.out.println("Похоже оппонент " + ip + " умер не от моей руки");
                System.out.println(e.getMessage());
                return;
            }
        }
    }

}
