package edu.lab.server.coodinator.communication;

public enum RequestCode {
    REGISTRY((byte) 0),
    REMOVE_ME((byte) 1),
    IM_READY((byte) 2),
    IM_KILL((byte) 3),
    GET_TABLE((byte) 4);

    private final byte code;

    private RequestCode(byte code){
        this.code = code;
    }


    public byte getCode() {
        return code;
    }
}
