package admin;

import javafx.beans.property.SimpleStringProperty;

public class Przedmiot {
    private final SimpleStringProperty klasa, nazwa, nauczyciel;

    public Przedmiot(String klasa, String nazwa, String nauczyciel) {
        this.klasa = new SimpleStringProperty(klasa);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.nauczyciel = new SimpleStringProperty(nauczyciel);
    }

    public String getKlasa() {
        return klasa.get();
    }

    public void setKlasa(String klasa) {
        this.klasa.set(klasa);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public String getNauczyciel() {
        return nauczyciel.get();
    }

    public void setNauczyciel(String nauczyciel) {
        this.nauczyciel.set(nauczyciel);
    }
}
