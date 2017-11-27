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
import java.util.List;

public class Client extends JFrame implements PanelListener {
    private Socket clientConnection;
    private BufferedReader input;
    private PrintWriter output;
    String text;
    
 // paneler,labels,knappar
 	private JPanel userInfo = new JPanel();
 	private Player user = new Player();
 	private Player opponent = new Player();
 	private int userScore = 0;
 	private int opponentScore = 0;
 	private JLabel resultLabel = new JLabel();
 	private QuestionPanel questionPanel = new QuestionPanel();
 	private CategoryPanel categoryPanel = new CategoryPanel();
 	private Database db = new Database();
 	private List<Category> categoryList = db.getCategoryList();
 	private Category category = null;
 	private List<Question> questionList; 
 	private MessagePanel messagePanel = new MessagePanel();
 	private EndOfRoundPanel endOfRoundPanel = new EndOfRoundPanel();
 	
 	
 	
 	private StartPanel startPanel = new StartPanel();
 	private int questionCounter = 0;
   
    Client(String serverAddress, int port) {
        try {
            clientConnection = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            output = new PrintWriter(clientConnection.getOutputStream(), true);
            
         // layouts, tillägg av labels och knappar på panelen, storlek, visibility etc
    		setLayout(new BorderLayout());
    		setBackground(Color.BLUE);

    		add(startPanel, BorderLayout.CENTER);
    		startPanel.setPanelListener(this);
    		
    		questionPanel.setPanelListener(this);
    		
    		endOfRoundPanel.setPanelListener(this);
    			
    		categoryPanel.setButtonNames(categoryList);
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
    		setLocation(800,200);
    		setVisible(true);
    		setDefaultCloseOperation(3);

            startGame();
        } catch (IOException e) {
            System.out.println("Could not connect to server");
        }
    }
	
	
    public void startGame() throws IOException {
        String fromServer;

        fromServer = input.readLine();
        if (fromServer.startsWith("WELCOME")) {
           messagePanel.setLabel(fromServer);
        }

        while (true) {
            fromServer = input.readLine();
            if (fromServer.startsWith("WAITING"))
            {
                System.out.println("Waiting on other player");
            } 
            else if (fromServer.startsWith("QUESTION"))
            {
                fromServer = fromServer.substring(9);
                System.out.println("Question: " + fromServer);
            } 
            else if (fromServer.startsWith("RESULT"))
            {
                fromServer = fromServer.substring(7);
                if (fromServer.equalsIgnoreCase("TRUE")) 
                {
                    System.out.println("Correct answer");
                } else if (fromServer.equalsIgnoreCase("FALSE")) 
                {
                    System.out.println("Incorrect answer");
                }
            } 
            else if (fromServer.startsWith("MESSAGE")) 
            {
                
            	messagePanel.setLabel(fromServer.substring(8));
            	
            	
            }
            else if(fromServer.equalsIgnoreCase("STARTTOCATEGORY"))
            {
            	startToCategoryPanel();
            }
            else if(fromServer.equalsIgnoreCase("CategoryToQuestion"))
            {
            	categoryToQuestionPanel();
            }
            else if(fromServer.equalsIgnoreCase("WAIT"))
            {
            	messagePanel.setLabel("Väntar på motståndare");
            }
            else if(fromServer.equalsIgnoreCase("startToEndOfRound"))
            {
            	startToEndOfRoundPanel();
            	//endOfRoundPanel.disableButton();
            	messagePanel.setLabel("Quizkamp");
            }
            
            
            
            
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 4444);
    }

    
	public void nextQuestion() 
	{
		questionCounter++;
		questionPanel.setQuestion(questionList.get(questionCounter));
	}
	


	
	public void categoryToQuestionPanel() 
	{	
		//setCategory(categoryName);
		//questionList = category.getQuestionList();
		remove(categoryPanel);
		//questionCounter = 0;
		//questionPanel.setQuestionCounter(0);
		//questionPanel.setQuestion(questionList.get(questionCounter));		
		repaint();
		add(questionPanel, BorderLayout.CENTER);
		getContentPane().invalidate();
		getContentPane().revalidate();
	
		
	}
	public void setCategory(String categoryName)
	{
		for(int i=0; i<categoryList.size(); i++)
		{
			if (categoryName.equalsIgnoreCase(categoryList.get(i).getName()))
			{
				category = categoryList.get(i);
				break;
				
			}
		}
	}


	
	public void getNewScoreBoard() {
		
		userInfo.remove(resultLabel);
		userInfo.add(resultLabel);
		resultLabel.setText(user.getUsername()+"     "+userScore+" - "+opponentScore+"     "+opponent.getUsername());
		
	}
	// skapar scoreboarden under spelets gång
	 public void getScoreBoard(){
		
		resultLabel.setText(user.getUsername()+"     "+userScore+" - "+"DOLD"+"     "+opponent.getUsername()); 	
		resultLabel.setFont(new Font("Serif", Font.BOLD, 32));
		userInfo.add(resultLabel);
		resultLabel.setVisible(true);
		categoryPanel.setVisible(true);
}

	public void setScore(){
		 
		 userInfo.remove(resultLabel);
		 userScore++;
		 repaint();
		 getScoreBoard();
		 
	 }

	public void startToCategoryPanel()
	{
		remove(startPanel);
		repaint();
		add(userInfo, BorderLayout.NORTH);
		add(categoryPanel, BorderLayout.CENTER);
		getScoreBoard();
		getContentPane().invalidate();
		getContentPane().revalidate();
			
	}
	public void startToQuestionPanel()
	{
		remove(startPanel);
		repaint();
		add(userInfo, BorderLayout.NORTH);
		add(questionPanel, BorderLayout.CENTER);
		getScoreBoard();
		getContentPane().invalidate();
		getContentPane().revalidate();
			
	}
	public void endOfRoundToCategoryPanel()
	{
		remove(endOfRoundPanel);
		repaint();
		add(categoryPanel, BorderLayout.CENTER);
	}
	public void endOfRoundToQuestionPanel()
	{
		remove(endOfRoundPanel);
		repaint();
		add(questionPanel, BorderLayout.CENTER);
	}
	public void questionToEndOfRoundPanel()
	{
		remove(questionPanel);
		getNewScoreBoard();
		endOfRoundPanel.setLabel(resultLabel.getText());
		endOfRoundPanel.enableButton();
		repaint();
		add(endOfRoundPanel, BorderLayout.CENTER);
	}
	public void startToEndOfRoundPanel()
	{
		remove(startPanel);
		getNewScoreBoard();
		endOfRoundPanel.setLabel(resultLabel.getText());
		repaint();
		add(userInfo, BorderLayout.NORTH);
		add(endOfRoundPanel, BorderLayout.CENTER);
	}

	@Override
	public void sendToServer(String message) 
	{
		if(message.startsWith("USERNAME"))
			user.setUsername(message.substring(8));
		
		//System.out.println(message);
        output.println(message);
	}
	
	
	
   	
}
