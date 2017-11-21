package quizkampen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {

	JPanel userInfo = new JPanel();
	JPanel rond1Panel = new JPanel();
	JPanel rond2Panel = new JPanel();
	JPanel answerPanel = new JPanel();
	JLabel answerLabel = new JLabel("");
	JLabel userLabel = new JLabel("Användare:");
	Player user = new Player();
	JLabel userName; 
	JButton newGame = new JButton("Starta ett nytt spel");
	int userScore = 0;
	int opponentScore = 0;
	JLabel resultLabel; 
	//JLabel opponentLabel = new JLabel("Opponent"); 
	
	Border innerBorder;
	Border innerBorder2;
	Border outerBorder;
	
	JLabel chooseCategory = new JLabel("Välj Kategori: ");
	JLabel chooseCategory2 = new JLabel("Välj Kategori: ");
	Category sport = new Category("Sport");
	Category politics = new Category("Politik");
	Category music = new Category("Musik");
	Category whatever = new Category("Whatever");
	JButton category1 = new JButton(sport.getName());
	JButton category2 = new JButton(politics.getName());
	JButton category3 = new JButton(music.getName());
	JButton category4= new JButton(whatever.getName());
	JButton category5 = new JButton(sport.getName());
	JButton category6 = new JButton(politics.getName());
	JButton category7 = new JButton(music.getName());
	JButton category8 = new JButton(whatever.getName());
	
	Question sportQuestion1 = new Question("SportFråga1", "Rätt Svar", "Fel Svar","Fel Svar","Fel Svar");
	Question sportQuestion2 = new Question("SportFråga2", "Rätt Svar", "Fel Svar","Fel Svar","Fel Svar");
	Question musicQuestion1 = new Question("MusikFråga1", "Rätt Svar", "Fel Svar","Fel Svar","Fel Svar");
	Question musicQuestion2 = new Question("MusikFråga2", "Rätt Svar", "Fel Svar","Fel Svar","Fel Svar");
	JLabel question1 = new JLabel(sportQuestion1.getQuestion());
	JLabel question2 = new JLabel(sportQuestion2.getQuestion());
	JLabel question3 = new JLabel(musicQuestion1.getQuestion());
	JLabel question4 = new JLabel(musicQuestion2.getQuestion());
	JButton choice1 = new JButton(sportQuestion1.getRightChoice());
	JButton choice2 = new JButton(sportQuestion1.getWrongChoice1());
	JButton choice3 = new JButton(sportQuestion1.getWrongChoice2());
	JButton choice4 = new JButton(sportQuestion1.getWrongChoice3());
	JButton choice5 = new JButton(sportQuestion2.getRightChoice());
	JButton choice6 = new JButton(sportQuestion2.getWrongChoice1());
	JButton choice7 = new JButton(sportQuestion1.getWrongChoice2());
	JButton choice8 = new JButton(sportQuestion2.getWrongChoice3());
	JButton choice9 = new JButton(musicQuestion1.getRightChoice());
	JButton choice10 = new JButton(musicQuestion1.getWrongChoice1());
	JButton choice11 = new JButton(musicQuestion1.getWrongChoice2());
	JButton choice12 = new JButton(musicQuestion1.getWrongChoice3());
	JButton choice13 = new JButton(musicQuestion2.getRightChoice());
	JButton choice14 = new JButton(musicQuestion2.getWrongChoice1());
	JButton choice15 = new JButton(musicQuestion2.getWrongChoice2());
	JButton choice16 = new JButton(musicQuestion2.getWrongChoice3());

	
	
	Dimension dim1 = new Dimension(800,300);
	Dimension dim2 = new Dimension(800,100);

	
	
	public Frame() {
		// Skapa spelaren namnet
		user.setUsername(JOptionPane.showInputDialog("Ange användarnamn!"));
		userName = new JLabel(user.getUsername());
		resultLabel = new JLabel(user.getUsername()+"     "+userScore+" - "+opponentScore+"     "+"Motståndare");
		resultLabel.setFont(new Font("Serif", Font.BOLD, 32));
		//frame layout, storlek, dfco etc
		setLayout(new GridLayout(4,1));
		GridBagConstraints gc = new GridBagConstraints();
		
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weighty = 1;
		
		userInfo.setLayout(new FlowLayout());
		userInfo.add(userLabel);
		userInfo.add(userName);
		userInfo.add(newGame);
		resultLabel.setVisible(false);
		userInfo.add(resultLabel);
		newGame.addActionListener(this);
		userInfo.setPreferredSize(dim1);
		add(userInfo, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weighty = 3;
		
		rond1Panel.setLayout(new GridLayout(0,5));
		
		innerBorder = new TitledBorder("Rond1");
		outerBorder = new EmptyBorder(5,5,5,5);
		rond1Panel.setBorder(new CompoundBorder(outerBorder, innerBorder));
		rond1Panel.add(chooseCategory);
		rond1Panel.add(category1);
		rond1Panel.add(category2);
		rond1Panel.add(category3);
		rond1Panel.add(category4);
		category1.addActionListener(this);
		category2.addActionListener(this);
		category3.addActionListener(this);
		rond1Panel.setPreferredSize(dim1);
		rond1Panel.add(question1);
		rond1Panel.add(choice1);
		rond1Panel.add(choice2);
		rond1Panel.add(choice3);
		rond1Panel.add(choice4);
		rond1Panel.add(question2);
		rond1Panel.add(choice5);
		rond1Panel.add(choice6);
		rond1Panel.add(choice7);
		rond1Panel.add(choice8);
		question1.setVisible(false);
		choice1.setVisible(false);
		choice2.setVisible(false);
		choice3.setVisible(false);
		choice4.setVisible(false);
		question2.setVisible(false);
		choice5.setVisible(false);
		choice6.setVisible(false);
		choice7.setVisible(false);
		choice8.setVisible(false);
		rond1Panel.setVisible(false);
		add(rond1Panel, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		gc.weighty = 3;
		
		rond2Panel.setLayout(new GridLayout(0,5));
		innerBorder2 = new TitledBorder("Rond2");
		rond2Panel.setBorder(new CompoundBorder(outerBorder, innerBorder2));
		rond2Panel.add(chooseCategory2);
		rond2Panel.add(category5);
		rond2Panel.add(category6);
		rond2Panel.add(category7);
		rond2Panel.add(category8);
		category4.addActionListener(this);
		category5.addActionListener(this);
		category6.addActionListener(this);
		category7.addActionListener(this);
		category8.addActionListener(this);
		rond2Panel.add(question3);
		rond2Panel.add(choice9);
		rond2Panel.add(choice10);
		rond2Panel.add(choice11);
		rond2Panel.add(choice12);
		rond2Panel.add(question4);
		rond2Panel.add(choice13);
		rond2Panel.add(choice14);
		rond2Panel.add(choice15);
		rond2Panel.add(choice16);		
		question3.setVisible(false);
		choice9.setVisible(false);
		choice10.setVisible(false);
		choice11.setVisible(false);
		choice12.setVisible(false);
		question4.setVisible(false);
		choice13.setVisible(false);
		choice14.setVisible(false);
		choice15.setVisible(false);
		choice16.setVisible(false);
		
		choice1.addActionListener(this);
		choice2.addActionListener(this);
		choice3.addActionListener(this);
		choice4.addActionListener(this);
		choice5.addActionListener(this);
		choice6.addActionListener(this);
		choice7.addActionListener(this);
		choice8.addActionListener(this);
		choice9.addActionListener(this);
		choice10.addActionListener(this);
		choice11.addActionListener(this);
		choice12.addActionListener(this);
		choice13.addActionListener(this);
		choice14.addActionListener(this);
		choice15.addActionListener(this);
		choice16.addActionListener(this);
		
		rond2Panel.setPreferredSize(dim1);
		rond2Panel.setVisible(false);
		add(rond2Panel, gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		gc.weighty = 1;
		
		answerLabel.setFont(new Font("Serif", Font.BOLD, 32));
		answerPanel.add(answerLabel);
		answerPanel.setPreferredSize(dim2);
		add(answerPanel,gc);
		
		setSize(800,800);
		setLocation(800,200);
		setVisible(true);
		setDefaultCloseOperation(3);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGame) 
		{
			userLabel.setVisible(false);
			userName.setVisible(false);
			newGame.setVisible(false);
			resultLabel.setVisible(true);
			rond1Panel.setVisible(true);
		}
		if(e.getSource() == category1)
		{
			
			question1.setVisible(true);
			choice1.setVisible(true);
			choice2.setVisible(true);
			choice3.setVisible(true);
			choice4.setVisible(true);
			
		}
		if(e.getSource() == choice1)
		{
			choice1.setBackground(Color.GREEN);
			
			choice1.removeActionListener(this);
			choice2.removeActionListener(this);
			choice3.removeActionListener(this);
			choice4.removeActionListener(this);
			question2.setVisible(true);
			choice5.setVisible(true);
			choice6.setVisible(true);
			choice7.setVisible(true);
			choice8.setVisible(true);
			
			

		}
		else if(e.getSource() == choice2)
		{
			choice2.setBackground(Color.RED);
			choice1.removeActionListener(this);
			choice2.removeActionListener(this);
			choice3.removeActionListener(this);
			choice4.removeActionListener(this);
			
			question2.setVisible(true);
			choice5.setVisible(true);
			choice6.setVisible(true);
			choice7.setVisible(true);
			choice8.setVisible(true);
		}
		else if(e.getSource() == choice3)
		{
			choice3.setBackground(Color.RED);
			choice1.removeActionListener(this);
			choice2.removeActionListener(this);
			choice3.removeActionListener(this);
			choice4.removeActionListener(this);
			
			question2.setVisible(true);
			choice5.setVisible(true);
			choice6.setVisible(true);
			choice7.setVisible(true);
			choice8.setVisible(true);
		}
		else if(e.getSource() == choice4)
		{
			choice4.setBackground(Color.RED);
			choice1.removeActionListener(this);
			choice2.removeActionListener(this);
			choice3.removeActionListener(this);
			choice4.removeActionListener(this);
			
			question2.setVisible(true);
			choice5.setVisible(true);
			choice6.setVisible(true);
			choice7.setVisible(true);
			choice8.setVisible(true);
		}
		if(e.getSource() == choice5)
		{
			choice5.setBackground(Color.GREEN);
			choice5.removeActionListener(this);
			choice6.removeActionListener(this);
			choice7.removeActionListener(this);
			choice8.removeActionListener(this);
			
			

			
			rond2Panel.setVisible(true);
			
			
		}
		else if(e.getSource() == choice6)
		{
			choice6.setBackground(Color.RED);
			choice5.removeActionListener(this);
			choice6.removeActionListener(this);
			choice7.removeActionListener(this);
			choice8.removeActionListener(this);
			
			rond2Panel.setVisible(true);
		}
		else if(e.getSource() == choice7)
		{
			choice7.setBackground(Color.RED);
			choice5.removeActionListener(this);
			choice6.removeActionListener(this);
			choice7.removeActionListener(this);
			choice8.removeActionListener(this);
			
			rond2Panel.setVisible(true);
		}
		else if(e.getSource() == choice8)
		{
			choice8.setBackground(Color.RED);
			choice5.removeActionListener(this);
			choice6.removeActionListener(this);
			choice7.removeActionListener(this);
			choice8.removeActionListener(this);
			
			rond2Panel.setVisible(true);
		}
		
		if(e.getSource() == category7)
		{
			question3.setVisible(true);
			choice9.setVisible(true);
			choice10.setVisible(true);
			choice11.setVisible(true);
			choice12.setVisible(true);
		}
		
		if(e.getSource() == choice9)
		{
			choice9.setBackground(Color.GREEN);
			choice9.removeActionListener(this);
			choice10.removeActionListener(this);
			choice11.removeActionListener(this);
			choice12.removeActionListener(this);
			
			 
			
			question4.setVisible(true);
			choice13.setVisible(true);
			choice14.setVisible(true);
			choice15.setVisible(true);
			choice16.setVisible(true);
			
		}
		if(e.getSource() == choice10)
		{
			choice10.setBackground(Color.RED);
			choice9.removeActionListener(this);
			choice10.removeActionListener(this);
			choice11.removeActionListener(this);
			choice12.removeActionListener(this);
			
			question4.setVisible(true);
			choice13.setVisible(true);
			choice14.setVisible(true);
			choice15.setVisible(true);
			choice16.setVisible(true);
			
		}
		if(e.getSource() == choice11)
		{
			choice11.setBackground(Color.RED);
			choice9.removeActionListener(this);
			choice10.removeActionListener(this);
			choice11.removeActionListener(this);
			choice12.removeActionListener(this);
			
			question4.setVisible(true);
			choice13.setVisible(true);
			choice14.setVisible(true);
			choice15.setVisible(true);
			choice16.setVisible(true);
			
		}
		if(e.getSource() == choice12)
		{
			choice12.setBackground(Color.RED);
			choice9.removeActionListener(this);
			choice10.removeActionListener(this);
			choice11.removeActionListener(this);
			choice12.removeActionListener(this);
			
			question4.setVisible(true);
			choice13.setVisible(true);
			choice14.setVisible(true);
			choice15.setVisible(true);
			choice16.setVisible(true);
			
		}
		if(e.getSource() == choice13)
		{
			choice13.setBackground(Color.GREEN);
			choice13.removeActionListener(this);
			choice14.removeActionListener(this);
			choice15.removeActionListener(this);
			choice16.removeActionListener(this);
			

			
			answerLabel.setText(user.getUsername()+" Vann!");
		}
		
		else if(e.getSource() == choice14)
		{
			choice14.setBackground(Color.RED);
			choice13.removeActionListener(this);
			choice14.removeActionListener(this);
			choice15.removeActionListener(this);
			choice16.removeActionListener(this);
			
			answerLabel.setText(user.getUsername()+" Vann!");
		}
		else if(e.getSource() == choice15)
		{
			choice15.setBackground(Color.RED);
			choice13.removeActionListener(this);
			choice14.removeActionListener(this);
			choice15.removeActionListener(this);
			choice16.removeActionListener(this);
			
			answerLabel.setText(user.getUsername()+" Vann!");
			}
		else if(e.getSource() == choice16)
		{
			choice16.setBackground(Color.RED);
			choice13.removeActionListener(this);
			choice14.removeActionListener(this);
			choice15.removeActionListener(this);
			choice16.removeActionListener(this);
			
			answerLabel.setText(user.getUsername()+" Vann!");
		}
	}
	public static void main(String[] arg) 
	{
		new Frame();
	}

}
