package quizkampen;

import java.util.ArrayList;
import java.util.List;

public class Database 
{
	private List<Category> categoryList = new ArrayList<>();
	
	private Category sport = new Category("Sport");
	private Category music = new Category("Musik");
	private Category computerGames = new Category("Datorspel");
	
	
	public Database()
	{
		categoryList.add(sport);
		categoryList.add(music);
		categoryList.add(computerGames);
		
		for(Category c : categoryList)
		{
			for(int i=0; i<6; i++)
			{
				Question question = new Question(c.getName()+"fråga"+i,"rätt svar", "fel svar","fel svar","fel svar");
				c.addQuestion(question);
			}
			
		}
		
		
		
	}
	public List<Category> getCategoryList()
	{
		return categoryList;
	}

}
