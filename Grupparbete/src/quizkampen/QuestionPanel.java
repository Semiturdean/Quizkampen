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
		private Question question = new Question("Här har vi en fråga","rätt svar", "fel svar", "fel svar", "fel svar");
		
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
		
		private PanelListener panelListener;
		
		public QuestionPanel()
		{
			setLayout(new BorderLayout());
			
			
			mainPanel.setLayout(new GridLayout(2,0));
			
			
			//Vi har två panel, en till frågan och den andra till alla val
			
			/////Frågan////////////////////////////////////		
			
			questionPanel.setLayout(new GridBagLayout());
			questionLabel = new JLabel(question.getQuestion());
			questionLabel.setFont(new Font("Serif", Font.PLAIN, 20));
			questionPanel.add(questionLabel);
			questionPanel.setBorder(new LineBorder(Color.BLACK,5));
			mainPanel.add(questionPanel);
			
			
			
			
			////////////4 val//////////////////////
			
			
			choicePanel.setLayout(new GridLayout(2,2));
			choice1Btn = new JButton(question.getRightChoice());
			choicePanel.add(choice1Btn);
			choice2Btn = new JButton(question.getWrongChoice1());
			choicePanel.add(choice2Btn);
			choice3Btn = new JButton(question.getWrongChoice2());
			choicePanel.add(choice3Btn);
			choice4Btn = new JButton(question.getWrongChoice3());
			choicePanel.add(choice4Btn);
			
			choice1Btn.addActionListener(this);
			choice2Btn.addActionListener(this);
			choice3Btn.addActionListener(this);
			choice4Btn.addActionListener(this);
			
			mainPanel.add(choicePanel);
			
			add(mainPanel, BorderLayout.CENTER);
			
			nextBtn = new JButton("Nästa");
			add(nextBtn, BorderLayout.SOUTH);
			nextBtn.addActionListener(this);
				
		}
		
		
		public void setQuestion(Question question)
		{
			this.question = question;
		}


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
			}
			
		
			
		}
		
		public void setPanelListener(PanelListener panelListener)
		{
			this.panelListener = panelListener;
		}
		
		

}
