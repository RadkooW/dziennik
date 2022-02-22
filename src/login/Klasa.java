package login;

import javafx.beans.property.SimpleStringProperty;

public class Klasa {

    private final SimpleStringProperty nazwa;

    public Klasa(String nazwa) {
        this.nazwa = new SimpleStringProperty(nazwa);

    }

    public String getNazwa() {
        return nazwa.get();
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }


}
