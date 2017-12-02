package com.dischub.views;

import static com.gluonhq.charm.glisten.afterburner.DefaultDrawerManager.DRAWER_LAYER;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.dischub.DiscHub;
import com.gluonhq.charm.glisten.application.MobileApplication;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginPresenter extends GluonPresenter<DiscHub> {

    @FXML
    private View primary;

    @FXML
    private Label label;

    @FXML
    private ResourceBundle resources;

    public void initialize() {
        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("BINGO");
                AppBar appBar = getApp().getAppBar();
                appBar.setVisible(false);
                appBar.setTitleText("Login");
                appBar.setVisible(false);
            }
        });
    }

    @FXML
    void buttonClick() {
        System.out.println("Clicked");
        AppViewManager.PRIMARY_VIEW.switchView();
    }

}
