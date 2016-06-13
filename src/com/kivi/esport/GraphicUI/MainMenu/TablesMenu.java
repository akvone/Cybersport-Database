package com.kivi.esport.GraphicUI.MainMenu;

import com.kivi.esport.Database.SQLTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created on 02.06.2016.
 */

/**
 * This class is responsible part with tables on the top right
 */
public class TablesMenu {

    private Connection con;
    private SQLTable sqlT;

    private TabPane tableTabPane;

    //Tables which will be use
    enum Tables {Player,Team,Player_Roster,Tournament,Tournament_Participants,Simple_Logs,Additional}

    public TablesMenu(TabPane tabPane, MainScreen mainScreen) {
        con = mainScreen.getConnection();
        sqlT = new SQLTable(con);
        tableTabPane = tabPane;
    }

    public void loadTables(){
        initTableLayouts(Tables.values());
    }

    private void initTableLayouts(Tables... tables) {
        String tableName;
        for (Tables table : tables) {
            tableName = table.name();

            HBox hbox = new HBox();
            Label label = new Label(tableName);
            label.setFont(new Font("Arial", 20));
            Button refreshButton = new Button();
            Image imageRefresh = new Image(getClass().getClassLoader().getResourceAsStream("res/update.png"),20,20,false,false);
            refreshButton.setGraphic(new ImageView(imageRefresh));
            refreshButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    updateTable(table);
                }
            });
            hbox.setMargin(label,new Insets(0, 30, 0, 0));
            hbox.getChildren().addAll(label,refreshButton);

            VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(10));

            TableView tableView = new TableView();
            vbox.getChildren().addAll(hbox, tableView);

            Tab tab = new Tab(tableName);
            tableTabPane.getTabs().add(tab);
            tableTabPane.getTabs().get(table.ordinal()).setContent(vbox);
            updateTable(table);
        }
    }

    private void updateTable(Tables table) {
        if (table == Tables.Additional) { //If updatable table is Additional, then set empty table
            TableView tableView = new TableView();
            setTableInRightPlace(tableView, Tables.Additional);
            return;
        }
        String tableName = table.name();
        ResultSet resultSet = sqlT.getRSofTable(tableName);
        TableView tableView = getRefreshedTable(resultSet);
        setTableInRightPlace(tableView,table);
    }

    private TableView getRefreshedTable(ResultSet rs){
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        TableView tableView = new TableView();
        tableView.setEditable(true);
        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i) == null ? "NULL" : rs.getString(i));//
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
            }
            //FINALLY ADDED TO TableView
            tableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return tableView;
    }

    private void setTableInRightPlace(TableView tableView, Tables table){
        VBox addVBox = (VBox)(tableTabPane.getTabs().get(table.ordinal()).getContent());
        addVBox.getChildren().set(1,tableView);
    }


    public void buildAdditionalTable(ResultSet rs){
        TableView tableView = getRefreshedTable(rs);
        setTableInRightPlace(tableView,Tables.Additional);
    }
}
