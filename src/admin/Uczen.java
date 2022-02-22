package admin;

import javafx.beans.property.SimpleStringProperty;

public class Uczen {

    private final SimpleStringProperty imie, nazwisko, adres, telefon, rodzic, klasa;

    public Uczen(String imie, String nazwisko, String adres, String telefon,
                 String rodzic, String klasa) {
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.adres = new SimpleStringProperty(adres);
        this.telefon = new SimpleStringProperty(telefon);
        this.rodzic = new SimpleStringProperty(rodzic);
        this.klasa = new SimpleStringProperty(klasa);

    }

    public String getRodzic() {
        return rodzic.get();
    }

    public void setRodzic(String rodzic) {
        this.rodzic.set(rodzic);
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

    public String getKlasa() {
        return klasa.get();
    }

    public void setKlasa(String klasa) {
        this.klasa.set(klasa);
    }
}
