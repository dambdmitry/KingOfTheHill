package edu.lab;

import edu.lab.attack.attackers.AboveAttacker;
import edu.lab.attack.attackers.BelowAttacker;
import edu.lab.fortUDP.FortCommunication;

import java.net.SocketException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {


    //Игровые константы
    static final Integer port = 2222;

    public static void main(String[] args){
        FortCommunication fort = new FortCommunication();
        List<String> sacrifices = fort.waitAndGetListPlayersFromFort();//Далее замногопоточить дибилов
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

        Thread listener = new Thread(fort);
        listener.setPriority(7);
        listener.start();
    }
}
