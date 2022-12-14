import java.util.ArrayList;

/*
 * CarPark class
 * @description: getter and setter method for the class CarPark
 * - Instance variable:
 * |- carPark: ArrayList of ParkingSlot objs which contain information regarding the parking slot
 * 
 * @author: Peter LUONG - 1038 11153
 * @version: JRE-17
 * @date: 11 Sep 2022
 * @unit: COS70006 - Object-Oriented Programming
 */

public class CarPark {
    private ArrayList<ParkingSlot> carPark = new ArrayList<ParkingSlot>();


    
    // --------------- GETTERS -----------------
    // search and return for the next available slot
    public String searchAvailable(boolean forStaff) {
        String slotNumber = "none";
        boolean isOccupied;
        ParkingSlot currentSlot;
        
        for (int i = 0; i < carPark.size(); i ++) {
            currentSlot = carPark.get(i);
            isOccupied = currentSlot.isOccupied();
            if (!isOccupied && currentSlot.isForStaff() == forStaff) {
                slotNumber = currentSlot.getSlotNumber();
            }
        }
        return slotNumber;
    }

    // method to return the index number of a parking slot number
    public int getIndexOf(String slotNumber) {
        for (int i = 0; i < carPark.size(); i ++) {
            String curSlotNumber = carPark.get(i).getSlotNumber();
            if (curSlotNumber.equals(slotNumber)) {
                return i;
            }
        }
        return -1;
    } // close getIndexOf()

    // method to return the parkingSlot object given the index number
    public ParkingSlot getParkingSlot(int index) {
        return carPark.get(index);
    }

    // method to return the parkingSlot object given the parking slot number
    public ParkingSlot getParkingSlot(String slotNumber) {
        int objIndex = getIndexOf(slotNumber);
        return carPark.get(objIndex);
    }

    // method to return the number of parking slots
    public int getSize() {
        return carPark.size();
    }

    // method to get info about a parking slot
    // param: index number of the parking slot in carPark
    // return String []:
    // [0] String: parking slot number
    // if parking lot is occupied:
    // [1] String: registration number of the car
    // [2] String: first name of owner
    // [3] String: last name of owner
    // [4] String: "staff" or "visitor"
    // [5] String: when parked
    // [6] String: parking duration
    public String [] getSlotInfo(int slotIndex) {
        ParkingSlot currentSlot = getParkingSlot(slotIndex);
        String slotNumber = currentSlot.getSlotNumber();
        String regoNumber = "";
        String fName = "";
        String lName = "";
        String type = "";
        String parkingStarts = "";
        String parkingDuration = "";

        // get info incase the slot is occupied
        if (currentSlot.isOccupied()) {
            regoNumber = currentSlot.getRegoNumber();
            fName = currentSlot.getCar().getFirstName();
            lName = currentSlot.getCar().getLastName();
            // retrieve parking time
            parkingStarts = currentSlot.getParkingStarts();
            parkingDuration = currentSlot.getDuration();
        }
        // check if parking slot is for staff
        if (currentSlot.isForStaff()) {
            type = "staff";
        } else {
            type = "visitor";
        }

        String [] result = {slotNumber, regoNumber, fName, lName, type, parkingStarts, parkingDuration};
        return result;
    }


    // --------------- SETTERS -----------------
    // method to add new parking slot
    public void addParkingSlot(ParkingSlot parkingSlot) {
        carPark.add(parkingSlot);
    }

    // method to add new parking slot with more info
    public void addParkingSlot(String slotNumber, boolean forStaff) {
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setSlotNumber(slotNumber);
        parkingSlot.setForStaff(forStaff);
        carPark.add(parkingSlot);
    }

    // method to delete a parking slot using slot number
    public void deleteSlot(String slotNumber) {
        int slotIndex = getIndexOf(slotNumber);
        if (slotIndex != -1) {
            carPark.remove(slotIndex);
        } else {
            System.out.println(slotNumber + " parking slot does not exist.");
        }
    }

    // method to delete a parking slot using index number
    public void deleteSlot(int index) {
        carPark.remove(index);
    }
}  
