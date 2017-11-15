package quizkampen;

import java.io.Serializable;

public class Session implements Serializable {
    private ProtocolState state;
    private String question;
    private String answer;
    private boolean verdict;

    public ProtocolState getState() {
        return state;
    }

    public void setState(ProtocolState state) {
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

    public boolean getVerdict() {
        return verdict;
    }

    public void setVerdict(boolean verdict) {
        this.verdict = verdict;
    }

    Session() {
        state = ProtocolState.WAITING;
        verdict = false;
    }

    Session(String question) {
        this.question = question;
        state = ProtocolState.WAITING;
        verdict = false;
    }
}
