package edu.lab.controller.socket;


import java.util.List;

/*
* Клиент для сервера Игоря
*
* Допустим что
* */
public interface ClientForIgor {
    /*
     * Вернет актуальный список игроков
     * */
    List<String> getActualListOfPlayers();

    void registry(String info);

}
