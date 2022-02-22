package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.connectorDB.DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * Kontroler do obsługi logowania do aplikacji
 *

 */
    public class LoginController {
        @FXML
        private Label logoTest;

        @FXML
        private TextField user;

        @FXML
        private TextField pass;

        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        /**
         * Główna funkcja modułu obsługująca logowanie.
         */

        public ResultSet selectLogowanie(String username, String password){
            try {
                con= DBConnect.getDBConnection();

                pst = con.prepareStatement("SELECT * FROM logowanie WHERE login=? AND haslo=?");
                pst.setString(1, username);
                pst.setString(2 , password);

                rs = pst.executeQuery();
                return rs;

            }catch(Exception e){
                e.printStackTrace();
                e.getCause();
                return null;
            }
        }

        public void Login(ActionEvent event) throws Exception {

            String username = user.getText();
            String password = pass.getText();

            if(username.equals("") && password.equals(""))
            {
                logoTest.setText("Nie podano loginu lub hasla");
            }
            else
            {
                try{
                    ResultSet rs = selectLogowanie(username, password);

                    if(rs.next())
                    {

                        logoTest.setText("Dane poprawne");
                        ((Node)event.getSource()).getScene().getWindow().hide();
                        Stage primaryStage = new Stage();

                        Parent root = null;


                            root = FXMLLoader.load(getClass().getResource("../admin/Klasa.fxml"));

                        primaryStage.setScene(new Scene(root, 800, 600));
                        primaryStage.show();

                    }
                    else
                    {
                        logoTest.setText("Dane niepoprawne");
                        user.setText("");
                        pass.setText("");
                        user.requestFocus();
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    e.getCause();
                }

            }
        }


    }


