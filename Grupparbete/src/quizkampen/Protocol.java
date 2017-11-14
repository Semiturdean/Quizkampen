package quizkampen;

public class Protocol {
    private static final int WAITING = 0;
    private static final int SERVERSENTQUESTION = 1;
    private static final int CLIENTSENTANSWER = 2;
    private static final int SERVERSENTANSWER = 3;
    private static final int ROUNDCHECK = 4;

    private int state = WAITING;
    private int currentQuestion = 0;
    private int currentRound = 0;

    private String[] questions = {"ETT", "TVÅ", "TRE"};
    private String[] answers = {"1", "2", "3"};

    public String[] getQuestions() {
        return questions;
    }

    public String[] getAnswers() {
        return answers;
    }

    public Session processInput(Session userInput) {
        Session output = null;

        if (userInput.getState() == WAITING || userInput.getState() == SERVERSENTANSWER) {
            userInput.setQuestion(questions[currentQuestion]);
            userInput.setState(SERVERSENTQUESTION);
        } else if (userInput.getState() == CLIENTSENTANSWER) {
            currentQuestion++;
            if (userInput.getAnswer().equalsIgnoreCase(answers[currentQuestion])) {
                userInput.setVerdict(true);
                userInput.setState(SERVERSENTANSWER);
            } else {
                userInput.setVerdict(false);
                userInput.setState(SERVERSENTANSWER);
            }
        }
        return output;
    }
}
