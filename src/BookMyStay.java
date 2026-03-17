import java.util.*;

// ---------------- Reservation ----------------
class Reservation {
    String guestName;
    String roomType;
    String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// ---------------- Room Inventory ----------------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }
}

// ---------------- Booking Store ----------------
class BookingStore {

    private HashMap<String, Reservation> bookings;

    public BookingStore() {
        bookings = new HashMap<>();
    }

    public void addBooking(String reservationId, Reservation r) {
        bookings.put(reservationId, r);
    }

    public Reservation getBooking(String reservationId) {
        return bookings.get(reservationId);
    }

    public void removeBooking(String reservationId) {
        bookings.remove(reservationId);
    }

    public boolean exists(String reservationId) {
        return bookings.containsKey(reservationId);
    }
}

// ---------------- Cancellation Service ----------------
class CancellationService {

    private Stack<String> rollbackStack;

    public CancellationService() {
        rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId,
                              BookingStore store,
                              RoomInventory inventory) {

        // Validation
        if (!store.exists(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            System.out.println();
            return;
        }

        // Get booking
        Reservation r = store.getBooking(reservationId);

        // Push roomId to stack (rollback tracking)
        rollbackStack.push(r.roomId);

        // Restore inventory
        inventory.incrementRoom(r.roomType);

        // Remove booking
        store.removeBooking(reservationId);

        System.out.println("Booking Cancelled Successfully");
        System.out.println("Guest: " + r.guestName);
        System.out.println("Room Released: " + rollbackStack.peek());
        System.out.println();
    }
}

// ---------------- Main Class ----------------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Use Case 10");
        System.out.println("Cancellation & Inventory Rollback");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();
        BookingStore store = new BookingStore();
        CancellationService cancelService = new CancellationService();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("Alice", "Single Room", "S-101");
        Reservation r2 = new Reservation("Bob", "Double Room", "D-201");

        store.addBooking("R101", r1);
        store.addBooking("R102", r2);

        // Decrease inventory (simulate allocation)
        inventory.decrementRoom("Single Room");
        inventory.decrementRoom("Double Room");

        // Cancellation
        cancelService.cancelBooking("R101", store, inventory); // valid
        cancelService.cancelBooking("R200", store, inventory); // invalid
    }
}