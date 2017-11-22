package quizkampen;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;


public class FrameTest extends JFrame implements ActionListener, PanelListener {
	
	// paneler,labels,knappar
	private JPanel userInfo = new JPanel();
	private final JLabel userLabel = new JLabel("Användare:");
	private Player user = new Player();
	private JLabel userName = new JLabel(""); 
	private JButton newGame = new JButton("Starta ett nytt spel");
	private int userScore = 0;
	private int opponentScore = 0;
	private JTextField userNameInput = new JTextField(10);
	private JLabel resultLabel = new JLabel();
	private QuestionPanel questionPanel = new QuestionPanel();
	private CategoryPanel categoryPanel = new CategoryPanel();
	private Database db = new Database();
	private List<Category> categoryList = db.getCategoryList();
	private Category category = null;
	private List<Question> questionList; 
	private MessagePanel messagePanel = new MessagePanel();
	JLabel message = new JLabel("Ange ett användarnamn");
	private int questionCounter = 0;

	
	
	public FrameTest() {
		// layouts, tillägg av labels och knappar på panelen, storlek, visibility etc
		setLayout(new BorderLayout());
		setBackground(Color.BLUE);

		
		questionPanel.setPanelListener(this);
		
		
		categoryPanel.setButtonNames(categoryList);
		categoryPanel.setPanelListener(this);
		add(categoryPanel, BorderLayout.CENTER);
		categoryPanel.setVisible(false);
		
		
		userInfo.setLayout(new FlowLayout());
		userInfo.add(userLabel); userInfo.add(userNameInput); userInfo.add(userName); userInfo.add(resultLabel);
		userInfo.add(message);
		message.setVisible(false);
		userInfo.setBackground(Color.PINK); userInfo.setBorder(new LineBorder(Color.BLACK, 2));
		
		resultLabel.setVisible(false);
		userNameInput.addActionListener(this);
		
		
		add(userInfo, BorderLayout.NORTH);
		
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


	@Override
	public void questionToCategoryPanel() 
	{
		remove(questionPanel);
		getNewScoreBoard();
		add(categoryPanel, BorderLayout.CENTER);
		
		
	}
	
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
	public void setUserInfo() {
		if(!userName.getText().equals("")) {
		user.setUsername(userNameInput.getText());
		userInfo.remove(userNameInput);
		userInfo.remove(message);
		userInfo.add(newGame);
		newGame.addActionListener(this);
		}else {
			message.setVisible(true);
		}
	}
	 	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == userNameInput) {
				userName.setText(userNameInput.getText());
				setUserInfo();
			}
		
		if (e.getSource() == newGame) {
			userInfo.remove(newGame);
			userInfo.remove(userLabel);
			userInfo.remove(userName);
			getScoreBoard();
		}
		}
		
	public static void main(String[] arg) {
		new FrameTest();
	}

}
