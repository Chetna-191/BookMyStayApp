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

// ---------------- Room Inventory ----------------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    // synchronized method (critical section)
    public synchronized boolean bookRoom(String roomType) {
        int count = inventory.getOrDefault(roomType, 0);

        if (count > 0) {
            inventory.put(roomType, count - 1);
            return true;
        }
        return false;
    }
}

// ---------------- Shared Booking Queue ----------------
class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // synchronized add
    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    // synchronized remove
    public synchronized Reservation getRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

// ---------------- Booking Processor (Thread) ----------------
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {

            Reservation r;

            // synchronized access to queue
            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.getRequest();
            }

            // process booking
            boolean success = inventory.bookRoom(r.roomType);

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " SUCCESS: " + r.guestName + " got " + r.roomType);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " FAILED: No room for " + r.guestName);
            }
        }
    }
}


public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Use Case 11");
        System.out.println("Concurrent Booking Simulation");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Multiple guest requests (same room → conflict)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Double Room"));
        queue.addRequest(new Reservation("David", "Double Room"));

        // Multiple threads (simulate concurrent users)
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();
    }
}