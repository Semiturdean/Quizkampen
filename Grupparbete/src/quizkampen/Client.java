package quizkampen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.ArrayList;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Client extends JFrame implements ActionListener{
    // Server variables
    private Socket clientConnection;
    private boolean continueGame;
    private BufferedReader input;
    private PrintWriter output;
    private JTextField textField = new JTextField(10);
    private JButton categoryButton = new JButton("Skicka kategori");
    private JButton sendAnswer = new JButton("Send answer");
    private JPanel componentPanel = new JPanel();
    private JPanel picturePanel = new JPanel();
    private JLabel picLabel = new JLabel();
    private String text = "";
    private Frame frame;
    private ImageIcon icon = new ImageIcon();
    private JButton newGame = new JButton("Starta ett nytt spel");
  	private final JLabel userLabel = new JLabel("Ange ett användarnamn:");
  	private JLabel userName = new JLabel("");
	  private JTextField userNameInput = new JTextField(10);

    Client(String serverAddress, int port) {
        try {
            clientConnection = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            output = new PrintWriter(clientConnection.getOutputStream(), true);
            continueGame = true;
            URL url = new URL
            ("https://res-2.cloudinary.com/westfielddg/image/fetch/c_pad,f_auto,q_auto/http://res.cloudinary.com/westfielddg/image/upload/fph0rlhpygetnd63ydxz.png");
            BufferedImage img = ImageIO.read(url);
            icon.setImage(img);
            
            
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            add("North", componentPanel);
            componentPanel.add(userLabel);
            componentPanel.add(userName);
            componentPanel.add(userNameInput);
            componentPanel.add(textField);
            componentPanel.add(newGame);
            componentPanel.add(categoryButton);
            componentPanel.add(sendAnswer);
            componentPanel.setBackground(Color.WHITE);
            
            categoryButton.setVisible(false);
            sendAnswer.setVisible(false);
            newGame.setVisible(false);
            textField.setVisible(false);
            
            categoryButton.addActionListener(this);
            sendAnswer.addActionListener(this);
            textField.addActionListener(this);
            userNameInput.addActionListener(this);
            newGame.addActionListener(this);
            add("Center",picturePanel);
            picLabel.setIcon(icon);
            picturePanel.add(picLabel);
            picturePanel.setBackground(Color.WHITE);
            
            setSize(600,600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(3);
            setVisible(true);

            //frame = new Frame();

            startGame();
      }catch (MalformedURLException e) {
			e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not connect to server");
        }
    }
    
    private void startToGamePanel(){
    	componentPanel.remove(newGame);
    	textField.setVisible(true);
    	categoryButton.setVisible(true);
    	sendAnswer.setVisible(true);
    	
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

                fromServer = fromServer.substring(9).trim().replace("[", "").replace("]", "");
                List<String> list = splitToList(fromServer); // TODO
                list = shuffleAnswers(list);
                for (int i = 0; i < list.size(); i++) {
                    if (i == 0) {
                        System.out.println(list.get(i));
                    } else if (i > 0 && i < list.size()) {
                        if (i < 4) {
                            System.out.print(list.get(i) + " --- ");
                        } else {
                            System.out.println(list.get(i));
                        }
                    }
                }
            } else if (fromServer.startsWith(Commands.RESULT.toString())) {
                fromServer = fromServer.substring(7);
                if (fromServer.equalsIgnoreCase("TRUE")) {
                    System.out.println("\nCorrect answer");
                } else if (fromServer.equalsIgnoreCase("FALSE")) {
                    System.out.println("\nIncorrect answer");
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
                System.exit(0);
            }
        }
    }

    private List<String> shuffleAnswers(List<String> list){
        List<String> andralist = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            andralist.add(list.get(i));
        }
        Collections.shuffle(andralist);
        andralist.add(list.get(0));
        Collections.swap(andralist, 0, 4);
        return andralist;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == categoryButton) {
            if (textField.getText().equalsIgnoreCase("musik") ||
                    textField.getText().equalsIgnoreCase("historia") ||
                    textField.getText().equalsIgnoreCase("geografi")) {
                // textField.getAction();
                text += textField.getText();
                //System.out.println(text);
                sendCategory(text);
                text = "";

            } else {
                System.out.println("Ogiltig kategori");
            }
        }
        if(e.getSource() == userNameInput) {
        	if(!userNameInput.getText().equals("")) {
        		userName.setText(userNameInput.getText());
        		componentPanel.remove(userNameInput);
        		newGame.setVisible(true);
        		userLabel.setText("Användare:");
        	}
        }
        
        if(e.getSource() == newGame){
        	startToGamePanel();
        	
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
