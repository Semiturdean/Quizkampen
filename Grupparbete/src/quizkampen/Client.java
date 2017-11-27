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
    private JButton button = new JButton("Category (geografi)");
    private JButton button2 = new JButton("Send answer");
    private String text;

    Client(String serverAddress, int port) {
        try {
            clientConnection = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            output = new PrintWriter(clientConnection.getOutputStream(), true);
            continueGame = true;

            setLayout(new FlowLayout());
            add(textField);
            add(button);
            add(button2);
            button.addActionListener(this);
            button2.addActionListener(this);
            textField.addActionListener(this);

            setSize(300,300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(3);
            setVisible(true);

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
                sendCategory("Musik");
            } // This command from the server will be received when the next next round has been loaded
              else if (fromServer.startsWith(Commands.STARTROUND.toString())) {
                // Notify server to start the round
                output.println(Commands.STARTROUND);
            } else if (fromServer.startsWith(Commands.MESSAGE.toString())) {
                System.out.println(fromServer.substring(8));
            } else if (fromServer.startsWith(Commands.ENDGAME.toString())) {
                continueGame = false;
                System.out.println("Game has ended");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button)
        {
            // textField.getAction();
            text = Commands.CATEGORY.toString();
            text += textField.getText();
            System.out.println(text);
            output.println(text);
        }
        if(e.getSource() == textField){

        }
        if (e.getSource() == button2) {
            text = Commands.ANSWER.toString();
            text += textField.getText();
            output.println(text);
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 4444);
    }
}
