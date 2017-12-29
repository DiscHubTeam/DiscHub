package com.dischub.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.dischub.DiscHub;
import com.dischub.login.UserCredentials;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateAccountPresenter extends GluonPresenter<DiscHub> {

    @FXML
    private View primary;

    @FXML
    private TextField txfEmail;

    @FXML
    private TextField txfScreenName;

    @FXML
    private Label lblInfo;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private PasswordField pwdConfirmPassword;

    @FXML
    private ResourceBundle resources;

    private String encryptedPasswordString;

    public void initialize() {

        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = getApp().getAppBar();
                appBar.setTitleText("Login");
                appBar.setVisible(false);
            }
        });
    }

    @FXML
    void buttonClick() {
        if (isValidUserName(txfEmail.getText())) {
            if (isPasswordValid(pwdPassword.getText())) {
                createAccount();
            }
        }
    }

    private void createAccount() {
        //Navigate to new screen to make an account.
        System.out.println("Requested create new login \n Name: " + txfScreenName.getText() + " \n uname: " + txfEmail.getText() + " \n Password: " + pwdPassword.getText());
        DiscHub.setUserCredentials(new UserCredentials(txfEmail.getText(), pwdPassword.getText(), txfScreenName.getText(), 0));
        showConfirmation();
    }

    private boolean isValidUserName(String text) {
        //Database.query();
        return true;
    }

    private boolean isPasswordValid(String text) {
        if (pwdPassword.getText().equals(pwdConfirmPassword.getText())) {
            return true;
        } else {
            lblInfo.setText(resources.getString("label.text.4"));
            return false;
        }

    }

    private void showConfirmation() {
        primary.setTop(new Label("Created New Account \nLogging In"));
        //This thread allows us to wait for 2 seconds before being logged in 
        //- giving feedback to the user that the account creation was successful
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CreateAccountPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
            Platform.runLater(() -> {
                AppViewManager.PRIMARY_VIEW.switchView();
            });

            return;
        });

        t.start();
    }

}
