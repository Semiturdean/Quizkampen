package quizkampen;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerListener {
    private ServerSocket listenerSocket;
    private int port;

    /*
    Server that constantly listens for incoming connections from clients.
    Creates a game room when two client connects.
     */

    ServerListener(int port) throws IOException {
        this.port = port;
        listenerSocket = new ServerSocket(port);

        System.out.println("Server listener has started, awaiting connections...");
        try {
            while (true) {
                GameRoom game = new GameRoom();
                GameRoomPlayer player1 = new GameRoomPlayer(listenerSocket.accept(), game, 'X');
                System.out.println("First client connected.");
                GameRoomPlayer player2 = new GameRoomPlayer(listenerSocket.accept(), game, 'O');
                System.out.println("Second client connected.");
                player1.start();
                player2.start();
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
