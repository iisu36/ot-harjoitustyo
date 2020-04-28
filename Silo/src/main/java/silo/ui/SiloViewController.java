/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.ui;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import silo.dao.SiloDao;
import silo.dao.ClientDao;

import silo.domain.Client;
import silo.domain.Grain;
import silo.domain.Silo;

/**
 * @author  Iisakki
 * @version Viikko 6
 * 
 * This class controls the creating of a new client silo with wanted values.
 */
public class SiloViewController {

    public Silo silo;
    public Client client;
    public Grain grain;

    public TextField clientName;
    public TextField crop;
    public TextField variety;
    public TextField volume;
    public TextField productionMethod;
    public Button createButton;

    /**
     * Adds values to selected silo,updates silos values in the database and changes
     * silomap visual.
     * 
     * Also adds client to clientlist if the client is new. If it's an existing client
     * adds the new silo and grain to client's information. After pressing add, the newly
     * opened window closes.
     * 
     * @throws SQLException Exception.
     */
    @FXML
    public void addSilo() throws SQLException {

        silo = MainViewController.getSilo();

        grain = new Grain();
        grain.setCrop(crop.getText());
        grain.setVariety(variety.getText());
        grain.setVolume(Integer.valueOf(volume.getText()));
        grain.setProductionMethod(productionMethod.getText());

        silo.setGrain(grain);
        
        client = MainViewController.isNewClient(clientName.getText());

        if (client == null) {

            client = new Client(clientName.getText());
            silo.setClient(client);

            client.addGrain(grain);
            client.addSilo(silo);

            MainViewController.clientList.add(silo.getClient());

            ClientDao clientDao = new ClientDao();
            clientDao.create(client);
        } else {

            silo.setClient(client);

            client.addGrain(grain);
            client.addSilo(silo);
        }

        MainViewController.showInfo(silo);

        SiloDao siloDao = new SiloDao();
        siloDao.create(silo);

        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();

    }
}
