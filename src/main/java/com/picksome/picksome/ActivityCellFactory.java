package com.picksome.picksome;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ActivityCellFactory implements Callback<ListView<String>, ListCell<String>>
{
    @Override
    public ListCell<String> call(ListView<String> listview)
    {
        return new ActivityCell();
    }
}
