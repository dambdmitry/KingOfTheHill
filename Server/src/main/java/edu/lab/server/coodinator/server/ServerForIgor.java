package edu.lab.server.coodinator.server;

import edu.lab.server.coodinator.communication.Request;
import edu.lab.server.coodinator.communication.RequestCode;
import edu.lab.server.coodinator.communication.Response;
import edu.lab.server.coodinator.communication.requests.RequestFactory;
import edu.lab.server.myAttacker.AttackerCommunication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Класс для регистрации и мониторинга своей смерти
 * Имеет {единножды вызываемые} методы, которые вызываются один раз
 * Так же имеет метод run, который мониторит мою сметрь
 * КЛАСС ВЗАИМОДЕЙСТВУЕТ ТОЛЬКО С ЗАПРОСАМИ, КОТОРЫЙ ИНИЦИИРУЕТ СЕРВЕР ИГОРЯ
 * ЗАПРОСЫ ПО МОЕЙ ИНИЦИАЦИИ ОБРАБАТЫВАЮТСЯ В КЛАССЕ {@link edu.lab.server.coodinator.client.ClientForIgorImpl}
 */
public class ServerForIgor implements Runnable {
    private final int MAX_SIZE_BUFFER = 255;
    private final Integer port;
    private final Integer portToSend = 3333;
    private final String host;
    private String myIp = "127.0.0.1"; // !!!перед вызовом run вызвать {@method setMyIp(String myIp)} (костыль)
    public ServerForIgor(Integer port, String host) {
        this.port = port;
        this.host = host;
    }

    /**
     * Мониторит запрос с кодом 3, который убивает прогу, если ip будет мой
     */
    @Override
    public void run() {
        while (true){
            try(DatagramSocket server = new DatagramSocket(port)){
                byte[] buffer = getBuffer();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                System.out.println("Я слушаю свою смерть");
                server.receive(packet);
                byte[] pack = packet.getData();
                String code = pack[0] + "";
                if(code.equals("3")){
                    String deadIp = Response.getIdDead(pack);
                    if(deadIp.equals(myIp)){
                        System.out.println("Я умер");
                        AttackerCommunication.sendToAttackerBadNews();
                        System.exit(0);
                    }
                }
                if(code.equals("5")){
                    DatagramSocket response = new DatagramSocket();
                    //Request imReadyRequest = RequestFactory.createRequest(RequestCode.IM_READY);
                    //assert imReadyRequest != null;
                    byte[] request = new byte[255];
                    byte imAlive = 5;
                    request[0] = imAlive;
                    packet = new DatagramPacket(request, request.length);
                    response.connect(InetAddress.getByName(host), 3333);
                    response.send(packet);
                    response.close();
                }
            } catch (IOException e) {
                run();
            }
        }
    }

    public void setMyIp(String myIp) {
        this.myIp = myIp;
    }

    /**
     * Метод ждет запрос 0 о готовности к игре
     * После высылает в ответ запрос с кодом 2
     */
    public void ready(){
        try(DatagramSocket server = new DatagramSocket(port)) {
            byte[] buffer = getBuffer();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            server.receive(packet);
            String code = packet.getData()[0] + "";
            System.out.println("Получен код " + code);
            while(!code.equals("0")){
                server.receive(packet);
                code = packet.getData()[0] + "";
                System.out.println("Получен код " + code);
            }
            server.disconnect();
            //server.close();
            DatagramSocket response = new DatagramSocket();
            Request imReadyRequest = RequestFactory.createRequest(RequestCode.IM_READY);
            assert imReadyRequest != null;
            byte[] request = imReadyRequest.getPacket();
            packet = new DatagramPacket(request, request.length);
            response.connect(InetAddress.getByName(host), portToSend);
            response.send(packet);
            response.close();
        } catch (IOException e) {
            System.out.println("Повторное ожидание готовности" + e.getMessage());
            e.printStackTrace();
            //ready();
        }
    }

    /**
     * Метод просто ждет пока не получит запрос с кодом 1 (начало игры)
     */
    public void waitStartGame() {
        try(DatagramSocket server = new DatagramSocket(port)){
            byte[] buffer = getBuffer();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            server.receive(packet);
            String code = packet.getData()[0] + "";
            while(!code.equals("1")){
                server.receive(packet);
                code = packet.getData()[0] + "";
            }
        } catch (IOException e) {
            System.out.println("Повторное ожидание игры " + e.getMessage());
            e.printStackTrace();
        }
    }


    private byte[] getBuffer(){
        return new byte[MAX_SIZE_BUFFER];
    }


}
