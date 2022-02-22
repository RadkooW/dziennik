package login;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

/**
 * klasa odpowiedzialna za menu
 */
public class AdminMenu {

    private final AnchorPane menuPane;

    /**
     * konstruktor dla menu
     * @param pane przechowuje nazwe zmiennej AnchorPane
     */
    public AdminMenu(AnchorPane pane) {
        this.menuPane = pane;
    }



    /**
     * przejscie do okna Klasa
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void GoToKlasa() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Klasa.fxml")));
        menuPane.getChildren().setAll(pane);
    }

    /**
     * przejscie do okna Przedmiot
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void GoToPrzedmiot() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Przedmiot.fxml")));
        menuPane.getChildren().setAll(pane);
    }

    /**
     * przejscie do okna Uczen
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void GoToUczen() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Uczen.fxml")));
        menuPane.getChildren().setAll(pane);
    }

    /**
     * przejscie do okna Rodzic
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void GoToRodzic() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Rodzic.fxml")));
        menuPane.getChildren().setAll(pane);
    }

    /**
     * przejscie do okna Nauczyciel
     *
     * @throws IOException wyjatek gdy wskazane okno nie zostalo odnalezione
     */
    public void GoToNauczyciel() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Nauczyciel.fxml")));
        menuPane.getChildren().setAll(pane);
    }



}
