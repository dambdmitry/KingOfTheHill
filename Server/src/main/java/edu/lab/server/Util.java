package edu.lab.server;

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

    public static byte[] getIpBytesForAttacker(String ip){
        byte[] ipBytes = new byte[4];
        String[] split = ip.split("\\.");
        for(int i = 0; i < ipBytes.length; i++){
            byte oneByte = (byte) Integer.parseInt(split[i]);
            ipBytes[i] = oneByte;
        }
        return ipBytes;
    }
}
