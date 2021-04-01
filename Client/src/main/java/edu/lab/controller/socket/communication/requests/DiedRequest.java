package edu.lab.controller.socket.communication.requests;

import edu.lab.controller.socket.communication.Request;
import edu.lab.controller.socket.communication.RequestCode;

public class DiedRequest extends Request {
    private String killerIp;

    public DiedRequest(RequestCode code) {
        super(code);
    }

    public DiedRequest(String info) {
        super(RequestCode.DIED);
        this.killerIp = info;
    }

    @Override
    protected void buildInfoBytes() {
        byte[] killerIpBytes = getIpBytes(killerIp);
        insert(0, killerIpBytes, infoBytes);
    }

    private byte[] getIpBytes(String ip){
        byte[] ipBytes = new byte[4];
        String[] split = ip.split("\\.");
        for(int i = 0; i < ipBytes.length; i++){
            byte oneByte = (byte) Integer.parseInt(split[i]);
            ipBytes[i] = oneByte;
        }
        return ipBytes;
    }
}
