package quizkampen;

import javax.swing.*;
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

public class Client extends JFrame implements ActionListener {
    private Socket clientConnection;
    private boolean continueGame;
    private BufferedReader input;
    private PrintWriter output;
    private JTextField textField = new JTextField(10);
    private JButton categoryButton = new JButton("Skicka kategori");
    private JButton sendAnswer = new JButton("Send answer");
    private String text = "";

    Client(String serverAddress, int port) {
        try {
            clientConnection = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            output = new PrintWriter(clientConnection.getOutputStream(), true);
            continueGame = true;

            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            add(textField);
            add(categoryButton);
            add(sendAnswer);
            categoryButton.addActionListener(this);
            sendAnswer.addActionListener(this);
            textField.addActionListener(this);

            setSize(300,300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(3);
            setVisible(true);

            //frame = new Frame();

            startGame();
        } catch (IOException e) {
            System.out.println("Could not connect to server");
        }
    }

    private void sendCategory(String category) {
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
                System.out.println("Please choose a category");
                System.out.println(fromServer);
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

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 4444);
    }
}
