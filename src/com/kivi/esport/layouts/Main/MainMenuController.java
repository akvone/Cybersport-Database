package com.kivi.esport.layouts.Main;

import com.kivi.esport.GraphicUI.MainMenu.MainScreen;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;

public class MainMenuController {
    @FXML
    private CheckBox useBlackThemeCheckBox;
    @FXML
    private MenuItem changeUserMenuItem;
    @FXML
    private MenuItem quitMenuItem;

    // Ссылка на главное приложение.
    private MainScreen mainScreen;

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public MainMenuController() {
    }

    public void setMainScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }

    @FXML
    private void clickOnTheme(){
        if (useBlackThemeCheckBox.isSelected()) useBlackThemeCheckBox.setSelected(false);
        else useBlackThemeCheckBox.setSelected(true);
        mainScreen.changeTheme(useBlackThemeCheckBox.isSelected());
    }

    @FXML
    private void changeUserClicked(){
        mainScreen.changeUser();
    }

    @FXML
    private void quitClicked(){
        mainScreen.quit();
    }
}