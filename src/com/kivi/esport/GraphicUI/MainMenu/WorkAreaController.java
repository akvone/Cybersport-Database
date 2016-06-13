package com.kivi.esport.GraphicUI.MainMenu;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Created on 01.06.2016.
 */

/**
 * Controller for WorkAreaMenu
 */
public class WorkAreaController {

    private WorkAreaMenu workAreaMenu;

    private AnchorPane workPane;
    private ObservableList<Node> hBoxChildren;

    public WorkAreaController(AnchorPane workPane, WorkAreaMenu workAreaMenu) {
        this.workAreaMenu = workAreaMenu;
        this.workPane = workPane;
    }

    public void buttonClicked(Object tvKey, Object tvValueKey){
        ArrayList<String> fields;
        switch ((String)tvKey) {
            case "view":
                workAreaMenu.showView(tvValueKey);
                break;
            case "addEntity":
                fields = getArrayListOfTextFields();
                workAreaMenu.addEntity(tvValueKey,fields);
                break;
            case "receiveTableSendFields":
                fields = getArrayListOfTextFields();
                workAreaMenu.receiveTableSentFields(tvValueKey,fields);
                break;

            case "getAvgSPforNPlayers": //TODO Rewrite in future
                fields = new ArrayList<>();
                hBoxChildren = ((HBox)((VBox) workPane.getChildren().get(0)).getChildren().get(1)).getChildren();
                for (Node vBoxChild : hBoxChildren) {
                    try {
                        String s = ((TextField) vBoxChild).getText();
                        if (s!="") fields.add(s);
                        else fields.add(null);
                    } catch (ClassCastException e) {
                        break;
                    }
                }
                workAreaMenu.getAvgSPforNbestPlayers(tvValueKey,Integer.parseInt(fields.get(0)));
                break;
            case "getPlayerSkillPoints": //TODO Rewrite in future
                fields = new ArrayList<>();
                hBoxChildren = ((HBox)((VBox) workPane.getChildren().get(0)).getChildren().get(1)).getChildren();
                for (Node vBoxChild : hBoxChildren) {
                    try {
                        String s = ((TextField) vBoxChild).getText();
                        if (s!="") fields.add(s);
                        else fields.add(null);
                    } catch (ClassCastException e) {
                        break;
                    }
                }
                workAreaMenu.getSkillPointsofPlayer(tvValueKey,fields.get(0));
                break;
            default:
                System.out.println("I don't know what to do");
                break;
        }
    }

    public ArrayList<String> getArrayListOfTextFields(){
        ArrayList<String >fields = new ArrayList<>();
        hBoxChildren = ((HBox)((VBox) workPane.getChildren().get(0)).getChildren().get(1)).getChildren();
        for (Node vBoxChild : hBoxChildren) {
            try {
                String s = ((TextField) vBoxChild).getText();
                if (s.equals("")) fields.add("NULL");
                else fields.add(s);
            } catch (ClassCastException e) {
                break;
            }
        }
        return fields;
    }
}
