// project package
package com.ellington;
// import statements
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/** Simple Asset class to encapsulate asset data.
 */
public class Asset {
    // private data members of class 
    private SimpleIntegerProperty assetNumber;
    private SimpleStringProperty deviceType;
    private SimpleStringProperty serialNumber; 
    private SimpleStringProperty ownerName;
    private SimpleStringProperty location;
    private SimpleStringProperty dateAdded;

    // default constructor
    public Asset () {
        this(0, "", "", "", "", "");
    }

    // parameterized constructor
    public Asset (int initialAssetNumber, String initialDeviceType, String initialSerialNumber, String initialOwnerName, String initialLocation, String initialDateAdded) {
        this.assetNumber = new SimpleIntegerProperty(initialAssetNumber);
        this.deviceType = new SimpleStringProperty(initialDeviceType);
        this.serialNumber = new SimpleStringProperty(initialSerialNumber);
        this.ownerName = new SimpleStringProperty(initialOwnerName);
        this.location = new SimpleStringProperty(initialLocation);
        this.dateAdded = new SimpleStringProperty(initialDateAdded);
    }

    // GET methods for data members
    public int getAssetNumber() {
        return assetNumber.get();
    }

    public String getDeviceType() {
        return deviceType.get();
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public String getOwnerName() {
        return ownerName.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getDateAdded() {
        return dateAdded.get();
    }

    // SET methods for data members
    public void setAssetNumber(int enteredAssetNumber) {
        assetNumber.set(enteredAssetNumber);
    }

    public void setDeviceType(String enteredDevice) {
        deviceType.set(enteredDevice);
    }

    public void setSerialNumber(String enteredSerialNumber) {
        serialNumber.set(enteredSerialNumber);
    }

    public void setOwnerName(String enteredOwnerName) {
        ownerName.set(enteredOwnerName);
    }

    public void setLocation(String enteredLocation) {
        location.set(enteredLocation);
    }

    public void setDateAdded(String dateEntered) {
        dateAdded.set(dateEntered);
    }
}