package quizkampen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SearchCategory extends Questions {

    private List<String> questions = new ArrayList<>();

    public void readFile(File category) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        int i = 3;
        while (map.size() < i) {
            String s = null;
            try {
                s = choose(category);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (!map.containsKey(s))
                questions.add(map.size(), s);
            map.put(s, 0);
            map.put(s, map.get(s) + 1);
        }
        setQuestions(questions);
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