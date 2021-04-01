package edu.lab.controller.socket.communication.requests;

import edu.lab.controller.socket.communication.Request;
import edu.lab.controller.socket.communication.RequestCode;

public class RegistryRequest extends Request {
    private final Integer PASSWORD_BEGIN_INDEX = 1;
    private final Integer PORT_BEGIN_INDEX = 9;
    private final Integer FIO_BEGIN_INDEX = 11;
    private String info;

    public RegistryRequest(RequestCode code) {
        super(code);
    }

    public RegistryRequest(String info) {
        super(RequestCode.REGISTRY);
        this.info = info;
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
        createAndInsertBytesFromString(PORT_BEGIN_INDEX, bytesInfo, port);
        createAndInsertBytesFromString(FIO_BEGIN_INDEX, bytesInfo, fio);

        return bytesInfo;
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
