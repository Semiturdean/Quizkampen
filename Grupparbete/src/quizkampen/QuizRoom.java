package quizkampen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QuizRoom {
    private int totalRounds;
    private int questionsPerRound;
    private int currentRound;
    private int[] playerXScorePerRound;
    private int[] playerOScorePerRound;
    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private List<String> availableCategories = new ArrayList<>();
    private QuizRoomPlayer currentRoundPlayer;
    private ChooseCategory chosenCategory;

    public String getAvailableCategories() {
        String text = "";
        int i = 0;
        for (String s : availableCategories) {
            if(i != 2) {
                text += s + ",";
                i++;
            }
            else if( i == 2)
            text += s;
        }
        return text;
    }

    public int getPlayerXScorePerRound() {
        return playerXScorePerRound[currentRound - 1];
    }

    public int getPlayerOScorePerRound() {
        return playerOScorePerRound[currentRound - 1];
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public QuizRoomPlayer getCurrentRoundPlayer() {
        return currentRoundPlayer;
    }

    public void setCurrentRoundPlayer(QuizRoomPlayer currentRoundPlayer) {
        this.currentRoundPlayer = currentRoundPlayer;
    }

    public QuizRoom() {
        currentRound = 0;
        availableCategories.add("Geografi");
        availableCategories.add("Musik");
        availableCategories.add("Historia");
        readPropertyFile();
        playerXScorePerRound = new int[totalRounds];
        playerOScorePerRound = new int[totalRounds];
        chosenCategory = new ChooseCategory();
        mockDatabase();
    }

    private void removeCategory(String category) {
        // Remove used category from available ones
        availableCategories.remove(category);
    }

    // Only for testing
    private void mockDatabase() {
        questions.add("Fråga 1");
        questions.add("Fråga 2");
        questions.add("Fråga 3");
        answers.add("ett");
        answers.add("två");
        answers.add("tre");
    }

    public void chooseCategory(String category, QuizRoomPlayer player) {
        // TODO should call a method to fetch a category's questions and answers
        chosenCategory.setCurrentCategory(category);
        chosenCategory.setCategoryQuestions();
        questions = chosenCategory.getQuestions();
        removeCategory(category);
        // Opponent will choose the category next time
        currentRoundPlayer = player.getOpponent();
        currentRound++;
    }

    private void readPropertyFile() {
        String path = "settings.properties";
        File f = new File(path);
        if (f.exists() && !f.isDirectory()) {
            Properties p = new Properties();
            try {
                p.load(new FileInputStream(path));
                String rounds = p.getProperty("totalRounds", "2");
                String questions = p.getProperty("questionsPerRound", "2");
                totalRounds = Integer.parseInt(rounds);
                questionsPerRound = Integer.parseInt(questions);

                // If the value is zero or less for either totalRounds or questionsPerRound.
                // Set it to the default value, 2.
                if (totalRounds <= 0) {
                    totalRounds = 2;
                } else if (questionsPerRound <= 0) {
                    questionsPerRound = 0;
                } else if (totalRounds <= 0 && questionsPerRound <= 0) {
                    totalRounds = 2;
                    questionsPerRound = 2;
                }
            } catch (FileNotFoundException e) {
                System.out.println("The file could not be found.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            totalRounds = 2;
            questionsPerRound = 2;
        }
    }

    public boolean nextQuestion(int currentQuestion) {
        return currentQuestion < questionsPerRound;
    }

    public boolean nextRound() {
        return currentRound < totalRounds;
    }

    public void addScoreToPlayerRound(char playerMark) {
        if (playerMark == 'X') {
            playerXScorePerRound[currentRound - 1]++;
        } else if (playerMark == 'O') {
            playerOScorePerRound[currentRound - 1]++;
        }
    }
}
