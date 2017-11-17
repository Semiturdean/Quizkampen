package quizkampen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	//Den h�r best�mmer hur m�nga kategorier vi kommer att ha
	private int numberOfCategories = 3;
	
	public CategoryPanel()
	{
		//Panelen best�r av  en mainPanel och en knapp. mainPanelen har tv� paneler p� sig, en labelPanel 
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
		categoryLabel = new JLabel("V�lj Kategori");
		categoryLabel.setFont(new Font("Serif", Font.PLAIN, 40));
		labelPanel.add(categoryLabel);
		mainPanel.add(labelPanel);
		
		//buttonPanel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(numberOfCategories,0));
		
		//Knapparna l�ggs genom den h�r forloopen
		for(int i=0; i<numberOfCategories; i++)
		{
			JButton b = new JButton("Kategori"+i);
			buttonPanel.add(b);
			b.addActionListener(this);
		}
		mainPanel.add(buttonPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		
		//Knapp f�r att g� vidare
		nextBtn = new JButton("N�sta");
		nextBtn.setVisible(false);
		nextBtn.addActionListener(this);
		add(nextBtn, BorderLayout.SOUTH);
		
	}

	
	//actionListener f�rvandlar knappen som har valts till gul och visar nextBtn
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String category = "";
		for(Component c : buttonPanel.getComponents())
		{
			if(e.getSource() == c)
			{
				c.setBackground(Color.YELLOW);
				System.out.println(c.getName());
				category = c.getName();
				((AbstractButton) c).removeActionListener(this);
				nextBtn.setVisible(true);
			}
			else
			{
				((AbstractButton) c).removeActionListener(this);
			}
		}
		
		//h�r best�ms vad h�nder n�r man clickar p� nextBtn
		if(e.getSource() == nextBtn)
		{
			panelListener.categoryToQuestionPanel(category);
		}
		
	}

	public void setPanelListener(PanelListener panelListener)
	{
		this.panelListener = panelListener;
	}

}
