package com.picksome.picksome;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.HashMap;
import java.util.Map;

public class LanguageBox extends Pane {

    //Map<String, String> languageMap = Map.of("English", "EN",
    //                                              "Nederlands", "NL");
    BiMap<String, String> languageMap;
    ChoiceBox<String> box;
    Text keyText;

    public LanguageBox(String text, String previousValue) {
        initMap();
        VBox vbox = new VBox(5);
        box = new ChoiceBox<>();
        box.getItems().addAll(languageMap.keySet());
        box.setValue(languageMap.inverse().get(previousValue));
        keyText = new Text(text);
        vbox.getChildren().addAll(keyText, box);
        this.getChildren().add(vbox);

    }

    public void changeText(String text) {
        keyText.setText(text);
    }

    public ChoiceBox<String> getBox() {
        return this.box;
    }

    private void initMap() {
        languageMap = HashBiMap.create();
        languageMap.put("English", "EN");
        languageMap.put("Nederlands", "NL");
        //System.out.println(languageMap.keySet());
    }

    public String getLanguageAbbr() {
        return languageMap.getOrDefault(this.box.getValue(), "EN");
    }
}
