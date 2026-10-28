package unterricht;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSV_To_JSON {
    public static void main(String[] args) {
        try {
            Files.createDirectories(Path.of("resources"));
            Path csv_datei = Path.of("resources/personen.csv");
            String[] ueberschrift = null;
            List<Map<String, String>> map = new ArrayList<>();
            //CSV Daten einlesen und in Liste aus Schlüssel-Wert-Paaren speichern
            try (BufferedReader br = new BufferedReader(
                new FileReader(csv_datei.toFile(), StandardCharsets.UTF_8)
            )) {
                String zeile;
                while ((zeile = br.readLine()) != null) {
                    String[] felder = zeile.split(",");
                    //1. Zeile = Überschrift
                    if(ueberschrift == null) {
                        ueberschrift = felder; //ueberschrift[0] = Name
                        continue;
                    }
                    //Hierhin, wenn 2., 3. Zeile
                    Map<String, String> paare = new LinkedHashMap<>();
                    for(int i=0; i < ueberschrift.length; i++) {
                        paare.put(ueberschrift[i], felder[i]);
                    }
                    map.add(paare);
                }
            }
            //Map als json speichern, vorher "" ergänzen
            //Kürzer:
            //map.get(i).forEach((k, v) ->
            //   sb.append("\"").append(k).append("\": \"").append(v).append("\", "));
            StringBuilder sb = new StringBuilder("[\n");
            for(int i=0; i < map.size(); i++) {
                sb.append("\t{");
                Map<String, String> paar = map.get(i);
                List<String> keys = new ArrayList<>(paar.keySet());
                for(int j=0; j<keys.size();j++) {
                    String k = keys.get(j);
                    String v = paar.get(k);
                    sb.append("\"").append(k).append("\": \"").append(v).append("\"");
                    if(j < keys.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("\t}");
                sb.append(i < map.size() - 1? ",\n": "\n");
            }
            sb.append("]");

            try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("resources/personen.json", StandardCharsets.UTF_8)
            )) {
                bw.write(sb.toString());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

/*
[
  {"name": "Homer", "alter": "30", "stadt": "Berlin"},
  {"name": "Bart", "alter": "25", "stadt": "Hamburg"}
]
 */
