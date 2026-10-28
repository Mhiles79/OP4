public class Nachricht {
    String user;
    String message;

    public Nachricht(String user, String message) {
        this.user = user;
        this.message = message;
    }
    @Override
    public String toString() {
        return user + ": " + message;
    }
}
