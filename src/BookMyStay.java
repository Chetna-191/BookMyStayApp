import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomAllocationService {

    private HashMap<String, Set<String>> allocatedRooms;
    private int roomCounter = 1;

    public RoomAllocationService() {
        allocatedRooms = new HashMap<>();
    }

    public void processBooking(Reservation r, RoomInventory inventory) {

        if (inventory.getAvailability(r.roomType) > 0) {

            String roomId = r.roomType.replace(" ", "") + "-" + roomCounter++;

            allocatedRooms.putIfAbsent(r.roomType, new HashSet<>());

            allocatedRooms.get(r.roomType).add(roomId);

            inventory.decrementRoom(r.roomType);

            System.out.println("Reservation Confirmed");
            System.out.println("Guest: " + r.guestName);
            System.out.println("Room Type: " + r.roomType);
            System.out.println("Assigned Room ID: " + roomId);
            System.out.println();

        } else {
            System.out.println("No rooms available for " + r.roomType + " for guest " + r.guestName);
            System.out.println();
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Hotel Booking System");
        System.out.println("Version: 6.0");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));

        RoomAllocationService allocationService = new RoomAllocationService();

        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            allocationService.processBooking(r, inventory);
        }
    }
}