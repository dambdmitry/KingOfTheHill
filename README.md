# Царь горы
##Лабаратораная работа по сетевым ОС.
###Автор Митин Дмитрий ПМ-31
####Требования:
<ul>
    <li>Java 11</li>
    <li>Maven 3.6.3</li>
</ul>
Проект разбит на 2 независимых модуля Client и Server <br>
<p>Client  - боевая единица, отвечает за взаимодействие с <a href="https://github.com/Demonorium/MainGameServer">Main Game Server</a> (по UDP) и
за атаку оппонентов (по TCP).</p>

<p>Server - игрушка для битья, принимает атаки по tcp и отправляет результаты атак обидчикам</p>

#####Установка:
Установка клиента: <br>
Откройте в терминале папку `KingOfHill/Client` <br>
Далее выполните maven команду `mvn clean install` <br>

Выполните те же операции для сервера `KingOfHill/Server`

#####Запуск клиента:
Клиент запускается после запуска сервера <a href="https://github.com/Demonorium/MainGameServer">Main Game Server</a> <br>
Откройте в терминале папку `KingOfHill/Client/target` <br>
Выполните команду `java -jar Client-1.0.jar host` <br>
Где host - ip адресс сервера <a href="https://github.com/Demonorium/MainGameServer">Main Game Server</a> 
. Например: `java -jar Client-1.0.jar 127.0.0.1`<br>

#####Запуск сервера:
Сервер можно запустить в любой момент ДО начала игры. <br>
Откройте в терминале папку `KingOfHill/Server/target` <br>
Выполните команду `java -jar Client-1.0.jar` <br>

###После смерти оба модуля закончат работу самостоятельно.
