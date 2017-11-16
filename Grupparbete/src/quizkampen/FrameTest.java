package quizkampen;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private JLabel testLabel = new JLabel("TEST");	//Bara f�r att se vart den hamnar i panelen
	private JTextField userNameInput = new JTextField(10);
	private JLabel resultLabel = new JLabel();
	
	
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
		
		gamePanel.add(testLabel);
		add(userInfo, gc);
		
		setSize(800,800);
		setLocation(800,200);
		setVisible(true);
		setDefaultCloseOperation(3);
	}
	
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
	// private void setScore(){} // �kar scorecounten f�r spelare och opponent, tills�tter till JLabeln
	
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
