package models;

import interfaces.IReservable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Trip implements IReservable {
  private String tripId;
  private Vehicle vehicle;
  private Route route;
  private LocalDate date;
  private double price;
  private boolean[] seatStatus; // true = reserved, false = available
  private ArrayList<Reservation> reservations = new ArrayList<>();

  public Trip(String tripId, Vehicle vehicle, Route route, LocalDate date, double price) {
    this.tripId = tripId;
    this.vehicle = vehicle;
    this.route = route;
    this.date = date;
    this.price = price;
    this.seatStatus = new boolean[vehicle.getCapacity()];

    // Random olarak bazı koltukları doldur
    fillRandomSeats();
  }

  private void fillRandomSeats() {
    Random random = new Random();
    int capacity = vehicle.getCapacity(); // ✅ Düzeltildi

    double fillRate = random.nextDouble() * 0.8;
    int seatsToFill = (int) (capacity * fillRate);

    for (int i = 0; i < seatsToFill; i++) {
      int randomSeat;
      do {
        randomSeat = random.nextInt(capacity);
      } while (seatStatus[randomSeat]);
      seatStatus[randomSeat] = true;
      createVirtualReservation(randomSeat);
    }
  }

  private void createVirtualReservation(int seatNumber) {
    String firstName = NameGenerator.getRandomFirstName();
    String lastName = NameGenerator.getRandomLastName();

    Random random = new Random();
    String tcNo = String.format("%011d", random.nextInt(99999999) + 10000000000L);
    LocalDate birthDate = LocalDate.of(1970 + random.nextInt(40),
            1 + random.nextInt(12),
            1 + random.nextInt(28));

    Passenger virtualPassenger = new Passenger(firstName, lastName, tcNo, birthDate);
    String virtualReservationId = "VIRTUAL_" + tripId + "_" + seatNumber;
    Reservation virtualReservation = new Reservation(virtualReservationId, this);
    virtualReservation.addPassenger(virtualPassenger, seatNumber);
    virtualReservation.calculateTotalPrice();

    reservations.add(virtualReservation);
  }

  // ✅ @Override metotlar şimdi sınıf içinde
  @Override
  public boolean isSeatAvailable(int seatNumber) {
    return seatNumber >= 0 && seatNumber < seatStatus.length && !seatStatus[seatNumber];
  }

  @Override
  public void reserveSeat(int seatNumber) {
    if (isSeatAvailable(seatNumber)) {
      seatStatus[seatNumber] = true;
    }
  }

  @Override
  public void cancelReservation(int seatNumber) {
    if (seatNumber >= 0 && seatNumber < seatStatus.length) {
      seatStatus[seatNumber] = false;
    }
  }

  @Override
  public int getAvailableSeats() {
    int count = 0;
    for (boolean seat : seatStatus) {
      if (!seat) count++;
    }
    return count;
  }

  @Override
  public boolean[] getSeatStatus() {
    return seatStatus.clone();
  }

  public void addReservation(Reservation reservation) {
    reservations.add(reservation);
  }

  public void displaySeatMap() {
    System.out.println("\n------ Koltuk Haritası ------");
    for (int i = 0; i < seatStatus.length; i++) {
      String status = seatStatus[i] ? "🔴" : "🟢";
      System.out.print("Koltuk " + String.format("%2d", i + 1) + ": " + status + " ");
      if ((i + 1) % 4 == 0) {
        System.out.println();
      }
    }
    System.out.println("\n🟢 = Boş, 🔴 = Dolu");
    System.out.println("Toplam Boş Koltuk: " + getAvailableSeats() + "/" + seatStatus.length);
  }

  // Getters
  public String getTripId() { return tripId; }
  public Vehicle getVehicle() { return vehicle; }
  public Route getRoute() { return route; }
  public LocalDate getDate() { return date; }
  public double getPrice() { return price; }
  public ArrayList<Reservation> getReservations() { return reservations; }
}
