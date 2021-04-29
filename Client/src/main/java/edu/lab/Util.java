package edu.lab;

import java.util.LinkedList;
import java.util.List;

public class Util {
    public static List<String> getIpListFromBytes(byte[] bytes){
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

    public static String getIpFromBytes(byte[] ipBytes){
        StringBuilder sb = new StringBuilder();
        sb.append(ipBytes[0]);
        for(int i = 1; i < ipBytes.length; i++){
            sb.append(".");
            sb.append(ipBytes[i]);
        }
        return sb.toString();
    }
}
