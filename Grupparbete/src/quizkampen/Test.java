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
	private Category category;
	private List<Question> questionList;
	
	
	public Test()
	{
		setLayout(new BorderLayout());
		

		questionPanel.setPanelListener(this);
		
		
		
		categoryPanel.setPanelListener(this);
		add(categoryPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,800);
		setVisible(true);
	}
	
	
	@Override
	public void nextQuestion() 
	{
		remove(questionPanel);
		repaint();
		add(questionPanel);
		getContentPane().invalidate();
		getContentPane().revalidate();
	}
	
	public static void main(String[] args)
	{
//		String ctype = "musik";
		Test test = new Test();
//		Database db = new Database();
//		List<Category> cl = db.getCategoryList();
//		List<Question> ql = null;
//		
//		for(int i=0; i<cl.size(); i++)
//		{
//			if(cl.get(i).getName().equalsIgnoreCase(ctype))
//			{
//				ql = cl.get(i).getQuestionList();
//			}
//		}
//		System.out.println(ql.get(0).getQuestion());
	}


	@Override
	public void categoryToQuestionPanel(String str) 
	{
		remove(categoryPanel);
		add(questionPanel, BorderLayout.CENTER);
		getContentPane().invalidate();
		getContentPane().revalidate();
		
	}


	




}
