// project package
package com.ellington;
// import statements
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.net.URL;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.scene.effect.DropShadow; 
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.effect.BlurType; 

/** This class is used to control what happens within the search.fxml scene. Namely, the user input for asset number to be searched for is taken and added to the
 *  workingAsset object. The supplementary results scene is then loaded to display the results to the user. 
 */
public class SearchController implements Initializable{
    // FXML linked variables
    @FXML TextField assetNumberTextField_search;
    @FXML Label searchErrorLabel;

    /** Override of the initialize method to have control over the image view, image, and Hbox components within the primary.fxml.
     *  Additionally, DropShadow effects are defined and assigned to the appropriate FXML linked components. 
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

        // initialize a drop shadow and set the attributes to achieve the desired shadow effect on the TextField
        DropShadow dsTextField = new DropShadow();
        dsTextField.setRadius(2.2);
        dsTextField.setOffsetX(2.2);
        dsTextField.setOffsetY(2.2);
        dsTextField.setColor(Color.color(0.1, 0.1, 0.1));
        dsTextField.setBlurType(BlurType.GAUSSIAN);

        // set the defined effects above to the appropriate FXML linked components
        searchErrorLabel.setEffect(dsLabel);
        assetNumberTextField_search.setEffect(dsTextField);
    }

    /** This method is used to switch back to the primary (main) scene of the application.
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
    private void search() throws IOException {
        try { 
            // if the user did not enter anything in to the text field, display an error message
            if (assetNumberTextField_search.getText().isEmpty()) {
                searchErrorLabel.setText("Please enter a valid asset number.");
            } else { // otherwise, set the workingAsset's asset number to the user input and change to the results scene
                App.workingAsset.setAssetNumber(Integer.parseInt(assetNumberTextField_search.getText()));
                App.setRoot("results");
            }
        } catch (Exception e) { // catches exception if asset number is too big of an integer to parse as well as any other exception thrown
            searchErrorLabel.setText("Please enter a valid asset number." +e.getMessage());
        }
    }
    
}