package edu.lab.controller.socket.client.attackers;

import com.sun.source.tree.Scope;
import edu.lab.controller.socket.communication.Request;
import edu.lab.controller.socket.communication.RequestCode;
import edu.lab.controller.socket.communication.requests.RequestFactory;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public abstract class Attacker implements Runnable {
    protected String ip;
    protected Integer port;
    protected final static Integer MAX_POINT = 127;
    protected final static Integer MIN_POINT = 0;

    public Attacker(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * @param receiveAnswer ответ
     * @return тру если убил
     * остальное не надо
     */
    protected boolean checkAnswer(String receiveAnswer) {
        return receiveAnswer.equals("D");
    }

    protected void sendPoint(Integer point, OutputStream os) {
        PrintWriter printWriter = new PrintWriter(os);
        System.out.println("Атакую жертву " + point);
        printWriter.println(point);
        printWriter.flush();
    }

    protected String receiveAnswer(InputStream is) {
        Scanner in = new Scanner(is);
        String answer = "";
        if(in.hasNext()) {
            answer = in.nextLine();
        }
        while (answer == null || answer.equals("")) {
            if(in.hasNext()) {
                answer = in.nextLine();
            }
        }
        return answer;
    }

    protected void sendDead(String ip){

        try (DatagramSocket socket = new DatagramSocket()) {
            Request iKillRequest = RequestFactory.createRequest(RequestCode.IM_KILL, ip);
            byte[] deathBytes = iKillRequest.getPacket();
            DatagramPacket packet = new DatagramPacket(deathBytes, deathBytes.length);
            socket.connect(InetAddress.getByName("127.0.0.1"), 3333);
            socket.send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
