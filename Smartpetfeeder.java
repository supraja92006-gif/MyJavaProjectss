import java.util.*;
import java.time.*;

class SmartPetFeeder {
    private double foodStorage = 1000.0;  // grams in feeder
    private double portionSize = 100.0;   // grams per feeding
    private List<LocalTime> schedule = new ArrayList<>();

    public SmartPetFeeder() {
        // Set feeding times (you can adjust these)
        schedule.add(LocalTime.of(8, 0));   // 8:00 AM
        schedule.add(LocalTime.of(13, 0));  // 1:00 PM
        schedule.add(LocalTime.of(19, 0));  // 7:00 PM
    }

    public void showStatus() {
        System.out.println("Food left in storage: " + foodStorage + " grams");
        System.out.println("Feeding schedule: " + schedule);
    }

    public void feedPet(String mode) {
        if (foodStorage >= portionSize) {
            foodStorage -= portionSize;
            System.out.println("\n[" + mode + "] Feeding pet...");
            for (int i = 0; i < 3; i++) {
                System.out.print(".");
                try { Thread.sleep(500); } catch (Exception e) {}
            }
            System.out.println("\nDispensing " + portionSize + " grams of food.");
            System.out.println("Feeding complete ✅");
            System.out.println("Remaining food: " + foodStorage + " grams\n");
        } else {
            System.out.println("\n⚠️ Not enough food left! Please refill the feeder.\n");
        }
    }

    public void checkAutomaticFeed() {
        // You can simulate time here
        LocalTime now = LocalTime.of(8, 0); // Simulate 8 AM
        for (LocalTime time : schedule) {
            if (now.getHour() == time.getHour() && now.getMinute() == time.getMinute()) {
                feedPet("Automatic (Scheduled)");
                return;
            }
        }
        System.out.println("\n⏰ No feeding scheduled at this time (" + now.truncatedTo(java.time.temporal.ChronoUnit.MINUTES) + ")");
    }
}

public class SmartPetFeederSimulation {
    public static void main(String[] args) throws InterruptedException {
        SmartPetFeeder feeder = new SmartPetFeeder();
        Scanner sc = new Scanner(System.in);

        System.out.println("🐾 SMART PET FEEDER SIMULATION 🐾");
        feeder.showStatus();

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Manual Feed (Owner Control)");
            System.out.println("2. Check Automatic Feeding");
            System.out.println("3. Show Food Status");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    feeder.feedPet("Manual");
                    break;
                case 2:
                    feeder.checkAutomaticFeed();
                    break;
                case 3:
                    feeder.showStatus();
                    break;
                case 4:
                    System.out.println("Exiting... Bye 👋");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
