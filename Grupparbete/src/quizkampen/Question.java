<<<<<<< HEAD
package quizkampen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Question 
{
	//Skapar en fråga med rätt svar och 3 fel svar
	private String question;
	private String rightChoice;
	private String wrongChoice1;
	private String wrongChoice2;
	private String wrongChoice3;
	
	//0 = Fråga, 1 = ifall frågan har ställts, 2 & 3 = rätt svar, 4-6 = fel svar
		static String[][] stupidQuestion = new String[][] {
			{"Hur många llllllll?", "0", "l", "l", "3", "4", "5"},
			{"Hur många aaaaaaaaaa ?", "0", "a", "a", "3", "4", "5"},
			{"Hur många hhhhhhhhhh", "0", "h", "h", "3", "4", "5"},
			{"Hur många ttttttttt", "0", "t", "t", "3", "4", "5"},
			{"Hur många bbbbbbbbbbb", "0", "b", "b", "3", "4", "5"}
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
		
		//Loopar igenom för att lägga till de 4 svarsalternativ i en arraylist.
		for(int i=3; i<stupidQuestion[rad].length; i++) {
			stupidQuestion[rad][1] = "used";
			tempList.add(stupidQuestion[rad][i]);
		}	
		
		//Slumpar innehållet i arrayListen
		Collections.shuffle(tempList);
		
		//Skriver in de slumpade värdena på deras tidigare platser.
		int y= 0;
		for(int i=3; i<stupidQuestion[rad].length; i++) {
			
			stupidQuestion[rad][i] = tempList.get(y);
			y++;
			System.out.println(stupidQuestion[rad][i]);
		}
	}
	
}
=======
package quizkampen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Question
{
	//Skapar en frÃ¥ga med rÃ¤tt svar och 3 fel svar
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

    //0 = FrÃ¥ga, 1 = ifall frÃ¥gan har stÃ¤llts, 2 & 3 = rÃ¤tt svar, 4-6 = fel svar
    String[][] stupidQuestion = new String[][]{
            {"Hur mÃ¥nga llllllll?", "0", "l", "l", "3", "4", "5"},
            {"Hur mÃ¥nga aaaaaaaaaa ?", "0", "a", "a", "3", "4", "5"},
            {"Hur mÃ¥nga hhhhhhhhhh", "0", "h", "h", "3", "4", "5"},
            {"Hur mÃ¥nga ttttttttt", "0", "t", "t", "3", "4", "5"},
            {"Hur mÃ¥nga bbbbbbbbbbb", "0", "b", "b", "3", "4", "5"}
    };

	public void mix(String[][] stupidQuestion) {
		Random rnd = new Random();
		int rad = rnd.nextInt(stupidQuestion.length) + 0;
		ArrayList<String> tempList = new ArrayList<>();

		//Loopar igenom fÃ¶r att lÃ¤gga till de 4 svarsalternativ i en arraylist.
        for (int i = 3; i < stupidQuestion[rad].length; i++) {
            stupidQuestion[rad][1] = "used";
            tempList.add(stupidQuestion[rad][i]);
        }

		//Slumpar innehÃ¥llet i arrayListen
		Collections.shuffle(tempList);

		//Skriver in de slumpade vÃ¤rdena pÃ¥ deras tidigare platser.
        int y = 0;
        for (int i = 3; i < stupidQuestion[rad].length; i++) {

            stupidQuestion[rad][i] = tempList.get(y);
            y++;
            System.out.println(stupidQuestion[rad][i]);
        }
    }
}
>>>>>>> branch 'master' of https://github.com/Semiturdean/Quizkampen.git
