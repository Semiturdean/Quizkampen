package quizkampen;

import java.util.*;

public class Questions {

    private List<String> questionOne = new ArrayList<>();
    private List<String> questionTwo = new ArrayList<>();
    private List<String> questionThree = new ArrayList<>();

    private String FirstQuestion;
    private String SecondQuestion;
    private String ThirdQuestion;

    private String RightAnswerOne;
    private String RightAnswerTwo;
    private String RightAnswerThree;

    private List<String> firstQuestionAllAnswers = new ArrayList<>();
    private List<String> secondQuestionAllAnswers = new ArrayList<>();
    private List<String> thirdQuestionAllAnswers = new ArrayList<>();

    private List<String> answers = new ArrayList<>();



    public void setQuestionOne(List<String> questionOne) {
        String s = questionOne.get(0);
        questionOne = Arrays.asList(s.split(","));
        this.questionOne = questionOne;
        setFirstQuestion(questionOne.get(0));
        setFirstQuestionAllQuestions(questionOne.subList(1,5));
    }

    public void setQuestionTwo(List<String> questionTwo) {
        String s = questionTwo.get(0);
        questionTwo = Arrays.asList(s.split(","));
        this.questionTwo = questionTwo;
        setSecondQuestion(questionTwo.get(0));
        setSecondQuestionAllAnswers(questionTwo.subList(1,5));
    }

    public void setQuestionThree(List<String> questionThree) {
        String s = questionThree.get(0);
        questionThree = Arrays.asList(s.split(","));
        this.questionThree = questionThree;
        setThirdQuestion(questionThree.get(0));
        setThirdQuestionAllAnswers(questionThree.subList(1,5));
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

    public void setFirstQuestion(String firstQuestion) {
        FirstQuestion = firstQuestion;
    }

    public void setSecondQuestion(String secondQuestion) {
        SecondQuestion = secondQuestion;
    }

    public void setThirdQuestion(String thirdQuestion) {
        ThirdQuestion = thirdQuestion;
    }

    public String getFirstQuestion() {
        return FirstQuestion;
    }

    public String getSecondQuestion() {
        return SecondQuestion;
    }

    public String getThirdQuestion() {
        return ThirdQuestion;
    }

    public void setFirstQuestionAllQuestions(List<String> firstQuestionAllAnswers) {
        setRightAnswerOne(firstQuestionAllAnswers.get(0));
        Collections.shuffle(firstQuestionAllAnswers);
        this.firstQuestionAllAnswers = firstQuestionAllAnswers;
    }


    public void setSecondQuestionAllAnswers(List<String> secondQuestionAllAnswers) {
        setRightAnswerTwo(secondQuestionAllAnswers.get(0));
        Collections.shuffle(secondQuestionAllAnswers);
        this.secondQuestionAllAnswers = secondQuestionAllAnswers;
    }

    public void setThirdQuestionAllAnswers(List<String> thirdQuestionAllAnswers) {
        setRightAnswerThree(thirdQuestionAllAnswers.get(0));
        Collections.shuffle(thirdQuestionAllAnswers);
        this.thirdQuestionAllAnswers = thirdQuestionAllAnswers;
    }

    public List<String> getFirstQuestionAllAnswers() {
        return firstQuestionAllAnswers;
    }

    public List<String> getSecondQuestionAllAnswers() {
        return secondQuestionAllAnswers;
    }

    public List<String> getThirdQuestionAllAnswers() {
        return thirdQuestionAllAnswers;
    }

    public void setRightAnswerOne(String rightAnswerOne) {
        RightAnswerOne = rightAnswerOne;
    }

    public void setRightAnswerTwo(String rightAnswerTwo) {
        RightAnswerTwo = rightAnswerTwo;
    }

    public void setRightAnswerThree(String rightAnswerThree) {
        RightAnswerThree = rightAnswerThree;
    }

    public String getRightAnswerOne() {
        return RightAnswerOne;
    }

    public String getRightAnswerTwo() {
        return RightAnswerTwo;
    }

    public String getRightAnswerThree() {
        return RightAnswerThree;
    }


    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getAnswers() {
        return answers;
    }
}

