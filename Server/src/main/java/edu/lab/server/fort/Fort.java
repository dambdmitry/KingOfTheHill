package edu.lab.server.fort;

import edu.lab.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Random;

import static edu.lab.server.Util.getDeathBytes;

public class Fort {
    private Integer port;
    private boolean isAlive;

    private static final Integer defendNumber = new Random(127).nextInt();

    public Fort(Integer port){
        this.port = port;
        isAlive = true;
    }

    public void startFight() {
        try(ServerSocket server = new ServerSocket(port)){
            while (isAlive){
                Socket socket = server.accept();
                String shotResult = getShotResult(socket);
                if (shotResult.equals("D")){
                    isAlive = false;
                    sendDeathRattle(getDeathBytes(socket.getInetAddress().toString()));
                }
                sendResponse(socket, shotResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(Socket socket, String shotResult) {
        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream());) {
            printWriter.println(shotResult);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("send point went wrong: " + e.getMessage());
        }
    }

    private void sendDeathRattle(byte[] deathBytes) {
        try (DatagramSocket socket = new DatagramSocket()){
            DatagramPacket packet = new DatagramPacket(deathBytes, deathBytes.length);
            socket.connect(InetAddress.getByName("127.0.0.1"), 3333);
            socket.send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getShotResult(Socket socket) {
        try(InputStreamReader in = new InputStreamReader(socket.getInputStream())){
            BufferedReader br = new BufferedReader(in);
            String shot = br.readLine();
            return getResult(shot);
        } catch (IOException e) {
            throw new RuntimeException("get shot went wrong: " + e.getMessage());
        }
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
        return new Random(101).nextInt() < 20;
    }
}
