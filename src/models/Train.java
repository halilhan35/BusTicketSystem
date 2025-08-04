// Train.java
package models;

public class Train extends Vehicle {
  public Train(String id, int capacity, String fuelType) {
    super(id, capacity, fuelType);
  }

  @Override
  public double calculateFuelCost(double distance) {
    // Company'den yakıt maliyetini al
    if (company != null) {
      return distance * company.getFuelCostPerKm();
    }
    return distance * 3; // Elektrik için varsayılan
  }
}
