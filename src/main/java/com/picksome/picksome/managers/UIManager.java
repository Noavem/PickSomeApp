package com.picksome.picksome.managers;

import com.picksome.picksome.*;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class UIManager {
    Button settings;
    MainApp app;
    PropertiesManager propertiesManager;
    StackPane activityPane;
    ComboBox<String> activity;
    Button button;
    Button about;
    Text history;
    Pane overlay;
    StackPane stackPane;
    Label text;
    Text suggestionText;
    ListView<String> listView;

    public UIManager(Button settings, MainApp app, PropertiesManager propertiesManager, StackPane activityPane, ComboBox<String> activity, Button button, Text history, Button about, Pane overlay, StackPane stackPane, Label text, Text suggestionText, ListView<String> listView) {
        this.settings = settings;
        this.app = app;
        this.propertiesManager = propertiesManager;
        this.activityPane = activityPane;
        this.activity = activity;
        this.button = button;
        this.about = about;
        this.history = history;
        this.overlay = overlay;
        this.stackPane = stackPane;
        this.text = text;
        this.suggestionText = suggestionText;
        this.listView = listView;
    }
    public void setUpResultText(Label text) {
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        text.setMaxWidth(150);
        text.setWrapText(true);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setAlignment(Pos.CENTER);
        Tooltip copyTip = new Tooltip("Click to copy");
        copyTip.setShowDelay(new Duration(200));
        text.setTooltip(copyTip);
        copyTip.setStyle("-fx-text-fill: white;");
    }

    public void setUpActivityBox(ComboBox<String> activity) {
        activity.setCellFactory(new ActivityCellFactory());
        activity.setButtonCell(new ActivityCell());
        activity.getItems().addAll("games", "books");
        activity.setValue("games");
    }

    public void setUpSettings(Stage settingsWindow) {
        this.settings.setOnAction(e -> {
            initSettingsWindow(settingsWindow);
            settingsWindow.show();
            settingsWindow.toFront();
        });
    }

    public void setUpAbout(Stage aboutWindow) {
        this.about.setOnAction(e -> {
            initAboutWindow(aboutWindow);
            aboutWindow.show();
            aboutWindow.toFront();
        });
    }

    protected void initSettingsWindow(Stage settingsWindow) {
        KeySettingsPane pane = new KeySettingsPane(this.app, this.propertiesManager, this);
        Scene scene = new Scene(pane, 300, 300);
        scene.getStylesheets().add(MainApp.class.getResource("style.css").toExternalForm());
        settingsWindow.setScene(scene);
    }

    protected void initAboutWindow(Stage aboutWindow) {
        StackPane pane = new StackPane();
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        Image icon = new Image(MainApp.class.getResourceAsStream("assets/avatar.png"));
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(60);
        iconView.setPreserveRatio(true);
        Rectangle clip = new Rectangle(70, 70);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        iconView.setClip(clip);
        Text name = new Text("Made by Noavem");
        Label gitHubLink = new Label("GitHub.com/link_to_project");
        setTextCopyable(gitHubLink);
        Tooltip copyTip = new Tooltip("Click to copy");
        copyTip.setShowDelay(new Duration(200));
        gitHubLink.setTooltip(copyTip);
        copyTip.setStyle("-fx-text-fill: white;");
        vbox.getChildren().addAll(iconView, name, gitHubLink);
        pane.getChildren().add(vbox);
        Scene scene = new Scene(pane, 300, 200);
        scene.getStylesheets().add(MainApp.class.getResource("style.css").toExternalForm());
        aboutWindow.setScene(scene);
    }

    public void setUpSuggestButton(Button button, ComboBox<String> activity, FetchManager fetchManager, ProgressIndicator loadingIndicator) {
        button.setOnAction(e -> {
            if (activity.getSelectionModel().getSelectedItem().equals("games")) {
                fetchManager.initGameFetch(loadingIndicator);
            } else {
                fetchManager.initBookFetch(loadingIndicator);
            }
        });
    }

    public void setUpListView(ListView<String> listView) {
        listView.setOnMouseClicked(event -> handleMouseClick(event, listView));
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(shortenText(item, 20));
                }
            }
        });
    }

    private void handleMouseClick(MouseEvent event, ListView<String> listView) {
        if (event.getClickCount() == 1) {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedItem);
                clipboard.setContent(content);
            }
        }
    }

    private String shortenText(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }

    public void reloadScene() {
        settings.setText(propertiesManager.getLanguageProperty(propertiesManager.getUserProperties().getProperty("app.language", "EN") + ".app.menu.settings"));
        suggestionText.setText(propertiesManager.getTextinLanguage("app.main.suggest." + this.activity.getSelectionModel().getSelectedItem()));
        activityPane.getChildren().remove(activity);
        ComboBox<String> activityNew = new ComboBox<>();
        activityNew.setCellFactory(new ActivityCellFactory());
        activityNew.setButtonCell(new ActivityCell());
        activityNew.getItems().addAll("games", "books");
        activityNew.setValue(activity.getSelectionModel().getSelectedItem());
        activityPane.getChildren().add(activityNew);
        activity = activityNew;

        button.setText(propertiesManager.getTextinLanguage("app.main.button"));
        history.setText(propertiesManager.getTextinLanguage("app.main.history"));
        about.setText(propertiesManager.getTextinLanguage("app.menu.about"));
    }

    public void setLoadOVerlay(ProgressIndicator loadingIndicator) {
        loadingIndicator.setVisible(true);
        this.overlay.setVisible(true);
        double height = stackPane.getBoundsInParent().getHeight();
        double width = stackPane.getBoundsInParent().getWidth();
        this.overlay.setMinWidth(width);
        this.overlay.setMinHeight(height);
    }

    public void setTextCopyable(Label text) {
        text.setOnMouseClicked(event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(text.getText());
            clipboard.setContent(content);

            text.setStyle("-fx-text-fill: #0ea5e9;");

            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(e -> text.setStyle("-fx-text-fill: #475569;"));
            pause.play();
        });
    }

    public void setFetchResult(String result, ProgressIndicator loadingIndicator) {
        loadingIndicator.setVisible(false);
        suggestionText.setText(propertiesManager.getTextinLanguage("app.main.suggest." + this.activity.getSelectionModel().getSelectedItem()));
        text.setText(result);
        try {
            app.getTextFileManager().prependLine(result);
            listView.getItems().addFirst(result);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        overlay.setVisible(false);
        setTextCopyable(this.text);
    }

    public void setFetchFail(String text, ProgressIndicator loadingIndicator) {
        loadingIndicator.setVisible(false);
        this.text.setText(text);
        overlay.setVisible(false);
        setTextUncopyable();
    }

    public void setTextUncopyable() {
        text.setOnMouseClicked(event -> {});
    }
}
