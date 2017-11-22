package quizkampen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SearchCategory extends Questions {

    private List<String> threeQuestions = new ArrayList<>();


    public void readFile(File category) {
        Map<String, Integer> map = new HashMap<>();
        while (map.size() < 3) {
            String s = null;
            try {
                s = choose(category);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (!map.containsKey(s))
                threeQuestions.add(s);
            map.put(s, 0);
            map.put(s, map.get(s) + 1);
        }
        setQuestionOne(threeQuestions.subList(0,1));
        setQuestionTwo(threeQuestions.subList(1,2));
        setQuestionThree(threeQuestions.subList(2,3));

    }

    public String choose(File f) throws FileNotFoundException {
        String result = null;
        Random rand = new Random();
        int n = 0;
        for (Scanner sc = new Scanner(f); sc.hasNext(); ) {
            ++n;
            String line = sc.nextLine();
            if (rand.nextInt(n) == 0)
                result = line;
        }
        return result;
    }

}

