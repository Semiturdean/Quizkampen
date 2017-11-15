package quizkampen;

import java.io.*;
import java.net.Socket;

public class GameRoom implements Runnable {
    private Socket clientSocket;
    private String clientName;

    GameRoom(Socket clientSocket) {
        this.clientSocket = clientSocket;
        clientName = clientSocket.getInetAddress().getHostName();
    }

    @Override
    public void run() {
        System.out.println("Server room has been created");

        // Create the necessary input- and output-stream for communication with client
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            System.out.println("Established connection with client: " + clientName);

            // Initialization with client
            Protocol protocol = new Protocol();
            Session input;
            Session output = protocol.getInitialSession();
            outputStream.writeObject(output);

            while ((input = (Session)inputStream.readObject()) != null) {
                output = protocol.processInput(input);
                outputStream.writeObject(output);
            }
        } catch (IOException e) {
            System.out.println("A client unexpectedly disconnected: " + clientName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
