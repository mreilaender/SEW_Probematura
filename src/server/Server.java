package server;

import util.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author mreilaender
 * @version 03.05.2016
 */
public class Server {
    private ServerSocket serverSocket;
    private boolean running;

    public Server() {
        this.running = true;
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(Config.serverPort);
            System.out.println("Server started, waiting for clients");
            while (running) {
                Socket client = serverSocket.accept();
                System.out.println("Client connected");
                ClientManager.addClient(client);
                new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
