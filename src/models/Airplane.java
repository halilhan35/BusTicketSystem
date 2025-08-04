package models;

public class Airplane extends Vehicle {
    public Airplane(String id, int capacity, String fuelType) {
        super(id, capacity, fuelType);
    }

    @Override
    public double calculateFuelCost(double distance) {
        // Company'den yakıt maliyetini al
        if (company != null) {
            return distance * company.getFuelCostPerKm();
        }
        return distance * 22; // Gaz için varsayılan (yüksek)
    }
}
