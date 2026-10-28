import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

//com.google.code.gson:gson:2.13.2
public class JSONServer {
    static void main(String[] args) {
        Map<String, Person> person_db;

        try (ServerSocket serverSocket = new ServerSocket(5555)) {
            System.out.println("Warte ...");
            person_db = ladeJson("resources/personen.json");
            try(Socket client = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                String id;
                while ((id = in.readLine()) != null) {
                    if(id.equalsIgnoreCase("q")) {
                        break;
                    }
                    Person gesucht = person_db.get(id);
                    if (gesucht == null) {
                        out.println(id + " gibt es nicht");
                    } else {
                        out.println(new Gson().toJson(gesucht));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    static Map<String, Person> ladeJson(String pfad) throws Exception {
        Type listType = new TypeToken<List<Person>>() {}.getType();
        try(BufferedReader br = new BufferedReader(new FileReader(pfad))) {
            List<Person> personen = new Gson().fromJson(br, listType);
            return personen.stream()
                    .collect(Collectors.toMap(p -> String.valueOf(p.getId()), p -> p));
        }
    }
}
