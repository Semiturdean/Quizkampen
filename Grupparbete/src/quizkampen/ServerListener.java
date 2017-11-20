package quizkampen;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
    private ServerSocket listenerSocket;
    private int port;
    private Socket client1;
    private Socket client2;

    ServerListener(int port) throws IOException {
        this.port = port;
        listenerSocket = new ServerSocket(port);

        System.out.println("Server listener has started, awaiting connections...");
        try {
            while (true) {
                client1 = listenerSocket.accept();
                System.out.println("First client connected: " + client1.getInetAddress().getHostName());

                /*ObjectOutputStream toClient = new ObjectOutputStream(client1.getOutputStream());
                toClient.writeObject("Waiting on another player...");
                toClient.close();*/

                client2 = listenerSocket.accept();
                System.out.println("Second client connected: " + client2.getInetAddress().getHostName());

                GameRoom serverHandler = new GameRoom(client1, client2);
                Thread t = new Thread(serverHandler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                listenerSocket.close();
                System.out.println("Stopping server listener");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ServerListener s = new ServerListener(4444);
        } catch (IOException e) {
            System.out.println("Could not create a server socket with the given port.\n" +
                    "The port could be in use.");
        }
    }
}
