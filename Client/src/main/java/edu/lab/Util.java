package edu.lab;

import java.util.LinkedList;
import java.util.List;

public class Util {
    public static List<String> getIpListFromBytes(byte[] bytes){
        List<String> listOfPlayers = new LinkedList<>();
        byte count = bytes[1];
        if(count < 1){//todo исправить на count < 2
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
}
