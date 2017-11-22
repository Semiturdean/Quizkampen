package quizkampen;

import java.io.*;

public class ChooseCategory extends SearchCategory {

    private File Music = new File("src\\Musik.txt");
    private File Geografi = new File("src\\Geografi.txt");

    ChooseCategory(String category) throws IOException {
        if (category.equalsIgnoreCase("Musik"));
            readFile(Music);
        if (category.equalsIgnoreCase("Geografi"));
            readFile(Geografi);
    }
}
