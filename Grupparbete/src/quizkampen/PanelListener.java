package quizkampen;

public interface PanelListener 
{
	public void nextQuestion();
	public void categoryToQuestionPanel(String categoryName);
	public void questionToCategoryPanel();

}
