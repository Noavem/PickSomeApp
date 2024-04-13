package com.picksome.picksome;
import com.picksome.picksome.managers.PropertiesManager;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ActivityCell extends ListCell<String>
{
    PropertiesManager propertiesManager = new PropertiesManager();
    @Override
    public void updateItem(String item, boolean empty)
    {
        super.updateItem(item, empty);

        if (empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        {
            setText(propertiesManager.getTextinLanguage("app.main."+item));
            Node shape = this.getShape(item);
            setGraphic(shape);
            //setAlignment(Pos.CENTER);
        }
    }

    public Node getShape(String shapeType)
    {
        Node shape = null;

        Image icon = new Image(MainApp.class.getResourceAsStream("assets/" + shapeType + ".png"));
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(20);
        iconView.setPreserveRatio(true);
        shape = iconView;

        return shape;
    }
}
