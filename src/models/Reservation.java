package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Reservation {
    private String reservationId;
    private Trip trip;
    private Map<Passenger, Integer> passengerSeatMap = new HashMap<>();
    private double totalPrice;
    private LocalDateTime reservationDate;
    private PaymentStatus paymentStatus;
    private String pnr; // Passenger Name Record

    // Ödeme durumları
    public enum PaymentStatus {
        PENDING,    // Beklemede
        COMPLETED,  // Tamamlandı
        CANCELLED   // İptal edildi
    }

    public Reservation(String reservationId, Trip trip) {
        this.reservationId = reservationId;
        this.trip = trip;
        this.reservationDate = LocalDateTime.now();
        this.paymentStatus = PaymentStatus.PENDING;
        this.pnr = generatePNR();
    }

    /**
     * Yolcu ve koltuk eşleştirmesi ekler
     */
    public void addPassenger(Passenger passenger, int seatNumber) {
        passengerSeatMap.put(passenger, seatNumber);
    }

    /**
     * Toplam fiyatı hesaplar
     */
    public void calculateTotalPrice() {
        this.totalPrice = passengerSeatMap.size() * trip.getPrice();
    }

    /**
     * Ödeme simülasyonu yapar
     */
    public boolean processPayment() {
        System.out.println("\n💳 Ödeme İşlemi Başlatılıyor...");
        System.out.println("💰 Toplam Tutar: " + totalPrice + " TL");
        System.out.println("🏦 Banka ile iletişim kuruluyor...");

        // Simülasyon için kısa bekleme
        try {
            Thread.sleep(2000); // 2 saniye bekle
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // %95 başarı oranı simülasyonu
        boolean paymentSuccess = Math.random() > 0.05;

        if (paymentSuccess) {
            this.paymentStatus = PaymentStatus.COMPLETED;

            // Koltukları kalıcı olarak rezerve et
            for (int seatNumber : passengerSeatMap.values()) {
                trip.reserveSeat(seatNumber);
            }

            // Trip'e rezervasyonu ekle
            trip.addReservation(this);

            System.out.println("✅ Ödeme başarılı!");
            return true;
        } else {
            this.paymentStatus = PaymentStatus.CANCELLED;
            System.out.println("❌ Ödeme başarısız! Lütfen tekrar deneyin.");
            return false;
        }
    }

    /**
     * Bilet bilgilerini yazdırır
     */
    public void printTicket() {
        if (paymentStatus != PaymentStatus.COMPLETED) {
            System.out.println("❌ Ödeme tamamlanmadığı için bilet yazdırılamıyor!");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎫                    ELEKTRONİK BİLET                    🎫");
        System.out.println("=".repeat(60));

        // Rezervasyon bilgileri
        System.out.println("📋 REZERVASYON BİLGİLERİ");
        System.out.println("-".repeat(30));
        System.out.println("PNR Kodu        : " + pnr);
        System.out.println("Rezervasyon No  : " + reservationId);
        System.out.println("Rezervasyon Tar.: " + reservationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

        // Sefer bilgileri
        System.out.println("\n🚌 SEFER BİLGİLERİ");
        System.out.println("-".repeat(30));
        System.out.println("Güzergah        : " + trip.getRoute().getDeparture() + " → " + trip.getRoute().getArrival());
        System.out.println("Seyahat Tarihi  : " + trip.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        System.out.println("Araç Tipi       : " + trip.getVehicle().getClass().getSimpleName());
        System.out.println("Araç ID         : " + trip.getVehicle().getId());
        System.out.println("Mesafe          : " + trip.getRoute().getDistance() + " km");

        // Yolcu bilgileri
        System.out.println("\n👥 YOLCU BİLGİLERİ");
        System.out.println("-".repeat(30));
        int passengerCount = 1;
        for (Map.Entry<Passenger, Integer> entry : passengerSeatMap.entrySet()) {
            Passenger passenger = entry.getKey();
            int seatNumber = entry.getValue();

            System.out.println("Yolcu " + passengerCount + ":");
            System.out.println("  Ad Soyad      : " + passenger.getFirstName() + " " + passenger.getLastName());
            System.out.println("  TC Kimlik No  : " + passenger.getTcNumber());
            System.out.println("  Doğum Tarihi  : " + passenger.getBirthDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            System.out.println("  Koltuk No     : " + (seatNumber + 1));
            System.out.println();
            passengerCount++;
        }

        // Ödeme bilgileri
        System.out.println("💰 ÖDEME BİLGİLERİ");
        System.out.println("-".repeat(30));
        System.out.println("Bilet Fiyatı    : " + trip.getPrice() + " TL/kişi");
        System.out.println("Yolcu Sayısı    : " + passengerSeatMap.size() + " kişi");
        System.out.println("Toplam Tutar    : " + totalPrice + " TL");
        System.out.println("Ödeme Durumu    : ✅ ÖDENMIŞ");

        // Koltuk haritası
        System.out.println("\n🪑 KOLTUK HARİTASI");
        System.out.println("-".repeat(30));
        boolean[] seatStatus = trip.getSeatStatus();
        System.out.print("Seçilen Koltuklar: ");
        for (int seatNumber : passengerSeatMap.values()) {
            System.out.print((seatNumber + 1) + " ");
        }
        System.out.println();

        // QR kod simülasyonu
        System.out.println("\n📱 DIJITAL KONTROL");
        System.out.println("-".repeat(30));
        System.out.println("QR Kod: [██ ▄▄ ██ ▄▄ ██]");
        System.out.println("Bu kodu seyahat sırasında hazır bulundurun.");

        // Önemli notlar
        System.out.println("\n⚠️  ÖNEMLİ NOTLAR");
        System.out.println("-".repeat(30));
        System.out.println("• Seyahat öncesi kimlik belgenizi hazır bulundurun");
        System.out.println("• Sefer saati: Lütfen 30 dk önceden hazır olun");
        System.out.println("• İptal koşulları için müşteri hizmetleri: 444-0-SEFER");
        System.out.println("• Bu bilet başkasına devredilemez");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("           İyi yolculuklar dileriz! 🌟");
        System.out.println("=".repeat(60));
    }

    /**
     * PNR (Passenger Name Record) kodu oluşturur
     */
    private String generatePNR() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder pnr = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            pnr.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return pnr.toString();
    }

    /**
     * Rezervasyon iptal eder
     */
    public boolean cancelReservation() {
        if (paymentStatus == PaymentStatus.COMPLETED) {
            // Koltukları serbest bırak
            for (int seatNumber : passengerSeatMap.values()) {
                trip.cancelReservation(seatNumber);
            }

            this.paymentStatus = PaymentStatus.CANCELLED;
            System.out.println("✅ Rezervasyon iptal edildi. PNR: " + pnr);
            return true;
        } else {
            System.out.println("❌ Sadece ödenmiş rezervasyonlar iptal edilebilir!");
            return false;
        }
    }

    /**
     * Rezervasyon özeti gösterir
     */
    public void showReservationSummary() {
        System.out.println("\n📄 REZERVASYON ÖZETİ");
        System.out.println("=".repeat(40));
        System.out.println("PNR Kodu: " + pnr);
        System.out.println("Durum: " + getPaymentStatusText());
        System.out.println("Güzergah: " + trip.getRoute().getDeparture() + " → " + trip.getRoute().getArrival());
        System.out.println("Tarih: " + trip.getDate());
        System.out.println("Yolcu Sayısı: " + passengerSeatMap.size());
        System.out.println("Toplam Tutar: " + totalPrice + " TL");
        System.out.print("Koltuklar: ");
        for (int seat : passengerSeatMap.values()) {
            System.out.print((seat + 1) + " ");
        }
        System.out.println("\n" + "=".repeat(40));
    }

    /**
     * Ödeme durumunu Türkçe metne çevirir
     */
    private String getPaymentStatusText() {
        switch (paymentStatus) {
            case PENDING: return "⏳ Ödeme Beklemede";
            case COMPLETED: return "✅ Ödeme Tamamlandı";
            case CANCELLED: return "❌ İptal Edildi";
            default: return "❓ Bilinmeyen Durum";
        }
    }

    // Getter ve Setter metotları
    public String getReservationId() { return reservationId; }
    public Trip getTrip() { return trip; }
    public Map<Passenger, Integer> getPassengerSeatMap() { return passengerSeatMap; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getReservationDate() { return reservationDate; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public String getPnr() { return pnr; }

    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}