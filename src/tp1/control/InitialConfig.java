package tp1.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InitialConfig {
    public static final InitialConfig NONE = new InitialConfig();

    private List<String> stringDescriptions;
    private InitialConfig(List<String> stringDescriptions){
        this.stringDescriptions = stringDescriptions;
    }
    private InitialConfig(){
    }
    public static InitialConfig readFromFile(String fileName) throws FileNotFoundException, IOException {
        FileReader file = new FileReader(fileName);
        Scanner scanner = new Scanner(file);
        List<String> descriptions = new ArrayList<>();

        while (scanner.hasNextLine()) {
            descriptions.add(scanner.nextLine());
        }
        return new InitialConfig(descriptions);
    }

}
