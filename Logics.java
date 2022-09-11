import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

/*
 * Logics class
 * @description: this class contains the logics for the application
 * - Public methods:
 * |- 1. addParkingLot: to add a new parking lot to the car park
 * |- 2. deleteParkingLot: to remove an existing parking lot from the car park
 * |- 3. listSlots: to print a table listing all the existing parking lots with info of the car parked, if available
 * |- 4. parkCar: to park a car to an available car park
 * |- 6. removeCar: to remove a car from an existing car park
 * - Private methods:
 * |- correctFormat: return true if input format is correct, false otherwise. Input can be either rego or parking slot number
 * |- parkWhere: find the parking slot number for a given rego number. Return "none" if that rego number is not found
 * |- takeInfo: prompt user for input, evaluate and return that input. Input can either be rego number or parking slot number
 * |- exists: check if the input is already existed in the car park. Input can either be rego number of parking slot number
 * 
 * @author: Peter LUONG - 1038 11153
 * @version: JRE-17
 * @date: 11 Sep 2022
 * @unit: COS70006 - Object-Oriented Programming
 */


public class Logics {
    private Scanner sc = new Scanner(System.in);
    private final ArrayList<String> yes = new ArrayList<String>() {{add("yes"); add("y");}}; // list of acceptable yes answers
    private final ArrayList<String> no = new ArrayList<String>() {{add("no"); add("n");}}; // list of acceptable no answers
    private String errorMessageYN = "Enter \"y\" or \"n\" only."; // default error message or Y/N question
    private Helper helper = new Helper();
    private final String regoFormat = "^[A-Z]{1}[0-9]{4}$";
    private final String slotFormat = "^[A-Z]{1}[0-9]{2}$";
    private Pattern regoPattern = Pattern.compile(regoFormat);
    private Pattern slotPattern = Pattern.compile(slotFormat);

    // -------------------------- 1. addParkingSlot --------------------------
    // Add a parking slot
    // Ask user for the following info:
    // - New parking slot number
    // - Whether the parking slot is for staff
    // Logics: Check whether number entered is correct

    public void addParkingSlot(CarPark carPark) {
        String slotNumber;
        boolean alreadyExists = false;
        // ask user if the parking slot is for staff
        boolean forStaff = takeYNResponse(yes, no, "Is this parking slot for staff (y/n)? ");

        do {
            slotNumber = takeInfo("slot", true); // get the slot number of the right format
            alreadyExists = exists(carPark, "slot", slotNumber);
            if (alreadyExists) { // if slot number already exists
                helper.printError(slotNumber + " already exists in the car park.");
            } else {
                carPark.addParkingSlot(slotNumber, forStaff);
                helper.printConfirm("Parking slot " + slotNumber + " has been added to car park");
                System.out.println();
            }
        } while (alreadyExists);
    }
    // -------------------------- CLOSE addParkingSlot() --------------------------


    // -------------------------- 2. deleteParkingSlot() --------------------------
    // Delete parking slot
    // Ask user for the following info:
    // - Parking slot number to delete
    // Logics:
    // - Check whether the parking slot to be deleted is valid
    // - Check whether the parking slot is occupied, quit if it is
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
            helper.printError(slotNumber + " does not exist");
        } else { // if parking slot exists
            currentSlot = carPark.getParkingSlot(slotNumber);
            isOccupied = currentSlot.isOccupied();
            if (isOccupied) { // check if the parking slot is occupied
                regoNumber = currentSlot.getRegoNumber();
                helper.printError(regoNumber + " is currently parked in " + slotNumber);
                // ask if user wants to remove the car and delete the parking slot
                removeCar = takeYNResponse(yes, no, "Do you want to remove " + regoNumber + " (y/n)?");
                if (removeCar) { // if yes to remove car
                    carInfo = currentSlot.removeCar(); // remove car
                    helper.printConfirm(regoNumber + " which parked at " + slotNumber + " for " + carInfo[3] + " has been removed.");
                    carPark.deleteSlot(slotNumber); // delete slot
                    helper.printConfirm("Parking slot " + slotNumber + " has been deleted from your car park.");
                } else { // if no
                    helper.printConfirm("Returning to main menu.");
                }
            } else {
                carPark.deleteSlot(slotNumber); // delete slot
                helper.printConfirm("Parking slot " + slotNumber + " has been deleted from your car park.");
            }
        }
    }
    // -------------------------- CLOSE deleteParkingSlot() --------------------------



    // -------------------------- 3. listSlots() -------------------------- 
    // method to list info of all slot numbers
    public void listSlots(CarPark carPark) {
        String result;
        int size = carPark.getSize();
        // if carPark is empty
        if (size == 0) {
            System.out.println("The car park currently has no parking slot.");
        } else {
            // print header
            helper.appPrint("list slots");
            // loop through every parking slot in carPark
            for (int i = 0; i < size; i ++) {
                String [] info = carPark.getSlotInfo(i);
                String slotNumber = helper.limitStr(info[0], " ", 9);
                String regoNumber = "vacant";
                String fName = "";
                String lName = "";
                String type = "";
                String start = "";
                String duration = "";
                if (carPark.getParkingSlot(i).isOccupied()) {
                    regoNumber = info[1];
                    fName = info[2];
                    lName = info[3];
                    type = info[4];
                    start = info[5];
                    duration = info[6];
                }

                // format the output
                regoNumber = helper.limitStr(regoNumber, " ", 9);
                fName = helper.limitStr(fName, " ", 12);
                lName = helper.limitStr(lName, " ", 12);
                type = helper.limitStr(type, " ", 10);
                start = helper.limitStr(start, " ", 31); 
                duration = helper.limitStr(duration, " ", 18);

                result = "||" + slotNumber;
                result += "|" + regoNumber;
                result += "|" + " " + fName + " ";
                result += "|" + " " + lName + " ";
                result += "|" + type;
                result += "|" + start;
                result += "|" + duration;
                result += "||";

                // print result
                System.out.println(result);
            }
            // print final line
            helper.printLine("=", 115, true);
            System.out.println();
        }
    }
    // -------------------------- CLOSE listSlots() --------------------------



    // -------------------------- 4. parkCar() --------------------------
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
        boolean alreadyParked = false;

        // check if there is any available car park before prompting user to enter info
        if (carPark.searchAvailable(true) != "none" || carPark.searchAvailable(false) != "none") {
            // prompt user for information & evaluate information

            // REGISTRATION NUMBER
            do {
                regoNumber = takeInfo("rego", true);
                // check if user is trying to park a car that is already in the car park
                alreadyParked = !parkWhere(regoNumber, carPark).equals("none");
                if (alreadyParked) {
                    helper.printError(regoNumber + " is already parked at " + parkWhere(regoNumber, carPark));
                }
            } while (alreadyParked);

            // add rego number to the car obj
            car.setRegoNumber(regoNumber);

            // OWNER NAMES
            helper.printQuestion("Enter the owner's first name: ");
            fName = sc.next();
            helper.printQuestion("Enter the owner's last name: ");
            lName = sc.next();
            // add owner names to the car obj
            car.setNames(fName, lName);

            // IS OWNER A STAFF
            staff = takeYNResponse(yes, no, "Is the owner a staff (y/n)? ");
            // add staff to the car obj
            car.setIsStaff(staff);

            // CHECK FOR AVAILABLE PARKING MATCHES OWNER STATUS (STAFF/VISITOR)
            slotNumber = carPark.searchAvailable(staff);
            if (!slotNumber.equals("none")) {
               // if there is available parking which one?
               do {
                // prompt user to enter parking slot number
                helper.printQuestion("Enter the parking slot number to park " + regoNumber + ": ");
                slotNumber = sc.next();
                // if slot entered exists
                if (exists(carPark, "slot", slotNumber)) {
                    boolean forStaff = carPark.getParkingSlot(slotNumber).isForStaff();
                    isOccupied = carPark.getParkingSlot(slotNumber).isOccupied();
                    if (!isOccupied) { // if parking slot is vacant
                        if (forStaff != staff) { // if staff and visitor status of car does not match parking slot's
                            if (forStaff) helper.printError("This parking lot is for staff only.");
                            if (!forStaff) helper.printError("This parking lot is for visitor only.");
                        } else {
                            invalidInput = false;
                        }
                    } else { // if parking slot is occupied
                        helper.printError(slotNumber + " is currently occupied.");
                    }
                } else {
                    // if slot entered does not exist
                    helper.printError(slotNumber + " does not exist");
                }
            } while (invalidInput);

            // PARK THE CAR
            carPark.getParkingSlot(slotNumber).setCar(car);
            helper.printConfirm(regoNumber + " is now parked at " + slotNumber);
            } else {

                // if no parking for staff or visitor
                if (staff) helper.printError("There is no parking for staff available.");
                if (!staff) helper.printError("There is no parking for visitor available.");
            }   
        } else {
            // if no parking is available
            helper.printError("There is no parking slot available.");
        }
    }
    // -------------------------- CLOSE parkCar() --------------------------



    // -------------------------- 5. searchCar() --------------------------
    // method to search for a car in the car park
    // if the car exists, return the slot number and owner
    // if not, returns a message
    public void searchCar(CarPark carPark) {
        String regoNumber = takeInfo("rego", true);
        ParkingSlot currentSlot;
        int slotIndex = 0;
        String owner;
        String result;
        boolean carFound = false;
        String slotNumber = "";
 

        // search for the parking slot of the input rego number
        for (int i = 0; i < carPark.getSize(); i ++) {
            currentSlot = carPark.getParkingSlot(i);
            if (regoNumber.equals(currentSlot.getRegoNumber())) {
                slotNumber = currentSlot.getSlotNumber();
                slotIndex = i;
                carFound = true;
                break;
            }
        }

        // if parking slot does not exist
        if (!carFound) {
            helper.printError(regoNumber + " is not found!");
        } else {
            // if parking slot exist
            owner = carPark.getSlotInfo(slotIndex)[2] + " " + carPark.getSlotInfo(slotIndex)[3];
            result = "||" + helper.limitStr(regoNumber, " ", 13);
            result += "|" + helper.limitStr(slotNumber, " ", 14);
            result += "|" + " " + helper.limitStr(owner, " ", 30) + " ";
            result += "||";
            // print output
            helper.printConfirm(regoNumber + " is found:");
            helper.appPrint("search car");
            System.out.println(result);
            helper.printLine("=", 65, true);
            System.out.println();
        }
    }
    // -------------------------- CLOSE searchCar() --------------------------



    // -------------------------- 6. removeCar() --------------------------
    // method to remove a car
    public void removeCar(CarPark carPark) {
        String regoNumber;
        ParkingSlot currentSlot;
        boolean carFound = false;

        regoNumber = takeInfo("rego", true);

        carFound = !parkWhere(regoNumber, carPark).equals("none");
        for (int i = 0; i < carPark.getSize(); i ++) {
            currentSlot = carPark.getParkingSlot(i);
            if (currentSlot.getRegoNumber().equals(regoNumber)) {
                currentSlot.removeCar();
                helper.printConfirm(regoNumber + " has been removed!");
                break;
            }
        }

        if (!carFound) {
            helper.printError(regoNumber + " is not in the car park");
        }
    }
    // -------------------------- CLOSE removeCar() --------------------------




    // PRIVATE METHODS
    // method to evaluate format slot number and rego number
    private boolean correctFormat(String input, String type) {
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


    // method to return the parking slot number if a car is already parked in the slot
    // return "none" otherwise
    private String parkWhere(String regoNumber, CarPark carPark) {
        ParkingSlot currentSlot;
        String slotNumber = "none";
        for (int i = 0; i < carPark.getSize(); i ++) {
            currentSlot = carPark.getParkingSlot(i);
            if (currentSlot.getRegoNumber().equals(regoNumber)) {
                slotNumber = currentSlot.getSlotNumber();
                break;
            }
        }
        return slotNumber;
    }

    // method to check format for car rego number and slot number
    private String takeInfo(String type, boolean prompt) {
        String result = "";
        boolean invalidInput = true;
        String promptMessage = "";
        String errorMessage = "Incorrect format:";
        String explanation = "";
        if (type.equals("rego")) {
            promptMessage = "Enter a registration number: ";
            explanation = "A registration number should contain an uppercase letter followed by four digits.";
        } else if (type.equals("slot")) {
            promptMessage = "Enter a slot number: ";
            explanation = "A slot number should contain an uppercase letter followed by two digits.";
        }
        do {
            // prompt and take user input to result
            if (prompt) helper.printQuestion(promptMessage);
            result = sc.next();
            // match the user input with the format
            // if info entered is for rego number
            if (type.equals("rego") && correctFormat(result, "rego")) {
                invalidInput = false;
            // if info entered is for parking slot number
            } else if (type.equals("slot") && correctFormat(result, "slot")) {
                invalidInput = false;
            }

            // if format is incorrect, print error
            if (invalidInput) {
                helper.printError(errorMessage + "\n" + explanation);
            }
        } while (invalidInput);
        return result;
    }

    // method to check whether the rego/slot number already exists
    // return true if yes
    // return false if no
    private boolean exists(CarPark carPark, String type, String info) {
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
    private boolean takeYNResponse(ArrayList<String> yes, ArrayList<String> no, String question) {
        // evaluate if input is yes
        String response;
        boolean invalidResponse = true;
        String errorMessage = "out of takeYNResponse loop";
        
        do {
            helper.printQuestion(question);
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
            helper.printError(errorMessageYN);
        } while (invalidResponse);
        helper.printError(errorMessage);
        return false;
    }
}
