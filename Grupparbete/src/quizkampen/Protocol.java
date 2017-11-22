package quizkampen;

public class Protocol {
    private int currentQuestion = 0;

    private String[] questions = null;
    private String[] answers = null;

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    Protocol() {

    }

    /*
    All states starting with "SERVER" are commands sent to the client.
    States starting with "CLIENT" are sent to the server.
     */
    public Session processInput(Session userInput) {
        // Will runt if the state is waiting and there are more questions
        if (userInput.getState() == ProtocolState.WAITING) {
            if (currentQuestion > questions.length - 1) {
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
        } else if (userInput.getState() == ProtocolState.CLIENTWAITING ||
                userInput.getState() == ProtocolState.ENDGAME) {
            // Just pass the session object back to server without processing
        }
        return userInput;
    }

    public Session getNewSession() {
        currentQuestion = 0;
        Session session = new Session();
        session.setQuestion(questions[0]);
        return session;
    }
}
