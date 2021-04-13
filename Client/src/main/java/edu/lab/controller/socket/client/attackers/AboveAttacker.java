package edu.lab.controller.socket.client.attackers;

public class AboveAttacker extends Attacker {

    public AboveAttacker(String ip, Integer port) {
        super(ip, port);
    }

    @Override
    public void run() {
        for (int i = MAX_POINT; i > MAX_POINT / 2; i--) {
            sendPoint(i);
            if (checkAnswer(receiveAnswer())) break;
        }
    }

}
