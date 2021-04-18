package edu.lab;

import edu.lab.controller.socket.ClientForIgor;
import edu.lab.controller.socket.client.ClientForIgorImpl;
import edu.lab.controller.socket.client.attackers.AboveAttacker;
import edu.lab.controller.socket.client.attackers.BelowAttacker;
import edu.lab.controller.socket.server.ServerForIgor;

import java.net.SocketException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    //Константы для сервера Игоря
    static final Integer PORT_TO_IGOR = 4242;
    static final Integer BYTE_PORT_TO_IGOR = 37392;
    static final String IGOR_SERVER_PASSWORD = "valid cd";
    static final String FIO = "Mitin Dmitry";
    //Игровые константы
    static final Integer port = 2222;

    static String host = "127.0.0.1";
    public static void main(String[] args) throws SocketException {
        ClientForIgor client = new ClientForIgorImpl(host, BYTE_PORT_TO_IGOR);
        ServerForIgor server = new ServerForIgor(BYTE_PORT_TO_IGOR, host);

        //Форма записи регистрации - строка "password;port;fio"
        String validRegistryString = IGOR_SERVER_PASSWORD + ";" + PORT_TO_IGOR + ";" + FIO;
        String myIp = client.registry(validRegistryString);
        System.out.println("Я зарегался, мой ip: " + myIp);
        server.setMyIp(myIp);
        System.out.println("Жду запрос готовности...");
        server.ready();
        System.out.println("Получил запрос готовности, жду начала игры...");
        server.waitStartGame();
        System.out.println("Игра началась");
        List<String> sacrifices = client.getActualListOfPlayers();//Далее замногопоточить дибилов
        Integer amount = sacrifices.size() * 2;
        ExecutorService exec = Executors.newFixedThreadPool(amount);

        for (String sacrifice : sacrifices) {
            System.out.println(sacrifice);
        }
        for(String ip : sacrifices){
            System.out.println("Запускаю псов");
            exec.execute(new AboveAttacker(ip, port));
            exec.execute(new BelowAttacker(ip, port));
            System.out.println("Псы запущены");
        }

        Thread listener = new Thread(server);
        listener.start();
    }
}
