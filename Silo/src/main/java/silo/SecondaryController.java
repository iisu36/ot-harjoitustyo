package silo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SecondaryController {

    @FXML
    private Text mouseOn;
    
    @FXML
    protected void mouseOn(ActionEvent event) throws IOException {
        mouseOn.toFront();
    }
}