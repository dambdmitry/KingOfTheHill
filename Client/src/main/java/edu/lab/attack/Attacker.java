package edu.lab.attack;


import edu.lab.attack.connectionAttacker.AttackerConnectionTCP;

import java.io.IOException;

public class Attacker implements Runnable {

    private final static Integer MAX_POINT = Integer.MAX_VALUE;
    private final static Integer MIN_POINT = 0;
    private String ip;
    private Integer port;

    public Attacker(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        long high = MAX_POINT;
        long low = MIN_POINT;
        boolean isEnemyAlive = true;
        while(isEnemyAlive){
            long shot = (low + high) / 2;
            try (AttackerConnectionTCP connection = new AttackerConnectionTCP(ip, port)) {
                connection.sendShot(shot);
                String shotResult = connection.receiveResult();
                switch (shotResult){
                    case "L" : high = (shot - 1); break;
                    case "R" : low = (shot + 1); break;
                    case "D" : {
                        System.out.println("Я УБИЛ " + ip);
                        connection.sendDead();
                        isEnemyAlive = false;
                        break;
                    }
                    case "N" : break;
                    default:
                        System.out.println("Что-то не то мне подсунули: " + shot);
                }
            } catch (IOException e) {
                System.out.println("Не удалось подключиться к " + ip);
                e.printStackTrace();
            }
        }
    }
}
