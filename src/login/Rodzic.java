package login;

import javafx.beans.property.SimpleStringProperty;

public class Rodzic {

    private final SimpleStringProperty imie, nazwisko,imie1, nazwisko1, adres, telefon;

    public Rodzic(String imie, String nazwisko,String imie1, String nazwisko1, String adres, String telefon) {
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.imie1 = new SimpleStringProperty(imie1);
        this.nazwisko1 = new SimpleStringProperty(nazwisko1);
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

    public String getImie1() {
        return imie1.get();
    }

    public void setImie1(String imie1) {
        this.imie1.set(imie1);
    }

    public String getNazwisko1() {
        return nazwisko1.get();
    }

    public void setNazwisko1(String nazwisko1) {
        this.nazwisko1.set(nazwisko1);
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
