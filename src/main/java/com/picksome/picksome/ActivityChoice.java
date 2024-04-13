package com.picksome.picksome;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ActivityChoice extends Pane {
    public ActivityChoice(String textVal) {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        Text text = new Text(textVal);
        Image icon = new Image(MainApp.class.getResourceAsStream("assets/" + textVal + ".png"));
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(20);
        iconView.setPreserveRatio(true);
        StackPane p = new StackPane();
        p.setPadding(new Insets(0, 0 ,10, 0));
        p.getChildren().add(iconView);
        p.setStyle("-fx-border-width: 1; -fx-border-color: blue;");
        hBox.getChildren().addAll(iconView, text);
        this.getChildren().add(hBox);
    }
}
