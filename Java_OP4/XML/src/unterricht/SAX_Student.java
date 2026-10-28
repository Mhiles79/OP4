package unterricht;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

//SAX (Simple API for XML): große xml-Dateien
//Eventbasiert: <start>Inhalt</start> hier treten 3 Events auf
//Einschränkung: Nach Lesen alles weg, kein Baum im Speicher
public class SAX_Student {
    record Student(String id, String name, double grade) {}

    public static void main(String[] args) {
        try {
            SAXParserFactory sax_factory = SAXParserFactory.newInstance();
            SAXParser parser = sax_factory.newSAXParser();
            StudentHandler handler = new StudentHandler();
            parser.parse("resources/students_sax.xml", handler);
            for (Student s: handler.getStudenten()) {
                if(s.grade() < 2.0) {
                    System.out.printf("%s: %s, %.2f%n", s.id, s.name, s.grade);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    static class StudentHandler extends DefaultHandler {
        private List<Student> studenten = new ArrayList<>();
        private String curr_Element;
        private String curr_Id;
        private String curr_Name;
        private String curr_Grade;

        //liest öffnendes <start>
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            curr_Element = qName;
            if(curr_Element.equals("student")) {
                curr_Id = attributes.getValue("id");
            }
        }

        //liest Inhalt von Tag
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String text = new String(ch, start, length).trim();
            if(text.isEmpty()) {
                return;
            }

            switch (curr_Element) {
                case "name":
                    curr_Name = text;
                    break;
                case "grade":
                    curr_Grade = text;
                    break;
            }
        }

        //liest </start>
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equals("student")) {
                studenten.add(new Student(curr_Id, curr_Name, Double.parseDouble(curr_Grade)));
                curr_Name = "";
                curr_Grade = "";
                curr_Id = "";
            }
            curr_Element = "";
        }

        public List<Student> getStudenten() {
            return studenten;
        }
    }
}
