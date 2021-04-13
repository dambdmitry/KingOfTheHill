package edu.lab.controller.socket.client.attackers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Attacker implements Runnable {
    protected Socket attackSocket;
    protected final static Integer MAX_POINT = 127;
    protected final static Integer MIN_POINT = 0;

    public Attacker(String ip, Integer port) {
        try {
            this.attackSocket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param receiveAnswer ответ
     * @return тру если убил
     * остальное не надо
     */
    protected boolean checkAnswer(String receiveAnswer) {
        return receiveAnswer.equals("D");
    }

    protected void sendPoint(Integer point) {
        try (PrintWriter printWriter = new PrintWriter(attackSocket.getOutputStream());) {
            printWriter.println(point);
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("send point went wrong: " + e.getMessage());
        }
    }

    protected String receiveAnswer() {
        try (InputStreamReader in = new InputStreamReader(attackSocket.getInputStream())) {
            BufferedReader bf = new BufferedReader(in);
            String answer = "";
            while (answer == null || answer.equals("")) {
                answer = bf.readLine();
            }
            return answer;
        } catch (IOException e) {

            throw new RuntimeException("receive answer went wrong: " + e.getMessage());
        }
    }
}
