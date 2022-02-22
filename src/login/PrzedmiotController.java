package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.connectorDB.DBConnect;

import java.io.IOException;
import java.sql.*;

/**
 * klasa kontrolera dla okna Przedmiot
 */
public class PrzedmiotController {

    @FXML
    private MenuItem idMenuKlasa, idMenuNauczyciel, idMenuUczen, idMenuRodzic;
    @FXML
    private AnchorPane przedmiot;
    @FXML
    private TextField idNazwa, idEditNazwa;
    @FXML
    private ChoiceBox<String> idListaNazwKlas, idListaNauczycieli, idEditListaNazwKlas, idEditListaNauczycieli;
    @FXML
    private TableView<Przedmiot> idTabelaPrzedmioty;
    @FXML
    private TableColumn<Przedmiot, String> idTabelaKlasa, idTabelaNazwa, idTabelaNauczyciel;
    @FXML
    public Button idZapisz, idEditZapisz;


    /**
     * wywolanie metod
     *
     * @see PrzedmiotController#wypiszKlasy()
     * @see PrzedmiotController#wypiszNauczycieli()
     * <p>
     * oraz dodanie danych do choice boxow
     */
    @FXML
    private void initialize() {
        wypiszKlasy();
        wypiszNauczycieli();
        idListaNazwKlas.getItems().addAll(klasy);
        idEditListaNazwKlas.getItems().addAll(klasy);
        idListaNauczycieli.getItems().addAll(nauczyciele);
        idEditListaNauczycieli.getItems().addAll(nauczyciele);
        odswiezTabele();
    }

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    ObservableList<String> listaKlas = FXCollections.observableArrayList();
    ObservableList<String> listaNauczycieli = FXCollections.observableArrayList();
    ObservableList<String> klasy = FXCollections.observableArrayList();
    ObservableList<String> nauczyciele = FXCollections.observableArrayList();

    /**
     * @param event przechowuje element wybrany z menu
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void menu(ActionEvent event) throws IOException {
        AdminMenu am = new AdminMenu(przedmiot);

        if (idMenuKlasa.equals(event.getSource())) {
            am.GoToKlasa();
        } else if (idMenuNauczyciel.equals(event.getSource())) {
            am.GoToNauczyciel();
        } else if (idMenuUczen.equals(event.getSource())) {
            am.GoToUczen();
        } else if (idMenuRodzic.equals(event.getSource())) {
            am.GoToRodzic();
        } else {
            am.GoToPrzedmiot();
        }
    }

    /**
     * uzupelnienie listy wartosciami z bazy danych i wypisanie ich w tabeli
     */
    private void uzupelnijTabele() {
        ObservableList<Przedmiot> ol = FXCollections.observableArrayList();
        int i = 0;

        try {
            con = DBConnect.getDBConnection();

            pst = con.prepareStatement("SELECT nazwa FROM przedmiot");

            rs = pst.executeQuery();

            while (rs.next()) {
                ol.add(new Przedmiot(listaKlas.get(i), rs.getString(1), listaNauczycieli.get(i)));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idTabelaKlasa.setCellValueFactory(new PropertyValueFactory<>("klasa"));
        idTabelaNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        idTabelaNauczyciel.setCellValueFactory(new PropertyValueFactory<>("nauczyciel"));

        idTabelaPrzedmioty.setItems(ol);
    }

    /**
     * wywolanie metod
     *
     * @see PrzedmiotController#znajdzKlase()
     * @see PrzedmiotController#znajdzNauczyciela()
     * @see PrzedmiotController#uzupelnijTabele()
     * w celu odswiezenia tabeli
     */
    private void odswiezTabele() {
        znajdzKlase();
        znajdzNauczyciela();
        uzupelnijTabele();
    }

    /**
     * pobierz z bazy nazwy klas
     */
    private void znajdzKlase() {
        try {
            con = DBConnect.getDBConnection();

            pst = con.prepareStatement("SELECT klasa.nazwa FROM klasa INNER JOIN przedmiot ON klasa.id_klasa=przedmiot.id_klasa ORDER BY przedmiot.id_przedmiot ");
            rs = pst.executeQuery();

            while (rs.next()) {
                listaKlas.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * pobierz z bazy imiona i nazwiska nauczycieli
     */
    private void znajdzNauczyciela() {

        String imieINazwiskoNauczyciela;

        try {
            con = DBConnect.getDBConnection();

            pst = con.prepareStatement("SELECT nauczyciel.imie, nauczyciel.nazwisko FROM nauczyciel INNER JOIN przedmiot ON nauczyciel.id_nauczyciel=przedmiot.id_nauczyciel ORDER BY przedmiot.id_przedmiot ");
            rs = pst.executeQuery();

            while (rs.next()) {
                imieINazwiskoNauczyciela = rs.getString(1) + " " + rs.getString(2);
                listaNauczycieli.add(imieINazwiskoNauczyciela);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * wypisz klasy z bazy danych do listy
     *
     * @see PrzedmiotController#klasy
     */
    private void wypiszKlasy() {

        try {
            con = DBConnect.getDBConnection();

            pst = con.prepareStatement("SELECT nazwa FROM klasa");

            rs = pst.executeQuery();

            while (rs.next()) {
                klasy.add(rs.getString(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * wypisz nauczycieli z bazy danych do listy
     *
     * @see PrzedmiotController#nauczyciele
     */
    private void wypiszNauczycieli() {

        String imieINazwiskoNauczyciela;

        try {
            con = DBConnect.getDBConnection();

            pst = con.prepareStatement("SELECT imie, nazwisko FROM nauczyciel");

            rs = pst.executeQuery();

            while (rs.next()) {
                imieINazwiskoNauczyciela = rs.getString(1) + " " + rs.getString(2);
                nauczyciele.add(imieINazwiskoNauczyciela);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private boolean validateInputsedit() {

        if (idEditNazwa.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano nazwy przedmiotu");
            alert.setContentText("Wpisz nazwe przedmiotu");

            alert.showAndWait();
            return false;


        }
        if (idEditListaNazwKlas.getValue()==null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie wybrano klasy");
            alert.setContentText("Wybierz klase");

            alert.showAndWait();
            return false;


        }
        if (idEditListaNauczycieli.getValue()==null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie wybrano nauczyciela");
            alert.setContentText("Wybierz nauczyciela");

            alert.showAndWait();
            return false;


        }

        return true;
    }

    private boolean validateInputsnew() {

        if (idNazwa.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano nazwy przedmiotu");
            alert.setContentText("Wpisz nazwe przedmiotu");

            alert.showAndWait();
            return false;


        }
        if (idListaNazwKlas.getValue()==null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie wybrano klasy");
            alert.setContentText("Wybierz klase");

            alert.showAndWait();
            return false;


        }
        if (idListaNauczycieli.getValue()==null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie wybrano nauczyciela");
            alert.setContentText("Wybierz nauczyciela");

            alert.showAndWait();
            return false;


        }

        return true;
    }
    /**
     * dodawanie przedmiotu do bazy danych
     *
     */
    public void addPrzedmiot(String nazwaKlasy, String przedmiot, String nauczyciel) {

        try {
            con = DBConnect.getDBConnection();

            pst = con.prepareStatement("SELECT id_klasa FROM klasa WHERE nazwa = ?;");
            pst.setString(1, nazwaKlasy);

            rs = pst.executeQuery();
            rs.next();
            int id_klasa = rs.getInt(1);

            pst = con.prepareStatement("SELECT id_nauczyciel FROM nauczyciel WHERE imie = ? AND nazwisko = ?;");    //wyszukiwanie id nauczycleia

            String[] imieINazwiskoNauczyciela = nauczyciel.split(" ");
            pst.setString(1, imieINazwiskoNauczyciela[0]);
            pst.setString(2, imieINazwiskoNauczyciela[1]);

            rs = pst.executeQuery();
            rs.next();
            int id_nauczyciel = rs.getInt(1);

            pst = con.prepareStatement("INSERT INTO przedmiot(id_klasa, nazwa, id_nauczyciel) VALUES (?, ?, ?);");

            pst.setInt(1, id_klasa);
            pst.setString(2, przedmiot);
            pst.setInt(3, id_nauczyciel);


            int i = pst.executeUpdate();
            System.out.println(i + " records inserted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void addPrzedmiotOnAction() throws IOException {
        if (validateInputsnew()) {
            String nazwaKlasy = idListaNazwKlas.getSelectionModel().getSelectedItem();
            String przedmiot = idNazwa.getText();
            String nauczyciel = idListaNauczycieli.getSelectionModel().getSelectedItem();

            addPrzedmiot(nazwaKlasy, przedmiot, nauczyciel);
        }
        menu(new ActionEvent());
    }

    /**
     * edytowanie przedmiotu w bazie danych
     *
     */
    public void editPrzedmiot(String nazwaKlasy, String przedmiot, String nauczyciel, String nazwaKlasyCondition,
                              String przedmiotCondition, String nauczycielCondition) {


        try {
            con = DBConnect.getDBConnection();

            pst = con.prepareStatement("SELECT id_klasa FROM klasa WHERE nazwa=?;");
            pst.setString(1, nazwaKlasy);

            rs = pst.executeQuery();
            rs.next();
            int id_klasa = rs.getInt(1);

            pst = con.prepareStatement("SELECT id_nauczyciel FROM nauczyciel WHERE imie = ? AND nazwisko = ?;");

            String[] imieINazwiskoNauczyciela = nauczyciel.split(" ");
            pst.setString(1, imieINazwiskoNauczyciela[0]);
            pst.setString(2, imieINazwiskoNauczyciela[1]);

            rs = pst.executeQuery();
            rs.next();
            int id_nauczyciel = rs.getInt(1);

            pst = con.prepareStatement("SELECT id_klasa FROM klasa WHERE nazwa=?;");
            pst.setString(1, nazwaKlasyCondition);

            rs = pst.executeQuery();
            rs.next();
            int id_klasaCondition = rs.getInt(1);

            pst = con.prepareStatement("SELECT id_nauczyciel FROM nauczyciel WHERE imie = ? AND nazwisko = ?;");

            String[] imieINazwiskoNauczycielaCondition = nauczycielCondition.split(" ");
            pst.setString(1, imieINazwiskoNauczycielaCondition[0]);
            pst.setString(2, imieINazwiskoNauczycielaCondition[1]);

            rs = pst.executeQuery();
            rs.next();
            int id_nauczycielCondition = rs.getInt(1);

            pst = con.prepareStatement("UPDATE przedmiot SET id_klasa = ?, nazwa = ?, id_nauczyciel = ? WHERE id_klasa = ? AND nazwa = ? AND id_nauczyciel = ?;");

            pst.setInt(1, id_klasa);
            pst.setString(2, przedmiot);
            pst.setInt(3, id_nauczyciel);
            pst.setInt(4, id_klasaCondition);
            pst.setString(5, przedmiotCondition);
            pst.setInt(6, id_nauczycielCondition);

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * metoda wywolywana po kliknieciu przycisku edytowania
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void editPrzedmiotOnAction() throws IOException {
        if (validateInputsedit()) {
            String nazwaKlasy = idEditListaNazwKlas.getSelectionModel().getSelectedItem();
            String przedmiot = idEditNazwa.getText();
            String nauczyciel = idEditListaNauczycieli.getSelectionModel().getSelectedItem();
            ObservableList<Przedmiot> selectedRow = idTabelaPrzedmioty.getSelectionModel().getSelectedItems();


            for (Przedmiot item : selectedRow) {

                String nazwaKlasyCondition = item.getKlasa();
                String przedmiotCondition = item.getNazwa();
                String nauczycielCondition = item.getNauczyciel();

                editPrzedmiot(nazwaKlasy, przedmiot, nauczyciel, nazwaKlasyCondition,
                        przedmiotCondition, nauczycielCondition);
            }
        }
        menu(new ActionEvent());
    }

    /**
     * usuwanie przedmiotu z bazy danych
     */
    public void deletePrzedmiot(String nazwaKlasy, String przedmiot, String nauczyciel) {

        try {
            con = DBConnect.getDBConnection();


            pst = con.prepareStatement("SELECT id_klasa FROM klasa WHERE nazwa = ?;");
            pst.setString(1, nazwaKlasy);

            rs = pst.executeQuery();
            rs.next();
            int id_klasa = rs.getInt(1);

            pst = con.prepareStatement("SELECT id_nauczyciel FROM nauczyciel WHERE imie = ? AND nazwisko = ?;");

            String[] imieINazwiskoNauczyciela = nauczyciel.split(" ");
            pst.setString(1, imieINazwiskoNauczyciela[0]);
            pst.setString(2, imieINazwiskoNauczyciela[1]);

            rs = pst.executeQuery();
            rs.next();
            int id_nauczyciel = rs.getInt(1);

            pst = con.prepareStatement("DELETE FROM przedmiot WHERE id_klasa = ? AND  nazwa = ? AND id_nauczyciel = ?");
            pst.setInt(1, id_klasa);
            pst.setString(2, przedmiot);
            pst.setInt(3, id_nauczyciel);

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * metoda wywolywana po kliknieciu przycisku dodawania
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void deletePrzedmiotOnAction() throws IOException {
        ObservableList<Przedmiot> selectedRow = idTabelaPrzedmioty.getSelectionModel().getSelectedItems();

        for (Przedmiot item : selectedRow) {

            String nazwaKlasy = item.getKlasa();
            String przedmiot = item.getNazwa();
            String nauczyciel = item.getNauczyciel();

            deletePrzedmiot(nazwaKlasy, przedmiot, nauczyciel);

        }

        menu(new ActionEvent());
    }


    /**
     * wyswetla pola do podawania danych ktore zostana wyslane do bazy danych
     *
     */
    private void pokazPola(ChoiceBox<String> idListaNazwKlas, TextField idNazwa, ChoiceBox<String> idListaNauczyciel,
                           Button idZapisz) {

        idListaNazwKlas.setVisible(true);
        idNazwa.setVisible(true);
        idListaNauczyciel.setVisible(true);
        idZapisz.setVisible(true);
    }

    /**
     * wywoluje metode
     *
     * @see PrzedmiotController#pokazPola(ChoiceBox, TextField, ChoiceBox, Button)
     * by wyswietlic pola dodawania klasy
     */
    public void newPokazPola() {
        pokazPola(idListaNazwKlas, idNazwa, idListaNauczycieli, idZapisz);
    }

    /**
     * wywoluje metode
     *
     * @see PrzedmiotController#pokazPola(ChoiceBox, TextField, ChoiceBox, Button)
     * by wyswietlic pola edytowania klasy
     */
    public void editPokazPola() {
        ObservableList<Przedmiot> selectedRow = idTabelaPrzedmioty.getSelectionModel().getSelectedItems();

        for (Przedmiot item : selectedRow) {

            idEditListaNazwKlas.setValue(item.getKlasa());
            idEditNazwa.setText(item.getNazwa());
            idEditListaNauczycieli.setValue(item.getNauczyciel());

        }
        pokazPola(idEditListaNazwKlas, idEditNazwa, idEditListaNauczycieli, idEditZapisz);
    }
    public void btnWyloguj(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }
}


