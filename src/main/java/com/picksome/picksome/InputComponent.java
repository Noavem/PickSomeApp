package com.picksome.picksome;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InputComponent extends Pane {
    TextField input;
    String defaultValue;
    Text keyText;

    public InputComponent(String text, String defaultValue) {
        VBox vbox = new VBox(5);
        keyText = new Text(text);
        this.input = new TextField();
        vbox.getChildren().addAll(keyText, input);
        this.getChildren().add(vbox);
        this.defaultValue = defaultValue;
        initText();
    }

    public String getText() {
        return this.input.getText();
    }

    public void initText() {
        this.input.setText(defaultValue);
    }

    public void changeText(String text) {
        keyText.setText(text);
    }

    public void setRed() {
        Parent parent = getParent();
        parent.getStyleClass().add("alarm");
    }
}
