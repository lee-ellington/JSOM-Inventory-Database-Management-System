// project package
package com.ellington;
// import statements
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/** Driving class of the JavaFX JSOM Inventory Database Management application. This class initializes and loads the initial scene to the user. It
 *  also defines two utility methods used throughouht the application to load appropriate scenes. 
 */
public class App extends Application {
    // data members
    private static Scene scene; // variable used to set scene(s)
    private static String title_window = "JSOM Inventory Database Management System"; //variable to hold title of application window
    Image icon = new Image(getClass().getResourceAsStream("emblem-blk.png"));  // initialization of variable that holds application window icon
    public static Asset workingAsset; // variable used to initialize the Asset object used for data passing
    public static SQLQueries sqlQueries; // variable used to initialize the SQLQueries object used for method calls for SQL query executions

    /** This method is utilized to set the initial scene, set the title of the application window, set the application icon,
     *  and show the stage once the scene has been set. 
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 1366, 768);  // load the starting (primary) scene of the application
        stage.setScene(scene);  // set the scene to the stage
        stage.setTitle(title_window); // set applicaiton window title
        stage.getIcons().add(icon); // apply application window icon
        stage.show(); // display the stage with the set scene
    }

    /** This method takes in an fxml file name and uses that to load the FXML and set it as the currently displayed scene. 
     * 
     * @param fxml
     * @throws IOException
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /** This method takes in an fxml file name and uses it to initialize the FXMLLoader with the passed fxml file name and then 
     *  retrurns a calls to the load() method on that fxml loader.
     * 
     * @param fxml
     * @return
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml")); // 
        return fxmlLoader.load();
    }

    /** Main method of the application. The workingAsset Asset and sqlQueries SQLQueries objects are initialized so they are accessible by other classes for use
     * throughout the execution of the application. 
     * @param args
     */
    public static void main(String[] args) {
        workingAsset = new Asset(); // Asset object initialization - persistant object used for data management throughout the execution
        sqlQueries = new SQLQueries(); // SQLQueries object initialization - persistant object used to make SQL query method calls
        launch(); // launch the application window
    }

}