package edu.lab.server;

import edu.lab.server.coodinator.ClientForIgor;
import edu.lab.server.coodinator.client.ClientForIgorImpl;
import edu.lab.server.coodinator.server.ServerForIgor;
import edu.lab.server.fort.Fort;
import edu.lab.server.myAttacker.AttackerCommunication;

public class Server {
    //Константы для сервера Игоря
    static final Integer PORT_TO_IGOR = 4242;
    static final Integer BYTE_PORT_TO_IGOR = 37392;
    static final String IGOR_SERVER_PASSWORD = "valid cd";
    static final String FIO = "Mitin Dmitry";

    public static void main(String[] args) {
        ClientForIgorImpl client = new ClientForIgorImpl(args[0], BYTE_PORT_TO_IGOR);
        ServerForIgor server = new ServerForIgor(BYTE_PORT_TO_IGOR, args[0]);
        AttackerCommunication myAttacker = new AttackerCommunication(args[0], BYTE_PORT_TO_IGOR);
        //Форма записи регистрации - строка "password;port;fio"
        String validRegistryString = IGOR_SERVER_PASSWORD + ";" + PORT_TO_IGOR + ";" + FIO;
        String myIp = client.registry(validRegistryString);
        myAttacker.sendOurIp(myIp);
        System.out.println("Я зарегался, мой ip: " + myIp);
        server.setMyIp(myIp);
        System.out.println("Жду запрос готовности...");
        server.ready();
        System.out.println("Получил запрос готовности, жду начала игры...");
        server.waitStartGame();
        System.out.println("Игра началась");

        myAttacker.sendToAttackerList(client.getActualListOfPlayers());
        Fort fort = new Fort(2222);

        Thread listener = new Thread(server);
        listener.start();

        Thread defender = new Thread(fort);
        defender.start();




    }
}
