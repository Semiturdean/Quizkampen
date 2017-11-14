package quizkampen;

import java.io.*;
import java.net.Socket;

public class GameRoom implements Runnable {
    private Socket clientSocket;
    private String clientName;
    private Session session;
    private Protocol protocol;

    GameRoom(Socket clientSocket) {
        this.clientSocket = clientSocket;
        clientName = clientSocket.getInetAddress().getHostName();
        protocol = new Protocol();
        session = new Session();
        session.setAnswer(protocol.getAnswers()[0]);
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

            outputStream.writeObject("Var vänlig skriv något");

            Object inputStreamObject, outputStreamObject;

            while ((inputStreamObject = inputStream.readObject()) != null) {
                Session output = protocol.processInput((Session) inputStreamObject);
                //outputStreamObject = "Client " + clientName + ": " + inputStreamObject;
                outputStream.writeObject(output);
            }
        } catch (IOException e) {
            System.out.println("A client unexpectedly disconnected: " + clientName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
