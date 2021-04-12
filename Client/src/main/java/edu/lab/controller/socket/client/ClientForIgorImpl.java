package edu.lab.controller.socket.client;

import edu.lab.controller.socket.ClientForIgor;
import edu.lab.controller.socket.communication.Request;
import edu.lab.controller.socket.communication.RequestCode;
import edu.lab.controller.socket.communication.requests.RequestFactory;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class ClientForIgorImpl implements ClientForIgor {
    private static final String host = "127.0.0.1";
    private static final Integer port = 3333;


    @Override
    public List<String> getActualListOfPlayers() {
        return null;
    }

    @Override
    public void registry(String info) {
        try (DatagramSocket socket = new DatagramSocket()){
            Request registryRequest = RequestFactory.createRequest(RequestCode.REGISTRY, info);
            byte[] msg = registryRequest.getPacket();
            DatagramPacket packet = new DatagramPacket(msg, msg.length);
            socket.connect(InetAddress.getByName(host), port);
            socket.send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
