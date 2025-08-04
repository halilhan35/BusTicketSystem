package models;

public class Route {
    private String departure;
    private String arrival;
    private double distance;

    public Route(String departure, String arrival, double distance) {
        this.departure = departure;
        this.arrival = arrival;
        this.distance = distance;
    }

    public String getDeparture() { return departure; }
    public String getArrival() { return arrival; }
    public double getDistance() { return distance; }

    @Override
    public String toString() {
        return departure + " -> " + arrival + " (" + distance + " km)";
    }
}