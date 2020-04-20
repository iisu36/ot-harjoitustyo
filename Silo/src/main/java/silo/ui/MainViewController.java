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
    public TreeTableView<Client> clientTable;
    public TreeTableColumn<Client, String> clientColumn;
    public TreeTableColumn<Client, String> siloColumn;

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
        siloDao = new SiloDao();
        clientDao = new ClientDao();

        int rows = siloDao.getRows();
        int columns = siloDao.getColumns();

        int k = 1;

        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++, k++) {

                Button button = new Button("Silo " + k);
                button.setMaxSize(75, 75);
                //button.setMinSize(75, 75);
                silo = siloDao.getSilo(row + 1, column + 1);
                silo.setButton(button);
                silo.setIndex(k);
                siloList.add(silo);
                siloMap.put(button, silo);
                siloLabelMap.put(silo.getLabel(), silo);
                siloGrid.add(silo.getLabel(), column, row);
                siloGrid.add(button, column, row);
                clientList.add(new Client(""));

                showInfo(silo);

                //silo.getLabel().setOnMouseEntered(eh -> mouseEnteredLabel(eh));
                //silo.getLabel().setOnMouseClicked(eh -> mouseClickedLabel(eh));
                button.setOnMouseEntered(e -> mouseEntered(e));
                button.setOnMouseClicked(e -> {
                    try {
                        mouseClicked(e);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

            }
        }

        //source = (Button) siloGrid.getChildren().get(1);
        //hover = (Button) siloGrid.getChildren().get(1);
    }

    @FXML
    public void mouseEntered(MouseEvent e) {

//        hover = (Button) e.getSource();
//        silo = siloMap.get(hover);
//        if (silo.getClient() != null) {
//            info.setText("Silo " + silo.getIndex() + " " + silo.toString());
//        } else {
//            info.setText("");
//        }
//        Integer colIndex = siloGrid.getColumnIndex(source);
//        Integer rowIndex = siloGrid.getRowIndex(source);
    }

    @FXML
    public void mouseClicked(MouseEvent e) throws SQLException {

        source = (Button) e.getSource();
        silo = siloMap.get(source);

        if (silo.getClient() != null) {

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

        if (silo.getClient() != null) {
            clientList.set(silo.getIndex() - 1, new Client(""));

            source.setStyle(null);
            silo.reset();

            silo.getLabel().setText("");
            silo.getLabel().setStyle("");

            siloDao.remove(silo);

        } else {

            //source.setDisable(true);
        }

        info.setText("");

    }

    @FXML
    public void createClientTree() throws SQLException {
        
        clientColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        siloColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        
        clientTable.setShowRoot(true);
        
        TreeItem<Client> treeSilo = new TreeItem<>(new Client("Asiakas"));
        
        TreeItem<Client> treeClient = new TreeItem<>(new Client("Silo"));
        treeClient.setExpanded(true);
        
        treeClient.getChildren().setAll(treeSilo);
        
        clientTable.setRoot(treeClient);
    }

    @FXML
    public static Silo getSilo() {
        return siloMap.get(source);
    }

    public static void showInfo(Silo silo) {

        if (silo.getClient() != null) {

            silo.getLabel().setText(silo.toString());

            silo.setStyle();

            silo.getButton().setStyle("-fx-opacity: 0.25; -fx-text-fill: black; "
                    + "-fx-font: 58 Tahoma; -fx-font-weight: bold; ");

        }
    }

    public static boolean isNewClient(Client client) {

        for (Client current : clientList) {

            if (current.getName().equals(client.getName())) {

                return false;
            }
        }

        return true;
    }
}
