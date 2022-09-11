import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/*
 * ParkingSlot class
 * @description: getter and setter method for the class ParkingSlot
 * - Instance variable:
 * |- slotNumber: the parking slot number
 * |- forStaff: true if the parking stot is for staff, false for visitor
 * |- msParkingDuration: duration of parking in milliseconds
 * |- msParkingStarts & msParkingEnds: used to calculate msParkingDuration
 * |- dateTimeFormat: regular expression to format the the parking duration in readable format
 * |- dtf: datetime formatter
 * |- strParkingStarts: String formatted datetime of when parking starts
 * |- strParkingEnds: String formatted datetime of when parking ends
 * |- strParkingDuration: String formatted datetime of parking duration
 * |- car: Car object containing information of the car parked in the parking slot
 * |- isOccupied: true if there's a car currently park in the slot, false otherwise
 * |- helper: an instance of Helper class object
 * 
 * @author: Peter LUONG - 1038 11153
 * @version: JRE-17
 * @date: 11 Sep 2022
 * @unit: COS70006 - Object-Oriented Programming
 */

public class ParkingSlot {
    private String slotNumber;
    private boolean forStaff;
    // variables of parking time end and start in milliseconds
    // this is to calculate duration of parking
    private long msParkingDuration;
    private long msParkingStarts;
    private long msParkingEnds;
    // variable to get the formatted parking time
    private final String dateTimeFormat = "EEE, dd-MMM-yyyy hh:mm:ss a";
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateTimeFormat);
    private String strParkingStarts;
    private String strParkingEnds;
    private String strParkingDuration;

    private Car car = new Car();
    private boolean isOccupied;
    private Helper helper = new Helper();

    // ------------ CONSTRUCTORS --------------
    public ParkingSlot(String slotNumber, boolean forStaff) {
        this.slotNumber = slotNumber;
        this.forStaff = forStaff;
    }

    public ParkingSlot() {}

    // --------------- GETTERS -----------------
    public String getSlotNumber() {
        return slotNumber;
    }

    public boolean isForStaff() {
        return forStaff;
    }

    public String getDuration() {
        long msDuration = System.currentTimeMillis() - msParkingStarts;
        String stringDuration = helper.millisecToTime(msDuration);
        return stringDuration;
    }

    // method to get when the car is park in
    public String getParkingStarts() {
        return strParkingStarts;
    }

    public String getRegoNumber() {
        if (isOccupied) {
            return car.getRegoNumber();
        }
    return "none";
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Car getCar() {
        return car;
    }

    // --------------- SETTERS -----------------
    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public void setForStaff(boolean forStaff) {
        this.forStaff = forStaff;
    }

    public void setCar(Car car) {
        this.car = car;
        isOccupied = true;
        msParkingStarts = System.currentTimeMillis();
        strParkingStarts = dtf.format(LocalDateTime.now());
    }

    // method to remove car
    public String[] removeCar() {

        String regoNumber = car.getRegoNumber();
        isOccupied = false;
        // get time of leaving in ms and String
        msParkingEnds = System.currentTimeMillis();
        strParkingEnds = dtf.format(LocalDateTime.now());
        // get duration in ms and convert duration to String
        msParkingDuration = msParkingEnds - msParkingStarts;
        strParkingDuration = helper.millisecToTime(msParkingDuration);
        String [] result = {regoNumber, strParkingStarts, strParkingEnds, strParkingDuration};
        this.car = null;
        return result;
    }
}
