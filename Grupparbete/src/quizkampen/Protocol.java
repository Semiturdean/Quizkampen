package quizkampen;

public class Protocol {
    private int currentQuestion = 0;
    private int totalRounds;

    private String[] questions = null;
    private String[] answers = null;

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    Protocol() {
        // TODO
        //totalRounds = questions.length;
    }

    public Session processInput(Session userInput) {
        // Will runt if the state is waiting and there are more questions
        if (userInput.getState() == ProtocolState.WAITING) {
            if (currentQuestion < questions.length) {
                userInput.setState(ProtocolState.SERVERENDROUND);
                return userInput;
            } else {
                userInput.setQuestion(questions[currentQuestion]);
                userInput.setState(ProtocolState.SERVERSENTQUESTION);
            }
        } else if (userInput.getState() == ProtocolState.CLIENTSENTANSWER) {
            // Runs after the client has answered
            if (userInput.getAnswer().equalsIgnoreCase(answers[currentQuestion])) {
                userInput.setVerdict(true);
            } else {
                userInput.setVerdict(false);
            }
            userInput.setState(ProtocolState.SERVERSENTANSWER);
            currentQuestion++;
        } else if (userInput.getState() == ProtocolState.CLIENTENDROUND) {

        }
        return userInput;
    }

    public Session getInitialSession() {
        currentQuestion = 0;
        Session session = new Session();
        session.setQuestion(questions[0]);
        return session;
    }
}
