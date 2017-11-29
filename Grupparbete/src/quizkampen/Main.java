package quizkampen;

public class Main {

    public static void main(String[] args) {
        ChooseCategory c = new ChooseCategory(2);
        c.setCurrentCategory("Musik");
        c.setCategoryQuestions();
        System.out.println(c.getQuestions());
        System.out.println(c.getAnswers());
    }
}
