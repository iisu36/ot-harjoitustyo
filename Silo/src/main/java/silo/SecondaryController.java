package silo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class SecondaryController {

    public static Map<Button, ArrayList> siloMap;
    public TextField name;
    public TextField plant;
    public TextField variety;
    public TextField volume;
    public TextField moisture;
    public Label textLabel;
    public Text clients;

    @FXML
    private GridPane siloGrid;
    private ArrayList<Client> clientList;
    private Button source;
    private Button hover;
    private Client client;

    @FXML
    public void initialize() {

        clientList = new ArrayList<>();
        siloMap = new HashMap<>();

        int k = 1;

        for (int row = 0; row < PrimaryController.row; row++) {

            for (int column = 0; column < PrimaryController.column; column++, k++) {

                Button button = new Button("Siilo " + k);
                button.setPrefSize(75, 75);
                siloMap.put(button, new ArrayList<>());
                siloGrid.add(button, column, row);
                clientList.add(new Client("", "", "", "", ""));
                button.setOnMouseEntered(e -> mouseEntered(e));
                button.setOnMouseClicked(e -> mouseClicked(e));
            }
        }

        source = (Button) siloGrid.getChildren().get(0);
        hover = (Button) siloGrid.getChildren().get(0);
    }

    @FXML
    private void mouseEntered(MouseEvent e) {

        hover = (Button) e.getSource();

        if (!siloMap.get(hover).isEmpty()) {
            textLabel.setText(siloMap.get(hover).get(0).toString()
                    + " Siilo " + (siloGrid.getChildren().indexOf(hover) + 1));
        } else {
            textLabel.setText("");
        }
//        Integer colIndex = siloGrid.getColumnIndex(source);
//        Integer rowIndex = siloGrid.getRowIndex(source);
    }

    @FXML
    public void mouseClicked(MouseEvent e) {

        source = (Button) e.getSource();

        if (!siloMap.get(source).isEmpty()) {
            textLabel.setText(siloMap.get(source).get(0).toString()
                    + " Siilo " + (siloGrid.getChildren().indexOf(source) + 1));
        } else {
            textLabel.setText("");
        }
    }

    @FXML
    public void remove() {

        if (!siloMap.get(source).isEmpty()) {
            clientList.set(siloGrid.getChildren().lastIndexOf(source), new Client("", "", "", "", ""));
            siloMap.get(source).clear();
        } else {
            int i = siloGrid.getChildren().lastIndexOf(source);
            siloMap.remove(source);
            siloGrid.getChildren().remove(source);
            siloGrid.getChildren().add(i, new Region());
        }

        list();

        textLabel.setText("");
    }

    @FXML
    public void add() {

        if (!name.getText().isBlank()) {
            client = new Client(name.getText(), plant.getText(),
                    variety.getText(), volume.getText(), moisture.getText());

            clientList.add(siloGrid.getChildren().lastIndexOf(source), client);
            siloMap.get(source).add(client);
            list();

            name.clear();
            plant.clear();
            variety.clear();
            volume.clear();
            moisture.clear();

            textLabel.setText(siloMap.get(source).get(0).toString()
                    + " Siilo " + (siloGrid.getChildren().indexOf(source) + 1));
        }
    }

    @FXML
    public void change() {

        if (!siloMap.get(source).isEmpty()) {

            client = (Client) siloMap.get(source).get(0);

            client.setName(name.getText());
            client.setPlant(plant.getText());
            client.setVariety(variety.getText());
            client.setVolume(volume.getText());
            client.setMoisture(moisture.getText());
        }

        list();

        textLabel.setText(siloMap.get(source).get(0).toString()
                + " Siilo " + (siloGrid.getChildren().indexOf(source) + 1));
    }

    @FXML
    public void list() {

        String listing = "";

        int i = 0;

        for (Client client : clientList) {

            i++;

            if (!client.getName().isBlank()) {
                listing += client.toString() + " Siilo " + i + "\n";
            }
        }

        clients.setText(listing);
    }
}
