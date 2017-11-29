package quizkampen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
    private List<String> questions = new ArrayList<>();
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
        questions = new ArrayList<>();
        questions.addAll(room.getQuestions());
        //questions = room.getQuestions();
        answers = room.getAnswers();
        //output.println(Commands.QUESTION + questions.get(currentQuestion));
        output.println(Commands.QUESTION + convertListToString());
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

    private String convertListToString() {
        List<String> temp = new ArrayList<>();
        temp.addAll(questions);
        String s = "";
        for (int i = 0; i < 5; i++) {
            s += questions.get(i);
            if (i >= 0 && i < 4) {
                s += ",";
            }
        }
        temp.subList(0,5).clear();
        questions.clear();
        questions.addAll(temp);
        //System.out.println(questions.size());
        return s;
    }

    private boolean isBothPlayersFinished() {
        return room.isPlayerOFinish() && room.isPlayerXFinish();
    }

    private void checkPlayersFinish() {
        room.setPlayerFinish(playerMark);
        if (isBothPlayersFinished()) {
            output.println(Commands.SCORE + room.getScoreResult(playerMark));
            // Notify other server/client to get final score
            opponent.output.println(Commands.SENDSCORE);
            output.println(Commands.ENDGAME);
        } else {
            output.println(Commands.WAITSCORE);
        }
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
                        room.addScoreToPlayerRound(playerMark);
                        output.println(Commands.RESULT + "TRUE");
                    } else {
                        output.println(Commands.RESULT + "FALSE");
                    }

                    // Check if there are more questions
                    if (room.nextQuestion(currentQuestion)) {
                        output.println(Commands.QUESTION + convertListToString());
                        currentQuestion++;
                    } else {
                        // If there are no more questions, check if there are more rounds
                        if (room.nextRound()) {
                            currentQuestion = 0;
                            // If there are more rounds, check which player gets to choose a category
                            clientChooseCategory();

                        } else {
                            // End game if there are no more rounds
                            room.setPlayerFinish(playerMark);
                            checkPlayersFinish();
                        }
                    }
                } // Important! This command is only sent from the second player server and not the client
                  else if (fromClient.startsWith(Commands.STARTROUND.toString())) {
                    startNewRound();
                } /*else if (fromClient.startsWith(Commands.CATEGORY.toString())) {
                    fromClient = fromClient.substring(9);

                    // TODO
                }*/ else if (fromClient.startsWith(Commands.MESSAGE.toString())) {
                    fromClient = fromClient.substring(8);
                    System.out.println(fromClient);
                } else if (fromClient.startsWith(Commands.SENDSCORE.toString())) {
                    output.println(Commands.SCORE + room.getScoreResult(playerMark));
                    output.println(Commands.ENDGAME);
                }
            } catch (IOException e) {
                System.out.println("Player disconnected");
                continueGame = false;
            }
        }
    }
}
