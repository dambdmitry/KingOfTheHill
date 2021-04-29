package edu.lab.server.myAttacker;

import edu.lab.server.Util;
import edu.lab.server.coodinator.communication.Request;
import edu.lab.server.coodinator.communication.RequestCode;
import edu.lab.server.coodinator.communication.Response;
import edu.lab.server.coodinator.communication.requests.RequestFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AttackerCommunication implements Runnable{
    private static final Integer fortPort = 5676;
    private final String host;
    private final Integer port;
    private static String MY_IP;

    public AttackerCommunication(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        while(true){
            try(DatagramSocket socket = new DatagramSocket(fortPort)){
                byte[] data = new byte[10];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);
                byte[] res = packet.getData();
                String ip = Response.getIpDeadFromIgor(res);
                socket.disconnect();
                Request weKill = RequestFactory.createRequest(RequestCode.IM_KILL, ip);
                byte[] request = weKill.getPacket();
                packet = new DatagramPacket(request, request.length);
                DatagramSocket toIgor = new DatagramSocket();
                toIgor.connect(InetAddress.getByName(host), port);
                toIgor.send(packet);
                toIgor.disconnect();
                toIgor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToAttackerList(byte[] list){
        try(DatagramSocket socket = new DatagramSocket()){
            DatagramPacket packet = new DatagramPacket(list, list.length);
            socket.connect(InetAddress.getByName(MY_IP), fortPort);
            socket.send(packet);
            socket.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendToAttackerBadNews(){
        byte[] badNews = new byte[255];
        badNews[0] = 13; //Сообщение о смерти
        try(DatagramSocket socket = new DatagramSocket()){
            DatagramPacket packet = new DatagramPacket(badNews, badNews.length);
            socket.connect(InetAddress.getByName(MY_IP), fortPort);
            socket.send(packet);
            socket.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendOurIp(String myIp) {
        myIp = myIp;
        byte[] ipBytes = Util.getIpBytesForAttacker(myIp);
        try(DatagramSocket socket = new DatagramSocket()){
            DatagramPacket packet = new DatagramPacket(ipBytes, ipBytes.length);
            socket.connect(InetAddress.getByName(myIp), fortPort);
            socket.send(packet);
            socket.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
