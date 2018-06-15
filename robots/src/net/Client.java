package net;

import gui.GameWindow;
import log.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends Thread {
    private GameWindow gameWindow;
    private String ipAddress;

    public Client(GameWindow gameWindow, String ipAddress) {
        this.gameWindow = gameWindow;
        this.ipAddress = ipAddress;
        start();
    }

    public void run() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ipAddress, Constants.PORT));
            Logger.debug("Клиент подключился к серверу");
            gameWindow.sendMap(socket);
            Logger.debug("Клиент отправил карту серверу");
        }
        catch (IOException e) {
            e.printStackTrace();
            Logger.error("Не удалось подключиться");
        }
    }
}
