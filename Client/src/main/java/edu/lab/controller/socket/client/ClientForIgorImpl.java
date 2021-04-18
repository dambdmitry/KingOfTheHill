package edu.lab.controller.socket.client;

import edu.lab.controller.socket.ClientForIgor;
import edu.lab.controller.socket.communication.Request;
import edu.lab.controller.socket.communication.RequestCode;
import edu.lab.controller.socket.communication.Response;
import edu.lab.controller.socket.communication.requests.RequestFactory;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class ClientForIgorImpl implements ClientForIgor {
    private static final int MAX_BUFFER_SIZE = 255;
    private String host;
    private static final Integer registryPort = 3333;
    private final Integer port;

    public ClientForIgorImpl(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public List<String> getActualListOfPlayers() {
        try (DatagramSocket socket = new DatagramSocket()) {
            System.out.println("Создаю реквест");
            Request getTableRequest = RequestFactory.createRequest(RequestCode.GET_TABLE);
            byte[] msg = getTableRequest.getPacket();
            DatagramPacket packet = new DatagramPacket(msg, msg.length);
            System.out.println("Реквест создан");
            socket.connect(InetAddress.getByName(host), registryPort);
            System.out.println("Подключился к Игорю");
            DatagramSocket response = new DatagramSocket(port);
            byte[] responseBytes = getBuffer();
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
            socket.send(packet);
            System.out.println("Запросил таблицу игроков");
            System.out.println("Жду таблицу");
            response.receive(responsePacket);
            System.out.println("Получил таблицу");
            List<String> table = Response.getTableResponse(responsePacket.getData());
            System.out.println(table.size());
            System.out.println("Таблица получена");
            return table;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return getActualListOfPlayers();
        }
    }

    @Override
    public String registry(String info) {
        try (DatagramSocket socket = new DatagramSocket()) {
            Request registryRequest = RequestFactory.createRequest(RequestCode.REGISTRY, info);
            byte[] msg = registryRequest.getPacket();
            DatagramPacket packet = new DatagramPacket(msg, msg.length);
            System.out.println("Отправляю");
            socket.connect(InetAddress.getByName(host), registryPort);
            socket.send(packet);
            System.out.println("ОТправлено");
            byte[] responseBytes = getBuffer();
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
            socket.disconnect();
            DatagramSocket responseSocket = new DatagramSocket(port);
            System.out.println("Ждем ответ");
            responseSocket.receive(responsePacket);
            String myIp = Response.getMyIp(responsePacket.getData());
            System.out.println("Ответ получен");
            responseSocket.close();
            return myIp;


        } catch (IOException e) {
            throw new RuntimeException("Регистрация не прошла");
        }
    }

    @Override
    public void iKilledThis(String deadIp) {
        try (DatagramSocket socket = new DatagramSocket()) {
            Request iKillRequest = RequestFactory.createRequest(RequestCode.IM_KILL, deadIp);
            byte[] pack = iKillRequest.getPacket();
            DatagramPacket packet = new DatagramPacket(pack, pack.length);
            socket.connect(InetAddress.getByName(host), port);
            socket.send(packet);

            byte[] responseBytes = getBuffer();
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
            socket.receive(responsePacket);
            boolean isMyCode = (responsePacket.getData()[0] + "").equals("3");
            if (isMyCode) {
                System.out.println(Response.getIdDead(responsePacket.getData()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getBuffer() {
        return new byte[MAX_BUFFER_SIZE];
    }
}
