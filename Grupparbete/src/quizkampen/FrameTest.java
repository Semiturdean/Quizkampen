package quizkampen;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class FrameTest extends JFrame implements ActionListener, PanelListener {
	
	// paneler,labels,knappar
	private JPanel userInfo = new JPanel();
	private final JLabel userLabel = new JLabel("Anv�ndare:");
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
	
	private int questionCounter = 0;

	
	
	public FrameTest() {
		// layouts, till�gg av labels och knappar p� panelen, storlek, visibility etc
		setLayout(new BorderLayout());
		setBackground(Color.BLUE);

		
		questionPanel.setPanelListener(this);
		
		
		categoryPanel.setButtonNames(categoryList);
		categoryPanel.setPanelListener(this);
		add(categoryPanel, BorderLayout.CENTER);
		 categoryPanel.setVisible(false);

		
		
		userInfo.setLayout(new FlowLayout());
		userInfo.add(userLabel);
		userInfo.add(userNameInput);
		userInfo.add(userName);
		userInfo.add(resultLabel);
		userInfo.setBackground(Color.PINK);
		userInfo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10));
		
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
		repaint();
		questionCounter = 0;
		questionPanel.setQuestionCounter(0);
		questionPanel.setQuestion(questionList.get(questionCounter));		
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


	@Override
	public void questionToCategoryPanel() 
	{
		remove(questionPanel);
		repaint();
		add(categoryPanel, BorderLayout.CENTER);
		
		
	}
	
	// skapar scoreboarden under spelets g�ng
	 public void getScoreBoard(){
		 	resultLabel.setText(user.getUsername()+"     "+userScore+" - "+opponentScore+"     "+"Motst�ndare");
			resultLabel.setFont(new Font("Serif", Font.BOLD, 32));
			userInfo.add(resultLabel);
			resultLabel.setVisible(true);
			categoryPanel.setVisible(true);
			userInfo.remove(newGame);
			userInfo.remove(userLabel);
			userInfo.remove(userName);
}
	// private void removeUnusedComponents() {	}  //Metoden kommer beh�vas senare//
	// private void setFalse(){} 	//S�tter fel svar till r�d och alternativ tar bort svartsalternativen efter n�gra sekunder eller tar bort ActionListener//
	// private void setTrue(){} //	-||- r�tt svar till gr�n
	// private void setScore(){}
	 	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == userNameInput) {
			userName.setText(userNameInput.getText());
			user.setUsername(userNameInput.getText());
				userInfo.remove(userNameInput);
			userInfo.add(newGame);
				newGame.addActionListener(this);
		}
		if (e.getSource() == newGame) {
			getScoreBoard();
		}
		}
		
	public static void main(String[] arg) {
		new FrameTest();
	}

}
