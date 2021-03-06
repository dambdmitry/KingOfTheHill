package edu.lab.fortUDP;

import edu.lab.Util;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class FortCommunication implements Runnable{
    private static final Integer fortPort = 5676;
    public List<String> waitAndGetListPlayersFromFort(){
        try(DatagramSocket socket = new DatagramSocket(fortPort)){
            byte[] players = new byte[255];
            DatagramPacket packet = new DatagramPacket(players, players.length);
            socket.receive(packet);
            List<String> playersList = Util.getIpListFromBytes(packet.getData());
            return playersList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Слушаю");
            try (DatagramSocket socket = new DatagramSocket(fortPort)) {
                byte[] msg = new byte[255];
                DatagramPacket packet = new DatagramPacket(msg, msg.length);
                socket.receive(packet);
                System.out.println("Получил что-то");
                if ((packet.getData()[0] + "").equals("13")) {
                    System.out.println("Я умер");
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static void sendImaKiller(String ip){
        try(DatagramSocket socket = new DatagramSocket()){
            byte[] data = ip.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.connect(InetAddress.getLocalHost(), fortPort);
            socket.send(packet);
            System.out.println("Отправил убийство");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveIp() {
        try(DatagramSocket socket = new DatagramSocket(fortPort)){
            byte[] bytes = new byte[4];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            socket.receive(packet);
            String myIp = Util.getIpFromBytes(packet.getData());
            return myIp;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
