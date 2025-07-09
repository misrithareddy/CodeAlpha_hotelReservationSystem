class Booking {
    String customerName;
    int roomNumber;
    String category;

    Booking(String name, int number, String category) {
        this.customerName = name;
        this.roomNumber = number;
        this.category = category;
    }

    @Override
    public String toString() {
        return customerName + " booked Room " + roomNumber + " (" + category + ")";
    }
}