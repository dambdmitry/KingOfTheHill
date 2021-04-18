package edu.lab.controller.socket.server;

import edu.lab.controller.socket.ClientForIgor;
import edu.lab.controller.socket.communication.Request;
import edu.lab.controller.socket.communication.RequestCode;
import edu.lab.controller.socket.communication.Response;
import edu.lab.controller.socket.communication.requests.RequestFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Класс для регистрации и мониторинга своей смерти
 * Имеет {единножды вызываемые} методы, которые вызываются один раз
 * Так же имеет метод run, который мониторит мою сметрь
 * КЛАСС ВЗАИМОДЕЙСТВУЕТ ТОЛЬКО С ЗАПРОСАМИ, КОТОРЫЙ ИНИЦИИРУЕТ СЕРВЕР ИГОРЯ
 * ЗАПРОСЫ ПО МОЕЙ ИНИЦИАЦИИ ОБРАБАТЫВАЮТСЯ В КЛАССЕ {@link ClientForIgor}
 */
public class ServerForIgor implements Runnable {
    private final int MAX_SIZE_BUFFER = 255;
    private final Integer port;
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
                if((pack[0] + "").equals("3")){
                    String deadIp = Response.getIdDead(pack);
                    if(deadIp.equals(myIp)){
                        System.out.println("Я умер");
                        System.exit(0);
                    }
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
            server.disconnect();
            server.close();
            if (code.equals("0")){
                DatagramSocket response = new DatagramSocket(port);
                Request imReadyRequest = RequestFactory.createRequest(RequestCode.IM_READY);
                assert imReadyRequest != null;
                byte[] request = imReadyRequest.getPacket();
                packet = new DatagramPacket(request, request.length);
                response.connect(InetAddress.getByName(host), port);
                response.send(packet);
                response.close();
            }else{
                System.out.println("Повторное ожидание готовности, не верен код " + code);
                ready();
            }
        } catch (IOException e) {
            System.out.println("Повторное ожидание готовности" + e.getMessage());
            ready();
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
            if(code.equals("1")){
                return;
            }
            System.out.println("Повторное ожидание игры, не верен код " + code);
            waitStartGame();
        } catch (IOException e) {
            System.out.println("Повторное ожидание игры");
            waitStartGame();
        }
    }


    private byte[] getBuffer(){
        return new byte[MAX_SIZE_BUFFER];
    }


}
