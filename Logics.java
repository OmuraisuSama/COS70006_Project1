import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Logics {
    private Scanner sc = new Scanner(System.in);
    private final ArrayList<String> yes = new ArrayList<String>() {{add("yes"); add("y");}};
    private final ArrayList<String> no = new ArrayList<String>() {{add("no"); add("n");}};
    private String errorMessageYN = "Enter \"y\" or \"n\" only.";
    private Helper helper = new Helper();
    private final String regoFormat = "^[A-Z]{1}[0-9]{4}$";
    private final String slotFormat = "^[A-Z]{1}[0-9]{2}$";
    private Pattern regoPattern = Pattern.compile(regoFormat);
    private Pattern slotPattern = Pattern.compile(slotFormat);

    // -------------------------- Option 1 --------------------------
    // Add a parking slot
    // Ask user for the following info:
    // - New parking slot number
    // - Whether the parking slot is for staff
    // Logics: Check whether number entered is correct
    // - use takeYNResponse() to get user's answer regarding if this slot is for staff
    // - use takeInfo() to get user's entered slot number & check the format of input
    // - use exist() to check whether the slot number has already existed in the car park.
    public void addParkingSlot(CarPark carPark) {
        String slotNumber;
        // ask user if the parking slot is for staff
        boolean forStaff = takeYNResponse(yes, no, "Is this parking slot for staff (y/n)? ");

        slotNumber = takeInfo("slot", true); // get the slot number of the right format
        if (exists(carPark, "slot", slotNumber)) { // if slot number already exists
            System.out.println("Error: " + slotNumber + " already exists in the car park.");
        } else {
            carPark.addParkingSlot(slotNumber, forStaff);
        }
    }

    // -------------------------- Option 2 --------------------------
    // Delete parking slot
    // Ask user for the following info:
    // - Parking slot number to delete
    // Logics:
    // - Check whether the parking slot to be deleted is valid
    // - Check whether the parking slot is occupied, break if it is
    public void deleteParkingSlot(CarPark carPark) {
        String slotNumber;
        boolean isOccupied;
        String regoNumber;
        boolean removeCar = false;
        String [] carInfo;
        ParkingSlot currentSlot;
        // prompt user for parking slot number
        slotNumber = takeInfo("slot", true);

        if (!exists(carPark, "slot", slotNumber)) { // if parking slot does not exist
            System.out.println(slotNumber + " does not exist");
        } else { // if parking slot exists
            currentSlot = carPark.getParkingSlot(slotNumber);
            isOccupied = currentSlot.isOccupied();
            if (isOccupied) { // check if the parking slot is occupied
                regoNumber = currentSlot.getRegoNumber();
                System.out.println(regoNumber + " is currently parked in " + slotNumber);
                // ask if user wants to remove the car and delete the parking slot
                removeCar = takeYNResponse(yes, no, "Do you want to remove " + regoNumber + " (y/n)?");
                if (removeCar) { // if yes to remove car
                    carInfo = currentSlot.removeCar(); // remove car
                    System.out.println(regoNumber + " which parked at " + slotNumber + " for " + carInfo[3] + " has been removed.");
                    carPark.deleteSlot(slotNumber); // delete slot
                    System.out.println(slotNumber + " has been deleted from your car park.");
                } else { // if no
                    System.out.print("Returning to main menu.");
                }
            }
        }
    }

    // -------------------------- Option 3 -------------------------- 
    // method to list info of all slot numbers
    public void listSlots(CarPark carPark) {
        int size = carPark.getSize();
        // print header
        helper.printHeader("list slots");
        // loop through every parking slot in carPark
        for (int i = 0; i < size; i ++) {
            boolean isOccupied = carPark.getParkingSlot(i).isOccupied();
            String [] info = carPark.getSlotInfo(i);
            String slotNumber = helper.limitStr(info[0], " ", 9);
            String regoNumber;
            if (!isOccupied) regoNumber = info[1]; else regoNumber = "Vacant";
            regoNumber = helper.limitStr(regoNumber, " ", 9);
            String fName = helper.limitStr(info[2], " ", 12);
            String lName = helper.limitStr(info[3], " ", 12);
            String type = helper.limitStr(info[4], " ", 10);
            String start = helper.limitStr(info[5], " ", 31); 
            String duration = helper.limitStr(info[6], " ", 18);
            // print result
            System.out.println("||" + slotNumber + "|" + regoNumber + "|" + fName + "|" + lName + "|" + type + "|" + start + "|" + duration + "||");
        }
        // print final line
        helper.printLine("=", 111, true);
    }

    // -------------------------- Option 4 --------------------------
    // Park a car
    // Ask user for the following info:
    // - Registration number
    // - Owner's first and last name
    // - Whether owner is staff
    // - Which slot to park
    // --- If not specified, park in any available slot
    // ------- if no available slot, ask user to create one -> us addParkingSlot()
    // --- If specified, park in the specified slot
    public void parkCar(CarPark carPark) {
        String regoNumber;
        String fName;
        String lName;
        String slotNumber;
        boolean staff;
        Car car = new Car();
        boolean invalidInput = true;
        boolean isOccupied = true;

        // prompt user for information & evaluate information
        // registration number:
        regoNumber = takeInfo("rego", true);
        car.setRegoNumber(regoNumber);
        // owner name
        System.out.print("Enter the owner's first name: ");
        fName = sc.next();
        System.out.print("Enter the owner's last name: ");
        lName = sc.next();
        car.setNames(fName, lName);
        // is owner a staff
        staff = takeYNResponse(yes, no, "Is the owner a staff (y/n)? ");
        car.setIsStaff(staff);

        // which car park?
        do {
            System.out.println("Enter the parking slot number to park " + regoNumber);
            System.out.println("Leave blank to park at the next available slot");
            slotNumber = sc.next();

            if (slotNumber.equals("")){
                slotNumber = carPark.searchAvailable(staff);
                if (slotNumber.equals("none")) {
                    System.out.print("There is no parking slot available.");
                    System.out.print("Please create another parking slot.");
                    break;
                } else invalidInput = false;
            } else if (exists(carPark, "slot", slotNumber)) {
                boolean forStaff = carPark.getParkingSlot(slotNumber).isForStaff();
                isOccupied = carPark.getParkingSlot(slotNumber).isOccupied();
                if (!isOccupied) { // if parking slot is vacant
                    if (forStaff != staff) { // if staff and visitor status of car does not match parking slot's
                        if (forStaff) System.out.println("This parking lot is for staff only.");
                        if (!forStaff) System.out.println("This parking lot is for visitor only.");
                    } else {
                        invalidInput = false;
                    }
                } else { // if parking slot is occupied
                    System.out.println(slotNumber + " is currently occupied.");
                }
            }
        } while (invalidInput);

        // park the car
        carPark.getParkingSlot(slotNumber).setCar(car);
    }


    // method to evaluate format slot number and rego number
    public boolean correctFormat(String input, String type) {
        boolean correctFormat = false;
        Matcher matcher;
        if (type.equals("rego")) {
            matcher = regoPattern.matcher(input);
            if (matcher.find()) correctFormat = true;
        } else if (type.equals("slot")) {
            matcher = slotPattern.matcher(input);
            if (matcher.find()) correctFormat = true;
        }

        return correctFormat;
    }

    // method to check format for car rego number and slot number
    public String takeInfo(String type, boolean prompt) {
        String result = "";
        boolean invalidInput = true;
        String promptMessage = "";
        String errorMessage = "";
        Matcher matcher;
        if (type.equals("rego")) {
            promptMessage = "Enter a registration number: ";
            errorMessage = "A registration number should contain an uppercase letter followed by four digits.";
        } else if (type.equals("slot")) {
            promptMessage = "Enter a slot number: ";
            errorMessage = "A slot number should contain an uppercase letter followed by two digits.";
        }
        do {
            // prompt and take user input to result
            if (prompt) System.out.print(promptMessage);
            result = sc.next();
            // match the user input with the format
            if (type.equals("rego") && correctFormat(result, "rego")) {
                invalidInput = false;
            } else if (type.equals("slot") && correctFormat(result, "slot")) {
                invalidInput = false;
            }
            // if format is incorrect, print error
            if (invalidInput) System.out.print(errorMessage);
        } while (invalidInput);
        return result;
    }

    // method to check whether the rego/slot number already exists
    public boolean exists(CarPark carPark, String type, String info) {
        boolean exists = false;
        ParkingSlot currentSlot;
        for (int i = 0; i < carPark.getSize(); i ++) {
            currentSlot = carPark.getParkingSlot(i);
            if (type == "rego" && currentSlot.getRegoNumber().equals(info)) exists = true;
            if (type == "slot" && currentSlot.getSlotNumber().equals(info)) exists = true;
        }
        return exists;
    }

    // method to evaluate y/n result
    public boolean takeYNResponse(ArrayList<String> yes, ArrayList<String> no, String question) {
        // evaluate if input is yes
        String response;
        boolean invalidResponse = true;
        
        do {
            System.out.println(question);
            response = sc.next();
            for (int i = 0; i < yes.size(); i ++) {
                if (yes.get(i).equalsIgnoreCase(response)) {
                    invalidResponse = false;
                    return true;
                }
            }
            // evaluate if input is no
            for (int i = 0; i < no.size(); i ++) {
                if (no.get(i).equalsIgnoreCase(response)) {
                    invalidResponse = false;
                    return false;
                }
            }
            // if input is invalid
            System.out.println ("Enter \"y\" or \"n\" only.");
        } while (invalidResponse);
        System.out.print("ERROR: out of takeYNResponse loop");
        return false;
    }
}
