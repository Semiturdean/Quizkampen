package quizkampen;

import java.io.File;
import java.io.IOException;

public class ChooseCategory extends SearchCategory {

    private File Music = new File("C:\\Users\\Stanley\\Documents\\Java17\\OOP\\JAVA\\QuizServer\\Musik.txt");
    private File Geografi = new File("Geografi.txt");
    private File Historia = new File("Historia.txt");

    private String currentCategory;

    public void setCurrentCategory(String category) {
        currentCategory = category;
    }

    public void setCategoryQuestions() {
        try {
            if (currentCategory.equalsIgnoreCase("Musik")) {
                readFile(Music);
            } else if (currentCategory.equalsIgnoreCase("Geografi")) {
                readFile(Geografi);
            } else if (currentCategory.equalsIgnoreCase("Historia")) {
                readFile(Historia);
            }
        } catch (IOException e) {
        }
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    ChooseCategory() {

    }

}
