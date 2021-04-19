package edu.lab.server.fort;

import edu.lab.server.Server;

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
        this.defendNumber = new Random().nextInt(127);
    }

    @Override
    public void run() {
        System.out.println("Защищаю число " + defendNumber);
        try (ServerSocket server = new ServerSocket(port)) {
            while (isAlive) {
                System.out.println("Жду нападений");
                Socket socket = server.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                System.out.println("Кто-то напал");
                String shotResult = getShotResult(in);
                System.out.println("Выстрелил на " + shotResult);
                sendResponse(out, shotResult);
                System.out.println("Отправил ответ");
                if (shotResult.equals("D")) {
                    isAlive = false;
                    System.out.println("Я умер");
                } else {
                    System.out.println(shotResult + " мимо");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void startFight() {

    }

    private void sendResponse(OutputStream os, String shotResult) {
        PrintWriter printWriter = new PrintWriter(os);
        System.out.println("Пишу ответ");
        printWriter.println(shotResult);
        printWriter.flush();
    }

    /**
     * Это задача переложена на оппонента
     *
     * @param deathBytes
     */
    private void sendDeathRattle(byte[] deathBytes) {
        try (DatagramSocket socket = new DatagramSocket()) {
            DatagramPacket packet = new DatagramPacket(deathBytes, deathBytes.length);
            socket.connect(InetAddress.getByName("127.0.0.1"), 3333);
            socket.send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            return kick < defendNumber ? "L" : "R";
        }
        return "N";
    }

    private boolean isNeedToAnswer() {
        return new Random().nextInt(101) < 20;
    }


}
