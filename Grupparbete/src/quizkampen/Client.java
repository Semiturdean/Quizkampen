package quizkampen;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Client extends JFrame implements ActionListener, PanelListener {
    // Server variables
    private Socket clientConnection;
    private boolean continueGame;
    private BufferedReader input;
    private PrintWriter output;
    private JTextField textField = new JTextField(10);
    private JButton categoryButton = new JButton("Skicka kategori");
    private JButton sendAnswer = new JButton("Send answer");
    private String text = "";
    private Frame frame;
    private String serverAddress;
    private int port;

    // GUI variables
    // paneler,labels,knappar
    private JPanel userInfo = new JPanel();
    private int userScore = 0;
    private int opponentScore = 0;
    private JLabel resultLabel = new JLabel();
    private QuestionPanel questionPanel = new QuestionPanel();
    private CategoryPanel categoryPanel = new CategoryPanel();
    private MessagePanel messagePanel = new MessagePanel();
    private EndOfRoundPanel endOfRoundPanel = new EndOfRoundPanel();
    private StartPanel startPanel = new StartPanel();
    private int questionCounter = 0;

    Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;

        // layouts, till�gg av labels och knappar p� panelen, storlek, visibility etc
        setLayout(new BorderLayout());
        setBackground(Color.BLUE);

        add(startPanel, BorderLayout.CENTER);
        startPanel.setPanelListener(this);

        questionPanel.setPanelListener(this);

        endOfRoundPanel.setPanelListener(this);

        //categoryPanel.setButtonNames(categoryList);
        categoryPanel.setPanelListener(this);

        userInfo.setLayout(new FlowLayout());
        userInfo.add(resultLabel);
        userInfo.setBackground(Color.PINK); userInfo.setBorder(new LineBorder(Color.BLACK, 2));
        resultLabel.setVisible(false);

        getScoreBoard();
        userInfo.setBackground(Color.PINK);
        userInfo.setBorder(new LineBorder(Color.BLACK, 2));


        add(messagePanel, BorderLayout.SOUTH);

        setSize(800,800);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /*
    Client
     */

    public void connectToServer() {
        try {
            clientConnection = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            output = new PrintWriter(clientConnection.getOutputStream(), true);
            continueGame = true;
            startToCategoryPanel();
            startGame();
        } catch (IOException e) {
            System.out.println("Could not connect to server");
        }
    }

    public void sendCategory(String category) {
        output.println(Commands.CATEGORY + category);
    }

    private void sendAnswer(String answer) {
        output.println(Commands.ANSWER + answer);
    }

    private List<String> splitToList(String fromServer) {
        return Arrays.asList(fromServer.split(","));
    }

    public void startGame() throws IOException {
        String fromServer;

        fromServer = input.readLine();
        if (fromServer.startsWith(Commands.WELCOME.toString())) {
            System.out.println("Welcome");
        }

        while (continueGame) {
            fromServer = input.readLine();
            if (fromServer.startsWith(Commands.WAIT.toString())) {
                System.out.println("Waiting on other player");
            } else if (fromServer.startsWith(Commands.QUESTION.toString())) {
                fromServer = fromServer.substring(9);
                List<String> list = splitToList(fromServer); // TODO
                System.out.println("Question: " + fromServer);
            } else if (fromServer.startsWith(Commands.RESULT.toString())) {
                fromServer = fromServer.substring(7);
                if (fromServer.equalsIgnoreCase("TRUE")) {
                    System.out.println("Correct answer");
                } else if (fromServer.equalsIgnoreCase("FALSE")) {
                    System.out.println("Incorrect answer");
                }
            } else if (fromServer.startsWith(Commands.CHOOSECATEGORY.toString())) {
                List<String> list = splitToList(fromServer); // TODO

                //frame.setCategory(list);
            } // This command from the server will be received when the next next round has been loaded
              else if (fromServer.startsWith(Commands.STARTROUND.toString())) {
                // Notify server to start the round
                output.println(Commands.STARTROUND);
            } else if (fromServer.startsWith(Commands.MESSAGE.toString())) {
                System.out.println(fromServer.substring(8));
            } else if (fromServer.startsWith(Commands.WAITSCORE.toString())) {
                System.out.println("Waiting on other player to finish their game");
            } else if (fromServer.startsWith(Commands.SCORE.toString())) {
                // Get both players' scores
                // First number is the player score, second number the opponent
                fromServer = fromServer.substring(6);
                List<String> scores = splitToList(fromServer);
                System.out.println(scores);
            } // This command from the server will be received when both players are finished with their game
              else if (fromServer.startsWith(Commands.SENDSCORE.toString())) {
                output.println(Commands.SENDSCORE);
            } else if (fromServer.startsWith(Commands.ENDGAME.toString())) {
                continueGame = false;
                System.out.println("Game has ended");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == categoryButton)
        {
            // textField.getAction();
            text += textField.getText();
            //System.out.println(text);
            sendCategory(text);
            text = "";
        }
        if(e.getSource() == textField){

        }
        if (e.getSource() == sendAnswer) {
            text = Commands.ANSWER.toString();
            text += textField.getText();
            output.println(text);
            text = "";
        }
    }

    /*

     */

    /*
    GUI
     */

    // skapar scoreboarden under spelets g�ng
    public void getScoreBoard(){

        //resultLabel.setText(user.getUsername()+"     "+userScore+" - "+"DOLD"+"     "+"Motst�ndare");
        resultLabel.setFont(new Font("Serif", Font.BOLD, 32));
        userInfo.add(resultLabel);
        resultLabel.setVisible(true);
        categoryPanel.setVisible(true);
    }

    public void getNewScoreBoard() {

        userInfo.remove(resultLabel);
        userInfo.add(resultLabel);
        //resultLabel.setText(user.getUsername()+"     "+userScore+" - "+opponentScore+"     "+"Motst�ndare");

    }

    public void nextQuestion() {
        questionCounter++;
        //questionPanel.setQuestion(questionList.get(questionCounter));
    }

    public void categoryToQuestionPanel(String categoryName) {
        //setCategory(categoryName);
        //questionList = category.getQuestionList();
        remove(categoryPanel);
        questionCounter = 0;
        questionPanel.setQuestionCounter(0);
        //questionPanel.setQuestion(questionList.get(questionCounter));
        repaint();
        add(questionPanel, BorderLayout.CENTER);
    }

    public void questionToEndOfRoundPanel() {
        remove(questionPanel);
        getNewScoreBoard();
        endOfRoundPanel.setLabel(resultLabel.getText());
        endOfRoundPanel.enableButton();
        repaint();
        add(endOfRoundPanel, BorderLayout.CENTER);
    }

    public void endOfRoundToCategoryPanel() {
        remove(endOfRoundPanel);
        repaint();
        add(categoryPanel, BorderLayout.CENTER);
    }

    public void setScore() {
        userInfo.remove(resultLabel);
        userScore++;
        repaint();
        getScoreBoard();
    }

    public void startToCategoryPanel() {
        //user.setUsername(username);
        remove(startPanel);
        repaint();
        add(userInfo, BorderLayout.NORTH);
        add(categoryPanel, BorderLayout.CENTER);
        getScoreBoard();
    }

    @Override
    public void sendToServer(String message)
    {
        if(message.startsWith("CONNECT"))
        {
            connectToServer();
        }

    }

    /*

     */

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 4444);
    }
}
