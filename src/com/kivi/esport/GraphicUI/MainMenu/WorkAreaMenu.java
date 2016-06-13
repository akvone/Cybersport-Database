package com.kivi.esport.GraphicUI.MainMenu;

import com.kivi.esport.Database.SQLFunctions;
import com.kivi.esport.Database.SQLProcedures;
import com.kivi.esport.Database.SQLViews;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created on 31.05.2016.
 */

/**
 * This class is responsible part with information output on the bottom
 */
public class WorkAreaMenu {
    private SQLViews sqlV;
    private SQLProcedures sqlP;
    private SQLFunctions sqlF;
    private TablesMenu tablesMenu;

    private AnchorPane workPane;
    private TextArea informationTextArea;
    private HashMap<String,HBox> hBoxes; //Map of HBox that contains different WorkAreas
    private HashMap<Object,JSONWorkObject> titleToDatabaseName; //Map that contains correspondence between Title in TreeViewMenu and name of procedure/function/view in Database

    private WorkAreaController workAreaController;


    public static class JSONWorkObject{
        String nameInDatabase;
        String type;

        JSONWorkObject(String nameInDatabase, String type){
            this.nameInDatabase = nameInDatabase;
            this.type = type;
        }
    }

    public WorkAreaMenu(AnchorPane anchorPane,MainScreen mainScreen){
        workPane = anchorPane;
        informationTextArea = (TextArea) ((VBox)workPane.getChildren().get(0)).getChildren().get(2);
        sqlV = new SQLViews(mainScreen.getConnection());
        sqlP = new SQLProcedures(mainScreen.getConnection());
        sqlF = new SQLFunctions(mainScreen.getConnection());
        tablesMenu = mainScreen.getTablesMenu();
        hBoxes = new HashMap<>();
        titleToDatabaseName = new HashMap<>();
        workAreaController = new WorkAreaController(workPane,this);
    }

    public void loadWorkArea(){
        initWorkVBoxes();
    }

    public void showWorkArea(String workAreaString){
        if(hBoxes.containsKey(workAreaString)) {
            Label label = ((Label) ((VBox) workPane.getChildren().get(0)).getChildren().get(0));
            label.setText(workAreaString);
            ObservableList<Node> vBoxChildren = ((VBox) workPane.getChildren().get(0)).getChildren();
            vBoxChildren.set(1, hBoxes.get(workAreaString));
        }
    }

    private void initWorkVBoxes(){
        JSONParser parser = new JSONParser();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("res/work tree.json")) ;
            Object obj = parser.parse(inputStreamReader);
            JSONObject rootObject = (JSONObject) obj;
            JSONObject tvObj = (JSONObject) rootObject.get("treeView");
            for (Object tvKey : tvObj.keySet()) {
                for (Object tvValueKey : ((JSONObject) tvObj.get(tvKey)).keySet()) {
                    HBox hbox = new HBox();
                    JSONObject workDirectory = null;

                    //Putting information into titleToDatabaseName
                    String type = (String)((JSONObject)((JSONObject) tvObj.get(tvKey)).get(tvValueKey)).get("type");
                    String nameInDatabase = (String)((JSONObject)((JSONObject) tvObj.get(tvKey)).get(tvValueKey)).get("nameInDatabase");
                    if (nameInDatabase!=null){
                        titleToDatabaseName.put(tvValueKey, new JSONWorkObject(nameInDatabase,type));
                    }

                    workDirectory = (JSONObject)((JSONObject)((JSONObject) tvObj.get(tvKey)).get(tvValueKey)).get("workingDirectory");
                    if (workDirectory!=null) {
                        JSONArray textFields = (JSONArray) workDirectory.get("textFields");
                        if (textFields!=null) {
                            for (Object textField : textFields) {
                                String promptText = ((JSONObject) textField).get("promptText").toString();
                                TextField tf = new TextField();
                                tf.setPromptText(promptText);
                                HBox.setMargin(tf, new Insets(0, 20, 0, 0));

                                hbox.getChildren().add(tf);
                            }
                        }
                        JSONArray buttons = (JSONArray) workDirectory.get("buttons");
                        if (buttons!=null) {
                            for (Object button : buttons) {
                                String text = ((JSONObject) button).get("text").toString();
                                Button b = new Button();
                                b.setText(text);
                                b.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent e) {
                                        workAreaController.buttonClicked(type,tvValueKey);
                                    }
                                });
                                hbox.getChildren().add(b);
                            }
                        }
                        String hboxLabelText = tvValueKey.toString();
                        hBoxes.put(hboxLabelText, hbox);
                    }
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void clearInformationTextArea(){
        informationTextArea.clear();
    }


    public void showView(Object tvValueKey){
        String viewName = titleToDatabaseName.get(tvValueKey).nameInDatabase;
        ResultSet rs = sqlV.getRSofView(viewName);
        tablesMenu.buildAdditionalTable(rs);
        informationTextArea.setText("Successfully. Look at additional tab");
    }

    public void addEntity(Object tvValueKey,ArrayList<String> fields){
        String procedureName = titleToDatabaseName.get(tvValueKey).nameInDatabase;
        String information = sqlP.addEntity(procedureName,fields);
        informationTextArea.setText(information);
    }

    public void receiveTableSentFields(Object tvValueKey,ArrayList<String> fields){
        String procedureName = titleToDatabaseName.get(tvValueKey).nameInDatabase;
        ResultSet rs = sqlP.receiveTableSendFields(procedureName,fields);
        if (rs!=null){
            tablesMenu.buildAdditionalTable(rs);
            informationTextArea.setText("Successfully. Look at additional tab");
        }
        else informationTextArea.setText("There was an error with getting table");
    }

    public void getAvgSPforNbestPlayers(Object tvValueKey, int number){
        int avgSP = sqlP.getAvgSPforNbestPlayers(number);
        informationTextArea.setText("Average skill points are "+avgSP);
    }

    public void getSkillPointsofPlayer(Object tvValueKey, String s){
        int SP = sqlF.getSkillPoints(s);
        informationTextArea.setText("Skill points of "+s+" are "+SP);
    }
}
