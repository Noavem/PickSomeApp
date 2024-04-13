package com.picksome.picksome;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;

public class APIManager {
    public static void main(String[] args) throws Exception {
        GameResponse gameSuggestion = new GameResponse();
        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.rawg.io/api/games?genres=indie&key=7567003ex45341329b5c47267e568568&page=1&page_size=1"))
                .build();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        if (getResponse.statusCode() == 401) {
            System.out.println("Invalid key. Change your key in the settings.");
        } else {
            gameSuggestion = gson.fromJson(getResponse.body(), GameResponse.class);
            Thread.sleep(2000);  // Consider removing Thread.sleep unless there's a specific reason for it
            System.out.println(gameSuggestion.getCount());
        }
    }
}
