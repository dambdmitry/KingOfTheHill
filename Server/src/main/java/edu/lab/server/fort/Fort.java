package edu.lab.server.fort;

import edu.lab.server.Server;
import edu.lab.server.myAttacker.AttackerCommunication;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;


public class Fort implements Runnable{
    private Integer port;
    private boolean isAlive;

    private final Integer defendNumber;

    public Fort(Integer port) {
        this.port = port;
        isAlive = true;
        this.defendNumber = new Random().nextInt(Integer.MAX_VALUE);
    }

    @Override
    public void run() {
        System.out.println("Защищаю число " + defendNumber);
        System.out.println("Жду нападений");
        try (ServerSocket server = new ServerSocket(port)) {
            while (isAlive) {

                Socket socket = server.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                System.out.println("Кто-то напал");
                String shotResult = getShotResult(in);
                System.out.println("Выстрелил на " + shotResult);
                sendResponse(out, shotResult);
                if (shotResult.equals("D")) {
                    isAlive = false;
                    System.out.println("Я умер");
                    AttackerCommunication.sendToAttackerBadNews();
                    server.close();
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(OutputStream os, String shotResult) {
        PrintWriter printWriter = new PrintWriter(os);
        System.out.println("Пишу ответ");
        printWriter.println(shotResult);
        printWriter.flush();
    }

    private String getShotResult(InputStream is) {
        Scanner in = new Scanner(is);
        String shot = in.nextLine();
        return getResult(shot);
    }

    private String getResult(String shot) {
        Integer kick = Integer.parseInt(shot);
        if (kick.equals(defendNumber)) return "D";
        if (isNeedToAnswer()) {
            return kick < defendNumber ? "R" : "L";
        }
        return "N";
    }

    private boolean isNeedToAnswer() {
        return new Random().nextInt(101) + 1 < 20;
    }


}
