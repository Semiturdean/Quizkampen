package quizkampen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Properties;

public class GameRoom {
    private boolean playerOFinished;
    private boolean playerXFinished;
    private int totalRounds;
    private int questionsPerRound;
    private int currentRound;
    private String[] answers;
    private String[] questions;
    private ObjectOutputStream playerXOutput;
    private ObjectOutputStream playerOOutput;
    private boolean endGame;

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
        currentRound = 0;
        endGame = false;
        playerOFinished = false;
        playerXFinished = false;
    }

    // TODO Create methods getting specific questions/answers from a category and the amount
    // This method should read all the questions and answers from a file
    public void initGameInfo() {
        questions = new String[]{"ETT", "TVÅ", "TRE"};
        answers = new String[]{"1", "2", "3"};
    }

    /*
    Game setting read from a property file.
    If the file could not be found, default values will be used
     */
    public void initProperties() {
        File f = new File("settings.properties");
        if (f.exists() && !f.isDirectory()) {
            Properties p = new Properties();
            try {
                p.load(new FileInputStream("settings.properties"));
                String rounds = p.getProperty("totalRounds", "2");
                String questions = p.getProperty("questionsPerRound", "2");
                totalRounds = Integer.parseInt(rounds);
                questionsPerRound = Integer.parseInt(questions);
            } catch (IOException e) {
                System.out.println("The file could not be found.");
            }
        } else {
            totalRounds = 2;
            questionsPerRound = 2;
        }
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
