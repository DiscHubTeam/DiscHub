package com.dischub.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.dischub.DiscHub;
import com.dischub.login.UserCredentials;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccountPresenter extends GluonPresenter<DiscHub> {

    @FXML
    private View primary;
    
    @FXML
    private TextField txfEmail;
    
    @FXML
    private TextField txfScreenName;
    
    @FXML
    private PasswordField pwdPassword;
    
    @FXML
    private PasswordField pwdConfirmPassword;
    
    @FXML
    private Button btnLogin;
    
    @FXML
    private Button btnCreateAccount;
    
    @FXML
    private Button btnResetPassword;

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
        if(isValidUserName(txfEmail.getText())){
            if(isPasswordValid(pwdPassword.getText())){
                DiscHub.setUserCredentials(new UserCredentials(txfEmail.getText(), encryptedPasswordString,"Rowan"/*db.query*/, 0));
                createAccount();
                //AppViewManager.PRIMARY_VIEW.switchView();
                
            }
        }
        
    }
        
    private void createAccount(){
        //Navigate to new screen to make an account.
        System.out.println("Requested create new login \n Name: "+txfScreenName.getText()+" \n uname: "+txfEmail.getText()+" \n Password: "+pwdPassword.getText());
        DiscHub.setUserCredentials(new UserCredentials(txfEmail.getText(), pwdPassword.getText(), txfScreenName.getText(), 0));
        AppViewManager.PRIMARY_VIEW.switchView();
    }
    
    private boolean isValidUserName(String text) {
        //Database.query();
        return true;
    }

    private boolean isPasswordValid(String text) {
       //query datatbase
        return true;
    }
    
    

}
