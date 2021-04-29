# Царь горы
##Лабаратораная работа по сетевым ОС.
###Автор Митин Дмитрий ПМ-31
####Требования:
<ul>
    <li>Java 11</li>
    <li>Maven 3.6.3</li>
</ul>
Проект разбит на 2 независимых модуля Client и Server <br>

### Server
<p>Server - Многофункциональный модуль. Выполняет следующие фкнкции:</p>
<ul>
    <li>Взаимодействие с <a href="https://github.com/Demonorium/MainGameServer">Main Game Server</a> (по UDP)</li>
    <li>Является посредником между Client и <a href="https://github.com/Demonorium/MainGameServer">Main Game Server</a> (Отправляет UDP запросы для Client)</li>
    <li>Принимает атаки по TCP от соперников и отправляет им ответ</li>
</ul>

###Client
<p>Client  - боевая единица, его единственная задача атаковать соперников по TCP. Также он
 может взаимодействовать с <a href="https://github.com/Demonorium/MainGameServer">Main Game Server</a> 
 через Server, общаясь с ним по UDP</p>
 
 <p>Клиент запускает по 2 потока на каждого игрока из таблицы (кроме себя). Каждый 
 поток пытается с помощью бинарного поиска угадать число оппонента. Оппонент отвечает "больше", "меньше" 
 с вероятностью 20%</p>


#####Установка:
Установка клиента: <br>
Откройте в терминале папку `KingOfHill/Client` <br>
Далее выполните maven команду `mvn clean install` <br>

Выполните те же операции для сервера `KingOfHill/Server`

#####Запуск сервера:
Сервер запускается после запуска сервера Игоря <a href="https://github.com/Demonorium/MainGameServer">Main Game Server</a> <br>
Откройте в терминале папку `KingOfHill/Server/target` <br>
Выполните команду `java -jar Server-1.0.jar host` <br>
Где host - ip адресс сервера <a href="https://github.com/Demonorium/MainGameServer">Main Game Server</a> 
. <br> Например: `java -jar Server-1.0.jar 127.0.0.1`<br>

#####Запуск Клиента:
Клиент можно запустить в любой момент ДО начала игры. <br>
Откройте в терминале папку `KingOfHill/Client/target` <br>
Выполните команду `java -jar Client-1.0.jar` <br>

###После смерти оба модуля закончат работу самостоятельно.
