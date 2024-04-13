package com.picksome.picksome.services;

import com.google.gson.Gson;
import com.picksome.picksome.BookResponse;
import com.picksome.picksome.Response;
import javafx.concurrent.Service;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public abstract class FetchService<T extends Response> extends Service<T> {
    HttpResponse<String> resp;
    //TODO Better error handling
    protected HttpResponse<String> fetchCount(Class<T> xd, String url, HttpClient httpClient) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();
        return httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
    }

    protected T fetchAll(Class<T> xd, String url) throws InterruptedException, URISyntaxException, IOException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = fetchCount(xd, url, httpClient);
        resp = response;
        T countSample = new Gson().fromJson(response.body(), xd);
        int count = countSample.getCount();
        int randomPage = (new Random()).nextInt(count) + 1;

        Thread.sleep(200);

        HttpRequest getGameRequest = HttpRequest.newBuilder()
                .uri(new URI(getQuery(randomPage)))
                .build();
        HttpResponse<String> gameSuggestion = httpClient.send(getGameRequest, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(gameSuggestion.body(), xd);
    }

    public abstract String getQuery(int num);

    public int getStatusCode() {
        return resp.statusCode();
    }
}
