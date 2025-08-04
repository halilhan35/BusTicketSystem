package models;

public class Bus extends Vehicle {
  public Bus(String id, int capacity, String fuelType) {
    super(id, capacity, fuelType);
  }

  @Override
  public double calculateFuelCost(double distance) {
    // Company'den yakıt maliyetini al
    if (company != null) {
      return distance * company.getFuelCostPerKm();
    }
    return distance * 8; // Varsayılan (Benzin/Motorin ortalaması)
  }
}
