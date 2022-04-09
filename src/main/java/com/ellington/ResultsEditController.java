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
import java.sql.SQLException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label; 
import javafx.scene.control.TextField;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.scene.effect.DropShadow; 
import javafx.scene.paint.Color;
import javafx.scene.effect.BlurType; 

/** This class is used to control what happens in the results_edit.fxml scene. Namely, the asset information of the asset searched for by the user is displayed
 *  the user then enteres into the TextFields the updates they wish to make to the asset. The edits are applied when the user clicks the execute edit button.
 *  the results_edit scene is reloaded after to display the newly updated asset to the user. 
 */
public class ResultsEditController implements Initializable {
    // establish link with FXML objects under control of this controller
    @FXML TableView<Asset> resultsTableView;
    @FXML Label editErrorLabel;
    @FXML TextField deviceTypeTextField_edit;
    @FXML TextField serialNumberTextField_edit;
    @FXML TextField ownerNameTextField_edit;
    @FXML TextField locationTextField_edit;
    @FXML TextField dateAddedTextField_edit;
    @FXML Label userNotificationLabel_edit;
    // data members
    private boolean assetFound = false; // variable used to determine if the user specified asset exists in the database
    private boolean assetEdited = false; // variable used to determine if the asset has been edited successfully

    /** This method is called as soon as controller is initialized. Is used to initialize the results table columns and load the appropriate data in to it. 
     *  Additionally, DropShadow effects are defined and assigned to the appropriate FXML linked components.
     * 
     * @param url
     * @param rb
     */
    @Override
    @SuppressWarnings("unchecked") // permissable because is used to avoid the type safety warning when adding columns to the TableView
    public void initialize(URL url, ResourceBundle rb) {
        // initialize a drop shadow and set the attributes to achieve the desired text shadow effect on the Label
        DropShadow dsLabel = new DropShadow();
        dsLabel.setRadius(5.0);
        dsLabel.setOffsetX(3.0);
        dsLabel.setOffsetY(3.0);
        dsLabel.setColor(Color.color(0.0, 0.0, 0.0));
        dsLabel.setBlurType(BlurType.GAUSSIAN);

        // initialize a drop shadow and set the attributes to achieve the desired shadow effect on the TableView
        DropShadow dsTableView = new DropShadow();
        dsTableView.setRadius(5.0);
        dsTableView.setOffsetX(3.0);
        dsTableView.setOffsetY(3.0);
        dsTableView.setColor(Color.color(0.0, 0.0, 0.0));
        dsTableView.setBlurType(BlurType.GAUSSIAN);

        // initialize a drop shadow and set the attributes to achieve the desired shadow effect on the TextField
        DropShadow dsTextField = new DropShadow();
        dsTextField.setRadius(2.2);
        dsTextField.setOffsetX(2.2);
        dsTextField.setOffsetY(2.2);
        dsTextField.setColor(Color.color(0.1, 0.1, 0.1));
        dsTextField.setBlurType(BlurType.GAUSSIAN);

        // apply the effects defined above to the appropriate components
        userNotificationLabel_edit.setEffect(dsLabel);
        resultsTableView.setEffect(dsTableView);
        deviceTypeTextField_edit.setEffect(dsTextField);;
        serialNumberTextField_edit.setEffect(dsTextField);;
        ownerNameTextField_edit.setEffect(dsTextField);;
        locationTextField_edit.setEffect(dsTextField);;
        dateAddedTextField_edit.setEffect(dsTextField);;

        // initialize the columns within the results table
        TableColumn<Asset, Integer> columnAssetNumber = new TableColumn<Asset, Integer>("Asset Number");
        TableColumn<Asset, String> columnDeviceType = new TableColumn<Asset, String>("Device Type");
        TableColumn<Asset, String> columnSerialNumber = new TableColumn<Asset, String>("Serial Number");
        TableColumn<Asset, String> columnOwnerName = new TableColumn<Asset, String>("Owner Name");
        TableColumn<Asset, String> columnLocation = new TableColumn<Asset, String>("Location");
        TableColumn<Asset, String> columnDateAdded = new TableColumn<Asset, String>("Date Added");
  
        // adjust the height property of the TableView to show only one row (only one asset can be found via a search as implemented)
        resultsTableView.setMaxHeight(75);

        // set the minimum width of the colums to best fit the data being displayed. To improve this in the future have it dynamically size to the data size.
        columnAssetNumber.setMinWidth(100);
        columnDeviceType.setMinWidth(300);
        columnSerialNumber.setMinWidth(150);
        columnOwnerName.setMinWidth(300);
        columnLocation.setMinWidth(100);
        columnDateAdded.setMinWidth(125);

        // add the intialized columns to the TableView
        resultsTableView.getColumns().addAll(columnAssetNumber, columnDeviceType, columnSerialNumber, columnOwnerName, columnLocation, columnDateAdded);

        // query database for data on asset with entered asset number and store the success results in the assetFound variable
        try {
            assetFound = App.sqlQueries.searchDatabase_assetNumber(App.workingAsset.getAssetNumber());

            // if the asset was found proceed with adding the data to the TableView to display to the user
            if (assetFound) {
                // add asset data (retrieve from database) to the observable list for display
                final ObservableList<Asset> assetData = FXCollections.observableArrayList(App.workingAsset);

                // define which members of the Asset object correspond to which columns in the TableView (associate data with columns)
                columnAssetNumber.setCellValueFactory(new PropertyValueFactory<Asset, Integer>("assetNumber"));
                columnDeviceType.setCellValueFactory(new PropertyValueFactory<Asset, String>("deviceType"));
                columnSerialNumber.setCellValueFactory(new PropertyValueFactory<Asset, String>("serialNumber"));
                columnOwnerName.setCellValueFactory(new PropertyValueFactory<Asset, String>("ownerName"));
                columnLocation.setCellValueFactory(new PropertyValueFactory<Asset, String>("location"));
                columnDateAdded.setCellValueFactory(new PropertyValueFactory<Asset, String>("dateAdded"));

                // add the data to the table for display
                resultsTableView.setItems(assetData);
            } else { // otherwise, display an appropriate error message to the user
                editErrorLabel.setText("The asset (#" + App.workingAsset.getAssetNumber() + ") was not found in the inventory database. Please try again.");
            }
        } catch (Exception e) {
            editErrorLabel.setText("Error: " + e.getMessage() + ". Please try again.");
        }
    }

    /** Method that is used to take the user's view back to the main "primary" scene.
     * 
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    /** TRhis method is utilized to edit an asset that has already been found to exist within the inventory database. 
     * 
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void editAsset() throws IOException, SQLException {
        // local variable
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");   // used to specify the format the date needs to be in
        format.setLenient(false); // the format must be followed exactly to be sent to the database

        try {
            // add user inputted data to the workingAsset object
            App.workingAsset.setDeviceType(deviceTypeTextField_edit.getText());
            App.workingAsset.setSerialNumber(serialNumberTextField_edit.getText());
            App.workingAsset.setOwnerName(ownerNameTextField_edit.getText());
            App.workingAsset.setLocation(locationTextField_edit.getText());
            App.workingAsset.setDateAdded(dateAddedTextField_edit.getText());

            // database schema input validation - strings
            if (App.workingAsset.getDeviceType().length() >= 60) {
                throw new Exception("Asset device type length must be less than 60 characters.");
            }
            if (App.workingAsset.getSerialNumber().length() >= 40) {
                throw new Exception("Asset serial number length must be less than 40 characters.");
            }
            if (App.workingAsset.getOwnerName().length() >= 50) {
                throw new Exception("Asset owner name length must be less than 50 characters.");
            }
            if (App.workingAsset.getLocation().length() >= 10) {
                throw new Exception("Asset location length must be less than 10 characters.");
            }

            // check to make sure the date entered follows the correct format (throws exception that is caught if it does not)
            format.parse(App.workingAsset.getDateAdded());

            // pass the workingAsset object to the editAsset() method to attempt to edit the asset in the database
            assetEdited = App.sqlQueries.editAsset(App.workingAsset);

            // if the asset was edited successfuly, let the user know
            if (assetEdited){
                userNotificationLabel_edit.setText("Asset #" + App.workingAsset.getAssetNumber() + " was edited successfully.");
            } else { // otherwise, inform the user of the failure to edit the asset in question 
                userNotificationLabel_edit.setText("Unable to edit the asset, please try again.");
            }        

            // reload the scene with the updated asset info so that the user can check the changes they made to the asset within the database
            App.setRoot("results_edit");
            
        } catch(NumberFormatException e) {
            userNotificationLabel_edit.setText("Error: Please enter a valid asset number.");
        } catch (ParseException e) {
            userNotificationLabel_edit.setText("Error: " + e.getMessage() + " Please enter date with format YYYY-MM-DD.");
        } catch (Exception e) {
            userNotificationLabel_edit.setText("Error: " + e.getMessage() + " Please try again.");
        }
    }

}