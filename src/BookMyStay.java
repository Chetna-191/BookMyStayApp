import java.util.*;

// ---------------- Custom Exception ----------------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ---------------- Reservation ----------------
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ---------------- Room Inventory ----------------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void decrementRoom(String roomType) throws InvalidBookingException {
        int count = inventory.get(roomType);

        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        inventory.put(roomType, count - 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }
}

// ---------------- Validator ----------------
class BookingValidator {

    public static void validate(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        // Check null or empty name
        if (r.guestName == null || r.guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (!inventory.isValidRoomType(r.roomType)) {
            throw new InvalidBookingException("Invalid room type: " + r.roomType);
        }

        // Check availability
        if (inventory.getAvailability(r.roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.roomType);
        }
    }
}

// ---------------- Booking Service ----------------
class BookingService {

    public void processBooking(Reservation r, RoomInventory inventory) {
        try {
            // Validation (Fail-Fast)
            BookingValidator.validate(r, inventory);

            // If valid → proceed
            inventory.decrementRoom(r.roomType);

            System.out.println("Booking Successful");
            System.out.println("Guest: " + r.guestName);
            System.out.println("Room: " + r.roomType);
            System.out.println();

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
            System.out.println();
        }
    }
}

// ---------------- Main Class ----------------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Use Case 9");
        System.out.println("Error Handling & Validation");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        // Test cases
        Reservation r1 = new Reservation("Alice", "Single Room");   // valid
        Reservation r2 = new Reservation("", "Double Room");        // invalid name
        Reservation r3 = new Reservation("Bob", "Suite Room");      // invalid type
        Reservation r4 = new Reservation("Charlie", "Double Room"); // valid
        Reservation r5 = new Reservation("David", "Double Room");   // no availability

        service.processBooking(r1, inventory);
        service.processBooking(r2, inventory);
        service.processBooking(r3, inventory);
        service.processBooking(r4, inventory);
        service.processBooking(r5, inventory);
    }
}