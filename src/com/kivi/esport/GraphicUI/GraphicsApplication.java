package com.kivi.esport.GraphicUI;

import com.kivi.esport.Database.Database;
import com.kivi.esport.GraphicUI.Authentication.AuthenticationMenu;
import com.kivi.esport.GraphicUI.MainMenu.MainScreen;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class GraphicsApplication extends Application {

    private Database db;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            db = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.stage = stage;
        setStage(Frames.Authentication);
    }

    enum Frames {Authentication,MainApp};

    private void setStage(Frames f){
        switch (f){
            case Authentication:
                AuthenticationMenu authenticationMenu = new AuthenticationMenu(this);
                authenticationMenu.start();
                break;
            case MainApp:
                MainScreen mainScreen = new MainScreen(this);
                mainScreen.start();
                break;
        }
    }

    public void connectUser(Boolean useLocal, String ip, String port, String login, String password) throws SQLException {
        //If connection is unsuccessful then throws SQLException to AuthenticationMenu
        if (useLocal) {
            db.connectUser(login, password);
        }
        else {
            db.connectUser(ip,port,login,password);
        }
        setStage(Frames.MainApp);
    }

    public void changeUser(){
        db.dropConnection();
        setStage(Frames.Authentication);
    }

    public Database getDB(){
        return db;
    }

    public Stage getStage(){
        return stage;
    }
}
