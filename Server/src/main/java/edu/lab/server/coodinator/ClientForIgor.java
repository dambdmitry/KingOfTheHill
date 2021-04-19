package edu.lab.server.coodinator;


import java.util.List;

/*
* Клиент для сервера Игоря
*
* Допустим что
* */
public interface ClientForIgor {

    /**
     * Метод отправляет запрос 4 на получение списка участников
     * Ждет ответ и возвращает список участников
     * @return список участников
     */
    List<String> getActualListOfPlayers();

    /**
     * ЕДИННОЖДЫ ВЫЗЫВАЕМЫЙ МЕТОД
     * Метод отправляет запрос с кодом 0 на регистрацию
     *
     * После:
     * Метод ожидает подтверждение регистрации от Игоря
     * Ждет запрос с кодом 4, который содержит мой ip
     * @return мой ip, пример для локолхоста "127.0.0.1"
     * @param info Инфо для регистрации выглядит так "password;port;fio"
     */
    String registry(String info);

    /**
     * Отправляет запрос 4 к Игорю
     * Что убил игрока
     * @param deadIp ip игрока
     */
    void iKilledThis(String deadIp);

}
