package com.dischub;

import com.dischub.login.UserCredentials;
import com.dischub.views.AppViewManager;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DiscHub extends MobileApplication {
private static UserCredentials usrCreds;
    
    public static UserCredentials getUserCredentials(){
        return usrCreds;
    }
    
    public static void setUserCredentials(UserCredentials creds){
        usrCreds=creds;
    }
    
    @Override
    public void init() {
        AppViewManager.registerViewsAndDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(DiscHub.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(DiscHub.class.getResourceAsStream("/icon.png")));
    }
}
