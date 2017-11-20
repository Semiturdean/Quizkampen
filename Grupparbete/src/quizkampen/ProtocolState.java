package quizkampen;

public enum ProtocolState {
    WAITING,
    SERVERSENTQUESTION,
    CLIENTSENTANSWER,
    SERVERSENTANSWER,
    SERVERENDROUND
}
