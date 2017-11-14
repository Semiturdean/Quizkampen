package quizkampen;

import java.io.Serializable;

public class Session implements Serializable {
    private int state;
    private String question;
    private String answer;
    private boolean verdict;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isVerdict() {
        return verdict;
    }

    public void setVerdict(boolean verdict) {
        this.verdict = verdict;
    }

    Session() {
        state = 0;
        verdict = false;
    }
}
