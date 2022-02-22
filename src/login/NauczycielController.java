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
 * klasa kontrolera dla okna Nauczyciel
 */
public class NauczycielController {

    @FXML
    private MenuItem idMenuKlasa, idMenuPrzedmiot, idMenuUczen, idMenuRodzic;
    @FXML
    public TextField newImie, newNazwisko, newAdres, newTelefon, editImie, editNazwisko, editAdres, editTelefon;
    @FXML
    public Button newZapisz, editZapisz;
    @FXML
    public AnchorPane nauczyciel;
    @FXML
    private TableColumn<Nauczyciel, String> idTabelaImie, idTabelaNazwisko, idTabelaAdres, idTabelaTelefon;
    @FXML
    private TableView<Nauczyciel> idTabela;

    /**
     * wywolanie metod
     *

     * <p>
     * oraz dodanie danych do choice boxow
     */
    @FXML
    private void initialize() {

        uzupelnijTabele();
    }

    Connection con;
    PreparedStatement pst;
    ResultSet rs;


    /**
     * @param event przechowuje element wybrany z menu
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void menu(ActionEvent event) throws IOException {
        AdminMenu am = new AdminMenu(nauczyciel);

        if (idMenuKlasa.equals(event.getSource())) {
            am.GoToKlasa();
        } else if (idMenuPrzedmiot.equals(event.getSource())) {
            am.GoToPrzedmiot();
        } else if (idMenuUczen.equals(event.getSource())) {
            am.GoToUczen();
        } else if (idMenuRodzic.equals(event.getSource())) {
            am.GoToRodzic();
        } else {
            am.GoToNauczyciel();
        }
    }

    /**
     * uzupelnienie listy wartosciami z bazy danych i wypisanie ich w tabeli
     */
    private void uzupelnijTabele() {
        ObservableList<Nauczyciel> ol = FXCollections.observableArrayList();
        int i = 0;

        try {
            con= DBConnect.getDBConnection();
            pst = con.prepareStatement("SELECT imie, nazwisko, adres, telefon FROM nauczyciel");

            rs = pst.executeQuery();

            while (rs.next()) {
                ol.add(new Nauczyciel(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idTabelaImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        idTabelaNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        idTabelaAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
        idTabelaTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));


        idTabela.setItems(ol);
    }

    private boolean validateInputsnew() {
        if (newImie.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano imienia");
            alert.setContentText("Wpisz imie");

            alert.showAndWait();
            return false;


        }
        if (newNazwisko.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano nazwiska");
            alert.setContentText("Wpisz nazwisko");

            alert.showAndWait();
            return false;


        }
        if (newAdres.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano adresu");
            alert.setContentText("Wpisz adres");

            alert.showAndWait();
            return false;


        }
        if (newTelefon.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano telefonu");
            alert.setContentText("Wpisz numer telefonu");

            alert.showAndWait();
            return false;


        }

        return true;
    }
    private boolean validateInputsedit() {
        if (editImie.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano imienia");
            alert.setContentText("Wpisz imie");

            alert.showAndWait();
            return false;


        }
        if (editNazwisko.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano nazwiska");
            alert.setContentText("Wpisz nazwisko");

            alert.showAndWait();
            return false;


        }
        if (editAdres.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano adresu");
            alert.setContentText("Wpisz adres");

            alert.showAndWait();
            return false;


        }
        if (editTelefon.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano telefonu");
            alert.setContentText("Wpisz numer telefonu");

            alert.showAndWait();
            return false;


        }

        return true;
    }


    /**
     * dodawanie nauczyciela do bazy danych
     *
     */
    public void addNauczyciel(String imie, String nazwisko, String adres, int telefon) {

        try {
            con= DBConnect.getDBConnection();
            pst = con.prepareStatement("INSERT INTO nauczyciel(imie, nazwisko, adres, telefon) VALUES (?,?,?,?);");

            pst.setString(1, imie);
            pst.setString(2, nazwisko);
            pst.setString(3, adres);
            pst.setInt(4, telefon);

            int i = pst.executeUpdate();
            System.out.println(i + " records inserted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * metoda wywolywana po kliknieciu przycisku dodawania
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void addNauczycielOnAction() throws IOException {
        if (validateInputsnew()) {
            String imie = newImie.getText();
            String nazwisko = newNazwisko.getText();
            String adres = newAdres.getText();
            int telefon = Integer.parseInt(newTelefon.getText());

            addNauczyciel(imie, nazwisko, adres, telefon);
        }
        menu(new ActionEvent());
    }

    /**
     * edytowanie nauczyciela w bazie danych
     *
     */
    public void editNauczyciel(String imie, String nazwisko, String adres, int telefon,
                               String imieCondition, String nazwiskoCondition, String telefonCondition) {

        try {
            con= DBConnect.getDBConnection();
            pst = con.prepareStatement("UPDATE nauczyciel SET imie = ? , nazwisko = ?, adres = ?, telefon = ? WHERE imie = ? AND nazwisko = ? AND telefon = ?;");

            pst.setString(1, imie);
            pst.setString(2, nazwisko);
            pst.setString(3, adres);
            pst.setInt(4, telefon);
            pst.setString(5, imieCondition);
            pst.setString(6, nazwiskoCondition);
            pst.setString(7, telefonCondition);

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void editNauczycielOnAction() throws IOException {
        if (validateInputsedit()) {
            String imie = editImie.getText();
            String nazwisko = editNazwisko.getText();
            String adres = editAdres.getText();
            int telefon = Integer.parseInt(editTelefon.getText());
            ObservableList<Nauczyciel> selectedRow = idTabela.getSelectionModel().getSelectedItems();

            System.out.println(nazwisko);


            for (Nauczyciel item : selectedRow) {

                String imieCondition = item.getImie();
                String nazwiskoCondition = item.getNazwisko();
                String telefonCondition = item.getTelefon();

                editNauczyciel(imie, nazwisko, adres, telefon, imieCondition, nazwiskoCondition, telefonCondition);
            }
        }
        menu(new ActionEvent());
    }

    /**
     * usuwanie nauczyciela z bazy danych
     */
    public void deleteNauczyciel(String imie, String nazwisko, String telefon) {

        try {

            con= DBConnect.getDBConnection();
            pst = con.prepareStatement("DELETE FROM nauczyciel WHERE imie = ? AND  nazwisko = ? AND telefon = ?");
            pst.setString(1, imie);
            pst.setString(2, nazwisko);
            pst.setString(3, telefon);

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * metoda wywolywana po kliknieciu przycisku usuwania
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void deleteNauczycielOnAction() throws IOException {
        ObservableList<Nauczyciel> selectedRow = idTabela.getSelectionModel().getSelectedItems();

        for (Nauczyciel item : selectedRow) {

            String imie = item.getImie();
            String nazwisko = item.getNazwisko();
            String telefon = item.getTelefon();

            deleteNauczyciel(imie, nazwisko, telefon);
        }
        menu(new ActionEvent());
    }

    /**
     * wyswetla pola do podawania danych ktore zostana wyslane do bazy danych
     *
     */
    private void pokazPola(TextField imie, TextField nazwisko, TextField adres, TextField telefon,
                           Button zapisz) {

        imie.setVisible(true);
        nazwisko.setVisible(true);
        adres.setVisible(true);
        telefon.setVisible(true);
        zapisz.setVisible(true);
    }

    /**
     * wywoluje metode
     *
     * @see NauczycielController#pokazPola(TextField, TextField, TextField, TextField, Button)
     * by wyswietlic pola dodawania nauczyciela
     */
    public void newPokazPola() {
        pokazPola(newImie, newNazwisko, newAdres, newTelefon, newZapisz);
    }

    /**
     * wywoluje metode
     *
     * @see NauczycielController#pokazPola(TextField, TextField, TextField, TextField, Button)
     * by wyswietlic pola edytowania nauczyciela
     */
    public void editPokazPola() {
        ObservableList<Nauczyciel> selectedRow = idTabela.getSelectionModel().getSelectedItems();

        for (Nauczyciel item : selectedRow) {

            editImie.setText(item.getImie());
            editNazwisko.setText(item.getNazwisko());
            editAdres.setText(item.getAdres());
            editTelefon.setText(item.getTelefon());

        }
        pokazPola(editImie, editNazwisko, editAdres, editTelefon, editZapisz);
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
