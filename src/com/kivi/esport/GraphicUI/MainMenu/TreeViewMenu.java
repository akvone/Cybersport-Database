package com.kivi.esport.GraphicUI.MainMenu;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created on 31.05.2016.
 */

/**
 * This class is responsible part with tree view on the top left
 */
public class TreeViewMenu {
    private TreeView treeView;

    public TreeViewMenu(TreeView treeView, MainScreen mainScreen) {
        this.treeView = treeView;

        treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                System.out.println("Selected Item: " + selectedItem.getValue());
                // do what ever you want
                mainScreen.getWorkAreaMenu().clearInformationTextArea();
                mainScreen.getWorkAreaMenu().showWorkArea(selectedItem.getValue());
            }
        });
    }

    public void loadTreeView(){
        initTreeViewLayout();
    }

    private void initTreeViewLayout() {
        TreeItem<String> rootItem = new TreeItem<>("ROOT ITEM");
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false); //To unshow "ROOT ITEM" rootItem

        JSONParser parser = new JSONParser();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("res/work tree.json")) ;
            Object obj = parser.parse(inputStreamReader);
            JSONObject rootObject = (JSONObject) obj;
            JSONObject tvObj = (JSONObject) rootObject.get("treeView");
            for (Object tvKey : tvObj.keySet()) {
                TreeItem<String> firstLevelItem;
                firstLevelItem = new TreeItem<>(tvKey.toString());
                rootItem.getChildren().add(firstLevelItem);
                for (Object tvValueKey : ((JSONObject) tvObj.get(tvKey)).keySet()) {
                    TreeItem<String> secondLevelItem = new TreeItem<>(tvValueKey.toString());
                    firstLevelItem.getChildren().add(secondLevelItem);
                }
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
