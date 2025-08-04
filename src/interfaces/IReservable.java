package interfaces;

/**
 * Rezervasyon yapılabilir nesneler için arayüz
 * Trip sınıfı tarafından implement edilir
 */
public interface IReservable {

    /**
     * Belirtilen koltuğun müsait olup olmadığını kontrol eder
     * @param seatNumber Koltuk numarası (0'dan başlar)
     * @return Koltuk müsaitse true, değilse false
     */
    boolean isSeatAvailable(int seatNumber);

    /**
     * Belirtilen koltuğu rezerve eder
     * @param seatNumber Rezerve edilecek koltuk numarası
     */
    void reserveSeat(int seatNumber);

    /**
     * Belirtilen koltuk rezervasyonunu iptal eder
     * @param seatNumber İptal edilecek koltuk numarası
     */
    void cancelReservation(int seatNumber);

    /**
     * Toplam boş koltuk sayısını döndürür
     * @return Boş koltuk sayısı
     */
    int getAvailableSeats();

    /**
     * Tüm koltukların durumunu döndürür
     * @return boolean array (true = dolu, false = boş)
     */
    boolean[] getSeatStatus();
}