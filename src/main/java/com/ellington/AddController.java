// project package
package com.ellington;
//import statements
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.scene.effect.DropShadow; 
import javafx.scene.paint.Color;
import javafx.scene.effect.BlurType; 
import java.net.URL;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;

/** Controller class used to control the add.fxml scene where assets are added to the inventory database. 
 */
public class AddController implements Initializable {
    // FXML linked variables
    @FXML TextField assetNumberTextField_add;
    @FXML TextField deviceTypeTextField_add;
    @FXML TextField serialNumberTextField_add;
    @FXML TextField serviceTagTextField_add;
    @FXML TextField ownerNameTextField_add;
    @FXML TextField locationTextField_add;
    @FXML TextField dateAddedTextField_add;
    @FXML Label userNotificationLabel_add;
    // data member
    private boolean assetAdded = false;

    /** This method is called as soon as controller is called. Is used to initialize the results table columns and load the appropriate data in to it. 
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // initialize a drop shadow and set the attributes to achieve the desired text shadow effect on the label
        DropShadow dsLabel = new DropShadow();
        dsLabel.setRadius(5.0);
        dsLabel.setOffsetX(3.0);
        dsLabel.setOffsetY(3.0);
        dsLabel.setColor(Color.color(0.0, 0.0, 0.0));
        dsLabel.setBlurType(BlurType.GAUSSIAN);

        // initialize a drop shadow and set the attributes to achieve the desired shadow effect on the TextFields
        DropShadow dsTextField = new DropShadow();
        dsTextField.setRadius(2.2);
        dsTextField.setOffsetX(2.2);
        dsTextField.setOffsetY(2.2);
        dsTextField.setColor(Color.color(0.1, 0.1, 0.1));
        dsTextField.setBlurType(BlurType.GAUSSIAN);

        // assign the defined effects above to the appropriate fxml linked components
        assetNumberTextField_add.setEffect(dsTextField);
        deviceTypeTextField_add.setEffect(dsTextField);
        serialNumberTextField_add.setEffect(dsTextField);
        serviceTagTextField_add.setEffect(dsTextField);
        ownerNameTextField_add.setEffect(dsTextField);
        locationTextField_add.setEffect(dsTextField);
        dateAddedTextField_add.setEffect(dsTextField);
        userNotificationLabel_add.setEffect(dsLabel);
    }

    /** This method switches the scene from the current back to the primary, which is the main menu of the application. 
     * 
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    /** This method adds a new asset to the inventory database system. The workingAsset object, initialized in the App main method, is utilized to make an addAsset
     *  SQL query to the database. 
     * 
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void add() throws IOException, SQLException {
        // local variable
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");   // used to specify the format the date needs to be in
        format.setLenient(false); // the format must be followed exactly to be sent to the database

        try {
            // add user inputted data to the workingAsset object
            App.workingAsset.setAssetNumber(Integer.parseInt(assetNumberTextField_add.getText())); // will throw integer parsing exception if input too long
            App.workingAsset.setDeviceDescription(deviceTypeTextField_add.getText());
            App.workingAsset.setSerialNumber(serialNumberTextField_add.getText());
            App.workingAsset.setServiceTag(serviceTagTextField_add.getText());
            App.workingAsset.setOwnerName(ownerNameTextField_add.getText());
            App.workingAsset.setLocation(locationTextField_add.getText());
            App.workingAsset.setDateAdded(dateAddedTextField_add.getText());

            // database schema input validation - strings
            if (App.workingAsset.getSerialNumber().length() >= 40) {
                throw new Exception("Asset serial number length must be less than 45 characters.");
            }
            if (App.workingAsset.getServiceTag().length() >= 45) {
                throw new Exception("Asset service tag length must be less than 45 characters.");
            }
            if (App.workingAsset.getDeviceDescription().length() >= 100) {
                throw new Exception("Asset device description length must be less than 100 characters.");
            }
            if (App.workingAsset.getLocation().length() >= 45) {
                throw new Exception("Asset location length must be less than 45 characters.");
            }
            if (App.workingAsset.getOwnerName().length() >= 100) {
                throw new Exception("Asset owner name length must be less than 100 characters.");
            }

            // check to make sure the date entered follows the correct format (throws exception that is caught if it does not)
            format.parse(App.workingAsset.getDateAdded());

            // pass the workingAsset object to the addAsset() method to attempt to add the asset to the database
            assetAdded = App.sqlQueries.addAsset(App.workingAsset);

            // if the asset was added successfully, display appropriate success message to the user. 
            if (assetAdded){
                userNotificationLabel_add.setText("Asset #" + App.workingAsset.getAssetNumber() + " added to the inventory database successfully.");

                // reset text fields and workingAsset so that the user can enter another asset after a sucessful add
                assetNumberTextField_add.setText("");
                deviceTypeTextField_add.setText("");
                serialNumberTextField_add.setText("");
                serviceTagTextField_add.setText("");
                ownerNameTextField_add.setText("");
                locationTextField_add.setText("");
                dateAddedTextField_add.setText("");
                App.workingAsset.setAssetNumber(-1);
                App.workingAsset.setDeviceDescription("");
                App.workingAsset.setSerialNumber("");
                App.workingAsset.setOwnerName("");
                App.workingAsset.setLocation("");
                App.workingAsset.setDateAdded("");
            } else { // otherwise, let the user know that the asset failed to be added to the database
                userNotificationLabel_add.setText("Unable to add asset to the inventory database, please try again.");
            }
        } catch(NumberFormatException e) {
            userNotificationLabel_add.setText("Error: Please enter a valid asset number.");
        } catch (ParseException e) {
            userNotificationLabel_add.setText("Error: " + e.getMessage() + " Please enter date with format YYYY-MM-DD.");
        } catch (Exception e) {
            userNotificationLabel_add.setText("Error: " + e.getMessage() + " Please try again.");
        }
    }

}