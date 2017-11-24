package quizkampen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndOfRoundPanel extends JPanel implements ActionListener
{
	private JLabel resultLabel = new JLabel("");
	private JButton nextRoundBtn = new JButton("Nästa Rond");
	
	private PanelListener panelListener;
	
	public EndOfRoundPanel()
	{
		setLayout(new GridLayout(2,0));
		
		JPanel resultPanel = new JPanel();
		resultPanel.setBackground(Color.LIGHT_GRAY);
		resultPanel.setLayout(new GridBagLayout());
		resultLabel.setFont(new Font("Serif", Font.BOLD, 32));
		resultPanel.add(resultLabel);
		
		add(resultPanel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		nextRoundBtn.setPreferredSize(new Dimension(250,100));
		nextRoundBtn.setEnabled(false);
		nextRoundBtn.addActionListener(this);
		buttonPanel.add(nextRoundBtn);
		add(buttonPanel);
		
		
		
	}
	
	public void setLabel(String text)
	{
		resultLabel.setText(text);
	}
	
	public void enableButton()
	{
		nextRoundBtn.setEnabled(true);
	}
	public void setButtonText(String text)
	{
		nextRoundBtn.setText(text);
	}

	public void setPanelListener(PanelListener panelListener)
	{
		this.panelListener = panelListener;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == nextRoundBtn)
		panelListener.endOfRoundToCategoryPanel();
	}
}
