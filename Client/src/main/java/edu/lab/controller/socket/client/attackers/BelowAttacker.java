package edu.lab.controller.socket.client.attackers;

public class BelowAttacker extends Attacker {

    public BelowAttacker(String ip, Integer port) {
        super(ip, port);

    }

    @Override
    public void run() {
        for (int i = MIN_POINT; i <= MAX_POINT / 2 + 1; i++) {
            sendPoint(i);
            if (checkAnswer(receiveAnswer())) break;
        }
    }
}
