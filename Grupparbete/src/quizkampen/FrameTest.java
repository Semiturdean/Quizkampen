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
	private final JLabel userLabel = new JLabel("Användare:");
	private Player user = new Player();
	private JLabel userName = new JLabel(""); 
	private JButton newGame = new JButton("Starta ett nytt spel");
	private int userScore = 0;
	private int opponentScore = 0;
	private JPanel gamePanel = new JPanel();
	private JLabel testLabel = new JLabel("TEST");	//Bara för att se vart den hamnar i panelen
	private JTextField userNameInput = new JTextField(10);
	private JLabel resultLabel = new JLabel();
	
	
	public FrameTest() {
		// layouts, tillägg av labels och knappar på panelen, storlek, visibility etc
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
		 	resultLabel.setText(user.getUsername()+"     "+userScore+" - "+opponentScore+"     "+"Motståndare");
			resultLabel.setFont(new Font("Serif", Font.BOLD, 32));
			userInfo.add(resultLabel);
			resultLabel.setVisible(true);
			userInfo.remove(newGame);
			userInfo.remove(userLabel);
			userInfo.remove(userName);
			add(gamePanel);
}
	// private void removeUnusedComponents() {	}  //Metoden kommer behövas senare//
	// private void setFalse(){} 	//Sätter fel svar till röd och alternativ tar bort svartsalternativen efter några sekunder eller tar bort ActionListener//
	// private void setTrue(){} //	-||- rätt svar till grön
	// private void setScore(){} // Ökar scorecounten för spelare och opponent, tillsätter till JLabeln
	
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
