import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class ParkingSlot {
    private String slotNumber;
    private boolean forStaff;
    // variables of parking time end and start in milliseconds
    // this is to calculate duration of parking
    private long msParkingDuration;
    private long msParkingStarts;
    private long msParkingEnds;
    // variable to get the formatted parking time
    private final String dateTimeFormat = "E, dd MMM yyyy hh.mm.ss a";
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateTimeFormat);
    private String strParkingStarts;
    private String strParkingEnds;
    private String strParkingDuration;

    private Car car;
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
        return null;
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
        this.car = null;
        String regoNumber = car.getRegoNumber();
        isOccupied = false;
        // get time of leaving in ms and String
        msParkingEnds = System.currentTimeMillis();
        strParkingEnds = dtf.format(LocalDateTime.now());
        // get duration in ms and convert duration to String
        msParkingDuration = msParkingEnds - msParkingStarts;
        strParkingDuration = helper.millisecToTime(msParkingDuration);

        String [] result = {regoNumber, strParkingStarts, strParkingEnds, strParkingDuration};
        return result;
    }
}
