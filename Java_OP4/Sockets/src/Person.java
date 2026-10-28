public class Person {
    int id;
    String name;
    int alter;
    boolean maennlich;

    public Person() {}

    public Person(int id, String name, int alter, boolean maennlich) {
        this.id = id;
        this.name = name;
        this.alter = alter;
        this.maennlich = maennlich;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public boolean isMaennlich() {
        return maennlich;
    }

    public void setMaennlich(boolean maennlich) {
        this.maennlich = maennlich;
    }
}
