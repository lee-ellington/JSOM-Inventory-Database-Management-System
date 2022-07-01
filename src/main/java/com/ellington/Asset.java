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
    private SimpleStringProperty serialNumber;
    private SimpleStringProperty serviceTag;
    private SimpleStringProperty deviceDescription;
    private SimpleStringProperty location;
    private SimpleStringProperty ownerName;
    private SimpleStringProperty dateAdded;

    // default constructor
    public Asset () {
       this(0, "", "", "", "", "", "");
    }

    // parameterized constructor
    public Asset (int initialAssetNumber, String initialSerialNumber, String initialServiceTag, String initialDeviceDescription, String initialLocation, String initialOwnerName,String initialDateAdded) {
        this.assetNumber = new SimpleIntegerProperty(initialAssetNumber);
        this.serialNumber = new SimpleStringProperty(initialSerialNumber);
        this.serviceTag = new SimpleStringProperty(initialServiceTag);
        this.deviceDescription = new SimpleStringProperty(initialDeviceDescription);
        this.location = new SimpleStringProperty(initialLocation);
        this.ownerName = new SimpleStringProperty(initialOwnerName);
        this.dateAdded = new SimpleStringProperty(initialDateAdded);
    }

    // GET methods for data members
    public int getAssetNumber() {
        return assetNumber.get();
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public String getServiceTag() {
        return serviceTag.get();
    }

    public String getDeviceDescription() {
        return deviceDescription.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getOwnerName() {
        return ownerName.get();
    }

    public String getDateAdded() {
        return dateAdded.get();
    }

    // SET methods for data members
    public void setAssetNumber(int enteredAssetNumber) {
        assetNumber.set(enteredAssetNumber);
    }

    public void setSerialNumber(String enteredSerialNumber) {
        serialNumber.set(enteredSerialNumber);
    }

    public void setServiceTag(String enteredServiceTag) {
        serviceTag.set(enteredServiceTag);
    }

    public void setDeviceDescription(String enteredDeviceDescription) {
        deviceDescription.set(enteredDeviceDescription);
    }

    public void setLocation(String enteredLocation) {
        location.set(enteredLocation);
    }

    public void setOwnerName(String enteredOwnerName) {
        ownerName.set(enteredOwnerName);
    }

    public void setDateAdded(String dateEntered) {
        dateAdded.set(dateEntered);
    }
}