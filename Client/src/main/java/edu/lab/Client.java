package edu.lab;

import edu.lab.attack.Attacker;
import edu.lab.fortUDP.FortCommunication;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {


    //Игровые константы
    static final Integer port = 2222;

    public static void main(String[] args){
        FortCommunication fort = new FortCommunication();
        String myIp = fort.receiveIp();
        List<String> sacrifices = fort.waitAndGetListPlayersFromFort();
        Integer amount = sacrifices.size() * 2;
        ExecutorService exec = Executors.newFixedThreadPool(amount == 0 ? 1 : amount);
        for (String sacrifice : sacrifices) {
            System.out.println(sacrifice);
        }
        for(String ip : sacrifices){
            if (!ip.equals(myIp)) {
                System.out.println("Запускаю псов на " + ip);
                exec.execute(new Attacker(ip, port));
                exec.execute(new Attacker(ip, port));
                System.out.println("Псы запущены");
            }
        }

        Thread listener = new Thread(fort);
        listener.setPriority(5);
        listener.start();
    }
}
