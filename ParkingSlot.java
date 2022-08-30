public class ParkingSlot {
    private String slotNumber;
    private boolean forStaff;
    private long parkingDuration;
    private long parkingStarts;
    private long parkingEnds;
    private Car car;
    private boolean isOccupied;
    private Helper helper = new Helper();

    // --------------- GETTERS -----------------
    public String getSlotNumber() {
        return slotNumber;
    }

    public boolean isForStaff() {
        return forStaff;
    }

    public String getDuration() {
        parkingDuration = parkingEnds - parkingStarts;
        String stringDuration = helper.millisecToTime(parkingDuration);
        return stringDuration;
    }

    public String getRegoNumber(String slotNumber) {
        if (isOccupied) {

        }
    }

    // --------------- SETTERS -----------------
    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public void setForStaff(boolean forStaff) {
        this.forStaff = forStaff;
    }

    public void 


}
