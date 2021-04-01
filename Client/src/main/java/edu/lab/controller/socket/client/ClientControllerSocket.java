package edu.lab.controller.socket.client;

import edu.lab.controller.socket.Client;
import edu.lab.controller.socket.communication.RequestCode;
import edu.lab.controller.socket.communication.requests.RequestFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientControllerSocket implements Client {

    private String host;
    private String port;
    private DatagramSocket client;
    public ClientControllerSocket(String host, String port){
        this.host = host;
        this.port = port;
    }

    public void registry(String info) throws IOException {
        OutputStream scanner = this.client.getOutputStream();
        scanner.write(RequestFactory.createRequest(RequestCode.REGISTRY, info).getPacket());
        scanner.flush();


    }
    

    public void removeMe(){

    }

}
