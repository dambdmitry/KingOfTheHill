package edu.lab.attack.connectionAttacker;

import edu.lab.fortUDP.FortCommunication;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class AttackerConnectionTCP implements Closeable {
    private final Socket connection;
    private final InputStream input;
    private final OutputStream output;
    private final String ip;

    public AttackerConnectionTCP(String ip, Integer port) throws IOException {
        this.connection = new Socket(ip, port);
        this.input = this.connection.getInputStream();
        this.output = this.connection.getOutputStream();
        this.ip = ip;
    }

    public void sendShot(long shot) {
        PrintWriter printWriter = new PrintWriter(output);
        System.out.println("Атакую жертву " + shot);
        printWriter.println(shot);
        printWriter.flush();

    }

    public String receiveResult() {
        String answer = "";
        Scanner in = new Scanner(input);
        if (in.hasNext()) {
            answer = in.nextLine();
        }
        while (answer == null || answer.equals("")) {
            if (in.hasNext()) {
                answer = in.nextLine();
            }
        }
        return answer;
    }

    public void sendDead() {
        FortCommunication.sendImaKiller(ip);
    }

    @Override
    public void close() throws IOException {
        this.input.close();
        this.output.close();
        this.connection.close();
    }

}
