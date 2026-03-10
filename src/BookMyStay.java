import java.util.HashMap;

abstract class Room {
    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price per night: $" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 80);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 120);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500, 250);
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class RoomSearchService {

    public void searchRooms(RoomInventory inventory, Room[] rooms) {

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.roomType);

            if (available > 0) {
                System.out.println();
                room.displayRoomDetails();
                System.out.println("Available: " + available);
            }
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Hotel Booking System");
        System.out.println("Version: 4.0");
        System.out.println("======================================");

        Room single = new SingleRoom();
        Room doub = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] rooms = { single, doub, suite };

        RoomInventory inventory = new RoomInventory();

        RoomSearchService searchService = new RoomSearchService();

        System.out.println("\nAvailable Rooms:");

        searchService.searchRooms(inventory, rooms);
    }
}