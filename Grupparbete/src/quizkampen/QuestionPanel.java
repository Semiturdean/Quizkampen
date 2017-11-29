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
		 * QuestionPanelen har en mainPanel och en knapp f�r att g� vidare
		 * p� mainPanel finns det tv� paneler, en till fr�gan och den andra till knapparna som
		 * visar 4 val
		 */
	
	
		//Countern f�r att byta panel efter 3 fr�gor
		int questionCounter = 0;
		
		
		//Alla componenter initieras h�r
		private JLabel questionLabel;
		private JButton choice1Btn;
		private JButton choice2Btn;
		private JButton choice3Btn;
		private JButton choice4Btn;
		private JButton nextBtn;
		
		
		//Tv� panel skapas h�r
		JPanel mainPanel = new JPanel();
		JPanel questionPanel = new JPanel();
		JPanel choicePanel = new JPanel();
		
		//inteface f�r att kommunicera med framen
		private PanelListener panelListener;
		
		public QuestionPanel()
		{
			setLayout(new BorderLayout());
			
			
			mainPanel.setLayout(new GridLayout(2,0));
			
			
			
			
			/////Fr�gan////////////////////////////////////		
			
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
			
			
			///////Knappen f�r att g� vidare
			
			nextBtn = new JButton("N�sta");
			add(nextBtn, BorderLayout.SOUTH);
			nextBtn.addActionListener(this);
				
		}
		
		//F�r att l�nka en fr�ga till labeln och knapparna
		
//		public void setQuestion(Question question)
//		{
//			questionLabel.setText(question.getQuestion());
//			choice1Btn.setText(question.getRightChoice());
//			choice2Btn.setText(question.getWrongChoice1());
//			choice3Btn.setText(question.getWrongChoice2());
//			choice4Btn.setText(question.getWrongChoice3());
//		}

		/*
		 * actionListenern �ndrar f�rg p� knappen till gr�n om det �r r�tt svar och till r�d om det �r fel svar
		 * om fel svar har valts, det r�tta svaret visas ocks� genom att andr� f�rget p� knappen till gr�n
		 * n�r man kilckar p� n�sta s�tts de originala f�rgen tillbaka p� knapparna och s� g�r man till n�sta fr�ga
		 * questionCounter h�ller koll p� hur m�nga fr�gor har st�llts. n�r 3 fr�gor har st�llts byter actionListenern panel
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
					if(clicked.getText().equalsIgnoreCase("r�tt svar"))
					{
						c.setBackground(Color.GREEN); 
						//panelListener.setScore();
						
					}
					else 
					{
						c.setBackground(Color.RED);
					}
				}
				else if(((AbstractButton) c).getText().equalsIgnoreCase("r�tt svar") && clicked != nextBtn)
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
				//panelListener.nextQuestion();
				for(Component c : choicePanel.getComponents())
				{
					c.setBackground(nextBtn.getBackground());
					((JButton) c).addActionListener(this);
				}
				questionCounter++;
				if(questionCounter == 3)
				{
					//panelListener.questionToEndOfRoundPanel();
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
