package com.kivi.esport.GraphicUI.Authentication;

import com.kivi.esport.GraphicUI.GraphicsApplication;
import com.kivi.esport.layouts.Authentication.AuthenticationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created on 28.05.2016.
 */
/**
 * This class is responsible for authentication screen
 */
public class AuthenticationMenu {

    private Stage stage;
    private Scene scene;
    private Pane rootLayout;
    private GraphicsApplication app;
    private AuthenticationController controller;

    public AuthenticationMenu(GraphicsApplication app){
        this.app = app;
        stage=app.getStage();
        initLayout();
    }

    /**
     * Calls after creation AuthenticationMenu object
     */
    public void start(){
        stage.setTitle("eSport database");
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void initLayout(){
        try {
            //Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AuthenticationController.class.getResource("AuthenticationLayout.fxml"));
            rootLayout = (Pane) loader.load();
            // Give application access to controller.
            controller = loader.getController();
            controller.setAuthenticationMenu(this);
            // Set this layout on scene
            scene = new Scene(rootLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectUser(Boolean useLocal, String ip, String port, String login, String password){
        try {
            app.connectUser(useLocal,ip,port,login,password);
        } catch (SQLException e) {
            controller.alarm();
        }
    }
}
