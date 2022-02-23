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
    public TextField newImie, newNazwisko, newImie1, newNazwisko1,newAdres, newTelefon, editImie, editNazwisko,editImie1, editNazwisko1, editAdres, editTelefon;
    @FXML
    public Button newZapisz, editZapisz;
    @FXML
    private AnchorPane rodzic;
    @FXML
    private TableColumn<Rodzic, String> idTabelaImie, idTabelaNazwisko,idTabelaImie1, idTabelaNazwisko1, idTabelaAdres, idTabelaTelefon;
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
            String query = "SELECT imie, nazwisko,imie1, nazwisko1, adres, telefon FROM rodzic";
            Statement statement = connection.prepareStatement(query);

            rs = statement.executeQuery(query);

            while (rs.next()) {
                ol.add(new Rodzic(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idTabelaImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        idTabelaNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        idTabelaImie1.setCellValueFactory(new PropertyValueFactory<>("imie1"));
        idTabelaNazwisko1.setCellValueFactory(new PropertyValueFactory<>("nazwisko1"));
        idTabelaAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
        idTabelaTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        idTabela.setItems(ol);
    }

    /**
     * dodawanie rodzica do bazy danych
     */
    public void addRodzic(String imie, String nazwisko,String imie1, String nazwisko1, String adres, int telefon) {

        try {
            Connection connection= DBConnect.getDBConnection();
            String query = "INSERT INTO rodzic(imie, nazwisko, imie1, nazwisko1, adres, telefon) VALUES (?,?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, imie);
            preparedStatement.setString(2, nazwisko);
            preparedStatement.setString(3, imie1);
            preparedStatement.setString(4, nazwisko1);
            preparedStatement.setString(5, adres);
            preparedStatement.setInt(6, telefon);
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
        String imie1 = newImie1.getText();
        String nazwisko1 = newNazwisko1.getText();
        String adres = newAdres.getText();
        int telefon = parseInt(newTelefon.getText());

            addRodzic(imie, nazwisko,imie1, nazwisko1, adres, telefon);
        }
        menu(new ActionEvent());
    }
    private boolean validateInputsnew() {
        if (newImie.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano imienia Matki");
            alert.setContentText("Wpisz imie");

            alert.showAndWait();
            return false;


        }
        if (newNazwisko.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano nazwiska Matki");
            alert.setContentText("Wpisz nazwisko");

            alert.showAndWait();
            return false;


        }
        if (newImie1.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano imienia Ojca");
            alert.setContentText("Wpisz imie");

            alert.showAndWait();
            return false;


        }
        if (newNazwisko1.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano nazwiska Ojca");
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
            alert.setHeaderText("Nie podano imienia Matki");
            alert.setContentText("Wpisz imie");

            alert.showAndWait();
            return false;


        }
        if (editNazwisko.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano nazwiska Matki");
            alert.setContentText("Wpisz nazwisko");

            alert.showAndWait();
            return false;


        }
        if (editImie1.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano imienia Ojca");
            alert.setContentText("Wpisz imie");

            alert.showAndWait();
            return false;


        }
        if (editNazwisko1.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano nazwiska Ojca");
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
    public void editRodzic(String imie, String nazwisko,String imie1, String nazwisko1, String adres, int telefon,
                           String imieCondition, String nazwiskoCondition,String imie1Condition, String nazwisko1Condition, String adresCondition, int telefonCondition) {

        try {
            con = DBConnect.getDBConnection();
          pst = con.prepareStatement("UPDATE rodzic SET imie = ? , nazwisko = ?,imie1 = ? , nazwisko1 = ?, adres = ?, telefon = ? WHERE imie = ? AND nazwisko = ? AND imie1 = ? AND nazwisko1 = ? AND adres = ? AND telefon = ?;");

                pst.setString(1, imie);
                pst.setString(2, nazwisko);
                pst.setString(3, imie1);
                pst.setString(4, nazwisko1);
                pst.setString(5, adres);
                pst.setInt(6, telefon);
                pst.setString(7, imieCondition);
                pst.setString(8, nazwiskoCondition);
                pst.setString(9, imie1Condition);
                pst.setString(10, nazwisko1Condition);
                pst.setString(11, adresCondition);
                pst.setInt(12, telefonCondition);

                pst.executeUpdate();
            System.out.println("edytowano ==================================");

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
        String imie1 = editImie1.getText();
        String nazwisko1 = editNazwisko1.getText();
        String adres = editAdres.getText();
        int telefon = parseInt(editTelefon.getText());
        ObservableList<Rodzic> selectedRow = idTabela.getSelectionModel().getSelectedItems();

            for (Rodzic item : selectedRow) {
                String imieCondition = item.getImie();
                String nazwiskoCondition = item.getNazwisko();
                String imie1Condition = item.getImie1();
                String nazwisko1Condition = item.getNazwisko1();
                String adresCondition = item.getAdres();
                int telefonCondition = Integer.parseInt(item.getTelefon());

                editRodzic(imie, nazwisko,imie1, nazwisko1, adres, telefon,
                        imieCondition, nazwiskoCondition,imie1Condition, nazwisko1Condition, adresCondition, telefonCondition);
            }


            menu(new ActionEvent());
        }
    }

    /**
     * usuwanie rodzica z bazy danch
     */
    public void deleteRodzic(String imie, String nazwisko,String imie1, String nazwisko1,String adres, String telefon) {

        try {
            con = DBConnect.getDBConnection();
            pst = con.prepareStatement("DELETE FROM rodzic WHERE imie = ? AND  nazwisko = ? AND imie1 = ? AND  nazwisko1 = ? AND  adres = ? AND telefon = ?");
            pst.setString(1, imie);
            pst.setString(2, nazwisko);
            pst.setString(3, imie1);
            pst.setString(4, nazwisko1);
            pst.setString(5, adres);
            pst.setString(6, telefon);

            pst.executeUpdate();

        } catch (SQLException e) {

            //TODO: dodać alert
            System.out.println("alert");


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
            String imie1 = item.getImie1();
            String nazwisko1 = item.getNazwisko1();
            String adres = item.getAdres();
            String telefon = item.getTelefon();

            deleteRodzic(imie, nazwisko,imie1, nazwisko1, adres, telefon);
        }

        menu(new ActionEvent());
    }

    /**
     * wyswetla pola do podawania danych ktore zostana wyslane do bazy danych
     */
    private void pokazPola(TextField imie, TextField nazwisko,TextField imie1, TextField nazwisko1, TextField adres, TextField telefon, Button zapisz) {

        imie.setVisible(true);
        nazwisko.setVisible(true);
        imie1.setVisible(true);
        nazwisko1.setVisible(true);
        adres.setVisible(true);
        telefon.setVisible(true);
        zapisz.setVisible(true);
    }

    /**
     * wywoluje metode
     *
     * @see RodzicController#pokazPola(TextField, TextField,TextField, TextField, TextField, TextField, Button)
     * by wyswietlic pola dodawania klasy
     */
    public void newPokazPola() {
        pokazPola(newImie, newNazwisko,newImie1, newNazwisko1, newAdres, newTelefon, newZapisz);
    }

    /**
     * wywoluje metode
     *
     * @see RodzicController#pokazPola(TextField, TextField,TextField, TextField, TextField, TextField, Button)
     * by wyswietlic pola dodawania klasy
     */
    public void editPokazPola() {
        ObservableList<Rodzic> selectedRow = idTabela.getSelectionModel().getSelectedItems();

        for (Rodzic item : selectedRow) {

            editImie.setText(item.getImie());
            editNazwisko.setText(item.getNazwisko());
            editImie1.setText(item.getImie1());
            editNazwisko1.setText(item.getNazwisko1());
            editAdres.setText(item.getAdres());
            editTelefon.setText(item.getTelefon());
        }
        pokazPola(editImie, editNazwisko,editImie1, editNazwisko1, editAdres, editTelefon, editZapisz);
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
