package models;

import interfaces.IProfitable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Company extends User implements IProfitable
{
   private String name;
   private double fuelCostPerKm;
   private ArrayList<Vehicle> vehicles = new ArrayList<>();
   private ArrayList<Trip> trips = new ArrayList<>();
   private double totalProfit = 0;

public Company (String name, String username, String password, double fuelCostPerKm){
   super(username,password);
   this.name = name;
   this.fuelCostPerKm = fuelCostPerKm;
}

public double getFuelCostPerKm(){
   return fuelCostPerKm;
}

public void addVehicle(Vehicle v){
   v.setCompany(this);
   vehicles.add(v);
}

public ArrayList<Vehicle> getVehicles(){
   return vehicles;
}

public void addTrip(Trip t){
  trips.add(t);
}

   public String getName () {
      return name;
   }

   public ArrayList<Trip> getTrips () {
      return trips;
   }

   @Override
   public boolean login (String username, String password){
      return this.username.equals(username) &&
              this.password.equals(password);
   }

   @Override
   public double calculateTotalProfit() {
      return totalProfit;
   }

   public double calculateDailyProfit(double serviceFee) {
      Scanner sc = new Scanner(System.in);
      System.out.print("Hangi tarih için kar hesabı yapılsın? (YYYY-MM-DD): ");
      String dateStr = sc.nextLine();

      try {
         LocalDate targetDate = LocalDate.parse(dateStr);
         return calculateDailyProfit(targetDate, serviceFee);
      } catch (Exception e) {
         System.out.println("Geçersiz tarih formatı!");
         return 0;
      }
   }

   public double calculateDailyProfit(LocalDate targetDate, double serviceFee) {
      double dailyRevenue = 0;
      double dailyCosts = 0;

      System.out.println("\n📊 " + name + " Firması - " + targetDate + " Kar Analizi");
      System.out.println("=".repeat(60));

      // O günün seferlerini filtrele
      ArrayList<Trip> dailyTrips = new ArrayList<>();
      for (Trip trip : trips) {
         if (trip.getDate().equals(targetDate)) {
            dailyTrips.add(trip);
         }
      }

      if (dailyTrips.isEmpty()) {
         System.out.println("❌ Bu tarihte sefer bulunamadı.");
         return 0;
      }

      // Her sefer için hesaplama
      for (Trip trip : dailyTrips) {
         System.out.println("\n🚌 Sefer: " + trip.getRoute() + " | Araç: " + trip.getVehicle().getId());

         // Gelir: Dolu koltuk sayısı × bilet fiyatı
         int occupiedSeats = trip.getVehicle().getCapacity() - trip.getAvailableSeats();
         double tripRevenue = occupiedSeats * trip.getPrice();
         dailyRevenue += tripRevenue;
         System.out.println("💰 Gelir: " + occupiedSeats + " yolcu × " + trip.getPrice() + " TL = " + tripRevenue + " TL");

         // Giderler
         double distance = trip.getRoute().getDistance();

         // 1️⃣ Yakıt maliyeti
         double fuelCost = trip.getVehicle().calculateFuelCost(distance);
         dailyCosts += fuelCost;
         System.out.println("⛽ Yakıt: " + distance + " km × " + fuelCostPerKm + " TL/km = " + fuelCost + " TL");

         // 2️⃣ Personel maliyeti (firma bazlı)
         double personnelCost = getPersonnelCostPerTrip();
         dailyCosts += personnelCost;
         System.out.println("👥 Personel: " + personnelCost + " TL");

         // 3️⃣ Hizmet bedeli
         dailyCosts += serviceFee;
         System.out.println("🏢 Hizmet Bedeli: " + serviceFee + " TL");

         // Sefer karı
         double tripProfit = tripRevenue - (fuelCost + personnelCost + serviceFee);
         System.out.println("📈 Sefer Karı: " + tripProfit + " TL");
      }

      double dailyProfit = dailyRevenue - dailyCosts;
      totalProfit += dailyProfit;

      // Günlük özet
      System.out.println("\n📋 GÜNLÜK ÖZET");
      System.out.println("💰 Toplam Gelir: " + dailyRevenue + " TL");
      System.out.println("💸 Toplam Gider: " + dailyCosts + " TL");
      System.out.println("📊 NET KAR/ZARAR: " + dailyProfit + " TL");
      System.out.println("=".repeat(50));

      return dailyProfit;
   }


   // Personel maliyetini hesaplayan yardımcı metod
   private double getPersonnelCostPerTrip() {
      // Projenizin tablosuna göre firma bazlı personel ücretleri
      switch (this.name) {
         case "A":
            return 5000 + 2000; // Kullanan + Hizmet personeli
         case "B":
            return 3000 + 1500;
         case "C":
            // C firması hem otobüs hem uçak kullanıyor, ortalama alalım
            return 7000 + 4000; // (4000+10000)/2 + (2000+6000)/2
         case "D":
            return 2000 + 1000;
         case "F":
            return 7500 + 4000;
         default:
            return 3000 + 1500; // Varsayılan
      }

   }}