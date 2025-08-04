package models;

/**
 * Kişi bilgilerini tutan abstract sınıf
 * Passenger ve Personnel sınıfları bu sınıftan türetilir
 */
public abstract class Person {
    protected String firstName;  // Ad
    protected String lastName;   // Soyad

    /**
     * Person constructor
     * @param firstName Ad
     * @param lastName Soyad
     */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Tam adı döndürür
     * @return Ad + Soyad
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * İsim baş harflerini döndürür
     * @return Örn: "Ahmet Yılmaz" -> "A.Y."
     */
    public String getInitials() {
        String firstInitial = firstName != null && !firstName.isEmpty() ?
                firstName.substring(0, 1).toUpperCase() : "";
        String lastInitial = lastName != null && !lastName.isEmpty() ?
                lastName.substring(0, 1).toUpperCase() : "";
        return firstInitial + "." + lastInitial + ".";
    }

    /**
     * İsmin formatlanmış halini döndürür (İlk harfler büyük)
     * @return Formatlanmış tam isim
     */
    public String getFormattedFullName() {
        return capitalizeFirstLetter(firstName) + " " + capitalizeFirstLetter(lastName);
    }

    /**
     * String'in ilk harfini büyük yapar
     * @param str Düzenlenecek string
     * @return Düzenlenmiş string
     */
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * İsmin geçerli olup olmadığını kontrol eder
     * @return Geçerliyse true
     */
    public boolean isValidName() {
        return firstName != null && !firstName.trim().isEmpty() &&
                lastName != null && !lastName.trim().isEmpty() &&
                firstName.length() >= 2 && lastName.length() >= 2;
    }

    // Getter ve Setter metotları
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person person = (Person) obj;
        return firstName.equals(person.firstName) && lastName.equals(person.lastName);
    }

    @Override
    public int hashCode() {
        return (firstName + lastName).hashCode();
    }
}
