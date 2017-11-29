package quizkampen;

import java.io.File;
import java.io.IOException;

public class ChooseCategory extends SearchCategory {

    private File Music = new File("Musik.txt");
    private File Geografi = new File("Geografi.txt");
    private File Historia = new File("Historia.txt");
    private int questionsPerRound;

    private String currentCategory;

    ChooseCategory(int questionsPerRound) {
        this.questionsPerRound = questionsPerRound;
    }

    public void setCurrentCategory(String category) {
        currentCategory = category;
    }

    public void setCategoryQuestions() {
        try {
            if (currentCategory.equalsIgnoreCase("Musik")) {
                readFile(Music, questionsPerRound);
            } else if (currentCategory.equalsIgnoreCase("Geografi")) {
                readFile(Geografi, questionsPerRound);
            } else if (currentCategory.equalsIgnoreCase("Historia")) {
                readFile(Historia, questionsPerRound);
            }
        } catch (IOException e) {
        }
    }

    public String getCurrentCategory() {
        return currentCategory;
    }
}
