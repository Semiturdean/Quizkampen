package quizkampen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Questions {

    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();

    public void setQuestions(List<String> questions) {
        String s = questions.toString();
        questions = Arrays.asList(s.split(","));
        this.questions = questions;
        setAnswers();
    }

    public void setAnswers() {
        Scanner s = null;
        try {
            s = new Scanner(new File("Svar.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (s.hasNext()){
            answers.add(s.next());
        }
        s.close();
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getAnswers() {
        return answers;
    }

}


