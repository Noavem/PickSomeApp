package com.picksome.picksome;

import com.picksome.picksome.managers.FetchManager;
import com.picksome.picksome.managers.PropertiesManager;
import com.picksome.picksome.managers.TextFileManager;
import com.picksome.picksome.managers.UIManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

//TODO choose directory for necessary files
//TODO reset history button
//TODO remove tooltip for error message
//TODO style button and choicebox in settings
public class MainApp extends Application {

    PropertiesManager propertiesManager;
    TextFileManager textFileManager;
    UIManager uiManager;
    Stage settingsWindow;
    Stage aboutWindow;

    @Override
    public void start(Stage stage) {

        /* Setting up manager classes */
        textFileManager = new TextFileManager("./history.txt");
        this.propertiesManager = new PropertiesManager();
        propertiesManager.loadUserProperties();
        propertiesManager.loadLanguageProperties();
        FetchManager fetchManager = new FetchManager(this);


        /* Setting up UI nodes */
        Text suggestionText = new Text("...");
        Label text = new Label();

        Button settings = new Button(propertiesManager.getTextinLanguage("app.menu.settings"));
        Button about = new Button(propertiesManager.getTextinLanguage("app.menu.about"));

        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setVisible(false);

        ComboBox<String> activity = new ComboBox<>();

        Button button = new Button(propertiesManager.getTextinLanguage("app.main.button"));
        button.setId("suggest");

        ObservableList<String> fileContent = loadFileContent("./history.txt");
        ListView<String> listView = new ListView<>(fileContent);

        Text history = new Text(propertiesManager.getTextinLanguage("app.main.history"));
        history.setId("historyText");


        /* Panes */
        BorderPane layout = new BorderPane();
        layout.requestFocus();
        HBox topBox = new HBox(10);
        topBox.getChildren().addAll(settings, about);
        layout.setTop(topBox);

        StackPane activityPane = new StackPane(activity);
        VBox container = new VBox(20);
        container.setId("container");
        container.setPadding(new Insets(20, 20 ,20, 20));
        container.getChildren().addAll(activityPane, button, suggestionText, text);
        container.setAlignment(Pos.TOP_CENTER);

        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: black;");
        overlay.setVisible(false);
        overlay.setOpacity(0.3);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(container, overlay, loadingIndicator);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20 ,20, 20));
        vbox.setId("historyBox");
        vbox.getChildren().addAll(history, listView);
        SplitPane splitPane = new SplitPane(stackPane, vbox);
        layout.setCenter(splitPane);

        uiManager = new UIManager(settings, this, propertiesManager, activityPane, activity, button, history, about, overlay, stackPane, text, suggestionText, listView);
        uiManager.setUpResultText(text);
        uiManager.setUpActivityBox(activity);


        /* Set events for nodes */
        settingsWindow = new Stage();
        uiManager.setUpSettings(settingsWindow);
        uiManager.setUpListView(listView);
        uiManager.setUpSuggestButton(button, activity, fetchManager, loadingIndicator);
        uiManager.setTextCopyable(text);

        aboutWindow = new Stage();
        uiManager.setUpAbout(aboutWindow);


        /* Setting the stage */
        Scene scene = new Scene(layout, 500, 300);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        stage.setTitle("PickSome");
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
        layout.requestFocus();
    }

    private ObservableList<String> loadFileContent(String filePath) {
        ObservableList<String> content = FXCollections.observableArrayList();
        try {
            if (!Files.exists(Path.of(filePath))) {
                Files.createFile(Path.of(filePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            content.addAll(reader.lines().collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public TextFileManager getTextFileManager() {
        return this.textFileManager;
    }
    public UIManager getUiManager() {
        return this.uiManager;
    }
    public PropertiesManager getPropertiesManager() {
        return this.propertiesManager;
    }

    public static void main(String[] args) {
        launch();
    }
}