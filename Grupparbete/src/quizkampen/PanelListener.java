package quizkampen;

public interface PanelListener 
{
	public void nextQuestion();
	public void categoryToQuestionPanel(String categoryName);
	public void questionToEndOfRoundPanel();
	public void endOfRoundToCategoryPanel();
	public void setScore();
	public void startToCategoryPanel(String username);

}
