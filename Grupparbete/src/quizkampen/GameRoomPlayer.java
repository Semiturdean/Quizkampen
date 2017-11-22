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
        // Setup the protocol with all the questions and answers
        newProtocol();
    }

    public void newProtocol() {
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
            // Send info to the game room for later communication
            game.setPlayerOutput(outputStream, playerMark);

            // Get a new session object to send between server and client
            Session fromClient;
            Session toClient = protocol.getNewSession();
            outputStream.writeObject(toClient);

            // Waiting on commands from client
            while ((fromClient = (Session)inputStream.readObject()) != null) {
                // Process command
                toClient = protocol.processInput(fromClient);

                /*
                If client has answered all their questions for the given round,
                notify game room and await further commands from it.
                 */
                if (toClient.getState() == ProtocolState.CLIENTWAITING) {
                    game.setPlayerFinished(playerMark);
                    int checkResult = game.nextRoundCheck();
                    if (checkResult == 1) {           // STATE 1
                        // Run if both players are ready
                        newProtocol();
                        toClient = protocol.getNewSession();
                        toClient.setState(ProtocolState.SERVERSENTQUESTION);
                        game.notifyOtherPlayer(playerMark);
                    } else if (checkResult == 0) {    // STATE 0
                        /*
                        Wait if the other player is not finished.
                        Getting a null from the input stream means the game room
                        have notified the player can continue to the next round.
                         */
                        Object temp = inputStream.readObject();
                        if (temp == null) {
                            if (!game.isEndGame()) {
                                newProtocol();
                                toClient = protocol.getNewSession();
                                toClient.setState(ProtocolState.SERVERSENTQUESTION);
                            } else {
                                toClient.setState(ProtocolState.ENDGAME);
                            }
                        }
                    } else if (checkResult == 2) {    // STATE 2
                        // No more rounds, notify both players
                        toClient.setState(ProtocolState.ENDGAME);
                        game.notifyOtherPlayer(playerMark);
                    }
                }
                // Send response to client
                outputStream.writeObject(toClient);
            }
            System.out.println("Game has ended");
        } catch (IOException e) {
            System.out.println("Client disconnected");
            // Handle when a player disconnects
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
}
