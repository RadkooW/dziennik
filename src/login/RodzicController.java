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

import static java.lang.Integer.parseInt;

/**
 * klasa kontrolera dla okna Rodzic
 */
public class RodzicController {

    @FXML
    private MenuItem idMenuKlasa, idMenuNauczyciel, idMenuUczen, idMenuPrzedmiot;
    @FXML
    public TextField newImie, newNazwisko, newAdres, newTelefon, editImie, editNazwisko, editAdres, editTelefon;
    @FXML
    public Button newZapisz, editZapisz;
    @FXML
    private AnchorPane rodzic;
    @FXML
    private TableColumn<Rodzic, String> idTabelaImie, idTabelaNazwisko, idTabelaAdres, idTabelaTelefon;
    @FXML
    private TableView<Rodzic> idTabela;

    /**
     * wywolanie metody
     *
     * @see RodzicController#uzupelnijTabele()
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
        AdminMenu am = new AdminMenu(rodzic);

        if (idMenuKlasa.equals(event.getSource())) {
            am.GoToKlasa();
        } else if (idMenuNauczyciel.equals(event.getSource())) {
            am.GoToNauczyciel();
        } else if (idMenuUczen.equals(event.getSource())) {
            am.GoToUczen();
        } else if (idMenuPrzedmiot.equals(event.getSource())) {
            am.GoToPrzedmiot();
        } else {
            am.GoToRodzic();
        }
    }

    /**
     * uzupelnienie listy wartosciami z bazy danych i wypisanie ich w tabeli
     */
    private void uzupelnijTabele() {
        ObservableList<Rodzic> ol = FXCollections.observableArrayList();

        try {
            
            Connection connection = DBConnect.getDBConnection();
            connection.setAutoCommit(false);
            String query = "SELECT imie, nazwisko, adres, telefon FROM rodzic";
            Statement statement = connection.prepareStatement(query);

            rs = statement.executeQuery(query);

            while (rs.next()) {
                ol.add(new Rodzic(rs.getString(1), rs.getString(2),
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

    /**
     * dodawanie rodzica do bazy danych
     */
    public void addRodzic(String imie, String nazwisko, String adres, int telefon) {

        try {
            Connection connection= DBConnect.getDBConnection();
            String query = "INSERT INTO rodzic(imie, nazwisko, adres, telefon) VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, imie);
            preparedStatement.setString(2, nazwisko);
            preparedStatement.setString(3, adres);
            preparedStatement.setInt(4, telefon);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * metoda wywolywana po kliknieciu przycisku dodawania
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void addRodzicOnAction() throws IOException {
        if (validateInputsnew()) {
        String imie = newImie.getText();
        String nazwisko = newNazwisko.getText();
        String adres = newAdres.getText();
        int telefon = parseInt(newTelefon.getText());

            addRodzic(imie, nazwisko, adres, telefon);
        }
        menu(new ActionEvent());
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
     * edytowanie rodzica w bazie danch
     *
     */
    public void editRodzic(String imie, String nazwisko, String adres, int telefon, String imieCondition,
                           String nazwiskoCondition, String telefonCondition) {

        try {
            con = DBConnect.getDBConnection();
          pst = con.prepareStatement("UPDATE rodzic SET imie = ? , nazwisko = ?, adres = ?, telefon = ? WHERE imie = ? AND nazwisko = ? AND telefon = ?;");




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
     * metoda wywolywana po kliknieciu przycisku edytowania
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */

    public void editRodzicOnAction() throws IOException {
        if (validateInputsedit()) {

        String imie = editImie.getText();
        String nazwisko = editNazwisko.getText();
        String adres = editAdres.getText();
        int telefon = parseInt(editTelefon.getText());
        ObservableList<Rodzic> selectedRow = idTabela.getSelectionModel().getSelectedItems();




            for (Rodzic item : selectedRow) {

                String imieCondition = item.getImie();
                String nazwiskoCondition = item.getNazwisko();
                String telefonCondition = item.getTelefon();

                editRodzic(imie, nazwisko, adres, telefon, imieCondition, nazwiskoCondition, telefonCondition);
            }

            menu(new ActionEvent());

        }
    }

    /**
     * usuwanie rodzica z bazy danch
     */
    public void deleteRodzic(String imie, String nazwisko,String adres, String telefon) {

        try {
            con = DBConnect.getDBConnection();
            pst = con.prepareStatement("DELETE FROM rodzic WHERE imie = ? AND  nazwisko = ? AND  adres = ? AND telefon = ?");
            pst.setString(1, imie);
            pst.setString(2, nazwisko);
            pst.setString(3, adres);
            pst.setString(4, telefon);

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
    public void deleteRodzicOnAction() throws IOException {
        ObservableList<Rodzic> selectedRow = idTabela.getSelectionModel().getSelectedItems();

        for (Rodzic item : selectedRow) {

            String imie = item.getImie();
            String nazwisko = item.getNazwisko();
            String adres = item.getAdres();
            String telefon = item.getTelefon();


            deleteRodzic(imie, nazwisko, adres, telefon);
        }

        menu(new ActionEvent());
    }

    /**
     * wyswetla pola do podawania danych ktore zostana wyslane do bazy danych
     */
    private void pokazPola(TextField imie, TextField nazwisko, TextField adres, TextField telefon, Button zapisz) {

        imie.setVisible(true);
        nazwisko.setVisible(true);
        adres.setVisible(true);
        telefon.setVisible(true);
        zapisz.setVisible(true);

    }

    /**
     * wywoluje metode
     *
     * @see RodzicController#pokazPola(TextField, TextField, TextField, TextField, Button)
     * by wyswietlic pola dodawania klasy
     */
    public void newPokazPola() {
        pokazPola(newImie, newNazwisko, newAdres, newTelefon, newZapisz);
    }

    /**
     * wywoluje metode
     *
     * @see RodzicController#pokazPola(TextField, TextField, TextField, TextField, Button)
     * by wyswietlic pola dodawania klasy
     */
    public void editPokazPola() {
        ObservableList<Rodzic> selectedRow = idTabela.getSelectionModel().getSelectedItems();

        for (Rodzic item : selectedRow) {

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
