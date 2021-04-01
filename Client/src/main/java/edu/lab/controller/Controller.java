package edu.lab.controller;

import java.util.List;

/*
* Контроллер, Куратор, сервер Игоря
*
* 1)Регистрация в игре
*
* Отвечает за поставку свежего списка игроков
*
* и вроде надо ему отсылать что кого-то грохнул
*
*
* */
public interface Controller {

    /*
    * Вернет актуальный список игроков
    * */
    List<String> getActualListOfPlayers();

    void registry(String info);



}
