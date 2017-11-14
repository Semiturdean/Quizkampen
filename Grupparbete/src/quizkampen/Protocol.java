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
    private int totalRounds;

    private String[] questions = {"ETT", "TVÅ", "TRE"};
    private String[] answers = {"1", "2", "3"};

    public String[] getQuestions() {
        return questions;
    }

    public String[] getAnswers() {
        return answers;
    }

    Protocol() {
        totalRounds = questions.length;
    }

    public Session processInput(Session userInput) {
        if (userInput.getState() == WAITING) {
            // Will run after first initialization and next question
            userInput.setQuestion(questions[currentQuestion]);
            userInput.setState(SERVERSENTQUESTION);
        } else if (userInput.getState() == CLIENTSENTANSWER) {
            // Runs after the client has answered
            if (userInput.getAnswer().equalsIgnoreCase(answers[currentQuestion])) {
                userInput.setVerdict(true);
                userInput.setState(SERVERSENTANSWER);
            } else {
                userInput.setVerdict(false);
                userInput.setState(SERVERSENTANSWER);
            }
            currentQuestion++;
        }
        return userInput;
    }

    public Session getInitialSession() {
        Session session = new Session();
        session.setQuestion(questions[0]);
        return session;
    }
}
