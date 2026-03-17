import java.util.*;

// ---------------- Reservation ----------------
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ---------------- Booking History ----------------
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// ---------------- Booking Report Service ----------------
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> history) {
        System.out.println("----- Booking History -----");

        if (history.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation r : history) {
            System.out.println("Guest: " + r.guestName + " | Room: " + r.roomType);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> history) {

        HashMap<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history) {
            countMap.put(r.roomType, countMap.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("----- Booking Summary Report -----");

        for (String roomType : countMap.keySet()) {
            System.out.println(roomType + " booked: " + countMap.get(roomType) + " times");
        }
    }
}

// ---------------- Main Class ----------------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Use Case 8");
        System.out.println("Booking History & Reporting");
        System.out.println("======================================");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        history.addReservation(new Reservation("Alice", "Single Room"));
        history.addReservation(new Reservation("Bob", "Double Room"));
        history.addReservation(new Reservation("Charlie", "Single Room"));
        history.addReservation(new Reservation("David", "Suite Room"));

        // Admin views booking history
        System.out.println();
        reportService.showAllBookings(history.getAllReservations());

        System.out.println();

        // Admin generates report
        reportService.generateSummary(history.getAllReservations());
    }
}