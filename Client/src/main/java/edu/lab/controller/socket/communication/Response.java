package edu.lab.controller.socket.communication;

import java.util.LinkedList;
import java.util.List;

public abstract class Response {

    public static List<String> getTableResponse(byte[] bytes){
        List<String> listOfPlayers = new LinkedList<>();
        byte count = bytes[1];
        if(count < 2){
            return new LinkedList<>();
        }
        for(int i = 2; i < count * 4 + 2; i += 4){
            StringBuilder sb = new StringBuilder();
            sb.append(bytes[i]);
            for(int j = i + 1; j < i + 4; j++){
                sb.append(".");
                sb.append(bytes[j]);
            }
            listOfPlayers.add(sb.toString());
        }
        return listOfPlayers;
    }

    public static String getIdDead(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        sb.append(bytes[1]);
        for(int i = 2; i < 5; i++){
            sb.append(".");
            sb.append(bytes[i]);
        }
        return sb.toString();
    }


}
