package quizkampen;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameRoom {
    private boolean playerXFinished = false;
    private boolean playerOFinished = false;
    private int totalRounds;
    private int currentRound;
    private String[] answers;
    private String[] questions;
    private ObjectOutputStream playerXOutput;
    private ObjectOutputStream playerOOutput;
    private boolean endGame = false;

    public boolean isEndGame() {
        return endGame;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String[] getQuestions() {
        return questions;
    }

    public void setPlayerOutput(ObjectOutputStream stream, char playerMark) {
        if (playerMark == 'X') {
            playerXOutput = stream;
        } else if (playerMark == 'O') {
            playerOOutput = stream;
        }
    }

    public void setPlayerFinished(char playerMark) {
        if (playerMark == 'X') {
            playerXFinished = true;
        } else if (playerMark == 'O') {
            playerOFinished = true;
        }
    }

    GameRoom() {
        initProperties();
        initGameInfo();
    }

    // This method should read all the questions and answers from a file
    public void initGameInfo() {
        questions = new String[]{"ETT", "TVÅ", "TRE"};
        answers = new String[]{"1", "2", "3"};
    }

    // Game setting read from a property file
    public void initProperties() {
        totalRounds = 2;
        currentRound = 0;
    }

    /*
    Will return an integer depending on the game state.
    0 = The other player is not ready, standby
    1 = Both players are ready, continue to next round
    2 = Both players are ready and there are no more rounds
     */
    public int nextRoundCheck() {
        int nextRound = 0;
        // If both are true continue to next round
        if ((playerXFinished && playerOFinished) && currentRound < totalRounds - 1) {
            // Update game room with new questions and answers
            initGameInfo(); // TODO This should be changed to a method getting questions and answers from another category
            currentRound++;
            playerOFinished = false;
            playerXFinished = false;
            nextRound = 1;
        } else if ((playerXFinished && playerOFinished) && currentRound >= totalRounds - 1) {
            // Notify both players the game has ended
            endGame = true;
            nextRound = 2;
        }
        return nextRound;
    }

    public void notifyOtherPlayer(char currentPlayerMark) {
        // Notify the player can continue if they are waiting on second player
        try {
            if (currentPlayerMark == 'X') {
                playerOOutput.writeObject(null);
            } else {
                playerXOutput.writeObject(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
