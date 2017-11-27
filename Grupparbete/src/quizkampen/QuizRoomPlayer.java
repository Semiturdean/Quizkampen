package quizkampen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class QuizRoomPlayer extends Thread {
    private Socket server;
    private char playerMark;
    private int currentQuestion;
    private boolean continueGame;
    private QuizRoomPlayer opponent;
    private QuizRoom room;
    private BufferedReader input;
    private PrintWriter output;
    private List<String> questions;
    private List<String> answers;

    public char getPlayerMark() {
        return playerMark;
    }

    public QuizRoomPlayer getOpponent() {
        return opponent;
    }

    public void setOpponent(QuizRoomPlayer opponent) {
        this.opponent = opponent;
    }

    public QuizRoomPlayer(Socket server, char playerMark, QuizRoom room) {
        this.server = server;
        this.playerMark = playerMark;
        this.room = room;
        currentQuestion = 0;
        continueGame = true;

        try {
            input = new BufferedReader(new InputStreamReader(server.getInputStream()));
            output = new PrintWriter(server.getOutputStream(), true);
            output.println(Commands.WELCOME.toString() + playerMark);
            output.println(Commands.MESSAGE + "Waiting for an opponent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean nextCategory() {
        return room.getCurrentRoundPlayer().getPlayerMark() == playerMark;
    }

    private void clientChooseCategory() {
        /*
        The first connected player gets to choose category.
        The second player waits until a category has been chosen.
         */
        String fromClient;
        if (nextCategory()) {
            output.println(Commands.CHOOSECATEGORY + room.getAvailableCategories()); // TODO should fetch all categories and send it
            try {
                fromClient = input.readLine();
                if (fromClient.startsWith(Commands.CATEGORY.toString())) {
                    fromClient = fromClient.substring(9);
                    room.chooseCategory(fromClient, this);
                    startNewRound();
                    // Notify the second client/server a category has been chosen
                    opponent.output.println(Commands.STARTROUND);
                }
            } catch (IOException e) {

            }
        } else {
            output.println(Commands.WAIT);
            try {
                fromClient = input.readLine();
                if (fromClient.startsWith(Commands.STARTROUND.toString())) {
                    startNewRound();
                }
            } catch (IOException e) {

            }
        }
    }

    private void startNewRound() {
        questions = room.getQuestions();
        answers = room.getAnswers();
        output.println(Commands.QUESTION + questions.get(currentQuestion));
        currentQuestion++;
    }

    private boolean correctAnswer(String answer) {
        for (int i = 0; i < answers.size(); i++) {
            if (answer.equalsIgnoreCase(answers.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        String fromClient;
        output.println(Commands.MESSAGE + "All players have connected. Starting the game");
        clientChooseCategory();

        while (continueGame) {
            try {
                fromClient = input.readLine();
                if (fromClient.startsWith(Commands.ANSWER.toString())) {
                    fromClient = fromClient.substring(7);

                    // Check if the answer is correct and returns result to client
                    if (correctAnswer(fromClient)) {
                        // TODO
                        room.addScoreToPlayerRound(playerMark);
                        System.out.println("X: " + room.getPlayerXScorePerRound());
                        System.out.println("O: " + room.getPlayerOScorePerRound());
                        output.println(Commands.RESULT + "TRUE");
                        System.out.println("X: " + room.getPlayerXScorePerRound());
                        System.out.println("O: " + room.getPlayerOScorePerRound());
                    } else {
                        output.println(Commands.RESULT + "FALSE");
                    }

                    // Check if there are more questions
                    if (room.nextQuestion(currentQuestion)) {
                        output.println(Commands.QUESTION + questions.get(currentQuestion));
                        currentQuestion++;
                    } else {
                        // If there are no more questions, check if there are more rounds
                        if (room.nextRound()) {
                            currentQuestion = 0;
                            // If there are more rounds, check which player gets to choose a category
                            clientChooseCategory();
                        } else {
                            // End game if there are no more rounds
                            output.println(Commands.ENDGAME.toString());
                        }
                    }
                } // Important! This command is only sent from the second player server and not the client
                  else if (fromClient.startsWith(Commands.STARTROUND.toString())) {
                    startNewRound();
                } else if (fromClient.startsWith(Commands.CATEGORY.toString())) {
                    fromClient = fromClient.substring(9);
                    // TODO
                } else if (fromClient.startsWith(Commands.MESSAGE.toString())) {
                    fromClient = fromClient.substring(8);
                    System.out.println(fromClient);
                }
            } catch (IOException e) {
                System.out.println("Player disconnected");
                continueGame = false;
            }
        }
    }
}
