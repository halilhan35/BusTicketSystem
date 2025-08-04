package models;

public abstract class Vehicle {
    protected String id;
    protected int capacity;
    protected String fuelType;
    protected Company company;


    public Vehicle(String id, int capacity, String fuelType) {
        this.id = id;
        this.capacity = capacity;
        this.fuelType = fuelType;
    }

    public void setCompany(Company company){
        this.company = company;
    }

    public abstract double calculateFuelCost(double instance);

    public String getId(){return id;}
    public int getCapacity(){return capacity;}
    public String getFuelType(){return fuelType;}
}