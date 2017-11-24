package quizkampen;

import java.util.*;

public class Questions {

    private List<String> questionOne = new ArrayList<>();
    private List<String> questionTwo = new ArrayList<>();
    private List<String> questionThree = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    String answerOne;
    String answerTwo;
    String answerThree;

    public void setQuestionOne(List<String> questionOne) {
        String s = questionOne.get(0);
        questionOne = Arrays.asList(s.split(","));
        this.questionOne = questionOne;
        answerOne = questionOne.get(1);
    }

    public void setQuestionTwo(List<String> questionTwo) {
        String s = questionTwo.get(0);
        questionTwo = Arrays.asList(s.split(","));
        this.questionTwo = questionTwo;
        answerTwo = questionTwo.get(1);
    }

    public void setQuestionThree(List<String> questionThree) {
        String s = questionThree.get(0);
        questionThree = Arrays.asList(s.split(","));
        this.questionThree = questionThree;
        answerThree = questionThree.get(1);
        setAnswers(answerOne, answerTwo, answerThree);
    }

    public List<String> getQuestionOne() {
        return questionOne;
    }

    public List<String> getQuestionTwo() {
        return questionTwo;
    }

    public List<String> getQuestionThree() {
        return questionThree;
    }

    public void setAnswers(String one, String two, String three) {
        this.answers = answers;
        answers.add(one);
        answers.add(two);
        answers.add(three);
    }

    public List<String> getAnswers() {
        return answers;
    }
}
