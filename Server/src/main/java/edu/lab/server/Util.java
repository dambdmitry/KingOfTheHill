package edu.lab.server;

import java.nio.ByteBuffer;

public class Util {
    public static byte[] getDeathBytes(String ip){
        byte[] deathBytes = new byte[255];
        deathBytes[0] = (byte) 3;

        String[] ipBytes = ip.split("\\.");
        int i = 1;
        for (String ipByte : ipBytes){
            deathBytes[i] = Byte.parseByte(ipByte);
            i++;
        }
        return deathBytes;
    }
}
