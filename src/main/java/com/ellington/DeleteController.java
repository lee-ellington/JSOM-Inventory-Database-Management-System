// project package
package com.ellington;
// import statemetns
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow; 
import javafx.scene.paint.Color;
import javafx.scene.effect.BlurType; 
import java.net.URL;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;

/** This class is utilized to control what happens on the delete.fxml scene of the application. The user is allowed to enter an asset number of the asset
 *  they wish to delete from the database. If the asset exists, it is deleted, otherwise the user is informed that the asset is not on the database. 
 */
public class DeleteController implements Initializable {
    // fxml variable links
    @FXML TextField assetNumberTextField_delete;
    @FXML Label userNotificationLabel_delete;
    // data member
    private boolean assetDeleted; // variable used to store whether or not the asset was deleted successfully

    /** This method is called as soon as controller is called. Is used to initialize results table columns and load the appropriate data in to it. 
    *   Additionally, drop shadow effects are defined and applied to the FXML linked TextField and Label.
    *
    * @param url
    * @param rb
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // initialize a drop shadow and set the attributes to achieve the desired text shadow effect on the Label
        DropShadow dsLabel = new DropShadow();
        dsLabel.setRadius(5.0);
        dsLabel.setOffsetX(3.0);
        dsLabel.setOffsetY(3.0);
        dsLabel.setColor(Color.color(0.0, 0.0, 0.0));
        dsLabel.setBlurType(BlurType.GAUSSIAN);

        // initialize a drop shadow and set the attributes to achieve the desired shadow effect on the TextView
        DropShadow dsTextField = new DropShadow();
        dsTextField.setRadius(2.2);
        dsTextField.setOffsetX(2.2);
        dsTextField.setOffsetY(2.2);
        dsTextField.setColor(Color.color(0.1, 0.1, 0.1));
        dsTextField.setBlurType(BlurType.GAUSSIAN);

        // apply the effects defined above to the appropriate components
        userNotificationLabel_delete.setEffect(dsLabel);
        assetNumberTextField_delete.setEffect(dsTextField);
    }

    /** This method switches the scene from the current back to the primary, which is the main menu of the application. 
     * 
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    /** This method is used to delete an asset from the inventory database. It does this through taking the asset number in from the user and then calling the deleteAsset()
     *  method from SQLQueries. The user is notified accordingly for both success and failure to delete the asset. 
     * 
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void deleteAsset() throws IOException, SQLException {
        try {
            // if the user did not enter anything in to the text field, display an error message
            if (assetNumberTextField_delete.getText().isEmpty()) {
                userNotificationLabel_delete.setText("Please enter a valid asset number.");
            } else {
                // update the workingAsset Object with user inputted data
                App.workingAsset.setAssetNumber(Integer.parseInt(assetNumberTextField_delete.getText()));
                
                // make the call to the delete method and pass in the workingAsset object
                assetDeleted = App.sqlQueries.deleteAsset(App.workingAsset);

                // if the asset was deleted successfully, inform the user
                if (assetDeleted) {
                    userNotificationLabel_delete.setText("The asset #" + App.workingAsset.getAssetNumber() + " was deleted from the inventory database successfully.");
                } else { // otherwise, let the user know that the asset was not found in the database
                    userNotificationLabel_delete.setText("The asset #" + App.workingAsset.getAssetNumber() + " was not found in the inventory database. Please try again.");
                }
            }
        } catch (Exception e) { // catches exception if asset number is too big of an integer to parse as well as any other unanticipated exceptions thrown
            userNotificationLabel_delete.setText("Please enter a valid asset number.");
        }
    }
}