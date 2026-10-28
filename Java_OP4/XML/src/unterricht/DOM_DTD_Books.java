package unterricht;//XML ist wohlgeformt, wenn Syntax, Struktur passt (ein Wurzelelement, Schließen-Tags, etc.)
//XML ist valide, wenn Elemente und deren Reihenfolge und Anzahl stimmen (in dtd oder xsd)
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

//DOM = Document Object Model: Gesamte xml-Dokument wird als Baum in RAM geladen
//In dem Baum dann suchen, löschen, etc. (gut bei kleinen xml-Dateien)
public class DOM_DTD_Books {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory book_factory = DocumentBuilderFactory.newInstance();
            //Parser prüft ob es DTD entspricht
            book_factory.setValidating(true);
            //Parser erzeugen
            DocumentBuilder book_parser = book_factory.newDocumentBuilder();
            //Nur für Validierungsfehler
            book_parser.setErrorHandler(new DefaultHandler() {
                @Override
                public void error(SAXParseException e) throws SAXException {
                    throw new SAXException("Validierungsfehler: " + e.getMessage());
                }
            });
            Document doc_book = book_parser.parse(new File("resources/books_dtd.xml"));
            //Fügt getrennte Textknoten zusammen (empfohlen wegen bestimmten DOM Operationen)
            doc_book.getDocumentElement().normalize();
            NodeList books = doc_book.getElementsByTagName("book"); //All "book" im Wurzelknoten
            for (int i = 0; i < books.getLength(); i++) {
                Element book = (Element) books.item(i);
                String id = book.getAttribute("id");
                String title = book.getElementsByTagName("title").item(0).getTextContent();
                String author = book.getElementsByTagName("author").item(0).getTextContent();
                String year = book.getElementsByTagName("year").item(0).getTextContent();
                System.out.printf("%s: %s von %s im Jahr %s%n", id, title, author, year);
            }
            writeFiltered(books);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void writeFiltered(NodeList books) throws Exception {
        DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document books_filtered = parser.newDocument();
        Element root = books_filtered.createElement("result");
        books_filtered.appendChild(root);
        for (int i = 0; i < books.getLength(); i++) {
            Element book = (Element) books.item(i);
            //Filtern: alle Bücher wo year > 2000
            int year = Integer.parseInt(book.getElementsByTagName("year").item(0).getTextContent());
            if(year > 2000) {
                Node knoten = books_filtered.importNode(book, true);
                root.appendChild(knoten);
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(
            new DOMSource(books_filtered),
            new StreamResult(new File("resources/books_output.xml"))
        );
    }
}
