// project package
package com.ellington;
// import statements
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import java.net.URL;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow; 
import javafx.scene.paint.Color;
import javafx.scene.effect.BlurType; 

/** This class is used to control what happens within the display_all.fxml scene. Namely, the inventory database is queried to return all assets currently within it
 *  and display those results to the user. 
 */
public class DisplayAllController implements Initializable  {
    // establish link with FXML object under control of this controller
    @FXML TableView<Asset> resultsTableView;

    /** This method is called as soon as controller is called. Is used to initialize results table columns and load the appropriate data in to it.
     *  Additionally, DropShadow effects are defined and applied to the appropriate components.  
     * 
     * @param url
     * @param rb
     */
    @Override
    @SuppressWarnings("unchecked") // permissable because is used to avoid the type safety warning when adding columns to the TableView
    public void initialize(URL url, ResourceBundle rb) {
        // initialize a drop shadow and set the attributes to achieve the desired shadow effect on the TableView
        DropShadow dsTableView = new DropShadow();
        dsTableView.setRadius(5.0);
        dsTableView.setOffsetX(3.0);
        dsTableView.setOffsetY(3.0);
        dsTableView.setColor(Color.color(0.0, 0.0, 0.0));
        dsTableView.setBlurType(BlurType.GAUSSIAN);

        // set the effects defined above to the appropriate components
        resultsTableView.setEffect(dsTableView);

        // initialize the columns within the results table
        TableColumn<Asset, Integer> columnAssetNumber = new TableColumn<Asset, Integer>("Asset Number");
        TableColumn<Asset, String> columnDeviceType = new TableColumn<Asset, String>("Device Type");
        TableColumn<Asset, String> columnSerialNumber = new TableColumn<Asset, String>("Serial Number");
        TableColumn<Asset, String> columnOwnerName = new TableColumn<Asset, String>("Owner Name");
        TableColumn<Asset, String> columnLocation = new TableColumn<Asset, String>("Location");
        TableColumn<Asset, String> columnDateAdded = new TableColumn<Asset, String>("Date Added");

        // initialize the observalbe list of assets to be used to store the data from the result set
        ObservableList<Asset> assetData = FXCollections.observableArrayList();
  
        // set the column's mininum width to best display the data. Future iterations should improve on this by dynamically adjusting to data size. 
        resultsTableView.setMinWidth(1090);
        resultsTableView.setMinHeight(500);
        columnAssetNumber.setMinWidth(100);
        columnDeviceType.setMinWidth(300);
        columnSerialNumber.setMinWidth(150);
        columnOwnerName.setMinWidth(300);
        columnLocation.setMinWidth(100);
        columnDateAdded.setMinWidth(125);

        // add the intialized columns to the TableView
        resultsTableView.getColumns().addAll(columnAssetNumber, columnDeviceType, columnSerialNumber, columnOwnerName, columnLocation, columnDateAdded);

        // query database for data on all assets with currently within the database
        try {
            assetData = App.sqlQueries.getAllAssets();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // define which members of the Asset object correspond to which columns in the TableView (associate data with columns)
        columnAssetNumber.setCellValueFactory(new PropertyValueFactory<Asset, Integer>("assetNumber"));
        columnDeviceType.setCellValueFactory(new PropertyValueFactory<Asset, String>("deviceType"));
        columnSerialNumber.setCellValueFactory(new PropertyValueFactory<Asset, String>("serialNumber"));
        columnOwnerName.setCellValueFactory(new PropertyValueFactory<Asset, String>("ownerName"));
        columnLocation.setCellValueFactory(new PropertyValueFactory<Asset, String>("location"));
        columnDateAdded.setCellValueFactory(new PropertyValueFactory<Asset, String>("dateAdded"));

        // add the data to the table for display
        resultsTableView.setItems(assetData);
    }

    /** method that is used to take the user's view back to the main "primary" scene
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

}