package com.kivi.esport.GraphicUI.MainMenu;

import com.kivi.esport.GraphicUI.GraphicsApplication;
import com.kivi.esport.layouts.Main.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;


/**
 * This class is responsible for all main menu screen
 */
public class MainScreen {
    private GraphicsApplication app;
    private Connection con;
    private Stage stage;

    private MainMenuController controller;
    private Scene scene;

    private TreeViewMenu treeViewMenu;
    private TablesMenu tablesMenu;
    private WorkAreaMenu workAreaMenu;

    private BorderPane rootLayout;
    private TabPane tableTabPane;
    private TreeView treeView;
    private AnchorPane workPane;

    public MainScreen(GraphicsApplication app){
        this.app = app;
        con = app.getDB().getConnection();
        stage = app.getStage();
    }

    /**
     * Calls after creation MainScreen object
     */
    public void start(){
        initRootLayout();
        tableTabPane = ((TabPane)((SplitPane)((SplitPane)rootLayout.getCenter()).getItems().get(0)).getItems().get(1));
        treeView = ((TreeView) ((SplitPane)((SplitPane)rootLayout.getCenter()).getItems().get(0)).getItems().get(0));
        workPane = ((AnchorPane) ((SplitPane)rootLayout.getCenter()).getItems().get(1));

        tablesMenu = new TablesMenu(tableTabPane,this);
        tablesMenu.loadTables();
        treeViewMenu = new TreeViewMenu(treeView,this);
        treeViewMenu.loadTreeView();
        workAreaMenu = new WorkAreaMenu(workPane,this);
        workAreaMenu.loadWorkArea();

        stage.setTitle("eSport database");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.setMaximized(true);
        stage.show();
    }

    private void initRootLayout(){ //initialize rootLayout
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainMenuController.class.getResource("MainLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            controller = loader.getController();
            controller.setMainScreen(this);
            scene = new Scene(rootLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return con;
    }

    public TablesMenu getTablesMenu(){
        return tablesMenu;
    }

    public TreeViewMenu getTreeViewMenu() {
        return treeViewMenu;
    }

    public WorkAreaMenu getWorkAreaMenu() {
        return workAreaMenu;
    }

    //TODO Replace this function
    public void changeTheme(boolean b){
        if (b) {
            rootLayout.setBlendMode(BlendMode.DIFFERENCE);
        }else {
            rootLayout.setBlendMode(BlendMode.SRC_OVER);
        }
    }

    public void changeUser(){
        app.changeUser();
    }

    public void quit(){
        System.exit(0);
    }
}