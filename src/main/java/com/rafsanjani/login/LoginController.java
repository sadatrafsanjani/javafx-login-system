package com.rafsanjani.login;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private Label error;
    @FXML
    private TextField userF;
    @FXML
    private PasswordField passF;
    private Connection c;
    private PreparedStatement pstmt;
    private Statement stmt;
    private final String database = "jdbc:sqlite:C:/sqlite/db/test.db";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Class.forName("org.sqlite.JDBC");
            createDB();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private void createDB() throws SQLException {

        File file = new File(database);
        File dir = new File("C:/sqlite/db");
        
        if(!dir.exists()){
            dir.mkdirs();
        }

        if (!file.exists()) {
            c = DriverManager.getConnection(database);
            createTable();
        }
    }

    private void createTable() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS students (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL,\n"
                + "	password text NOT NULL\n"
                + ");";
        c = DriverManager.getConnection(database);
        stmt = c.createStatement();
        stmt.execute(sql);
        c.close();
    }

    private boolean check(String user, String pass) throws SQLException {

        c = DriverManager.getConnection(database);
        pstmt = c.prepareStatement("SELECT * FROM students WHERE name=? AND password=?");
        pstmt.setString(1, user);
        pstmt.setString(2, pass);
        ResultSet rs = pstmt.executeQuery();
        boolean login = rs.next();
        pstmt.close();
        c.close();

        return login;

    }

    @FXML
    public void loginAction(ActionEvent event) throws SQLException, IOException {

        if (check(userF.getText(), passF.getText())) {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
        } else {
            error.setText("Error");
        }
    }

    @FXML
    public void registerAction(ActionEvent event) throws SQLException, IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void updateAction(ActionEvent event) throws SQLException, IOException {
        //fill me
    }

    @FXML
    public void deleteAction(ActionEvent event) throws SQLException, IOException {
        //fill me
    }
}
