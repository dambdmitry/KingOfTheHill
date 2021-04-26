package edu.lab.server.coodinator.communication;

public enum RequestCode {
    REGISTRY((byte) 0),
    REMOVE_ME((byte) 1),   //юзлесс реквест
    IM_READY((byte) 2),
    IM_DEAD((byte) 3),     //deprecated реквест
    GET_TABLE((byte) 4),
    IM_ALIVE((byte) 5),
    GET_MY_NAME((byte) 6), //юзелесс реквест
    IM_KILL((byte) 7);

    private final byte code;

    private RequestCode(byte code){
        this.code = code;
    }


    public byte getCode() {
        return code;
    }
}
