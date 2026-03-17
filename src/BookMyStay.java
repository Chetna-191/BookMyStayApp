import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class RoomInventory implements Serializable {
    HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public void showInventory() {
        System.out.println("Current Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    public void saveData(List<Reservation> history, RoomInventory inventory) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(history);
            out.writeObject(inventory);
            out.close();
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    public Object[] loadData() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
            List<Reservation> history = (List<Reservation>) in.readObject();
            RoomInventory inventory = (RoomInventory) in.readObject();
            in.close();
            System.out.println("Data loaded successfully.");
            return new Object[]{history, inventory};
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Use Case 12");
        System.out.println("Persistence & Recovery");
        System.out.println("======================================");

        PersistenceService service = new PersistenceService();

        Object[] data = service.loadData();

        List<Reservation> history;
        RoomInventory inventory;

        if (data != null) {
            history = (List<Reservation>) data[0];
            inventory = (RoomInventory) data[1];
        } else {
            history = new ArrayList<>();
            inventory = new RoomInventory();
        }

        history.add(new Reservation("Alice", "Single Room"));
        history.add(new Reservation("Bob", "Double Room"));

        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            System.out.println(r.guestName + " -> " + r.roomType);
        }

        System.out.println();
        inventory.showInventory();

        service.saveData(history, inventory);
    }
}