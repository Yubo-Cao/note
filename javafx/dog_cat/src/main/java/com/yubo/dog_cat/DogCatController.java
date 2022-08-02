package com.yubo.dog_cat;

import io.github.palexdev.materialfx.controls.MFXRadioButton;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DogCatController implements Initializable {
    public ImageView image;
    public HBox buttons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var group = new ToggleGroup();
        buttons.getChildren().forEach(node -> {
            var btn = (MFXRadioButton) node;
            btn.setToggleGroup(group);
        });
        image.imageProperty().bind(Bindings.createObjectBinding(() -> new Image(Objects.requireNonNull(
                        DogCatController.class.getResourceAsStream((group.getSelectedToggle() == null) ? "placeholder.jpg" :
                                group.getSelectedToggle().getUserData().toString()))),
                group.selectedToggleProperty()));
    }
}
