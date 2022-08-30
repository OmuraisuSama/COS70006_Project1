import java.util.ArrayList;
public class CarPark {
    private ArrayList<ParkingSlot> carPark = new ArrayList<ParkingSlot>();

    public int getIndexOf(String slotNumber) {
        for (int i = 0; i < carPark.size(); i ++) {
            String curSlotNumber = carPark.get(i).getSlotNumber();
            if (curSlotNumber.equals(slotNumber)) {
                return i;
            }
        }
        return -1;
    }
}
