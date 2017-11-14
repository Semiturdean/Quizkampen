package quizkampen;

public class Question 
{
	//Skapar en fråga med rätt svar och 3 fel svar
	private String question;
	private String rightChoice;
	private String wrongChoice1;
	private String wrongChoice2;
	private String wrongChoice3;
	
	public Question(String question, String rightChoice, String wrongChoice1,String wrongChoice2, String wrongChoice3)
	{
		this.question = question;
		this.rightChoice = rightChoice;
		this.wrongChoice1 = wrongChoice1;
		this.wrongChoice2 = wrongChoice2;
		this.wrongChoice3 = wrongChoice3;
		
	}

	public String getQuestion() {
		return question;
	}

	public String getRightChoice() {
		return rightChoice;
	}

	public String getWrongChoice1() {
		return wrongChoice1;
	}

	public String getWrongChoice2() {
		return wrongChoice2;
	}

	public String getWrongChoice3() {
		return wrongChoice3;
	}
	

}
