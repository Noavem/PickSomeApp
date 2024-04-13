package com.picksome.picksome.services;

import com.picksome.picksome.BookResponse;
import javafx.concurrent.Task;

public class BookFetchService extends FetchService<BookResponse> {
    private final String url;
    public BookFetchService(String url) {
        this.url = url;
    }
    @Override
    protected Task<BookResponse> createTask() {
        return new Task<>() {
            @Override
            protected BookResponse call() throws Exception {
                return fetchAll(BookResponse.class, url);
            }
        };
    }

    @Override
    public String getQuery(int num) {
        return ("https://gutendex.com/books/?ids=" + num);
    }
}
