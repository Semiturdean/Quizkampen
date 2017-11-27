package quizkampen;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class Frame extends JFrame implements PanelListener {
	
	// paneler,labels,knappar
	private JPanel userInfo = new JPanel();
	private GameRoomPlayer user = new GameRoomPlayer();
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

	
	
	public Frame() {
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
	}
	
	@Override
	public void nextQuestion() 
	{
		questionCounter++;
		questionPanel.setQuestion(questionList.get(questionCounter));
	}
	


	@Override
	public void categoryToQuestionPanel(String categoryName) 
	{	
		setCategory(categoryName);
		questionList = category.getQuestionList();
		remove(categoryPanel);
		questionCounter = 0;
		questionPanel.setQuestionCounter(0);
		questionPanel.setQuestion(questionList.get(questionCounter));		
		repaint();
		add(questionPanel, BorderLayout.CENTER);
	
		
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


//	@Override
//	public void questionToCategoryPanel() 
//	{
//		remove(questionPanel);
//		getNewScoreBoard();
//		add(categoryPanel, BorderLayout.CENTER);
//		
//		
//	}
	
	public void getNewScoreBoard() {
		
		userInfo.remove(resultLabel);
		userInfo.add(resultLabel);
		resultLabel.setText(user.getUsername()+"     "+userScore+" - "+opponentScore+"     "+"Motståndare");
		
	}
	// skapar scoreboarden under spelets gång
	 public void getScoreBoard(){
		
		resultLabel.setText(user.getUsername()+"     "+userScore+" - "+"DOLD"+"     "+"Motståndare"); 	
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

	public void startToCategoryPanel(String username){
		
		user.setUsername(username);
		remove(startPanel);
		repaint();
		add(userInfo, BorderLayout.NORTH);
		add(categoryPanel, BorderLayout.CENTER);
		getScoreBoard();
			
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
		
	public static void main(String[] arg) {
		new Frame();
	}
}