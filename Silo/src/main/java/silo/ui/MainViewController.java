package silo.ui;

import java.io.IOException;
import java.sql.SQLException;
import silo.domain.*;
import silo.dao.SiloDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import silo.dao.ClientDao;
import static silo.ui.SiloUi.loadFXML;

public class MainViewController {

    public static ArrayList<Silo> siloList;
    public static Map<Button, Silo> siloMap;

    public Map<Label, Silo> siloLabelMap;
    public TextField name;
    public TextField plant;
    public TextField variety;
    public TextField volume;
    public TextField productionMethod;
    public Text info;
    public TreeTableView<Silo> clientTable;
    public TreeItem<Silo> clientTree;
    public TreeTableColumn<Silo, String> clientColumn;
    public TreeTableColumn<Silo, Integer> siloColumn;
    public TreeTableColumn<Silo, String> cropColumn;
    public TreeTableColumn<Silo, String> varietyColumn;
    public TreeTableColumn<Silo, String> volumeColumn;

    @FXML
    public GridPane siloGrid;
    @FXML
    public VBox box;

    public static ArrayList<Client> clientList;
    public static Button source;
    public Button hover;
    public Client client;
    public Silo silo;
    public Grain grain;
    public SiloDao siloDao;
    public ClientDao clientDao;

    @FXML
    public void initialize() throws SQLException {

        siloList = new ArrayList<>();
        clientList = new ArrayList<>();
        siloMap = new HashMap<>();
        siloLabelMap = new HashMap<>();
        siloDao = new SiloDao("silos");
        clientDao = new ClientDao("clients");

        int rows = siloDao.getRows();
        int columns = siloDao.getColumns();

        int k = 1;

        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++, k++) {

                Button button = new Button("Silo " + k);
                button.setMinSize(75, 75);
                button.setMaxSize(75, 75);
                button.setText(String.valueOf(k));
                button.setStyle("-fx-text-fill: #00000043; "
                        + " -fx-font: 58 Tahoma; -fx-font-weight: bold; ");
                button.setPadding(Insets.EMPTY);
                silo = siloDao.getSilo(row + 1, column + 1);
                silo.setButton(button);
                silo.setIndex(k);
                Label label = new Label("");
                label.setMaxSize(75, 75);
                label.setAlignment(Pos.CENTER);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setLineSpacing(11);
                silo.setLabel(label);
                siloList.add(silo);
                siloMap.put(button, silo);
                siloLabelMap.put(silo.getLabel(), silo);
                siloGrid.add(silo.getLabel(), column, row);
                siloGrid.add(button, column, row);
                client = silo.getClient();
                client.addGrain(silo.getGrain());
                client.addSilo(silo);

                if (isNewClient(client.getName()) == null) {
                    clientList.add(client);
                } else {
                    clientList.add(new Client(""));
                }

                showInfo(silo);

                button.setOnMouseClicked(e -> {
                    try {
                        mouseClicked(e);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }
    }

    @FXML
    public void mouseClicked(MouseEvent e) throws SQLException {

        source = (Button) e.getSource();
        silo = siloMap.get(source);

        if (!silo.getClient().getName().isBlank()) {

            info.setText("Silo " + silo.getIndex() + " \n" + getSilo().allInfo());

        } else {
            info.setText("");

            try {
                Stage stage = new Stage();
                stage.setTitle("SILO");
                stage.setScene(new Scene(loadFXML("SiloView")));
                stage.show();
            } catch (IOException eh) {
            }
        }
    }

    @FXML
    public void remove() throws IOException, SQLException {

        silo = getSilo();

        if (!silo.getClient().getName().isBlank()) {

            ArrayList<Silo> list = silo.getClient().getSiloList();
            list.remove(silo);
            silo.getClient().setSiloList(list);

            source.setStyle(null);
            resetSilo(silo, source);

            silo.getLabel().setText("");
            silo.getLabel().setStyle("");

            siloDao.remove(silo);

        }

        info.setText("");

    }

    @FXML
    public void createClientTree() throws SQLException {

        clientColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        siloColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("index"));
        siloColumn.setStyle("-fx-alignment: center");
        cropColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("crop"));
        cropColumn.setStyle("-fx-alignment: center");
        varietyColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("variety"));
        varietyColumn.setStyle("-fx-alignment: center");
        volumeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("volume"));
        volumeColumn.setStyle("-fx-alignment: center");

        clientTable.setShowRoot(true);

        Silo treeRoot = new Silo();
        treeRoot.setIndex(0);
        treeRoot.setClient(new Client("Clients"));
        Grain rootGrain = new Grain();
        rootGrain.setCrop("");
        rootGrain.setVariety("");
        rootGrain.setVolume(0);
        treeRoot.setGrain(rootGrain);

        clientTree = new TreeItem<>(treeRoot);
        clientTree.setExpanded(true);

        for (Client listClient : clientList) {

            if (!listClient.getName().isBlank() && !listClient.getSiloList().isEmpty()) {

                Silo clientRoot = new Silo();
                clientRoot.setIndex(0);
                clientRoot.setClient(new Client(listClient.getName()));
                Grain clientGrain = new Grain();
                clientGrain.setCrop("");
                clientGrain.setVariety("");
                clientGrain.setVolume(0);
                clientRoot.setGrain(clientGrain);

                TreeItem<Silo> treeClient = new TreeItem<>(clientRoot);
                treeClient.setExpanded(true);

                for (Silo listSilo : listClient.getSiloList()) {

                    TreeItem<Silo> treeSilo = new TreeItem<>(listSilo);

                    treeClient.getChildren().add(treeSilo);
                }

                clientTree.getChildren().add(treeClient);
            }
        }

        clientTable.setRoot(clientTree);
    }

    @FXML
    public void updateClientTree() throws SQLException {

        for (Client listClient : clientList) {

            if (!listClient.getName().isBlank()) {

                TreeItem<Silo> treeClient = new TreeItem<>(listClient.getSiloList().get(0));

                for (Silo listSilo : listClient.getSiloList()) {

                    TreeItem<Silo> treeSilo = new TreeItem<>(listSilo);

                    treeClient.getChildren().add(treeSilo);
                }

                clientTree.getChildren().add(treeClient);
            }
        }

        clientTable.setRoot(clientTree);
    }

    @FXML
    public static Silo getSilo() {
        return siloMap.get(source);
    }

    public static void showInfo(Silo silo) {

        if (!silo.getClient().getName().isBlank()) {

            silo.getLabel().setText(silo.toString());

            setSiloStyle(silo);

            silo.getButton().setStyle("-fx-opacity: 0.25; -fx-text-fill: black; "
                    + "-fx-font: 58 Tahoma; -fx-font-weight: bold; ");

        }
    }

    public static Client isNewClient(String name) {

        for (Client current : clientList) {

            if (current.getName().equals(name) && !current.getName().isBlank()) {

                return current;
            }
        }

        return null;
    }

    public void resetSilo(Silo silo, Button button) {
        silo.setClient(new Client(""));
        silo.setGrain(new Grain());

        button.setText(String.valueOf(silo.getIndex()));

        button.setStyle("-fx-text-fill: #00000043; "
                + " -fx-font: 58 Tahoma; -fx-font-weight: bold; ");
        button.setPadding(Insets.EMPTY);
    }

    public static void setSiloStyle(Silo silo) {

        Grain grain = silo.getGrain();

        if (grain.getVolume() == 0) {

            silo.getButton().setStyle("");

            return;
        }

        String background = "";

        if (grain.getVolume() < 150) {

            background = "-fx-background-color: greenyellow; ";

        } else if (grain.getVolume() < 200) {

            background = "-fx-background-color: yellowgreen; ";

        } else if (grain.getVolume() < 250) {

            background = "-fx-background-color: yellow; ";

        } else if (grain.getVolume() < 300) {

            background = "-fx-background-color: orange; ";

        } else if (grain.getVolume() <= 350) {

            background = "-fx-background-color: orangered; ";

        }

        silo.getLabel().setStyle(background + " -fx-font: 13 Tahoma; -fx-font-weight: bold; ");
    }
}
