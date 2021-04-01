package edu.lab.controller.socket;

import edu.lab.config.Config;
import edu.lab.controller.Controller;
import edu.lab.controller.socket.client.ClientControllerSocket;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

public class ControllerSocket implements Controller {
    private static final String host = Config.getProperty(Config.Context.CONTROLLER_SERVER_HOST);
    private static final String port = Config.getProperty(Config.Context.CONTROLLER_SERVER_PORT);
    private DatagramSocket socket = new DatagramSocket();

    public ControllerSocket() {
    }

    @Override
    public List<String> getActualListOfPlayers() {
        return null;
    }

    @Override
    public void registry(String info) {

    }
}
