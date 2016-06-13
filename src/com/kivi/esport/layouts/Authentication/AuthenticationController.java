package com.kivi.esport.layouts.Authentication;

import com.kivi.esport.GraphicUI.Authentication.AuthenticationMenu;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AuthenticationController {
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button logIn;
    @FXML
    private Label info;
    @FXML
    private CheckBox useLocal;

    // Ссылка на главное приложение.
    private AuthenticationMenu authenticationMenu;

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public AuthenticationController() {
    }

    public void setAuthenticationMenu(AuthenticationMenu authenticationMenu) {
        this.authenticationMenu = authenticationMenu;
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }

    @FXML
    private void keyOnFieldsPressed(KeyEvent ke){
        if (ke.getCode().equals(KeyCode.ENTER))
        {
            logInClicked();
        }
    }

    @FXML
    private void logInClicked() {
        authenticationMenu.connectUser(useLocal.isSelected(),ipField.getText(),portField.getText(), loginField.getText(), passwordField.getText());
    }

    @FXML
    private void useLocalChecked(){
        if (useLocal.isSelected()){
            ipField.setDisable(true);
            portField.setDisable(true);
        }
        else {
            ipField.setDisable(false);
            portField.setDisable(false);
        }
    }

    public void alarm(){
        passwordField.clear();
        info.setText("Connection Problems");
    }
}