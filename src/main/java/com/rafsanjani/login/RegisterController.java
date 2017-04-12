package com.rafsanjani.login;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController  implements Initializable {
    
    @FXML
    private TextField userF;
    @FXML
    private PasswordField passF;
    private Connection c;
    private PreparedStatement pstmt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/db/test.db");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    public void register(ActionEvent event) throws SQLException{
    
        pstmt = c.prepareStatement("INSERT INTO students (name, password) VALUES (?, ?)");
        pstmt.setString(1, userF.getText());
        pstmt.setString(2, passF.getText());
        pstmt.executeUpdate();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void update(ActionEvent event) throws SQLException{
    
        pstmt = c.prepareStatement("UPDATE students SET password=? WHERE name=?");
        pstmt.setString(1, passF.getText());
        pstmt.setString(2, userF.getText());
        pstmt.executeUpdate();
    }
    
    @FXML
    public void delete(ActionEvent event) throws SQLException{
    
        pstmt = c.prepareStatement("DELETE FROM students WHERE name=?");
        pstmt.setString(1, userF.getText());
        pstmt.executeUpdate();
    }

}
