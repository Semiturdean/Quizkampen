package quizkampen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class Test extends JFrame
{
	EndOfRoundPanel erp = new EndOfRoundPanel();
	public Test()
	{
		setLayout(new BorderLayout());
		
		erp.setLabel("0-0");
		erp.setButtonText("nästa rond");
		erp.enableButton();
		add(erp);
		
		
		setDefaultCloseOperation(3);
		setSize(600,400);
		setVisible(true);
		
	}
	
	public static void main(String[] args)
	{
		Test test = new Test();
	}

}
