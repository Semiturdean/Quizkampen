package quizkampen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CategoryPanel extends JPanel implements ActionListener
{
	private JPanel mainPanel;
	private JPanel labelPanel;
	private JPanel buttonPanel;
	private JButton nextBtn;
	private JLabel categoryLabel; 
	
	private PanelListener panelListener;
	
	//Den här bestämmer hur många kategorier vi kommer att ha
	private int numberOfCategories = 3;
	String categoryName = "";
	
	public CategoryPanel()
	{
		//Panelen består av  en mainPanel och en knapp. mainPanelen har två paneler på sig, en labelPanel 
		//och en buttonPanel
		
		//CategoryPanel
		setLayout(new BorderLayout());
		
		//mainPanel
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,0));
		
		//labelPanel
		labelPanel = new JPanel();
		labelPanel.setLayout(new GridBagLayout());
		labelPanel.setBorder(new LineBorder(Color.BLACK, 5));
		categoryLabel = new JLabel("Välj Kategori");
		categoryLabel.setFont(new Font("Serif", Font.PLAIN, 40));
		labelPanel.add(categoryLabel);
		mainPanel.add(labelPanel);
		
		//buttonPanel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(numberOfCategories,0));
		placeButtons();
		mainPanel.add(buttonPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		
		//Knapp för att gå vidare
		nextBtn = new JButton("Nästa");
		nextBtn.setVisible(false);
		nextBtn.addActionListener(this);
		add(nextBtn, BorderLayout.SOUTH);
		
	}

	
	//actionListener förvandlar knappen som har valts till gul och visar nextBtn
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		for(Component c : buttonPanel.getComponents())
		{
			if(e.getSource() == c)
			{
				c.setBackground(Color.YELLOW);
				categoryName = ((AbstractButton) c).getText();
				((AbstractButton) c).removeActionListener(this);
				nextBtn.setVisible(true);
			}
			else
			{
				((AbstractButton) c).removeActionListener(this);
			}
		}
		
		
		/*här bestäms vad händer när man clickar på nextBtn
		när spelaren klickar på nästa vi sätter tillbaks det originala färget på knappen 
		samt actionlistenern så att nästa gång vi kallar fram panelen allt är som början  */
		if(e.getSource() == nextBtn)
		{
			for(Component c : buttonPanel.getComponents())
			{
				c.setBackground(nextBtn.getBackground());
				((AbstractButton) c).addActionListener(this);
			}
			nextBtn.setVisible(false);
			
			//raden nedan kommunicerar kategorin som har valts till framen
			panelListener.categoryToQuestionPanel(categoryName);
		}
		
	}
	
	//Den här metoden används senare i framen för att koppla den här panelen till interfacet
	public void setPanelListener(PanelListener panelListener)
	{
		this.panelListener = panelListener;
	}
	
	//Den här metoden lägger knapparna på buttonPanel
	public void placeButtons()
	{
		//Knapparna läggs genom den här forloopen
				for(int i=0; i<numberOfCategories; i++)
				{
					JButton b = new JButton("Kategori"+i);
					buttonPanel.add(b);
					b.addActionListener(this);
				}
	}
	
	//Den här metoden sätter kategorinamn på knapparna (OBS. Det är framen som skickar en lista med kategorier hit)
	public void setButtonNames(List<Category> cl)
	{
		for(int i=0; i<numberOfCategories;i++)
		{
			((AbstractButton)buttonPanel.getComponents()[i]).setText(cl.get(i).getName());
		}
	}

}
