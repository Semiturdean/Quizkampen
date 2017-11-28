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

public class CategoryPanel extends JPanel implements ActionListener, PanelListener
{
    private JPanel mainPanel;
    private JPanel labelPanel;
    private JPanel buttonPanel;
    private JButton nextBtn;
    private JLabel categoryLabel;

    private PanelListener panelListener;

    //Den h�r best�mmer hur m�nga kategorier vi kommer att ha
    private int numberOfCategories = 3;
    String categoryName = "";

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
        placeButtons();
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


		/*h�r best�ms vad h�nder n�r man clickar p� nextBtn
		n�r spelaren klickar p� n�sta vi s�tter tillbaks det originala f�rget p� knappen
		samt actionlistenern s� att n�sta g�ng vi kallar fram panelen allt �r som b�rjan  */
        if(e.getSource() == nextBtn)
        {
            for(Component c : buttonPanel.getComponents())
            {
                c.setBackground(nextBtn.getBackground());
                ((AbstractButton) c).addActionListener(this);

            }
            nextBtn.setVisible(false);
            sendCategory(categoryName);

            //raden nedan kommunicerar kategorin som har valts till framen

        }

    }

    //Den h�r metoden anv�nds senare i framen f�r att koppla den h�r panelen till interfacet
    public void setPanelListener(PanelListener panelListener)
    {
        this.panelListener = panelListener;
    }

    //Den h�r metoden l�gger knapparna p� buttonPanel
    public void placeButtons()
    {
        //Knapparna l�ggs genom den h�r forloopen
        for(int i=0; i<numberOfCategories; i++) {
            if (i == 0){
                JButton b = new JButton("Musik");
            buttonPanel.add(b);
            b.addActionListener(this);
        }
            if(i==1){
                JButton b = new JButton("Histora");
                buttonPanel.add(b);
                b.addActionListener(this);
            }
            if (i==2){
                JButton b = new JButton("Geografi");
                buttonPanel.add(b);
                b.addActionListener(this);
            }

        }
    }

    //Den h�r metoden s�tter kategorinamn p� knapparna (OBS. Det �r framen som skickar en lista med kategorier hit)
    public void setButtonNames(List<Category> cl)
    {
        for(int i=0; i<numberOfCategories;i++)
        {
            //((AbstractButton)buttonPanel.getComponents()[i]).setText(cl.get(i).getName());
        }
    }

    @Override
    public void sendToServer(String message) {

    }
}
