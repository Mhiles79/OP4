import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spieler implements Serializable {
    String name;
    int punkte;
    int level;
    List<String> erfolge;

    public Spieler(String name, int punkte, int level, String... erfolge) {
        this.name = name;
        this.punkte = punkte;
        this.level = level;
        this.erfolge = new ArrayList<>(Arrays.asList(erfolge));
    }

    @Override
    public String toString() {
        return String.format("%s, Level %d, %d Punkte, %s", name, level, punkte, erfolge);
    }
}
