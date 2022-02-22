package admin;

import javafx.beans.property.SimpleStringProperty;

public class Rodzic {

    private final SimpleStringProperty imie, nazwisko, adres, telefon;

    public Rodzic(String imie, String nazwisko, String adres, String telefon) {
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.adres = new SimpleStringProperty(adres);
        this.telefon = new SimpleStringProperty(telefon);
    }

    public String getImie() {
        return imie.get();
    }

    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public String getAdres() {
        return adres.get();
    }

    public void setAdres(String adres) {
        this.adres.set(adres);
    }

    public String getTelefon() {
        return telefon.get();
    }

    public void setTelefon(String telefon) {
        this.telefon.set(telefon);
    }
}
