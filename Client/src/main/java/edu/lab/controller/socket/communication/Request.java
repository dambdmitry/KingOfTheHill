package edu.lab.controller.socket.communication;

/*
* Класс, отвечающий за взаимопонимание сервера контроллера и приложения.
* Подготавливает запросы к серверу в байтах
* пакет - 255 байтов
* 1-й байт всегда код запроса, остальное - доп инфа
* Коды запроса:
* 0 - регистрация | сигнатура массива байтов [0, пароль в 8 байтах, порт для udp в 2-х байтах, имя и фамилия в аски]
* 1 - удаление себя из списка игроков | сигнатура [1]
* 2 - согласие на готовность к игре | сигнатура [2]
* 3 - сообщение о своей смерти | сигнатура [3, ip убившего в 4 байтах]
* 4 - запрос таблицы с ip игроков | сигнатура [4]
* */
public abstract class Request {
    protected static final Integer MAX_BYTES_FOR_REQUEST = 255;
    protected byte[] packet = new byte[MAX_BYTES_FOR_REQUEST]; // То что принимает сервер - массив байтов
    protected byte[] infoBytes = new byte[MAX_BYTES_FOR_REQUEST - 1]; //массив байтов с доп информацией
    protected byte requestCode; // код запроса к серверу (1 байт)

    public Request(RequestCode code) {
        this.requestCode = code.getCode();
        packet[0] = this.requestCode; // сразу присваиваем пакету тип его запроса
        buildInfoBytes();
    }

    public byte[] getPacket(){
        Integer firstInfoIndex = 1;
        insert(firstInfoIndex, infoBytes, this.packet);
        return packet;

    }

    protected void insert(Integer start, byte[] from, byte[] to){
        int index = 0;
        for(int i = start; i < from.length + start; i++){
            to[i] = from[index];
            index++;
        }
    }

    protected abstract void buildInfoBytes(); // реализовать заполнение packet байтами с информацией
}
