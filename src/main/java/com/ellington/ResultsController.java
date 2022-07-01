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
import javafx.scene.control.Label; 
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow; 
import javafx.scene.paint.Color;
import javafx.scene.effect.BlurType; 

/** This class is used to control what happens on the results.fxml scene. Namely, an asset number is entered by the user and this class searches the
 *  inventory database for an entry with that asset number and then displays the results to the user. If no entry is found the user is informed. 
 */
public class ResultsController implements Initializable  {
    // FXML variable links
    @FXML TableView<Asset> resultsTableView;
    @FXML Label searchErrorLabel;
    @FXML TextField assetNumberTextField_resultSearch;
    // data member
    private boolean assetFound = false; // used to store the results of the asset search query

    /** This method is called as soon as controller is called. Is used to initialize results table columns and load the appropriate data in to it. 
     *  Additionally, DropShadow effects are defined and assigned to the appropriate FXML components. 
     * 
     * @param url
     * @param rb
     */
    @Override
    @SuppressWarnings("unchecked") // permissable because is used to avoid the type safety warning when adding columns to the tableview
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

        // initialize a drop shadow and set the attributes to achieve the desired shadow effect on the TextView
        DropShadow dsTextField = new DropShadow();
        dsTextField.setRadius(2.2);
        dsTextField.setOffsetX(2.2);
        dsTextField.setOffsetY(2.2);
        dsTextField.setColor(Color.color(0.1, 0.1, 0.1));
        dsTextField.setBlurType(BlurType.GAUSSIAN);

        // apply the effects defined above to the appropriate components
        searchErrorLabel.setEffect(dsLabel);
        resultsTableView.setEffect(dsTableView);
        assetNumberTextField_resultSearch.setEffect(dsTextField);

        // initialize the columns within the results table
        TableColumn<Asset, Integer> columnAssetNumber = new TableColumn<Asset, Integer>("Asset Number");
        TableColumn<Asset, String> columnSerialNumber = new TableColumn<Asset, String>("Serial Number");
        TableColumn<Asset, String> columnServiceTag = new TableColumn<Asset, String>("Service Tag");
        TableColumn<Asset, String> columnDeviceDescription = new TableColumn<Asset, String>("Description");
        TableColumn<Asset, String> columnLocation = new TableColumn<Asset, String>("Location");
        TableColumn<Asset, String> columnOwnerName = new TableColumn<Asset, String>("User");
        TableColumn<Asset, String> columnDateAdded = new TableColumn<Asset, String>("Date");
  
        // adjust the height property of the TableView to show only one row (only one asset can be found via the search functionality as implemented)
        resultsTableView.setMaxHeight(75);

        // set the minimum widths of the table columns to best show the data that is to be displayed. Improve in later iterations with dynamic column adjustment.
        columnAssetNumber.setMinWidth(100);
        columnSerialNumber.setMinWidth(150);
        columnServiceTag.setMinWidth(100);
        columnDeviceDescription.setMinWidth(300);
        columnLocation.setMinWidth(100);
        columnOwnerName.setMinWidth(300);
        columnDateAdded.setMinWidth(125);

        // add the intialized columns to the TableView
        resultsTableView.getColumns().addAll(columnAssetNumber, columnSerialNumber, columnServiceTag, columnDeviceDescription, columnLocation, columnOwnerName, columnDateAdded);

        // Query the inventory database for data on asset with entered asset number. assetFound variable stores a boolean that designates the success of the query.
        try {
            assetFound = App.sqlQueries.searchDatabase_assetNumber(App.workingAsset.getAssetNumber());
        } catch (Exception e) {
            searchErrorLabel.setText(e.getMessage());
        }

        // if the asset was found in the inventory database, proceed with adding it's data to the TableView columns
        if (assetFound) {
            // add asset data (retrieve from database) to the observable list for display
            final ObservableList<Asset> assetData = FXCollections.observableArrayList(App.workingAsset);

            // define which members of the Asset object correspond to which columns in the TableView (associate data with columns)
            columnAssetNumber.setCellValueFactory(new PropertyValueFactory<Asset, Integer>("assetNumber"));
            columnSerialNumber.setCellValueFactory(new PropertyValueFactory<Asset, String>("serialNumber"));
            columnServiceTag.setCellValueFactory(new PropertyValueFactory<Asset, String>("serviceTag"));
            columnDeviceDescription.setCellValueFactory(new PropertyValueFactory<Asset, String>("deviceDescription"));
            columnLocation.setCellValueFactory(new PropertyValueFactory<Asset, String>("location"));
            columnOwnerName.setCellValueFactory(new PropertyValueFactory<Asset, String>("ownerName"));
            columnDateAdded.setCellValueFactory(new PropertyValueFactory<Asset, String>("dateAdded"));

            // add the data to the table for display
            resultsTableView.setItems(assetData);
        } else { // otherwise, display an appropriate error message to the user
            searchErrorLabel.setText("The asset (#" + App.workingAsset.getAssetNumber() + ") was not found in the inventory database. Please try again.");
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

    /** This method is used to store the user asset number input within the workingAsset object and load the results suplementary scene.
     * 
     * @throws IOException
     */
    @FXML
    private void resultsSearch() throws IOException {
        try {
            // if the user did not enter anything in to the text field, display an error message
            if (assetNumberTextField_resultSearch.getText().isEmpty()) {
                searchErrorLabel.setText("Please enter a valid asset number.");
            } else { // otherwise, set the workingAsset's asset number to the user input and change to the results scene
                App.workingAsset.setAssetNumber(Integer.parseInt(assetNumberTextField_resultSearch.getText()));
                App.setRoot("results");
            }
        } catch (Exception e) { // catches exception if asset number is too big of an integer to parse as well as any other exception thrown
            searchErrorLabel.setText("Please enter a valid asset number.");
        }
    }

}