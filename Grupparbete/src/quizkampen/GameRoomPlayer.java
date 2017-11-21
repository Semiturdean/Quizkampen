package quizkampen;

import java.io.*;
import java.net.Socket;

public class GameRoomPlayer extends Thread {
	private String username;
	private String password;
	private String membershipType;
	private Socket serverConnection;
    private Protocol protocol;
    private GameRoom game;
    private char playerMark;

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

    public Socket getServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(Socket serverConnection) {
        this.serverConnection = serverConnection;
    }

    // Ändrat konstruktorn till tom
    public GameRoomPlayer() {
        membershipType = "basic";
    }

    public GameRoomPlayer(Socket serverConnection, GameRoom game, char playerMark) {
        this.serverConnection = serverConnection;
        this.game = game;
        this.playerMark = playerMark;
        protocol = new Protocol();
        protocol.setQuestions(game.getQuestions());
        protocol.setAnswers((game.getAnswers()));
    }

    @Override
    public void run() {
        // Create the necessary input- and output-stream for communication with client
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(serverConnection.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(serverConnection.getInputStream())
        ) {
            // Initialization with client
            Session fromClient;
            Session toClient = protocol.getInitialSession();
            outputStream.writeObject(toClient);

            // Waiting on commands from client
            while ((fromClient = (Session)inputStream.readObject()) != null) {
                // Process command
                toClient = protocol.processInput(fromClient);

                // TODO Server should end round and handle it here
                // If server has ended round, notify the game room the player is finished
                if (toClient.getState() == ProtocolState.CLIENTENDROUND) {
                    game.setPlayersFinished(playerMark);
                }

                // Check if both players are finished
                if (game.isBothPlayersFinished()) {
                    protocol = game.nextRound(protocol);
                    toClient = protocol.getInitialSession();
                    toClient.setState(ProtocolState.SERVERSENTQUESTION);
                }

                // Send response to client
                outputStream.writeObject(toClient);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
            // Handle when a player disconnects
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
}
