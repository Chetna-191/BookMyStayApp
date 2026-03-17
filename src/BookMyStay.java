import java.util.*;

// ---------------- Add-On Service ----------------
class AddOnService {
    String serviceName;
    double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }
}

// ---------------- Add-On Service Manager ----------------
class AddOnServiceManager {

    private HashMap<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Display services
    public void showServices(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services for Reservation ID: " + reservationId);
            return;
        }

        System.out.println("Services for Reservation ID: " + reservationId);
        for (AddOnService s : services) {
            System.out.println("Service: " + s.serviceName + " | Cost: ₹" + s.cost);
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);
        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.cost;
            }
        }
        return total;
    }
}

// ---------------- Main Class ----------------
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Use Case 7");
        System.out.println("Add-On Service Selection");
        System.out.println("======================================");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Assume existing reservation IDs
        String r1 = "R101";
        String r2 = "R102";

        // Guest selects services
        manager.addService(r1, new AddOnService("Breakfast", 200));
        manager.addService(r1, new AddOnService("Airport Pickup", 500));
        manager.addService(r1, new AddOnService("WiFi", 100));

        manager.addService(r2, new AddOnService("Extra Bed", 300));
        manager.addService(r2, new AddOnService("Dinner", 400));

        // Display services
        System.out.println();
        manager.showServices(r1);
        System.out.println("Total Additional Cost: ₹" + manager.calculateTotalCost(r1));

        System.out.println();

        manager.showServices(r2);
        System.out.println("Total Additional Cost: ₹" + manager.calculateTotalCost(r2));
    }
}