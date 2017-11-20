package quizkampen;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class FrameTest extends JFrame implements ActionListener {
	
	// paneler,labels,knappar
	private JPanel userInfo = new JPanel();
	private final JLabel userLabel = new JLabel("Anv�ndare:");
	private Player user = new Player();
	private JLabel userName = new JLabel(""); 
	private JButton newGame = new JButton("Starta ett nytt spel");
	private int userScore = 0;
	private int opponentScore = 0;
	private JPanel gamePanel = new JPanel();
	private JTextField userNameInput = new JTextField(10);
	private JLabel resultLabel = new JLabel();
	private Question questions = new Question("Fr�ga", "R�tt svar", "Fel svar", "Fel svar", "Fel svar");
	private JLabel question = new JLabel(questions.getQuestion());
	private JButton rightAnswer = new JButton(questions.getRightChoice());
	private JButton wrongAnswer1 = new JButton(questions.getWrongChoice1());
	private JButton wrongAnswer2 = new JButton(questions.getWrongChoice1());
	private JButton wrongAnswer3 = new JButton(questions.getWrongChoice1());
	
	
	public FrameTest() {
		// layouts, till�gg av labels och knappar p� panelen, storlek, visibility etc
		setLayout(new GridLayout(4,1));

		GridBagConstraints gc = new GridBagConstraints();
		
		 
		gc.gridx = 0; gc.gridy = 0; gc.weighty = 1;
		
		
		userInfo.setLayout(new FlowLayout());
		userInfo.add(userLabel);
		userInfo.add(userNameInput);
		userInfo.add(userName);
		userInfo.add(resultLabel);
		resultLabel.setVisible(false);
		userNameInput.addActionListener(this);
		
		gamePanel.add(question);
		gamePanel.add(rightAnswer); 	rightAnswer.addActionListener(this);
		gamePanel.add(wrongAnswer1);	wrongAnswer1.addActionListener(this);
		gamePanel.add(wrongAnswer2);	wrongAnswer2.addActionListener(this);
		gamePanel.add(wrongAnswer3);	wrongAnswer3.addActionListener(this);

		add(userInfo, gc);
		
		setSize(800,800);
		setLocation(800,200);
		setVisible(true);
		setDefaultCloseOperation(3);
	}
	// skapar scoreboarden under spelets g�ng
	 private void getScoreBoard(){
		 	resultLabel.setText(user.getUsername()+"     "+userScore+" - "+opponentScore+"     "+"Motst�ndare");
			resultLabel.setFont(new Font("Serif", Font.BOLD, 32));
			userInfo.add(resultLabel);
			resultLabel.setVisible(true);
			userInfo.remove(newGame);
			userInfo.remove(userLabel);
			userInfo.remove(userName);
			add(gamePanel);
}
	// private void removeUnusedComponents() {	}  //Metoden kommer beh�vas senare//
	// private void setFalse(){} 	//S�tter fel svar till r�d och alternativ tar bort svartsalternativen efter n�gra sekunder eller tar bort ActionListener//
	// private void setTrue(){} //	-||- r�tt svar till gr�n
	// private void setScore(){}
	// private void setQuestions() {} // Beh�vs? v�nta och se hur Sina fixat sin fr�gepanel
	 	
	 // S�tter f�rgen f�r fel svar
	 private void setWrongAnswerColor(){
		 wrongAnswer1.setBackground(Color.RED);
		 wrongAnswer2.setBackground(Color.RED);
		 wrongAnswer3.setBackground(Color.RED);
	 }
	 	// S�tter f�rgen f�r r�tt svar
	 private void setCorrectAnswerBackground() {
		 rightAnswer.setBackground(Color.GREEN);
	 }
	
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
		if(e.getSource() == wrongAnswer1 || e.getSource() == wrongAnswer2 || e.getSource() == wrongAnswer3) {
			if(wrongAnswer1.getAction() == null || wrongAnswer2.getAction() == null || wrongAnswer3.getAction() == null) {
				setWrongAnswerColor();
				setCorrectAnswerBackground();
			}
			}
		if(e.getSource() == rightAnswer) {
			setCorrectAnswerBackground();
			userScore++;
			
		}
		}
		
	public static void main(String[] arg) {
		new FrameTest();
	}

}
