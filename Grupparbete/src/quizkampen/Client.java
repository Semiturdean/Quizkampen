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

            Object inputStreamObject, outputStreamObject;
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while ((inputStreamObject = inputStream.readObject()) != null) {
                System.out.println(inputStreamObject.toString());

                // User can type something in the console and send it to the server
                // Only in testing phase
                if ((outputStreamObject = userInput.readLine()) != null) {
                    outputStream.writeObject(outputStreamObject);
                }
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
