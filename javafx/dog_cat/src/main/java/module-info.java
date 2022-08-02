module com.yubo.dog_cat {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;


    opens com.yubo.dog_cat to javafx.fxml;
    exports com.yubo.dog_cat;
}