package edu.lab.server.coodinator.communication;

import java.util.LinkedList;
import java.util.List;

public abstract class Response {

    /**
     * Метод узнает код запроса
     * @param packet массив байтов запроса
     * @return код запроса
     */
    public static String getPacketCode(byte[] packet){
        return packet != null ? packet[0] + "" : "-1";
    }

    /**
     * Код запроса от сервера Игоря 2
     * 1 байт - код
     * 2 байт - количество участников
     * остальные каждые 4 байта ip участников
     * @param bytes байтовый запрос от Игоря с кодом 2
     * @return Лист с ip учасников
     */
    public static List<String> getTableResponse(byte[] bytes){
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

    /**
     * Код запроса от сервера Игоря 3
     * 1 байт - код
     * 2 - 5 байты ip выбывшего участника
     * @param bytes байтовый запрос от Игоря с кодом 3
     * @return строка ip, пример для локалхоста "127.0.0.1"
     */
    public static String getIdDead(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        sb.append(bytes[1]);
        for(int i = 2; i < 5; i++){
            sb.append(".");
            sb.append(bytes[i]);
        }
        return sb.toString();
    }

    /**
     * Код запроса от сервера Игоря 4
     * 1 байт - код
     * 2 - 5 байты мой ip
     * @param bytes байтовый запрос от Игоря с кодом 4
     * @return строка ip, пример для локалхоста "127.0.0.1"
     */
    public static String getMyIp(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        sb.append(bytes[1]);
        for(int i = 2; i < 5; i++){
            sb.append(".");
            sb.append(bytes[i]);
        }
        return sb.toString();
    }


}
