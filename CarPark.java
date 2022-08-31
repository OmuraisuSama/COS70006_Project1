import java.util.ArrayList;
public class CarPark {
    private ArrayList<ParkingSlot> carPark = new ArrayList<ParkingSlot>();
    

    // method to add new parking slot
    public void addParkingSlot(ParkingSlot parkingSlot) {
        carPark.add(parkingSlot);
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

    // method to return the number of parking slots
    public int getSize() {
        return carPark.size();
    }

    // method to delete a parking slot
    public void deleteSlot(String slotNumber) {
        int slotIndex = getIndexOf(slotNumber);
        if (slotIndex != -1) {
            carPark.remove(slotIndex);
        } else {
            System.out.println(slotNumber + " parking slot does not exist.");
        }
    }

    // method to get info about a parking slot
    // param: index number of the parking slot in carPark
    // return {parking slot number, rego number, fName, lName, type, parking starts, parking duration}
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

            if (currentSlot.getCar().isStaff()) type = "staff";
            else type = "visitor";

            parkingStarts = currentSlot.getParkingStarts();
            parkingDuration = currentSlot.getDuration();
        }

        String [] result = {slotNumber, regoNumber, fName, lName, type, parkingStarts, parkingDuration};
        return result;
    }

}  
