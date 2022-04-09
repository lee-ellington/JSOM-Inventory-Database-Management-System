// project package
package com.ellington;
// import statements
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow; 
import javafx.scene.paint.Color;
import javafx.scene.effect.BlurType; 
import java.net.URL;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;

/** This class is used to control what happens within the edit.fxml scene. User input for the asset number of an asset to be edited is taken in.
 *  The workingAsset object is updated with this information and then the suplementary edit page is loaded if a corresponding asset is found. 
 */
public class EditController implements Initializable {
    // FXML linked variables
    @FXML TextField assetNumberTextField_edit;
    @FXML Label searchErrorLabel_edit;

    /** This method is called as soon as controller is called. It is used to initialize results table columns and load the appropriate data in to it. 
    *   Additionally, DropShadow effects are defined and assigned to the appropriate components.  
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

        // set the defined effects above to the appropriate FXML linked components
        searchErrorLabel_edit.setEffect(dsLabel);
        assetNumberTextField_edit.setEffect(dsTextField);
    }

    /** This method switches the scene from the current back to the primary, which is the main menu of the application. 
     * 
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    /** This method is used to confirm that an asset exists so that it can be edited. 
     * 
     * @throws IOException
     */
    @FXML
    private void confirmAsset_edit() throws IOException {
        try {
            // if the user did not enter anything in to the text field, display an error message
            if (assetNumberTextField_edit.getText().isEmpty()) {
                searchErrorLabel_edit.setText("Please enter a valid asset number.");
            } else { // otherwise, set the workingAsset's asset number to the user input and change to the results scene
                // get user input and store it in the workingAsset's asset number field
                App.workingAsset.setAssetNumber(Integer.parseInt(assetNumberTextField_edit.getText()));
                //go to asset edit suplementary page
                App.setRoot("results_edit");
            }
        } catch (Exception e) { // catches exception if asset number is too big of an integer to parse as well as any other exception thrown
            searchErrorLabel_edit.setText("Please enter a valid asset number.");
        }
    }
    
}