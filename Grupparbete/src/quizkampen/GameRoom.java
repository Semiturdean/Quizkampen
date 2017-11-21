package quizkampen;

public class GameRoom {
    private boolean playerXFinished = false;
    private boolean playerOFinished = false;
    private int totalRounds;
    private int currentRound;
    private String[] answers;
    private String[] questions;

    public void setPlayersFinished(char playerMark) {
        if (playerMark == 'X') {
            playerXFinished = true;
        } else if (playerMark == 'O') {
            playerOFinished = true;
        }
    }

    public boolean isBothPlayersFinished() {
        return playerXFinished && playerOFinished;
    }

    public String[] getQuestions() {
        return questions;
    }

    public String[] getAnswers() {
        return answers;
    }

    GameRoom() {
        initProperties();
        initGameInfo();
    }

    public void initGameInfo() {
        questions = new String[]{"ETT", "TVÅ", "TRE"};
        answers = new String[]{"1", "2", "3"};
    }

    public void initProperties() {
        totalRounds = 2;
        currentRound = 0;
    }

    public Protocol nextRound(Protocol protocol) {
        if (currentRound > totalRounds) {
            protocol.setAnswers(getAnswers());
            protocol.setQuestions(getQuestions());
            currentRound++;
        }
        playerOFinished = false;
        playerXFinished = false;
        return protocol;
    }
}
