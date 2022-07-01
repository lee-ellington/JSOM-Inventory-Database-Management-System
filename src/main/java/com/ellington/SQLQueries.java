// project package
package com.ellington;
// import statements
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/** This class contains methods that allow for connection to and queries of a hardcoded url MySQL database.
 *  Future improvements could let the user provide login credentials. 
 */
public class SQLQueries {
    // data members to define database connection URL and user credentials
    private static final String DATABASE_URL = "jdbc:mysql://10.182.131.75:3306/asset_inventory_database";
    private static final String DATABASE_NAME = "asset_inventory_database";
    private static final String DATABASE_TABLE_NAME = "inventory";
    private static final String DATABASE_USERNAME = "assetAdmin";
    private static final String DATABASE_PASSWORD = "greenlantern";

    /** This method takes in a number (asset number entered by user) and uses that to search the MySQL database for a matching asset.
     *  When found, the result set is used to add the data to the workingAsset object.
     * 
     * @param number
     * @throws SQLException
     */
    public boolean searchDatabase_assetNumber(int number) throws SQLException {
        boolean assetFound = false; // local variable to designate whether the asset had been found successfully or not

        // try to establish a connection with the inventory database
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM " + DATABASE_NAME + "." + DATABASE_TABLE_NAME + " WHERE assetNumber = '" + number + "';")) {

                ResultSet resultSet = preparedStatement.executeQuery(); // execute the query and store the results

                // if there is nothing in the returned result set, let the user know that the asset is not in the database
                if (resultSet.next() == false) {
                    // assetFound value set to false by default, no action needed here
                } else { // otherwise, add asset data to the workingAsset
                    assetFound = true; // set assetFound to true to designate the asset was found within the database
                     do {
                         // since search is by asset number, the database's primary key, only one asset can ever be returned
                        App.workingAsset.setAssetNumber(resultSet.getInt("assetNumber"));
                        App.workingAsset.setSerialNumber(resultSet.getString("serialNumber"));
                        App.workingAsset.setServiceTag(resultSet.getString("serviceTag"));
                        App.workingAsset.setDeviceDescription(resultSet.getString("description"));
                        App.workingAsset.setLocation(resultSet.getString("location"));
                        App.workingAsset.setOwnerName(resultSet.getString("user"));
                        App.workingAsset.setDateAdded(resultSet.getString("date"));
                    } while (resultSet.next());
                }
        } 
        return assetFound;
    }

    /** This method uses the data in the passed assetToAdd object to add an asset to the MySQL database.
     * 
     * @throws SQLException
     */
    public boolean addAsset(Asset assetToAdd) throws SQLException {
        // local variables
        boolean assetAdded = false; // used to track the success of adding the asset to the database
        int updateExecuted; // used to determine the success of the SQL query's execution

        // try to establish a connection with the inventory database
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO " + DATABASE_NAME + "." + DATABASE_TABLE_NAME + " (assetNumber, serialNumber, serviceTag, Description, Location, User, Date)" +
                    "values(" + assetToAdd.getAssetNumber() + ", '" + assetToAdd.getSerialNumber() + "', '" + assetToAdd.getServiceTag() + 
                    "', '" + assetToAdd.getDeviceDescription() + "', '" + assetToAdd.getLocation() + "', '" + assetToAdd.getOwnerName() + "', '" + assetToAdd.getDateAdded() + "');")) {
    
            updateExecuted = preparedStatement.executeUpdate(); // execute the insert into query to add the asset to the database
            
            // if the insert into statement was successfuly executed, set the boolean flag assetAdded to true
            if (updateExecuted > 0) {
                assetAdded = true;
            }           
        }
        return assetAdded;
    }

    /** This method uses the asset number from the passed in Asset object to delete an asset entry from the inventory database.
     * 
     * @param assetToDelete
     * @return
     * @throws SQLException
     */
    public boolean deleteAsset(Asset assetToDelete) throws SQLException {
        // local variables
        boolean assetDeleted = false; // used to track the success of deleting the asset from the database
        int deleteExecuted; //used to determine the success of the SQL query's execution

        // try to establish a connection with the inventory database
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM " + DATABASE_NAME + "." + DATABASE_TABLE_NAME + " WHERE assetNumber = " + assetToDelete.getAssetNumber() + ";")) {
    
            deleteExecuted = preparedStatement.executeUpdate(); // execute the asset delete query to delete the user specified asset in the database

            // if the delete statement was succesfully executed, set the boolean flag assetDeleted to true 
            if (deleteExecuted > 0) {
                assetDeleted = true;
            }           
        }
        return assetDeleted;
    }

    /** This method uses the data from the passed in Asset object to update the inventory database entry for the user specified asset. 
     * 
     * @param assetToEdit
     * @return
     * @throws SQLException
     */
    public boolean editAsset(Asset assetToEdit) throws SQLException {
        // local variables
        boolean assetEdited = false; // used to track the success of editing the asset in the database
        int editExecuted; // used to determine the success of the SQL query's execution

        // try to establish a connection with the inventory database
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE " + DATABASE_NAME + "." + DATABASE_TABLE_NAME + " SET serialNumber = '" + assetToEdit.getSerialNumber() + "', serviceTag = '" + assetToEdit.getServiceTag() + 
                "', description = '" + assetToEdit.getDeviceDescription()+ "', location = '" + assetToEdit.getLocation() + "', user = '" + assetToEdit.getOwnerName() +
                "', date = '" + assetToEdit.getDateAdded() + 
                "'WHERE assetNumber = '" + assetToEdit.getAssetNumber() + "';")) {
    
            editExecuted = preparedStatement.executeUpdate(); // execute the asset edit query to edit the user specified asset in the database

            // if the edit statement was succesfully executed, set the boolean flag assetEdited to true 
            if (editExecuted > 0) {
                assetEdited = true;
            }           
        }
        return assetEdited;
    }
    
    /** This method is utilized to display all the current assets within the inventory database. The assets are stored in and returned as a 
     *  ObservableList of Assets. 
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public  ObservableList<Asset> getAllAssets() throws SQLException, ClassNotFoundException {
        // local variable
        ObservableList<Asset> dbData = FXCollections.observableArrayList(); // initialize the observable list that will be used to store the assets from the result set

        // try to establish a connection with the inventory database
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM " + DATABASE_NAME + "." + DATABASE_TABLE_NAME + ";")) {

                ResultSet resultSet = preparedStatement.executeQuery(); // execute the query and store the results
                
                // add entries from the result set to the observable list of assets
                while (resultSet.next()) {
                    Asset assetEntry = new Asset();
                    assetEntry.setAssetNumber(resultSet.getInt("assetNumber"));
                    assetEntry.setSerialNumber(resultSet.getString("serialNumber"));
                    assetEntry.setServiceTag(resultSet.getString("serviceTag"));
                    assetEntry.setDeviceDescription(resultSet.getString("description"));
                    assetEntry.setLocation(resultSet.getString("location"));
                    assetEntry.setOwnerName(resultSet.getString("user"));
                    assetEntry.setDateAdded(resultSet.getString("date"));
                    
                    //Add asset to the ObservableList
                    dbData.add(assetEntry);
                }
        } 
    return dbData;
    }
    
}