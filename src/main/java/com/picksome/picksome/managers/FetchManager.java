package com.picksome.picksome.managers;

import com.picksome.picksome.BookResponse;
import com.picksome.picksome.GameResponse;
import com.picksome.picksome.MainApp;
import com.picksome.picksome.services.BookFetchService;
import com.picksome.picksome.services.GameFetchService;
import javafx.scene.control.ProgressIndicator;

public class FetchManager {
    BookFetchService bookFetchService;
    GameFetchService gameFetchService;
    PropertiesManager propertiesManager;
    MainApp UI;

    public FetchManager(MainApp UI) {
        this.UI = UI;
        this.propertiesManager = UI.getPropertiesManager();
    }

    public void initGameFetch(ProgressIndicator loadingIndicator) {
        gameFetchService = new GameFetchService("https://api.rawg.io/api/games?key=" + propertiesManager.getRawgKey() + "&page=1&page_size=1");
        gameFetchService.setOnRunning(e -> UI.getUiManager().setLoadOVerlay(loadingIndicator));

        gameFetchService.setOnSucceeded(e -> {
            GameResponse game = gameFetchService.getValue();
            UI.getUiManager().setFetchResult(game.getResults().get(0).getName(), loadingIndicator);
        });
        gameFetchService.setOnFailed(e -> {
            UI.getUiManager().setFetchFail(handleFailedRequest(gameFetchService.getStatusCode()), loadingIndicator);
        });
        gameFetchService.start();
    }

    public void initBookFetch(ProgressIndicator loadingIndicator) {
        bookFetchService = new BookFetchService("https://gutendex.com/books/");
        bookFetchService.setOnRunning(e -> UI.getUiManager().setLoadOVerlay(loadingIndicator));
        bookFetchService.setOnSucceeded(e -> {
            loadingIndicator.setVisible(false);
            BookResponse book = bookFetchService.getValue();
            UI.getUiManager().setFetchResult(book.getResults().get(0).getTitle(), loadingIndicator);
        });

        bookFetchService.setOnFailed(e -> {
            UI.getUiManager().setFetchFail(handleFailedRequest(bookFetchService.getStatusCode()), loadingIndicator);
        });
        bookFetchService.start();
    }

    private String handleFailedRequest(int statusCode) {
        if (statusCode == 401) {
            return "Invalid key. Change your key in the settings.";
        } else {
            return "This API might be down, try again later.";
        }
    }
}
