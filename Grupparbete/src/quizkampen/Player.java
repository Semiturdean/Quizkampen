package quizkampen;

import java.io.*;
import java.net.Socket;

public class Player implements Runnable {
	private String username;
	private String password;
	private String membershipType;
	private Socket serverConnection;
    private Protocol protocol;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = ((membershipType == "basic") || (membershipType == "premium") ? membershipType : null);
	}

    // Ändrat konstruktorn till tom
    public Player() {
        membershipType = "basic";
    }

    public Player(Socket serverConnection, Protocol protocol) {
        this.serverConnection = serverConnection;
        this.protocol = protocol;
    }

    @Override
    public void run() {
        // Create the necessary input- and output-stream for communication with client
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(serverConnection.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(serverConnection.getInputStream())
        ) {
            // Initialization with client
            Session input;
            Session output = protocol.getInitialSession();
            outputStream.writeObject(output);

            // Waiting on commands from server
            while ((input = (Session)inputStream.readObject()) != null) {
                // Process command
                output = protocol.processInput(input);

                // TODO Server should end round and handle it here


                // Send response to server
                outputStream.writeObject(output);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
            // Handle when a player disconnects
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
}
