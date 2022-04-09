// project package
package com.ellington;
// import statements
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import java.net.URL;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.effect.DropShadow; 
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.effect.BlurType; 

/** This class is used to control the primary.fxml scene. Since it is the main splash page of the application, 
 *  its function is to switch to the appropriate scene based off of the user's selection. Furthermore, it also 
 *  defines DropShadow effect to be applied to the the primary Label.
 */
public class PrimaryController implements Initializable {
    // FXML connections needed for control
    @FXML ImageView logoImageView;
    @FXML HBox imageBox;
    @FXML Label primaryLabel;

    /** Override of the initialize method to have control over the image view, image, Label, and Hbox components within the primary.fxml.\
     * 
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
        primaryLabel.setEffect(dsLabel);

        // create an image object and define its attributes, then add it to the HBox
        Image logo = new Image(getClass().getResourceAsStream("monogram circle-rgb.png")); // create new image object that holds the monogram logo
        logoImageView = new ImageView(logo); // add the image to the linked fxml imageview
        logoImageView.setFitHeight(300); // set the height and width of the image
        logoImageView.setFitWidth(300); 
        logoImageView.setPreserveRatio(true); // make sure the ration is preserved so the image is not distorted
        imageBox.getChildren().add(logoImageView); // add the imageview, with the image inside of it, to the Hbox within the gridpane
    }

    /** This method switches the current scene to the search scene.
     * 
     * @throws IOException
     */
    @FXML
    private void switchToSearch() throws IOException {
        App.setRoot("search");
    }

    /** This method switches the current scene to the add scene.
     * 
     * @throws IOException
     */
    @FXML
    private void switchToAdd() throws IOException {
        App.setRoot("add");
    }

    /** This method switches the current scene to the edit scene.
     * 
     * @throws IOException
     */
    @FXML
    private void switchToEdit() throws IOException {
        App.setRoot("edit");
    }

    /** This method switches the current scene to the delete scene.
     * 
     * @throws IOException
     */
    @FXML
    private void switchToDelete() throws IOException {
        App.setRoot("delete");
    }

    /** This method switches the current scene to the display_all scene.
     * 
     * @throws IOException
     */
    @FXML
    private void switchToDisplayAll() throws IOException {
        App.setRoot("display_all");
    }
}
