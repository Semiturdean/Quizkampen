package quizkampen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Test extends JFrame implements PanelListener
{
	private QuestionPanel questionPanel = new QuestionPanel();
	private CategoryPanel categoryPanel = new CategoryPanel();
	private Database db = new Database();
	private List<Category> categoryList = db.getCategoryList();
	private Category category = null;
	private List<Question> questionList; 
	
	int questionCounter = 0;
	
	
	public Test()
	{
		setLayout(new BorderLayout());
		

		questionPanel.setPanelListener(this);
		
		
		categoryPanel.setButtonNames(categoryList);
		categoryPanel.setPanelListener(this);
		add(categoryPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,800);
		setVisible(true);
	}
	
	
	@Override
	public void nextQuestion() 
	{
		questionCounter++;
		questionPanel.setQuestion(questionList.get(questionCounter));	
	}
	


	@Override
	public void categoryToQuestionPanel(String categoryName) 
	{	
		setCategory(categoryName);
		questionList = category.getQuestionList();
		remove(categoryPanel);
		repaint();
		questionCounter = 0;
		questionPanel.setQuestionCounter(0);
		questionPanel.setQuestion(questionList.get(questionCounter));		
		add(questionPanel, BorderLayout.CENTER);
		getContentPane().invalidate();
		getContentPane().revalidate();
		
	}
	public void setCategory(String categoryName)
	{
		for(int i=0; i<categoryList.size(); i++)
		{
			if (categoryName.equalsIgnoreCase(categoryList.get(i).getName()))
			{
				category = categoryList.get(i);
				break;
				
			}
		}
	}


	@Override
	public void questionToCategoryPanel() 
	{
		remove(questionPanel);
		repaint();
		add(categoryPanel, BorderLayout.CENTER);
		getContentPane().invalidate();
		getContentPane().revalidate();
		
	}
	 

	public static void main(String[] args)
	{
		
		Test test = new Test();
	}


}
