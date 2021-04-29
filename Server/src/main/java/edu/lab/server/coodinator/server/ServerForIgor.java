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

    private static final String READY_CODE = "0";
    private static final String START_CODE = "1";
    private static final String DEAD_CODE = "3";
    private static final String CHECK_CODE = "5";

    private final Integer portToSend = 3333;
    private final Integer port;
    private final String host;

    private String myIp = "127.0.0.1"; // !!!перед вызовом run вызвать {@method setMyIp(String myIp)} (костыль)
    public ServerForIgor(Integer port, String host) {
        this.port = port;
        this.host = host;
    }

    /**
     * Мониторит запрос с кодом 3, который убивает прогу, если ip будет мой
     * И код 5 о активности
     */
    @Override
    public void run() {
        while (true){
            try(DatagramSocket server = new DatagramSocket(port)){
                byte[] buffer = getBuffer();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                server.receive(packet);
                byte[] pack = packet.getData();
                String code = Response.getPacketCode(pack);
                server.disconnect();
                if(code.equals(DEAD_CODE)){
                    String deadIp = Response.getIpDeadFromIgor(pack);
                    if(deadIp.equals(myIp)){
                        System.out.println("Я умер");
                        AttackerCommunication.sendToAttackerBadNews();
                        System.exit(0);
                    }
                }

                if(code.equals(CHECK_CODE)){
                    DatagramSocket response = new DatagramSocket();
                    Request imReadyRequest = RequestFactory.createRequest(RequestCode.IM_ALIVE);
                    assert imReadyRequest != null;
                    byte[] request = imReadyRequest.getPacket();
                    packet = new DatagramPacket(request, request.length);
                    response.connect(InetAddress.getByName(host), portToSend);
                    response.send(packet);
                    response.close();
                    System.out.println("Ответил на 5 запрос, я еще жив");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                //run();
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
            byte[] pack = packet.getData();
            String code = Response.getPacketCode(pack);
            System.out.println("Получен код " + code);
            while(!code.equals(READY_CODE)){
                System.out.println("Не тот код " + code);
                server.receive(packet);
                code = packet.getData()[0] + "";
                System.out.println("Получен код " + code);
            }
            server.disconnect();
            DatagramSocket response = new DatagramSocket();
            Request imReadyRequest = RequestFactory.createRequest(RequestCode.IM_READY);
            assert imReadyRequest != null;
            byte[] request = imReadyRequest.getPacket();
            packet = new DatagramPacket(request, request.length);
            response.connect(InetAddress.getByName(host), portToSend);
            response.send(packet);
            response.disconnect();
            response.close();
        } catch (IOException e) {
            System.out.println("Повторное ожидание готовности" + e.getMessage());
            e.printStackTrace();
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
            byte[] pack = packet.getData();
            String code = Response.getPacketCode(pack);
            System.out.println("Получен код " + code);
            while(!code.equals(START_CODE)){
                System.out.println("Ожидался " + START_CODE + " получен " + code);
                server.receive(packet);
                code = Response.getPacketCode(packet.getData());
            }
            server.disconnect();
        } catch (IOException e) {
            System.out.println("Повторное ожидание игры " + e.getMessage());
            e.printStackTrace();
        }
    }

    private byte[] getBuffer(){
        return new byte[MAX_SIZE_BUFFER];
    }

}
