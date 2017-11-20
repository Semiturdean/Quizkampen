package quizkampen;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessagePanel extends JPanel
{
	private JLabel label;
	
	public MessagePanel()
	{
		setLayout(new FlowLayout());
		
		label = new JLabel();
		label.setText("QuizzKamp");
		add(label);
	}
	public void setLabel(String text)
	{
		label.setText(text);
	}

}
