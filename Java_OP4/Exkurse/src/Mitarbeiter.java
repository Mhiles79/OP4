public class Mitarbeiter {
    private String name;
    private String abteilung;
    private double gehalt;
    private int alter;
    private boolean isAktiv;

    public Mitarbeiter(String name, String abteilung, double gehalt, int alter, boolean isAktiv) {
        this.name = name;
        this.abteilung = abteilung;
        this.gehalt = gehalt;
        this.alter = alter;
        this.isAktiv = isAktiv;
    }

    public String getName() {
        return name;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public double getGehalt() {
        return gehalt;
    }

    public int getAlter() {
        return alter;
    }

    public boolean isAktiv() {
        return isAktiv;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }

    public void setGehalt(double gehalt) {
        this.gehalt = gehalt;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public void setAktiv(boolean aktiv) {
        isAktiv = aktiv;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %.2f, %d, %s", name, abteilung, gehalt, alter, isAktiv?"Aktiv":"Inaktiv");
    }
}

