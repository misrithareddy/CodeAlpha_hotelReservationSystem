import java.io.*;
import java.util.*;
public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static final String FILE_NAME = "bookings.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initRooms();
        loadBookings();

        int choice;
        do {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewAvailableRooms();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> viewBookings();
                case 5 -> saveBookings();
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    static void initRooms() {
       
        for (int i = 1; i <= 3; i++) rooms.add(new Room(i, "Standard"));
        for (int i = 4; i <= 6; i++) rooms.add(new Room(i, "Deluxe"));
        for (int i = 7; i <= 9; i++) rooms.add(new Room(i, "Suite"));
    }

    static void viewAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked) {
                System.out.println("Room " + room.roomNumber + " (" + room.category + ")");
            }
        }
    }

    static void bookRoom() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter room category (Standard/Deluxe/Suite): ");
        String category = sc.nextLine();

        for (Room room : rooms) {
            if (room.category.equalsIgnoreCase(category) && !room.isBooked) {
                room.isBooked = true;
                Booking booking = new Booking(name, room.roomNumber, room.category);
                bookings.add(booking);

                // Simulated payment
                System.out.println("Processing payment...");
                System.out.println("Payment successful!");
                System.out.println("Booking Confirmed: " + booking);
                return;
            }
        }

        System.out.println("No rooms available in " + category + " category.");
    }

    static void cancelBooking() {
        System.out.print("Enter your name to cancel booking: ");
        String name = sc.nextLine();
        boolean found = false;

        Iterator<Booking> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            Booking booking = iterator.next();
            if (booking.customerName.equalsIgnoreCase(name)) {
                for (Room room : rooms) {
                    if (room.roomNumber == booking.roomNumber) {
                        room.isBooked = false;
                        break;
                    }
                }
                iterator.remove();
                System.out.println("Booking cancelled for " + name);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No booking found for " + name);
        }
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("\nAll Bookings:");
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }

    static void saveBookings() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Booking booking : bookings) {
                writer.write(booking.customerName + "," + booking.roomNumber + "," + booking.category);
                writer.newLine();
            }
            System.out.println("Bookings saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    static void loadBookings() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Booking booking = new Booking(parts[0], Integer.parseInt(parts[1]), parts[2]);
                bookings.add(booking);
                for (Room room : rooms) {
                    if (room.roomNumber == booking.roomNumber) {
                        room.isBooked = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }
}