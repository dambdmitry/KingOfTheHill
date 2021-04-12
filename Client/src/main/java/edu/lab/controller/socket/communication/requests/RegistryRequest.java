package edu.lab.controller.socket.communication.requests;

import edu.lab.controller.socket.communication.Request;
import edu.lab.controller.socket.communication.RequestCode;

import java.nio.ByteBuffer;

public class RegistryRequest extends Request {
    private final Integer PASSWORD_BEGIN_INDEX = 0;
    private final Integer PORT_BEGIN_INDEX = 8;
    private final Integer FIO_BEGIN_INDEX = 10;

    public RegistryRequest(RequestCode code) {
        super(code);
    }

    public RegistryRequest(String info) {
        super(RequestCode.REGISTRY);
        super.info = info;
    }


    @Override
    protected void buildInfoBytes() {
        byte[] registryInfoBytes = getInfoBytes(info);
        insert(0, registryInfoBytes, infoBytes);
    }

    // password;port;fio
    private byte[] getInfoBytes(String info){
        byte[] bytesInfo = new byte[254];
        String[] split = info.split(";");
        String password = split[0];
        String port = split[1];
        String fio = split[2];

        createAndInsertBytesFromString(PASSWORD_BEGIN_INDEX, bytesInfo, password);
        createAndInsertBytesForPort(PORT_BEGIN_INDEX, bytesInfo, port);
        createAndInsertBytesFromString(FIO_BEGIN_INDEX, bytesInfo, fio);

        return bytesInfo;
    }

    private void createAndInsertBytesForPort(Integer start, byte[] bytes, String port) {
        Integer intPort = Integer.parseInt(port);
        byte[] res = new byte[2];
        ByteBuffer buf = ByteBuffer.allocate(Integer.BYTES);
        buf.putInt(intPort);
        for (byte b : buf.array()) {
            if (b != 0){
                if(res[0] == 0){
                    res[0] = b;
                }else{
                    res[1] = b;
                }
            }
        }
        insert(start, res, bytes);
    }

    /*
    * Пароль состоит из 8 байтов (то есть пароль только длиной 8 символов можно)
    *
    * Метод создает байтовый пароль и записывает его в массив bytes с индекса start
    *
    * */
    private void createAndInsertBytesFromString(Integer start, byte[] bytes, String password){
        byte[] passwordBytes = password.getBytes();
        insert(start, passwordBytes, bytes);
    }
}
