package edu.lab;

import edu.lab.controller.Controller;
import edu.lab.controller.socket.ControllerSocket;

public class Client {
    public static void main(String[] args) {
        Controller controller = new ControllerSocket();
        controller.registryMe();
    }
}
