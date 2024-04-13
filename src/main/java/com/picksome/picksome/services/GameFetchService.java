package com.picksome.picksome.services;
import com.picksome.picksome.BookResponse;
import com.picksome.picksome.GameResponse;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.util.Random;

public class GameFetchService extends FetchService<GameResponse> {
    private final String url;

    public GameFetchService(String url) {
        this.url = url;
    }

    @Override
    protected Task<GameResponse> createTask() {
        return new Task<>() {
            @Override
            protected GameResponse call() throws Exception {
                return fetchAll(GameResponse.class, url);
            }
        };
    }

    @Override
    public String getQuery(int num) {
        return url.replaceAll("page=1", "page="+ num);
    }
}

