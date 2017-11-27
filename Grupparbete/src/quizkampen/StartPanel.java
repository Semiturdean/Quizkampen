package quizkampen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class StartPanel extends JPanel implements ActionListener
{
	JPanel userInfo = new JPanel();
	private JButton newGame = new JButton("Starta ett nytt spel");
	private final JLabel userLabel = new JLabel("Ange ett användarnamn:");
	private JLabel userName = new JLabel(""); 
	private JLabel picLabel = new JLabel();
	ImageIcon icon = new ImageIcon();
	private JTextField userNameInput = new JTextField(10);
	private PanelListener panelListener;
	String username = "";

	GridBagConstraints gc = new GridBagConstraints();
	
	public StartPanel(){
		setLayout(new GridLayout(2,0));
		setBackground(Color.PINK);
		
		try {
		URL url = new URL
		("https://res-2.cloudinary.com/westfielddg/image/fetch/c_pad,f_auto,q_auto/http://res.cloudinary.com/westfielddg/image/upload/fph0rlhpygetnd63ydxz.png");
		BufferedImage img = ImageIO.read(url);
		icon.setImage(img);
		picLabel.setIcon(icon);
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		userInfo.setLayout(new GridBagLayout());
		userInfo.setBackground(Color.WHITE);
		
		gc.insets = new Insets(10,10,10,10);
		gc.gridx = 0;
		gc.gridy = 0;
		
		userLabel.setFont(new Font("Serif", Font.BOLD, 24));
		userName.setFont(new Font("Serif", Font.BOLD, 24));
		newGame.setPreferredSize(new Dimension(150,50));
		
		userInfo.add(userLabel, gc); 
	
		gc.gridx = 0;
		gc.gridy = 1;
		
		userInfo.add(userNameInput,gc); userInfo.add(userName, gc);
		
		
		gc.gridx = 0;
		gc.gridy = 2;
		userInfo.add(newGame, gc);
		newGame.setVisible(false);
		userNameInput.addActionListener(this);
		add(userInfo);
		
		JPanel picPanel = new JPanel();
		picPanel.setLayout(new GridBagLayout());
		picPanel.setBackground(Color.WHITE);
		//picLabel.setIcon(new ImageIcon("C:\\Users\\sinasa2\\Desktop\\Q.JPG"));
		picPanel.add(picLabel);
		add(picPanel);
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == userNameInput) {
			if(!userNameInput.getText().equals("")) {
				userName.setText(userNameInput.getText());
				username = userNameInput.getText();
				userInfo.remove(userNameInput);
				newGame.setVisible(true);
				newGame.addActionListener(this);
				userLabel.setText("Användare:");
		}
	}
		if (e.getSource() == newGame) 
		{
			panelListener.startToCategoryPanel(username);
		}
		
	}
	
	public void setPanelListener(PanelListener panelListener)
	{
		this.panelListener = panelListener;
	}

}
