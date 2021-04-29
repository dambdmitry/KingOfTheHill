package edu.lab.server.coodinator.communication.requests;

import edu.lab.server.coodinator.communication.Request;
import edu.lab.server.coodinator.communication.RequestCode;

public class ImKillRequest extends Request {
    private String killerIp;

    public ImKillRequest(RequestCode code) {
        super(code);
    }

    public ImKillRequest(String info) {
        super(RequestCode.IM_KILL);
        this.killerIp = info;
    }

    @Override
    protected void buildInfoBytes() {
        byte[] killerIpBytes = getIpBytes(killerIp);
        insert(0, killerIpBytes, infoBytes);
    }

    private byte[] getIpBytes(String ip){
        byte[] ipBytes = new byte[5];
        ipBytes[0] = requestCode;
        String[] split = ip.split("\\.");
        for(int i = 0; i < ipBytes.length; i++){
            byte oneByte = (byte) Integer.parseInt(split[i]);
            ipBytes[i] = oneByte;
        }
        return ipBytes;
    }
}
