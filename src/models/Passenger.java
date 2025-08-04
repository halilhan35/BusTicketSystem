package models;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Yolcu bilgilerini tutan sınıf
 * Person sınıfından türetilir
 */
public class Passenger extends Person {
    private String tcNumber;      // TC Kimlik numarası
    private LocalDate birthDate;  // Doğum tarihi
    private int age;              // Yaş (otomatik hesaplanır)

    /**
     * Passenger constructor
     * @param firstName Ad
     * @param lastName Soyad
     * @param tcNumber TC Kimlik numarası
     * @param birthDate Doğum tarihi
     */
    public Passenger(String firstName, String lastName, String tcNumber, LocalDate birthDate) {
        super(firstName, lastName);
        this.tcNumber = tcNumber;
        this.birthDate = birthDate;
        this.age = calculateAge();
    }

    /**
     * Yaşı hesaplar
     * @return Güncel yaş
     */
    private int calculateAge() {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * TC numarasını valide eder (basit kontrol)
     * @return Geçerliyse true
     */
    public boolean isValidTcNumber() {
        // Basit TC kontrol: 11 haneli ve sadece rakam
        return tcNumber != null &&
                tcNumber.length() == 11 &&
                tcNumber.matches("\\d+");
    }

    /**
     * Yolcunun çocuk olup olmadığını kontrol eder
     * @return 12 yaş altıysa true
     */
    public boolean isChild() {
        return age < 12;
    }

    /**
     * Yolcunun yaşlı olup olmadığını kontrol eder
     * @return 65 yaş üstüyse true
     */
    public boolean isSenior() {
        return age >= 65;
    }

    /**
     * Yolcu bilgilerini formatlanmış string olarak döndürür
     * @return Formatlanmış yolcu bilgisi
     */
    public String getFormattedInfo() {
        return String.format("%s %s (TC: %s, Yaş: %d)",
                getFirstName(),
                getLastName(),
                tcNumber,
                age);
    }

    /**
     * Doğum tarihini formatlanmış string olarak döndürür
     * @return Formatlanmış doğum tarihi
     */
    public String getFormattedBirthDate() {
        if (birthDate == null) return "Belirtilmemiş";
        return birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    /**
     * Yolcu tipini döndürür (Çocuk/Yetişkin/Yaşlı)
     * @return Yolcu tipi
     */
    public String getPassengerType() {
        if (isChild()) return "Çocuk";
        if (isSenior()) return "Yaşlı";
        return "Yetişkin";
    }

    // Getter ve Setter metotları
    public String getTcNumber() {
        return tcNumber;
    }

    public void setTcNumber(String tcNumber) {
        this.tcNumber = tcNumber;
        // TC değiştirildiğinde yaşı tekrar hesapla
        this.age = calculateAge();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        // Doğum tarihi değiştirildiğinde yaşı tekrar hesapla
        this.age = calculateAge();
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return getFormattedInfo();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Passenger passenger = (Passenger) obj;
        return tcNumber != null ? tcNumber.equals(passenger.tcNumber) : passenger.tcNumber == null;
    }

    @Override
    public int hashCode() {
        return tcNumber != null ? tcNumber.hashCode() : 0;
    }}