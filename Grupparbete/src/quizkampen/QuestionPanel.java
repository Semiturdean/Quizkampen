package quizkampen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;




public class QuestionPanel extends JPanel implements ActionListener
{
		/*
		 * QuestionPanelen har en mainPanel och en knapp för att gå vidare
		 * på mainPanel finns det två paneler, en till frågan och den andra till knapparna som
		 * visar 4 val
		 */
	
	
		//Countern för att byta panel efter 3 frågor
		int questionCounter = 0;
		
		
		//Alla componenter initieras här
		private JLabel questionLabel;
		private JButton choice1Btn;
		private JButton choice2Btn;
		private JButton choice3Btn;
		private JButton choice4Btn;
		private JButton nextBtn;
		
		
		//Två panel skapas här
		JPanel mainPanel = new JPanel();
		JPanel questionPanel = new JPanel();
		JPanel choicePanel = new JPanel();
		
		//inteface för att kommunicera med framen
		private PanelListener panelListener;
		
		public QuestionPanel()
		{
			setLayout(new BorderLayout());
			
			
			mainPanel.setLayout(new GridLayout(2,0));
			
			
			
			
			/////Frågan////////////////////////////////////		
			
			questionPanel.setLayout(new GridBagLayout());
			questionLabel = new JLabel();
			questionLabel.setFont(new Font("Serif", Font.PLAIN, 20));
			questionPanel.add(questionLabel);
			questionPanel.setBorder(new LineBorder(Color.BLACK,5));
			mainPanel.add(questionPanel);
			
			
			
			
			////////////4 val//////////////////////
			
			
			choicePanel.setLayout(new GridLayout(2,2));
			choice1Btn = new JButton();
			choicePanel.add(choice1Btn);
			choice2Btn = new JButton();
			choicePanel.add(choice2Btn);
			choice3Btn = new JButton();
			choicePanel.add(choice3Btn);
			choice4Btn = new JButton();
			choicePanel.add(choice4Btn);
			
			choice1Btn.addActionListener(this);
			choice2Btn.addActionListener(this);
			choice3Btn.addActionListener(this);
			choice4Btn.addActionListener(this);
			
			mainPanel.add(choicePanel);
			
			add(mainPanel, BorderLayout.CENTER);
			
			
			///////Knappen för att gå vidare
			
			nextBtn = new JButton("Nästa");
			add(nextBtn, BorderLayout.SOUTH);
			nextBtn.addActionListener(this);
				
		}
		
		//För att länka en fråga till labeln och knapparna
		
		public void setQuestion(Question question)
		{
			questionLabel.setText(question.getQuestion());
			choice1Btn.setText(question.getRightChoice());
			choice2Btn.setText(question.getWrongChoice1());
			choice3Btn.setText(question.getWrongChoice2());
			choice4Btn.setText(question.getWrongChoice3());
		}

		/*
		 * actionListenern ändrar färg på knappen till grön om det är rätt svar och till röd om det är fel svar
		 * om fel svar har valts, det rätta svaret visas också genom att andrä färget på knappen till grön
		 * när man kilckar på nästa sätts de originala färgen tillbaka på knapparna och så går man till nästa fråga
		 * questionCounter håller koll på hur många frågor har ställts. när 3 frågor har ställts byter actionListenern panel
		 * genom att anropa en metod i interfacet.
		 */
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			JButton clicked = (JButton) e.getSource();
			for(Component c : choicePanel.getComponents())
			{
				
				if(clicked == c)
				{
					if(clicked.getText().equalsIgnoreCase("rätt svar"))
					{
						c.setBackground(Color.GREEN); 
						panelListener.setScore();
						
					}
					else 
					{
						c.setBackground(Color.RED);
					}
				}
				else if(((AbstractButton) c).getText().equalsIgnoreCase("rätt svar") && clicked != nextBtn)
				{
					c.setBackground(Color.GREEN);
					
				}
				choice1Btn.removeActionListener(this);
				choice2Btn.removeActionListener(this);
				choice3Btn.removeActionListener(this);
				choice4Btn.removeActionListener(this);
				
				
			}
			if(clicked == nextBtn)
			{
				panelListener.nextQuestion();
				for(Component c : choicePanel.getComponents())
				{
					c.setBackground(nextBtn.getBackground());
					((JButton) c).addActionListener(this);
				}
				questionCounter++;
				if(questionCounter == 3)
				{
					panelListener.questionToEndOfRoundPanel();
				}
			}
			
		
			
		}
		
		public void setPanelListener(PanelListener panelListener)
		{
			this.panelListener = panelListener;
		}
		
		public void setQuestionCounter(int questionCounter)
		{
			this.questionCounter = questionCounter;
		}
		

}
