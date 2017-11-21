package quizkampen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Question 
{
	//Skapar en fr�ga med r�tt svar och 3 fel svar
	private String question;
	private String rightChoice;
	private String wrongChoice1;
	private String wrongChoice2;
	private String wrongChoice3;
	
	//0 = Fr�ga, 1 = ifall fr�gan har st�llts, 2 & 3 = r�tt svar, 4-6 = fel svar
		static String[][] stupidQuestion = new String[][] {
			{"Hur m�nga llllllll?", "0", "l", "l", "3", "4", "5"},
			{"Hur m�nga aaaaaaaaaa ?", "0", "a", "a", "3", "4", "5"},
			{"Hur m�nga hhhhhhhhhh", "0", "h", "h", "3", "4", "5"},
			{"Hur m�nga ttttttttt", "0", "t", "t", "3", "4", "5"},
			{"Hur m�nga bbbbbbbbbbb", "0", "b", "b", "3", "4", "5"}
		};
	
	
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
	
	public static void mix(String[][] stupidQuestion) {
		Random rnd = new Random();
		int rad = rnd.nextInt(stupidQuestion.length) + 0;
		ArrayList<String> tempList = new ArrayList<String>();
		
		//Loopar igenom f�r att l�gga till de 4 svarsalternativ i en arraylist.
		for(int i=3; i<stupidQuestion[rad].length; i++) {
			stupidQuestion[rad][1] = "used";
			tempList.add(stupidQuestion[rad][i]);
		}	
		
		//Slumpar inneh�llet i arrayListen
		Collections.shuffle(tempList);
		
		//Skriver in de slumpade v�rdena p� deras tidigare platser.
		int y= 0;
		for(int i=3; i<stupidQuestion[rad].length; i++) {
			
			stupidQuestion[rad][i] = tempList.get(y);
			y++;
			System.out.println(stupidQuestion[rad][i]);
		}
	}
	
}
