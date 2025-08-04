import models.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {


        System.out.println("Rezervasyon Sistemi Baslatılıyor...");

        //isim listesini yükle
        NameGenerator.loadNames();

        Admin admin = new Admin("admin", "1234");
        ArrayList<Company> companies = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.print("Kullanıcı Adı: ");
        String username = sc.nextLine();
        System.out.print("Şifre: ");
        String password = sc.nextLine();

        if (admin.login(username, password)) {
            System.out.println("Admin girişi başarılı!");
            boolean running = true;

            while (running) {
                System.out.println("\n1- Firma Listele\n2- Firma Ekle\n3- Firma Sil\n4- Hizmet Bedeli Ayarla\n" +
                        "5- Firma Paneline Geç\n6- Kullanıcı Paneli\n0- Çıkış");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> { // Firma Listeleme
                        if (companies.isEmpty()) System.out.println("Kayıtlı firma yok");
                        else companies.forEach(c -> System.out.println(c.getName()));
                    }

                    case 2 -> { // Firma Ekleme
                        System.out.print("Firma Adı: ");
                        String name = sc.nextLine();
                        System.out.print("Kullanıcı Adı: ");
                        String compUser = sc.nextLine();
                        System.out.print("Şifre: ");
                        String compPass = sc.nextLine();
                        System.out.print("Yakıt km ücreti: ");
                        double fuelCost = sc.nextDouble();
                        sc.nextLine();
                        companies.add(new Company(name, compUser, compPass, fuelCost));
                        System.out.println("Firma eklendi: " + name);
                    }

                    case 3 -> { // Firma Silme
                        System.out.print("Silinecek Firma Adı: ");
                        String delName = sc.nextLine();
                        companies.removeIf(c -> c.getName().equalsIgnoreCase(delName));
                        System.out.println("Firma silindi: " + delName);
                    }

                    case 4 -> { // Hizmet Bedeli Ayarı
                        System.out.print("Yeni hizmet bedeli: ");
                        double fee = sc.nextDouble();
                        admin.setServiceFee(fee);
                        System.out.println("Hizmet bedeli güncellendi: " + fee + " TL");
                    }

                    case 5 -> { // Firma Paneli
                        if (companies.isEmpty()) {
                            System.out.println("Henüz firma yok.");
                            break;
                        }

                        System.out.print("Firma kullanıcı adı: ");
                        String compUser = sc.nextLine();
                        System.out.print("Şifre: ");
                        String compPass = sc.nextLine();

                        Company loggedCompany = null;
                        for (Company c : companies) {
                            if (c.login(compUser, compPass)) {
                                loggedCompany = c;
                                break;
                            }
                        }

                        if (loggedCompany != null) {
                            System.out.println("Firma girişi başarılı: " + loggedCompany.getName());
                            boolean firmMenu = true;

                            while (firmMenu) {
                                System.out.println("\n------ Firma Paneli ------");
                                System.out.println("1- Araç Ekle");
                                System.out.println("2- Sefer Ekle");
                                System.out.println("3- Günlük Kar Hesapla");
                                System.out.println("0- Çıkış");
                                int ch = sc.nextInt();
                                sc.nextLine();

                                switch (ch) {
                                    case 1 -> { // Araç Ekle
                                        System.out.println("Araç Türü Seçin:\n1- Otobüs\n2- Tren\n3- Uçak");
                                        int vehicleChoice = sc.nextInt();
                                        sc.nextLine();

                                        System.out.print("Araç ID: ");
                                        String id = sc.nextLine();
                                        System.out.print("Kapasite: ");
                                        int cap = sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Yakıt Türü: ");
                                        String fuel = sc.nextLine();

                                        switch (vehicleChoice) {
                                            case 1 -> loggedCompany.addVehicle(new Bus(id, cap, fuel));
                                            case 2 -> loggedCompany.addVehicle(new Train(id, cap, fuel));
                                            case 3 -> loggedCompany.addVehicle(new Airplane(id, cap, fuel));
                                            default -> System.out.println("Geçersiz seçim!");
                                        }
                                        System.out.println("Araç başarıyla eklendi!");
                                    }

                                    case 2 -> {
                                        System.out.println("Sefer ekleme kısmı burada olacak (Trip sınıfı ile).");
                                        // Bu kısmı 4. adımda detaylandıracağız.
                                    }

                                    case 3 -> {
                                        System.out.print("Hizmet bedeli girin: ");
                                        double serviceFee = sc.nextDouble();
                                        sc.nextLine(); // Buffer temizleme
                                        System.out.println("Günlük kar: " + loggedCompany.calculateDailyProfit(serviceFee) + " TL");
                                    }

                                    case 0 -> firmMenu = false;
                                }
                            }
                        } else {
                            System.out.println("Hatalı firma girişi!");
                        }
                    }

                    case 6 ->{
                        System.out.println("\n-----Kullanıcı Paneli----\n");
                        System.out.println("🎫 Bilet Arama Sistemi");

                        ArrayList<Trip> allTrips = new ArrayList<>();
                        for (Company company : companies) {
                            allTrips.addAll(company.getTrips());
                        }

                        if (allTrips.isEmpty()) {
                            System.out.println("❌ Henüz tanımlanmış sefer yok.");
                            break;
                        }

                        // Kullanıcı girişleri
                        System.out.print("📍 Kalkış Noktası: ");
                        String departure = sc.nextLine();

                        System.out.print("📍 Varış Noktası: ");
                        String arrival = sc.nextLine();

                        // Tarih seçimi
                        System.out.println("\n📅 Mevcut Tarihler (4-10 Aralık 2023):");
                        System.out.println("1- 4 Aralık 2023   2- 5 Aralık 2023   3- 6 Aralık 2023");
                        System.out.println("4- 7 Aralık 2023   5- 8 Aralık 2023   6- 9 Aralık 2023");
                        System.out.println("7- 10 Aralık 2023");
                        System.out.print("Tarih seçin (1-7): ");

                        int dateChoice = sc.nextInt();
                        sc.nextLine();

                        if (dateChoice < 1 || dateChoice > 7) {
                            System.out.println("❌ Geçersiz tarih seçimi!");
                            break;
                        }

                        LocalDate selectedDate = LocalDate.of(2023, 12, 3 + dateChoice);

                        System.out.print("👥 Yolcu Sayısı: ");
                        int passengerCount = sc.nextInt();
                        sc.nextLine();

                        if (passengerCount <= 0) {
                            System.out.println("❌ Geçersiz yolcu sayısı!");
                            break;
                        }

                        // Uygun seferleri bul
                        ArrayList<Trip> suitableTrips = new ArrayList<>();
                        for (Trip trip : allTrips) {
                            if (trip.getRoute().getDeparture().equalsIgnoreCase(departure) &&
                                    trip.getRoute().getArrival().equalsIgnoreCase(arrival) &&
                                    trip.getDate().equals(selectedDate) &&
                                    trip.getAvailableSeats() >= passengerCount) {
                                suitableTrips.add(trip);
                            }
                        }

                        if (suitableTrips.isEmpty()) {
                            System.out.println("❌ Uygun sefer bulunamadı.");
                            System.out.println("💡 Farklı tarih veya güzergah deneyiniz.");
                            break;
                        }

                        // Uygun seferleri listele
                        System.out.println("\n🚌 UYGUN SEFERLER");
                        System.out.println("=".repeat(80));
                        for (int i = 0; i < suitableTrips.size(); i++) {
                            Trip currentTrip = suitableTrips.get(i);
                            System.out.printf("%d- %s → %s | Araç: %s | Fiyat: %.2f TL | Boş Koltuk: %d/%d%n",
                                    (i + 1),
                                    currentTrip.getRoute().getDeparture(),
                                    currentTrip.getRoute().getArrival(),
                                    currentTrip.getVehicle().getId(),
                                    currentTrip.getPrice(),
                                    currentTrip.getAvailableSeats(),
                                    currentTrip.getVehicle().getCapacity()
                            );
                        }

                        // Sefer seçimi
                        System.out.print("\n🎯 Sefer seçin (1-" + suitableTrips.size() + "): ");
                        int tripChoice = sc.nextInt() - 1;
                        sc.nextLine();

                        if (tripChoice < 0 || tripChoice >= suitableTrips.size()) {
                            System.out.println("❌ Geçersiz sefer seçimi!");
                            break;
                        }

                        Trip selectedTrip = suitableTrips.get(tripChoice);

                        // Koltuk durumunu göster
                        System.out.println("\n🪑 KOLTUK HARİTASI - " + selectedTrip.getVehicle().getId());
                        selectedTrip.displaySeatMap();

                        // Koltuk seçimi
                        ArrayList<Integer> selectedSeats = new ArrayList<>();
                        System.out.println("\n🎯 Koltuk Seçimi");

                        for (int i = 0; i < passengerCount; i++) {
                            boolean validSeat = false;
                            while (!validSeat) {
                                System.out.print((i + 1) + ". yolcu için koltuk numarası (1-" +
                                        selectedTrip.getVehicle().getCapacity() + "): ");
                                int seatNum = sc.nextInt() - 1;
                                sc.nextLine();

                                if (selectedTrip.isSeatAvailable(seatNum)) {
                                    if (!selectedSeats.contains(seatNum)) {
                                        selectedSeats.add(seatNum);
                                        validSeat = true;
                                        System.out.println("✅ Koltuk " + (seatNum + 1) + " seçildi.");
                                    } else {
                                        System.out.println("❌ Bu koltuğu zaten seçtiniz! Başka koltuk seçin.");
                                    }
                                } else {
                                    System.out.println("❌ Bu koltuk dolu veya geçersiz! Başka koltuk seçin.");
                                }
                            }
                        }

                        // Yolcu bilgileri alma
                        ArrayList<Passenger> passengers = new ArrayList<>();
                        System.out.println("\n👤 YOLCU BİLGİLERİ");
                        System.out.println("=".repeat(50));

                        for (int i = 0; i < passengerCount; i++) {
                            System.out.println("\n" + (i + 1) + ". Yolcu Bilgileri (Koltuk: " + (selectedSeats.get(i) + 1) + "):");

                            System.out.print("Ad: ");
                            String firstName = sc.nextLine();

                            System.out.print("Soyad: ");
                            String lastName = sc.nextLine();

                            System.out.print("TC Kimlik No (11 haneli): ");
                            String tcNo = sc.nextLine();

                            // TC kontrol
                            while (tcNo.length() != 11 || !tcNo.matches("\\d+")) {
                                System.out.print("❌ Geçersiz TC! 11 haneli TC Kimlik No girin: ");
                                tcNo = sc.nextLine();
                            }

                            System.out.print("Doğum Tarihi (YYYY-MM-DD): ");
                            String birthDateStr = sc.nextLine();

                            try {
                                LocalDate birthDate = LocalDate.parse(birthDateStr);
                                passengers.add(new Passenger(firstName, lastName, tcNo, birthDate));
                                System.out.println("✅ " + firstName + " " + lastName + " eklendi.");
                            } catch (Exception e) {
                                System.out.println("❌ Geçersiz tarih formatı! Varsayılan tarih atanıyor.");
                                passengers.add(new Passenger(firstName, lastName, tcNo, LocalDate.of(1990, 1, 1)));
                            }
                        }

                        // Rezervasyon oluştur
                        String reservationId = "RES" + System.currentTimeMillis();
                        Reservation reservation = new Reservation(reservationId, selectedTrip);

                        // Yolcuları ve koltukları eşleştir
                        for (int i = 0; i < passengers.size(); i++) {
                            reservation.addPassenger(passengers.get(i), selectedSeats.get(i));
                        }

                        // Fiyat hesapla
                        reservation.calculateTotalPrice();

                        // Rezervasyon özeti göster
                        reservation.showReservationSummary();

                        // Ödeme onayı
                        System.out.println("\n💳 ÖDEME İŞLEMİ");
                        System.out.println("=".repeat(40));
                        System.out.println("Toplam Ödenecek Tutar: " + reservation.getTotalPrice() + " TL");
                        System.out.print("Ödeme yapmak istiyor musunuz? (E/H): ");
                        String paymentConfirm = sc.nextLine().toUpperCase();

                        if (paymentConfirm.equals("E") || paymentConfirm.equals("EVET")) {
                            System.out.println("\n" + "=".repeat(50));

                            // Ödeme işlemi
                            boolean paymentSuccess = reservation.processPayment();

                            if (paymentSuccess) {
                                System.out.println("\n🎉 REZERVASYON BAŞARILI!");
                                System.out.println("=".repeat(50));

                                // Bilet yazdır
                                reservation.printTicket();

                                // Kullanıcıya seçenekler sun
                                System.out.println("\n📋 Diğer İşlemler:");
                                System.out.println("1- Ana menüye dön");
                                System.out.println("2- Yeni rezervasyon yap");
                                System.out.println("3- Çıkış");
                                System.out.print("Seçiminiz: ");

                                int nextAction = sc.nextInt();
                                sc.nextLine();

                                switch (nextAction) {
                                    case 1:
                                        // Ana menüye dön (döngü devam edecek)
                                        break;
                                    case 2:
                                        // Kullanıcı paneline tekrar git (recursive olmaması için break)
                                        break;
                                    case 3:
                                        running = false;
                                        System.out.println("👋 İyi günler dileriz!");
                                        break;
                                    default:
                                        System.out.println("Ana menüye dönülüyor...");
                                }

                            } else {
                                System.out.println("\n💔 ÖDEME BAŞARISIZ");
                                System.out.println("=".repeat(30));
                                System.out.println("❌ Ödeme işlemi gerçekleştirilemedi.");
                                System.out.println("💡 Lütfen kart bilgilerinizi kontrol edin ve tekrar deneyin.");
                                System.out.println("📞 Yardım için: 444-0-SEFER");
                            }

                        } else {
                            System.out.println("\n❌ REZERVASYON İPTAL EDİLDİ");
                            System.out.println("Seçilen koltuklar serbest bırakıldı.");
                            System.out.println("💡 Rezervasyon yapmak için tekrar deneyebilirsiniz.");
                        }
                    }

                    case 0 -> running = false;
                }
            }
        } else {
            System.out.println("Hatalı giriş!");
        }
    }
}
