package com.picksome.picksome;

import com.picksome.picksome.managers.PropertiesManager;
import com.picksome.picksome.managers.UIManager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class KeySettingsPane extends Pane {
    PropertiesManager propertiesManager;
    Button submitButton;
    InputComponent gameInput;
    LanguageBox languageBox;
    public KeySettingsPane(MainApp app, PropertiesManager propertiesManager, UIManager uiManager) {
        this.propertiesManager = propertiesManager;

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20, 20 ,20, 20));

        submitButton = new Button(propertiesManager.getTextinLanguage("app.settings.submit"));

        gameInput = new InputComponent(propertiesManager.getTextinLanguage("app.settings.RAWG"), propertiesManager.getUserProperties().getProperty("key.games", ""));

        languageBox = new LanguageBox(propertiesManager.getTextinLanguage("app.settings.language"), propertiesManager.getCurrentLanguageAbbr());

        vbox.getChildren().addAll(languageBox, gameInput, submitButton);
        getChildren().add(vbox);
        submitButton.setOnAction(e -> {
            propertiesManager.setUserProperties(gameInput.getText(), languageBox.getLanguageAbbr());
            propertiesManager.loadLanguageProperties();
            uiManager.reloadScene();
            reloadScene();
        });
    }

    private void reloadScene() {
        gameInput.changeText(propertiesManager.getTextinLanguage("app.settings.RAWG"));
        languageBox.changeText(propertiesManager.getTextinLanguage("app.settings.language"));
        submitButton.setText(propertiesManager.getTextinLanguage("app.settings.submit"));
    }
}
