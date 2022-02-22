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
 * klasa kontrolera dla okna Klasa
 */
public class KlasaController {

    @FXML
    private MenuItem  idMenuPrzedmiot, idMenuUczen, idMenuRodzic, idMenuNauczyciel;
    @FXML
    private AnchorPane klasa;
    @FXML
    private TextField idNazwa;
    @FXML
    private TableView<Klasa> idTabelaKlas;
    @FXML
    private TableColumn<Klasa, String> idTabelaNazwaKlasy;
    @FXML
    private Button idZapisz;

    /**
     * wywolanie metody
     *
     * @see KlasaController#uzupelnijTabele()
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
        AdminMenu am = new AdminMenu(klasa);

         if (idMenuPrzedmiot.equals(event.getSource())) {
            am.GoToPrzedmiot();
        } else if (idMenuUczen.equals(event.getSource())) {
            am.GoToUczen();
        } else if (idMenuRodzic.equals(event.getSource())) {
            am.GoToRodzic();
        } else if (idMenuNauczyciel.equals(event.getSource())) {
            am.GoToNauczyciel();
        }  else {
            am.GoToKlasa();
        }
    }

    /**
     * uzupelnienie listy wartosciami z bazy danych i wypisanie ich w tabeli
     */
    private void uzupelnijTabele() {
        ObservableList<Klasa> ol = FXCollections.observableArrayList();

        try {
            con= DBConnect.getDBConnection();
            pst = con.prepareStatement("SELECT nazwa FROM klasa");

            rs = pst.executeQuery();

            while (rs.next()) {
                ol.add(new Klasa(rs.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idTabelaNazwaKlasy.setCellValueFactory(new PropertyValueFactory<>("nazwa"));


        idTabelaKlas.setItems(ol);
    }
    private boolean validateInputsnew() {
        if (idNazwa.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano nazwy klasy");
            alert.setContentText("Wpisz nazwe klasy");

            alert.showAndWait();
            return false;
        }

        return true;
    }
    /**
     * dodawanie klasy do bazy danych
     *
     * @param nazwa przechowuje nazwe klasy
     */
    public void addKlasa(String nazwa) {

        try {
            con= DBConnect.getDBConnection();
            pst = con.prepareStatement("INSERT INTO klasa(nazwa) VALUES (?)");

            pst.setString(1, nazwa);

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
    public void addKlasaOnAction() throws IOException {
        if (validateInputsnew()) {
            String nazwa = idNazwa.getText();
            addKlasa(nazwa);
        }
            menu(new ActionEvent());

    }







    /**
     * usuwanie klasy z bazy danych
     *
     * @param nazwa przechowuje nazwe klasy
     */
    public void deleteKlasa(String nazwa) {

        try {
            con= DBConnect.getDBConnection();
            pst = con.prepareStatement("DELETE FROM klasa WHERE nazwa = ?");
            pst.setString(1, nazwa);

            pst.executeUpdate();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie mozna usunac zaznaczonej klasy");
            alert.setContentText("Dana klasa jest juz przypisana do ucznia");
            alert.showAndWait();
        }
    }

    /**
     * metoda wywolywana po kliknieciu przycisku usuwania
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void deleteKlasaOnAction() throws IOException {
        ObservableList<Klasa> selectedRow = idTabelaKlas.getSelectionModel().getSelectedItems();

        for (Klasa item : selectedRow) {
            String nazwa = item.getNazwa();
            deleteKlasa(nazwa);
        }

        menu(new ActionEvent());
    }

    /**
     * wywoluje metode
     *
     * @see KlasaController#pokazPola(TextField, Button)
     * by wyswietlic pola dodawania klasy
     */
    public void newPokazPola() {
        pokazPola(idNazwa, idZapisz);
    }


    private void pokazPola(TextField nazwa, Button zapisz) {

        nazwa.setVisible(true);
        zapisz.setVisible(true);

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

