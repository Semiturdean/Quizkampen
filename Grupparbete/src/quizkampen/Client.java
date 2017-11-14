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
            while ((clientChoice = (Session)inputStream.readObject()) != null) {
                if (clientChoice.getState() == 1) {
                    System.out.println("Server: " + clientChoice.getQuestion());
                    clientChoice.setAnswer(userInput.readLine().trim()); // Client answers question here
                    clientChoice.setState(2);
                } else if (clientChoice.getState() == 3) {
                    // Answer result goes here
                    if (clientChoice.getVerdict()) {
                        System.out.println("Du svarade rätt!");
                    } else {
                        System.out.println("Du svarade fel!");
                    }
                    // Update GUI button colors depending on answer

                    clientChoice.setState(0);
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
