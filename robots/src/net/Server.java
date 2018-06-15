package net;

import gui.GameWindow;
import log.Logger;

import java.io.*;
import java.net.*;

public class Server extends Thread {
    private GameWindow gameWindow;
    private String ipAddress = "UNDEFINED";

    public Server(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        start();
    }

    public void run() {
        Logger.debug("Сервер запущен и ожидает подключение");

        try (ServerSocket sSocket = new ServerSocket(Constants.PORT); Socket socket = sSocket.accept()) {
            Logger.debug("Сервер принял подключение");
            gameWindow.getMap(socket);
            Logger.debug("Сервер принял карту");
        }
        catch (IOException e) {
            e.printStackTrace();
            Logger.error("Ошибка сервера");
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
