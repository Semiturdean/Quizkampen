package quizkampen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {
    private ServerSocket listenerSocket;
    private int port;
    private boolean listening;

    ServerListener(int port) throws IOException {
        this.port = port;
        listening = true;
        listenerSocket = new ServerSocket(port);
    }

    void stopServer() {
        listening = false;
    }

    @Override
    public void run() {
        System.out.println("Server listener has started, awaiting connections...");
        try {
            while (listening) {
                Socket clientSocket = listenerSocket.accept();
                GameRoom serverHandler = new GameRoom(clientSocket);
                Thread t = new Thread(serverHandler);
                t.start();
                System.out.println("Client " + clientSocket.getInetAddress().getHostName() + " has connected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Stopping server listener");
    }

    public static void main(String[] args) {
        try {
            ServerListener s = new ServerListener(4444);
            s.start();
        } catch (IOException e) {
            System.out.println("Could not create a server socket with the given port.\n" +
                    "The port could be in use.");
        }
    }
}
