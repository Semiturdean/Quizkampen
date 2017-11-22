package quizkampen;

import java.io.IOException;

public class Protocol {
    private static final int WAITING = 0;
    private static final int SERVERSENTQUESTION = 1;
    private static final int CLIENTSENTANSWER = 2;
    private static final int SERVERSENTANSWER = 3;
    private static final int ROUNDCHECK = 4;

    private int currentQuestion = 0;
    private int currentRound = 0;
    private int totalRounds;

    String[] Categories = new String[]{"Musik, Geografi, Historia"};
    String CurrentCategory = "Musik";
    ChooseCategory test = new ChooseCategory(CurrentCategory);

    private String[] questions = {"ETT", "TVÅ", "TRE"};
    private String[] answers = {"1", "2", "3"};

    public String[] getQuestions() {
        return questions;
    }

    public String[] getAnswers() {
        return answers;
    }

    Protocol() throws IOException {
        ChooseCategory test = new ChooseCategory("Musik");
        totalRounds = questions.length;
    }

    public Session processInput(Session userInput) {
        if (userInput.getState() == ProtocolState.WAITING) {
            // Will run after first initialization and next question
            userInput.setQuestion(test.getFirstQuestion());
            userInput.setState(ProtocolState.SERVERSENTQUESTION);
        } else if (userInput.getState() == ProtocolState.CLIENTSENTANSWER) {
            // Runs after the client has answered
            if (userInput.getAnswer().equalsIgnoreCase(test.getRightAnswerOne())) {
                userInput.setVerdict(true);
            } else {
                userInput.setVerdict(false);
            }
            userInput.setState(ProtocolState.SERVERSENTANSWER);
            currentQuestion++;
        }
        return userInput;
    }

    public Session getInitialSession() {
        Session session = new Session();
        session.setQuestion(test.getFirstQuestion());
        return session;
    }
}
