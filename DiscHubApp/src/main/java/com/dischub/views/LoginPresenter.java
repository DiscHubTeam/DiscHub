package com.dischub.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.dischub.DiscHub;
import com.dischub.login.UserCredentials;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPresenter extends GluonPresenter<DiscHub> {

    @FXML
    private View primary;

    @FXML
    private Label label;
    
    @FXML
    private TextField txfEmail;
    
    @FXML
    private PasswordField pwdPassword;

    @FXML
    private ResourceBundle resources;
    
    private String encryptedPasswordString;

    public void initialize() {
        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = getApp().getAppBar();
                appBar.setVisible(false);
                appBar.setTitleText("Login");
                appBar.setVisible(false);
            }
        });
    }

    @FXML
    void buttonClick() {
        if(validUserName(txfEmail.getText())){
            if(passwordValid(pwdPassword.getText())){
                DiscHub.setUserCredentials(new UserCredentials(txfEmail.getText(), encryptedPasswordString,"Rowan"/*db.query*/, 0));
                AppViewManager.PRIMARY_VIEW.switchView();
                
            }
        }
        
    }

    private boolean validUserName(String text) {
        //Database.query();
        return true;
    }

    private boolean passwordValid(String text) {
       //query datatbase
        return true;
    }
    
    

}
