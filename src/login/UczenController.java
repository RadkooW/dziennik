package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
 * klasa kontrolera dla okna Uczen
 */
public class UczenController {

    @FXML
    private MenuItem idMenuKlasa, idMenuNauczyciel, idMenuPrzedmiot, idMenuRodzic;
    @FXML
    public TableColumn<Uczen, String> idTabelaImie, idTabelaNazwisko, idTabelaAdres, idTabelaTelefon,
            idTabelaRodzic,idTabelaKlasa;
    @FXML
    private TableView<Uczen> idTabela;
    @FXML
    private AnchorPane uczen;
    @FXML
    private TextField newImie, newNazwisko, newAdres, newTelefon, editImie, editNazwisko, editAdres, editTelefon;
    @FXML
    private ChoiceBox<String> newKlasa, newRodzic,editKlasa, editRodzic;
    @FXML
    private Button newZapisz, editZapisz;
    @FXML
    private TextField szukajField;
    ObservableList<Uczen> dataList;

    /**
     * wywolanie metod
     *
     * @see UczenController#wypiszKlasy()
     * @see UczenController#wypiszRodzicow()
     * @see UczenController#odswiezTabele()
     * <p>
     * oraz dodanie danych do choice boxow
     */
    @FXML
    private void initialize() {
        wypiszKlasy();
        wypiszRodzicow();
        newKlasa.getItems().addAll(klasy);
        newRodzic.getItems().addAll(rodzice);
        editKlasa.getItems().addAll(klasy);
        editRodzic.getItems().addAll(rodzice);
        odswiezTabele();
        search_user();
    }

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    ObservableList<String> klasy = FXCollections.observableArrayList();
    ObservableList<String> rodzice = FXCollections.observableArrayList();
    ObservableList<String> listaKlas = FXCollections.observableArrayList();
    ObservableList<String> listaRodzicow = FXCollections.observableArrayList();

    /**
     * @param event przechowuje element wybrany z menu
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void menu(ActionEvent event) throws IOException {
        AdminMenu am = new AdminMenu(uczen);

        if (idMenuKlasa.equals(event.getSource())) {
            am.GoToKlasa();
        } else if (idMenuNauczyciel.equals(event.getSource())) {
            am.GoToNauczyciel();
        } else if (idMenuPrzedmiot.equals(event.getSource())) {
            am.GoToPrzedmiot();
        } else if (idMenuRodzic.equals(event.getSource())) {
            am.GoToRodzic();
        } else {
            am.GoToUczen();
        }
    }

    /**
     * uzupelnienie listy wartosciami z bazy danych i wypisanie ich w tabeli
     * @return
     */
    private ObservableList<Uczen> uzupelnijTabele() {
        ObservableList<Uczen> ol = FXCollections.observableArrayList();
        int i = 0;

        try {
            con = DBConnect.getDBConnection();
            pst = con.prepareStatement("SELECT imie, nazwisko, adres, telefon FROM uczen");

            rs = pst.executeQuery();

            while (rs.next()) {
                ol.add(new Uczen(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        listaRodzicow.get(i), listaKlas.get(i)));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idTabelaImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        idTabelaNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        idTabelaAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
        idTabelaTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        idTabelaRodzic.setCellValueFactory(new PropertyValueFactory<>("rodzic"));
        idTabelaKlasa.setCellValueFactory(new PropertyValueFactory<>("klasa"));


        idTabela.setItems(ol);
        return ol;
    }
    @FXML
    void search_user() {
        idTabelaImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        idTabelaNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        idTabelaAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
        idTabelaTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        idTabelaRodzic.setCellValueFactory(new PropertyValueFactory<>("rodzic"));
        idTabelaKlasa.setCellValueFactory(new PropertyValueFactory<>("klasa"));

        dataList = uzupelnijTabele();

        idTabela.setItems(dataList);
        FilteredList<Uczen> filteredData = new FilteredList<>(dataList, b -> true);
        szukajField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if ( person.getImie().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (person.getNazwisko().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if (person.getTelefon().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (person.getAdres().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                     return true;
                }else if (person.getKlasa().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Uczen> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(idTabela.comparatorProperty());
        idTabela.setItems(sortedData);
    }
    /**
     * wywolanie metod
     *
     * @see UczenController#znajdzKlase()
     * @see UczenController#znajdzRodzica()
     * @see UczenController#uzupelnijTabele()
     * w celu odswiezenia tabeli
     */
    private void odswiezTabele() {
        znajdzKlase();
        znajdzRodzica();
        uzupelnijTabele();
    }

    /**
     * pobierz z bazy nazwy klas
     */
    private void znajdzKlase() {
        try {
            con = DBConnect.getDBConnection();
            pst = con.prepareStatement("SELECT nazwa FROM klasa INNER JOIN uczen ON klasa.id_klasa=uczen.id_klasa ORDER BY uczen.id_uczen");
            rs = pst.executeQuery();

            while (rs.next()) {
                listaKlas.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * wpisz imiona i nzawiska rodzicow z bazy danych do listy
     *
     * @see UczenController#listaRodzicow
     */
    private void znajdzRodzica() {

        String imieINazwiskoRodzica;

        try {
            con = DBConnect.getDBConnection();
            pst = con.prepareStatement("SELECT rodzic.imie, rodzic.nazwisko FROM rodzic INNER JOIN uczen ON rodzic.id_rodzic=uczen.id_rodzic ORDER BY uczen.id_uczen");
            rs = pst.executeQuery();

            while (rs.next()) {
                imieINazwiskoRodzica = rs.getString(1) + " " + rs.getString(2);
                listaRodzicow.add(imieINazwiskoRodzica);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * wpisz nazwy klas z bazy danych do listy
     *
     * @see UczenController#klasy
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
     * wpisz imiona i nzawiska rodzicow z bazy danych do listy
     *
     * @see UczenController#rodzice
     */
    private void wypiszRodzicow() {

        String imieINazwiskoRodzica;

        try {
            con = DBConnect.getDBConnection();
            pst = con.prepareStatement("SELECT imie, nazwisko FROM rodzic");

            rs = pst.executeQuery();

            while (rs.next()) {
                imieINazwiskoRodzica = rs.getString(1) + " " + rs.getString(2);
                rodzice.add(imieINazwiskoRodzica);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        if (newTelefon.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie podano telefonu");
            alert.setContentText("Wpisz numer telefonu");

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
        if (newRodzic.getValue()==null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie wybrano rodzica");
            alert.setContentText("Wybierz rodzica");

            alert.showAndWait();
            return false;


        }
        if (newKlasa.getValue()==null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Nie wybrano klasy");
            alert.setContentText("Wybierz klase");

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
     * dodawanie ucznia do bazy danych
     */
    public void addUczen(String imie, String nazwisko, String adres, int telefon, String nazwaKlasy, String daneRodzica) {

        try {
            con = DBConnect.getDBConnection();


            pst = con.prepareStatement("SELECT id_klasa FROM klasa WHERE nazwa=?;");    //wyszukiwanie id klasy
            pst.setString(1, nazwaKlasy);

            rs = pst.executeQuery();
            rs.next();
            int id_klasa = rs.getInt(1);


            pst = con.prepareStatement("SELECT id_rodzic FROM rodzic WHERE imie=? AND nazwisko=?;");    //wyszukiwanie id rodzica

            System.out.println();
            String[] imieINazwiskoRodzica = daneRodzica.split(" ");

            pst.setString(1, imieINazwiskoRodzica[0]);
            pst.setString(2, imieINazwiskoRodzica[1]);

            rs = pst.executeQuery();
            rs.next();
            int id_rodzic = rs.getInt(1);



            rs = pst.executeQuery();
            rs.next();


            pst = con.prepareStatement("INSERT INTO uczen(imie,nazwisko,adres,telefon,id_rodzic,id_klasa) VALUES (?,?,?,?,?,?);");

            pst.setString(1, imie);
            pst.setString(2, nazwisko);
            pst.setString(3, adres);
            pst.setInt(4, telefon);
            pst.setInt(5, id_rodzic);
            pst.setInt(6, id_klasa);


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
    public void addUczenOnAction() throws IOException {
        if (validateInputsnew()) {
            String imie = newImie.getText();
            String nazwisko = newNazwisko.getText();
            String adres = newAdres.getText();
            int telefon = Integer.parseInt(newTelefon.getText());
            String nazwaKlasy = newKlasa.getSelectionModel().getSelectedItem();
            String daneRodzica = newRodzic.getSelectionModel().getSelectedItem();


            addUczen(imie, nazwisko, adres, telefon, nazwaKlasy, daneRodzica);
        }
        menu(new ActionEvent());
    }

    /**
     * edytowanie danych ucznia w bazie danych
     *
     */
    public void editUczen(String imie, String nazwisko, String adres, int telefon, String nazwaKlasy, String daneRodzica,
                          String imieCondition, String nazwiskoCondition, String telefonCondition) {

        try {


            con = DBConnect.getDBConnection();

            pst = con.prepareStatement("SELECT id_klasa FROM klasa WHERE nazwa=?;");
            pst.setString(1, nazwaKlasy);

            rs = pst.executeQuery();
            rs.next();
            int id_klasa = rs.getInt(1);


            pst = con.prepareStatement("SELECT id_rodzic FROM rodzic WHERE imie=? AND nazwisko=?;");

            System.out.println();
            String[] imieINazwiskoRodzica = daneRodzica.split(" ");

            pst.setString(1, imieINazwiskoRodzica[0]);
            pst.setString(2, imieINazwiskoRodzica[1]);

            rs = pst.executeQuery();
            rs.next();
            int id_rodzic = rs.getInt(1);



            rs = pst.executeQuery();
            rs.next();



            pst = con.prepareStatement("UPDATE uczen SET imie = ? , nazwisko = ?, adres = ?, telefon = ?, id_rodzic = ?, id_klasa = ? WHERE imie = ? AND nazwisko = ? AND telefon = ?;");

            pst.setString(1, imie);
            pst.setString(2, nazwisko);
            pst.setString(3, adres);
            pst.setInt(4, telefon);
            pst.setInt(5, id_rodzic);
            pst.setInt(6, id_klasa);
            pst.setString(7, imieCondition);
            pst.setString(8, nazwiskoCondition);
            pst.setString(9, telefonCondition);

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
    public void editUczenOnAction() throws IOException {
        if (validateInputsedit()) {
            String imie = editImie.getText();
            String nazwisko = editNazwisko.getText();
            String adres = editAdres.getText();
            int telefon = Integer.parseInt(editTelefon.getText());
            String nazwaKlasy = editKlasa.getSelectionModel().getSelectedItem();
            String daneRodzica = editRodzic.getSelectionModel().getSelectedItem();
            ObservableList<Uczen> selectedRow = idTabela.getSelectionModel().getSelectedItems();

            for (Uczen item : selectedRow) {

                String imieCondition = item.getImie();
                String nazwiskoCondition = item.getNazwisko();
                String telefonCondition = item.getTelefon();

                editUczen(imie, nazwisko, adres, telefon, nazwaKlasy, daneRodzica,
                        imieCondition, nazwiskoCondition, telefonCondition);
            }
        }
        menu(new ActionEvent());
    }

    /**
     * usuwanie ucznia z bazy danych
     */
    public void deleteUczen(String imie, String nazwisko, String telefon) {

        try {
            con = DBConnect.getDBConnection();
            pst = con.prepareStatement("DELETE FROM uczen WHERE imie = ? AND  nazwisko = ? AND telefon = ?");
            pst.setString(1, imie);
            pst.setString(2, nazwisko);
            pst.setString(3, telefon);

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
    public void deleteUczenOnAction() throws IOException {
        ObservableList<Uczen> selectedRow = idTabela.getSelectionModel().getSelectedItems();

        for (Uczen item : selectedRow) {

            String imie = item.getImie();
            String nazwisko = item.getNazwisko();
            String telefon = item.getTelefon();

            deleteUczen(imie, nazwisko, telefon);
        }
        menu(new ActionEvent());
    }

    /**
     * wyswetla pola do podawania danych ktore zostana wyslane do bazy danych
     */
    private void pokazPola(TextField imie, TextField nazwisko, TextField adres, TextField telefon,
                           ChoiceBox<String> klasa, ChoiceBox<String> rodzic,
                           Button zapisz) {

        imie.setVisible(true);
        nazwisko.setVisible(true);
        adres.setVisible(true);
        telefon.setVisible(true);
        klasa.setVisible(true);
        rodzic.setVisible(true);
        zapisz.setVisible(true);
    }


    /**
     * wywoluje metode
     *
     * @see UczenController#pokazPola(TextField, TextField, TextField, TextField, ChoiceBox, ChoiceBox, Button)
     * by wyswietlic pola dodawania klasy
     */
    public void newPokazPola() {


        pokazPola(newImie, newNazwisko, newAdres, newTelefon, newKlasa, newRodzic,newZapisz);
    }

    /**
     * wywoluje metode
     *
     * @see UczenController#pokazPola(TextField, TextField, TextField, TextField, ChoiceBox, ChoiceBox,Button)
     * by wyswietlic pola edytowania klasy
     */
    public void editPokazPola() {
        ObservableList<Uczen> selectedRow = idTabela.getSelectionModel().getSelectedItems();

        for (Uczen item : selectedRow) {

            editImie.setText(item.getImie());
            editNazwisko.setText(item.getNazwisko());
            editAdres.setText(item.getAdres());
            editTelefon.setText(item.getTelefon());
            editRodzic.setValue(item.getRodzic());
            editKlasa.setValue(item.getKlasa());

        }
        pokazPola(editImie, editNazwisko, editAdres, editTelefon, editKlasa, editRodzic,editZapisz);
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

