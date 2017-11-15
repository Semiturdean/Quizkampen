package quizkampen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Frame extends JFrame implements ActionListener {

	JPanel userInfo = new JPanel();
	JPanel questionPanel = new JPanel();
	JLabel userLabel = new JLabel("Användare:");
	JButton newGame = new JButton("Starta ett nytt spel");
	Player user = new Player();
	
	public Frame() {
		// Skapa spelaren namnet
		user.setUsername(JOptionPane.showInputDialog("Ange användarnamn!"));
		JLabel userName = new JLabel(user.getUsername());
		
		//frame layout, storlek, dfco etc
		setLayout(new BorderLayout());
		add("North", userInfo);
		
		userInfo.setLayout(new FlowLayout());
		userInfo.add(userLabel);
		userInfo.add(userName);
		userInfo.add(newGame);
		newGame.addActionListener(this);
		
		setSize(800,800);
		setLocation(800,200);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGame) {
			System.out.println("Alternativ för start av spel");
		}
	}
	public static void main(String[] arg) {
		new Frame();
	}

}
