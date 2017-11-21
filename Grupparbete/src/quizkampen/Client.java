package quizkampen;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    private int port;
    private String serverAddress;

    Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    @Override
    public void run() {
        // Create the necessary input- and output-stream for communication with server
        try (
                Socket clientSocket = new Socket(serverAddress, port);
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            System.out.println("Established connection to the server");
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            Session clientChoice;
            // Waits for commands from server
            while ((clientChoice = (Session)inputStream.readObject()) != null) {
                if (clientChoice.getState() == ProtocolState.SERVERSENTQUESTION) {
                    System.out.println("Server: " + clientChoice.getQuestion());
                    // Client answers question here
                    clientChoice.setAnswer(userInput.readLine().trim());
                    clientChoice.setState(ProtocolState.CLIENTSENTANSWER);
                } else if (clientChoice.getState() == ProtocolState.SERVERSENTANSWER) {
                    // Answer result goes here
                    // Update GUI button colors depending on answer
                    if (clientChoice.getVerdict()) {
                        System.out.println("Du svarade rätt!");
                    } else {
                        System.out.println("Du svarade fel!");
                    }

                    clientChoice.setState(ProtocolState.WAITING);
                } else if (clientChoice.getState() == ProtocolState.SERVERENDROUND) {
                    // Notifying server the client has ended its round and waiting
                    clientChoice.setState(ProtocolState.CLIENTENDROUND);
                    outputStream.writeObject(clientChoice);

                    System.out.println("Waiting");
                    // Server should change the state in Session object to WAITING.
                    // Client will continue running afterwards
                    clientChoice = (Session) inputStream.readObject();
                }
                outputStream.writeObject(clientChoice);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client c = new Client("127.0.0.1", 4444);
        c.start();
    }
}
